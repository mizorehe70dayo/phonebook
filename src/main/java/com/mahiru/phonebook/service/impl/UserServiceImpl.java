package com.mahiru.phonebook.service.impl;

import com.mahiru.phonebook.common.Result;
import com.mahiru.phonebook.config.UserContext;
import com.mahiru.phonebook.mapper.IUserMapper;
import com.mahiru.phonebook.mapper.impl.UserMapperImpl;
import com.mahiru.phonebook.model.enums.StatusCode;
import com.mahiru.phonebook.model.po.User;
import com.mahiru.phonebook.service.IUserService;
import com.mahiru.phonebook.utils.MD5Util;

/**
 * @className UserServiceImpl
 * @description 用户业务层实现
 * @author mahiru
 * @date 2024/12/11 22:00
 * @version v1.0.0
**/
public class UserServiceImpl implements IUserService {
    /**
     * @author mahiru
     * @date 2024/12/12 14:46
     * @methodName login
     * @description 登录方法
     * @param userDTO
     * @return com.mahiru.phonebook.common.Result<java.lang.Object>
     */
    @Override
    public Result<Object> login(User userDTO) {
        IUserMapper userMapper = new UserMapperImpl();

        User userVO = userMapper.selectUserByUserName(userDTO.getUsername());

        if (userVO == null) {
            return Result.fail(StatusCode.LOGIN_FAIL, "用户名不存在");
        }

        if (!userVO.getPassword().equals(MD5Util.encrypt(userDTO.getPassword()))) {
            return Result.fail(StatusCode.LOGIN_FAIL, "密码错误");
        }

        UserContext.setUserId(userVO.getId());
        return Result.success(userVO);
    }

    /**
     * @author mahiru
     * @date 2024/12/12 14:46
     * @methodName register
     * @description 注册方法
     * @param userDTO
     * @return com.mahiru.phonebook.common.Result
     */
    @Override
    public Result register(User userDTO) {
        IUserMapper userMapper = new UserMapperImpl();

        if(userMapper.selectUserByUserName(userDTO.getUsername()) != null){
            return Result.fail(StatusCode.REGISTER_FAIL, "用户名已存在");
        }

        userDTO.setPassword(MD5Util.encrypt(userDTO.getPassword()));
        if (userMapper.insertUser(userDTO)) {
            return Result.success();
        }else{
            return Result.fail(StatusCode.REGISTER_FAIL);
        }
    }

    /**
     * @author mahiru
     * @date 2024/12/12 15:31
     * @methodName getUserNameByUserId
     * @description 获取用户姓名
     * @param userId 用户id
     * @return java.lang.String
     */
    @Override
    public String getUserNameByUserId(Long userId) {
        IUserMapper userMapper = new UserMapperImpl();

        return userMapper.selectUserNameByUserId(userId);
    }
}
