package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connection;

    public SqlHelper(ConnectionFactory connection) {
        this.connection = connection;
    }

    public <T> T execute(String request, Executor<T> executor) {
        try (Connection conn = connection.getConnection();
             PreparedStatement ps = conn.prepareStatement(request)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(e);
            }
            throw new StorageException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connection.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw  new ExistStorageException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
