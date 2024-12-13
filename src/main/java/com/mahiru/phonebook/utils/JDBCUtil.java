package com.mahiru.phonebook.utils;

import java.sql.*;
import java.util.List;
import java.util.function.Function;

/**
 * @className JdbcUtil
 * @description jdbc工具类
 * @author mahiru
 * @date 2024/12/12 13:38
 * @version v1.0.0
**/
public class JDBCUtil {

    // 数据库连接配置
    private static final String URL = "jdbc:mysql://192.168.58.101:3306/phonebook";
    private static final String USER = "root";
    private static final String PASSWORD = "123";

    // 获取数据库连接
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 执行查询操作，返回单个结果
    public static <T> T selectOne(String sql, Function<ResultSet, T> rowMapper, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 设置查询参数
            setParameters(stmt, params);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rowMapper.apply(rs);  // 使用 Lambda 将查询结果映射到对象
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 执行查询操作，返回多个结果
    public static <T> List<T> selectList(String sql, Function<ResultSet, T> rowMapper, Object... params) {
        List<T> results = new java.util.ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 设置查询参数
            setParameters(stmt, params);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(rowMapper.apply(rs));  // 使用 Lambda 将查询结果映射到对象
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }


    public static boolean insertOne(String sql, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 设置 PreparedStatement 的参数
            setParameters(stmt, params);

            // 执行插入操作
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // 返回是否插入成功

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 设置 PreparedStatement 的查询参数
    private static void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);  // 从1开始设置参数
        }
    }

    public static boolean update(String sql, Object... params) {
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 设置参数
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            // 执行更新
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Integer querySingleResult(String sql, Object... params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // 获取数据库连接（假设有一个 JDBCUtil.getConnection() 方法）
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            // 设置参数
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            // 执行查询
            resultSet = preparedStatement.executeQuery();

            // 返回单个结果
            if (resultSet.next()) {
                return resultSet.getInt(1); // 假设查询的结果是整数类型
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            // 关闭资源
//            JDBCUtil.close(resultSet, preparedStatement, connection);
        }

        return null; // 查询无结果时返回 null
    }


}
