package com.mahiru.phonebook.mapper.impl;

import com.mahiru.phonebook.utils.JDBCUtil;
import com.mahiru.phonebook.mapper.IUserMapper;
import com.mahiru.phonebook.model.po.User;

import java.sql.SQLException;

/**
 * @author mahiru
 * @version v1.0.0
 * @className UserMapperImpl
 * @description 用户类dao层实现
 * @date 2024/12/12 13:47
 **/
public class UserMapperImpl implements IUserMapper {

    /**
     * @param username
     * @return com.mahiru.phonebook.model.po.User
     * @author mahiru
     * @date 2024/12/12 14:01
     * @methodName selectUserByUserName
     * @description 根据username查询对应的user对象
     */
    @Override
    public User selectUserByUserName(String username) {
        String sql = "SELECT id, username, password FROM user WHERE username = ? AND is_deleted = 0";

        return JDBCUtil.selectOne(sql, rs -> {
            try {
                return User.builder()
                        .id(rs.getLong("id"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .build();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }, username);
    }

    /**
     * @param userDTO
     * @return boolean
     * @author mahiru
     * @date 2024/12/12 14:55
     * @methodName insertUser
     * @description 注册用户
     */
    @Override
    public boolean insertUser(User userDTO) {
        String sql = "INSERT INTO user (username, password) VALUES (?, ?)";

        return JDBCUtil.insertOne(sql, userDTO.getUsername(), userDTO.getPassword());
    }

    /**
     * @param userId
     * @return com.mahiru.phonebook.model.po.User
     * @author mahiru
     * @date 2024/12/12 15:33
     * @methodName selectUserNameByUserId
     * @description 根据用户id查询用户姓名
     */
    @Override
    public String selectUserNameByUserId(Long userId) {
        String sql = "SELECT username FROM user WHERE id = ? AND is_deleted = 0";

        return JDBCUtil.selectOne(sql, rs -> {
            try {
                return rs.getString("username");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }, userId);
    }
}
