package com.mahiru.phonebook.model.enums;

import lombok.Getter;

/**
 * @className StatusCode
 * @description 状态码集合
 * @author mahiru
 * @date 2024/12/11 21:03
 * @version v1.0.0
**/
@Getter
public enum StatusCode {
    SUCCESS(200, "操作成功"),
    FAIL(400, "操作失败"),
    LOGIN_FAIL(401, "登录失败"),
    REGISTER_FAIL(402, "注册失败"),
    ADD_CONTACT_FAIL(403, "添加联系人失败"),
    DELETE_CONTACT_FAIL(405, "删除联系人失败"),
    UPDATE_CONTACT_FAIL(406, "更新联系人失败"),
    ;

    private final int code;
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static StatusCode fromCode(int code) {
        for (StatusCode status : StatusCode.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}