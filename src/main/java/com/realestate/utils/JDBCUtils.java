package com.realestate.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCUtils {
    private static final Logger LOGGER = Logger.getLogger(JDBCUtils.class.getName());
    private static String url;
    private static String user;
    private static String password;
    private static String driver;

    static {
        try {
            // 尝试使用类加载器加载资源（推荐方式）
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("jdbc.properties");

            if (inputStream == null) {
                // 备选方案：使用当前类的类加载器
                inputStream = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
                if (inputStream == null) {
                    throw new IOException("无法加载配置文件：jdbc.properties");
                }
            }

            // 打印加载路径（用于调试）
            LOGGER.info("成功加载配置文件：jdbc.properties");

            // 加载配置
            Properties properties = new Properties();
            properties.load(inputStream);
            inputStream.close();

            // 获取配置参数并验证
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            driver = properties.getProperty("driver");

            if (url == null || user == null || password == null) {
                throw new IllegalArgumentException("配置文件不完整：缺少必要参数");
            }

            // 注册驱动
            Class.forName(driver);
            LOGGER.info("数据库驱动注册成功：" + driver);
        } catch (Exception e) {
            // 记录详细错误并抛出异常
            LOGGER.log(Level.SEVERE, "数据库连接初始化失败", e);
            throw new ExceptionInInitializerError("数据库连接初始化失败：" + e.getMessage());
        }
    }

    // 获取数据库连接
    public static Connection getConnection() throws SQLException {
        if (url == null) {
            throw new SQLException("数据库URL未初始化，请检查配置文件");
        }
        return DriverManager.getConnection(url, user, password);
    }

    // 关闭资源的方法（保持不变）
    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        // ...（代码不变）
    }
}