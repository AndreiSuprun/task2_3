package com.suprun.periodicals.util;

import java.util.ResourceBundle;

public enum Resource {

    DATABASE(ResourceBundle.getBundle("properties.database")),
    QUERIES(ResourceBundle.getBundle("properties.sql_queries")),
    PATH(ResourceBundle.getBundle("properties.path")),
    VIEW(ResourceBundle.getBundle("properties.view")),
    ATTRIBUTE(ResourceBundle.getBundle("properties.attribute")),
    PARAMETER(ResourceBundle.getBundle("properties.parameter"));

    private ResourceBundle resourceBundle;

    Resource(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
