package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlHelper<T>{
    T someMethod(PreparedStatement ps) throws SQLException;
}
