package com.mahiru.phonebook.config;

/**
 * @className UserContext
 * @description 记录用户id
 * @author mahiru
 * @date 2024/12/12 14:26
 * @version v1.0.0
**/
public class UserContext {
    private static final ThreadLocal<Long> USERTHREADLOCAL = new ThreadLocal<>();

    /**
     * @author mahiru
     * @date 2024/12/12 14:28
     * @methodName setUserId
     * @description 存取用户id
     * @param userId
     * @return void
     */
    public static void setUserId(Long userId) {
        USERTHREADLOCAL.set(userId);
    }

    /**
     * @author mahiru
     * @date 2024/12/12 14:28
     * @methodName getUserId
     * @description 获取用户id
     * @param
     * @return java.lang.Integer
     */
    public static Long getUserId() {
        return USERTHREADLOCAL.get();
    }

    /**
     * @author mahiru
     * @date 2024/12/12 14:28
     * @methodName clear
     * @description 清除用户id
     * @param
     * @return void
     */
    public static void clear() {
        USERTHREADLOCAL.remove();
    }
}