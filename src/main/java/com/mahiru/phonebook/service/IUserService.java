package com.mahiru.phonebook.service;

import com.mahiru.phonebook.common.Result;
import com.mahiru.phonebook.model.po.User;

/**
 * @className UserService
 * @description 用户业务层
 * @author mahiru
 * @date 2024/12/11 21:59
 * @version v1.0.0
**/
public interface IUserService {
    Result login(User userDTO);

    Result register(User userDTO);

    String getUserNameByUserId(Long userId);
}
