package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper helper;


    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("No suitable driver found for jdbc");
        }
        helper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        helper.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        helper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid = ?")) {
                String uuid = r.getUuid();
                ps.setString(1, r.getFullName());
                ps.setString(2, uuid);
                executeUpdate(ps, uuid);
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }
            insertContacts(conn, r);
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid = ?")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }
            insertSimpleSections(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        helper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContacts(conn, r);
            insertSimpleSections(conn, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.transactionalExecute(conn -> {
            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume" +
                    " LEFT JOIN contact ON resume.uuid = contact.resume_uuid" +
                    " WHERE uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
                do {
                    addContact(rs, resume);
                } while (rs.next());
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSimpleSection(rs, resume);
                }
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        helper.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            executeUpdate(ps, uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.transactionalExecute(conn -> {
            Map<String, Resume> result = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    result.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT type, value FROM contact where resume_uuid = ?")) {
                for (Map.Entry<String, Resume> entry : result.entrySet()) {
                    ps.setString(1, entry.getKey());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        addContact(rs, entry.getValue());
                    }
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT type, value FROM section where resume_uuid = ?")) {
                for (Map.Entry<String, Resume> entry : result.entrySet()) {
                    ps.setString(1, entry.getKey());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        addSimpleSection(rs, entry.getValue());
                    }
                }
            }
            return new ArrayList<>(result.values());
        });
    }

    @Override
    public int size() {
        return helper.execute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        });
    }

    private void executeUpdate(PreparedStatement ps, String uuid) throws SQLException {
        if (ps.executeUpdate() == 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    private void insertContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (type, value, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ps.setString(1, entry.getKey().name());
                ps.setString(2, entry.getValue());
                ps.setString(3, resume.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSimpleSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (type, value, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : r.getSections().entrySet()) {
                SectionType type = entry.getKey();
                switch (type) {
                    case OBJECTIVE, PERSONAL -> {
                        addRow(ps, type.name(), ((TextSection) entry.getValue()).getContent(), r.getUuid());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        String value = String.join("\n", ((ListSection) entry.getValue()).getItems());
                        addRow(ps, type.name(), value, r.getUuid());
                    }
                }
            }
            ps.executeBatch();
        }
    }

    private void addRow(PreparedStatement ps, String key, String value, String uuid) throws SQLException {
        ps.setString(1, key);
        ps.setString(2, value);
        ps.setString(3, uuid);
        ps.addBatch();
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String typeName = rs.getString("type");
        if (typeName != null) {
            resume.addContact(ContactType.valueOf(typeName), rs.getString("value"));
        }
    }

    private void addSimpleSection(ResultSet rs, Resume resume) throws SQLException {
        String type = rs.getString("type");
        if (type != null) {
            SectionType sectionType = SectionType.valueOf(type);
            switch (sectionType) {
                case OBJECTIVE, PERSONAL -> resume.addSection(sectionType, new TextSection(rs.getString("value")));
                case ACHIEVEMENT, QUALIFICATIONS -> resume.addSection(sectionType, new ListSection(Arrays.asList(rs.getString("value").split("\n"))));
            }
        }
    }
}