package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        execute("DELETE FROM resume", (SqlHelper<Void>) ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        execute("UPDATE resume SET full_name=? WHERE uuid=?", (SqlHelper<Void>) ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            executeUpdate(ps, r.getUuid());
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        execute("INSERT INTO resume(uuid, full_name) VALUES (?,?)", (SqlHelper<Void>) ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            try {
                ps.execute();
            } catch (SQLException e) {
                if (e.getSQLState().equals("23505")) {
                    throw new ExistStorageException(r.getUuid());
                }
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return execute("SELECT * FROM resume WHERE uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            executeUpdate(ps, uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = new ArrayList<>();
        execute("SELECT * FROM resume ORDER BY full_name, uuid", (SqlHelper<Void>) ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return null;
        });
        return result;
    }

    @Override
    public int size() {
        return execute("SELECT COUNT(*) FROM resume", ps -> {
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

    private <T> T execute(String string, SqlHelper<T> sqlHelper) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(string)) {
            return sqlHelper.someMethod(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}