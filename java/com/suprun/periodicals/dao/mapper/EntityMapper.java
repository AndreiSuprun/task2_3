package com.suprun.periodicals.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EntityMapper<T> {

    String EMPTY_STRING = "";

    default List<T> mapToObjectList(ResultSet resultSet) throws SQLException {
        return mapToObjectList(resultSet, EMPTY_STRING);
    }

    default List<T> mapToObjectList(ResultSet resultSet, String tablePrefix) throws SQLException {
        List<T> convertedObjects = new ArrayList<>();
        while (resultSet.next()) {
            convertedObjects.add(mapToObject(resultSet, tablePrefix));
        }
        return convertedObjects;
    }

    default T mapToObject(ResultSet resultSet) throws SQLException {
        return mapToObject(resultSet, EMPTY_STRING);
    }

    T mapToObject(ResultSet resultSet, String tablePrefix) throws SQLException;
}
