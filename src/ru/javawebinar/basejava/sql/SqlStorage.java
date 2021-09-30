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
        new SqlHelper(connectionFactory).startExecution("DELETE FROM resume", (Executor<Void>) ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        new SqlHelper(connectionFactory).startExecution("UPDATE resume SET full_name=? WHERE uuid=?", (Executor<Void>) ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            executeUpdate(ps, r.getUuid());
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        new SqlHelper(connectionFactory).startExecution("INSERT INTO resume(uuid, full_name) VALUES (?,?)", (Executor<Void>) ps -> {
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
        return new SqlHelper(connectionFactory).startExecution("SELECT * FROM resume WHERE uuid =?", ps -> {
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
        new SqlHelper(connectionFactory).startExecution("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            executeUpdate(ps, uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = new ArrayList<>();
        new SqlHelper(connectionFactory).startExecution("SELECT * FROM resume ORDER BY full_name, uuid", (Executor<Void>) ps -> {
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
        return new SqlHelper(connectionFactory).startExecution("SELECT COUNT(*) FROM resume", ps -> {
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

    public static class SqlHelper {
        private final ConnectionFactory connection;

        public SqlHelper(ConnectionFactory connection) {
            this.connection = connection;
        }

        public <T> T startExecution(String string, Executor<T> executor) {
            try (Connection conn = connection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(string)) {
                return executor.execute(ps);
            } catch (SQLException e) {
                throw new StorageException(e);
            }
        }
    }
}