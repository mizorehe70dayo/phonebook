package com.mahiru.phonebook.mapper;

import com.mahiru.phonebook.model.po.User;

/**
 * @className IUserMapper
 * @description 用户类dao层
 * @author mahiru
 * @date 2024/12/12 13:47
 * @version v1.0.0
**/
public interface IUserMapper {
    User selectUserByUserName(String username);

    boolean insertUser(User userDTO);

    String selectUserNameByUserId(Long userId);
}
