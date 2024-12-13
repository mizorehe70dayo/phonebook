package com.mahiru.phonebook.controller;

import com.mahiru.phonebook.common.Result;
import com.mahiru.phonebook.model.po.User;
import com.mahiru.phonebook.service.IUserService;
import com.mahiru.phonebook.service.impl.UserServiceImpl;
import lombok.NoArgsConstructor;

/**
 * @author mahiru
 * @version v1.0.0
 * @className IndexController
 * @description index视图控制层
 * @date 2024/12/11 20:35
 **/
@NoArgsConstructor
public class UserController {

    /**
     * @author mahiru
     * @date 2024/12/12 14:45
     * @methodName login
     * @description 登录方法
     * @param userDTO
     * @return com.mahiru.phonebook.common.Result
     */
    public Result login(User userDTO) {
        IUserService userService = new UserServiceImpl();
        return userService.login(userDTO);
    }

    /**
     * @author mahiru
     * @date 2024/12/12 14:46
     * @methodName register
     * @description 注册方法
     * @param userDTO
     * @return void
     */
    public Result register(User userDTO) {
        IUserService userService = new UserServiceImpl();
        return userService.register(userDTO);
    }

    /**
     * @author mahiru
     * @date 2024/12/12 14:46
     * @methodName getUserName
     * @description 根据用户id获取用户名
     * @param userId
     * @return java.lang.String
     */
    public String getUserName(Long userId){
        IUserService userService = new UserServiceImpl();
        return userService.getUserNameByUserId(userId);
    }
}
