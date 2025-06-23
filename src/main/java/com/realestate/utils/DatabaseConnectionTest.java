package com.realestate.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        // 1. 数据库连接参数
        String url = "jdbc:mysql://localhost:3306/house?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
        String user = "root";
        String password = "qwerty";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 2. 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 3. 建立连接
            conn = DriverManager.getConnection(url, user, password);

            // 4. 验证连接状态
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ 数据库连接成功！");

                // 5. 执行测试查询
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT VERSION() AS mysql_version");

                if (rs.next()) {
                    System.out.println("🔍 MySQL版本: " + rs.getString("mysql_version"));
                }
            }
        } catch (Exception e) {
            System.err.println("❌ 连接失败: " + e.getMessage());
            e.printStackTrace();

            // 特殊错误处理
            if (e.getMessage().contains("Access denied")) {
                System.err.println("\n⚠️ 用户名或密码错误！请检查：");
                System.err.println("用户名: " + user);
                System.err.println("密码: " + password);
            } else if (e.getMessage().contains("Communications link failure")) {
                System.err.println("\n⚠️ 无法连接到MySQL服务！请检查：");
                System.err.println("1. MySQL服务是否运行");
                System.err.println("2. 端口3306是否开放");
            }
        } finally {
            // 6. 关闭资源（重要！）
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                System.out.println("🔒 数据库连接已关闭");
            } catch (Exception e) {
                System.err.println("关闭连接时出错: " + e.getMessage());
            }
        }
    }
}