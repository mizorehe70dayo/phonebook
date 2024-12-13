package com.mahiru.phonebook.common;

import com.mahiru.phonebook.model.enums.StatusCode;
import lombok.Getter;

/**
 * @author mahiru
 * @version v1.0.0
 * @className Result
 * @description 通用返回类
 * @date 2024/12/11 21:02
 **/
@Getter
public class Result<T> {
    private final int code;
    private final String message;
    private final T data;

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(StatusCode.SUCCESS.getCode(),"操作成功",null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(StatusCode.SUCCESS.getCode(), "操作成功", data);
    }

    public static <T> Result<T> fail(StatusCode statusCode, String message) {
        return new Result<>(statusCode.getCode(), message, null);
    }

    public static <T> Result<T> fail(StatusCode statusCode) {
        return new Result<>(statusCode.getCode(), statusCode.getMessage(), null);
    }
}