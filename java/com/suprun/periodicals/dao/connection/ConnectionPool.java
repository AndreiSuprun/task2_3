package com.suprun.periodicals.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {

    private BlockingQueue<Connection> pool;
    private int maxPoolSize;
    private int initialPoolSize;
    private int currentPoolSize;
    private String databaseUrl;
    private String databaseUser;
    private String databasePassword;
    private String driverClassName;

    private static volatile ConnectionPool instance;

    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (ConnectionPool.class){
                instance = new ConnectionPool();
            }
        }
        return instance;
    }

    private ConnectionPool() throws SQLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        this.driverClassName = resourceBundle.getString("driver");
        this.databaseUrl = resourceBundle.getString("url");
        this.databaseUser = resourceBundle.getString("username");
        this.databasePassword = resourceBundle.getString("password");
        this.initialPoolSize = Integer.parseInt(resourceBundle.getString("initialPoolSize"));
        this.maxPoolSize = Integer.parseInt(resourceBundle.getString("maxPoolSize"));
        if (initialPoolSize > maxPoolSize) {
            initialPoolSize = maxPoolSize;
        }
        this.pool = new LinkedBlockingQueue<>(maxPoolSize);
        initPooledConnections(driverClassName);
    }

    private void initPooledConnections(String driverClassName)
            throws SQLException {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e){
            e.getStackTrace();
        }
        for (int i = 0; i < initialPoolSize; i++) {
            openAndPoolConnection();
        }
    }

    private synchronized void openAndPoolConnection() throws SQLException {
        if (currentPoolSize == maxPoolSize) {
            return;
        }
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
        pool.offer(new PooledConnection(connection));
        currentPoolSize++;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        if (pool.peek()==null && currentPoolSize < maxPoolSize) {
            openAndPoolConnection();
        }
        try {
            connection = pool.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (!(connection instanceof PooledConnection)) {
            return;
        }
        pool.offer(connection);
    }

    public void closeAllConnections(){
        try{
            for (Connection connection : pool){
                ((PooledConnection)connection).getConnection().close();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
