package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
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
