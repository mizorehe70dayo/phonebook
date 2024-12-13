package com.mahiru.phonebook.model.po;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @className User
 * @description 用户类
 * @author mahiru
 * @date 2024/12/11 20:02
 * @version v1.0.0
**/
@Data
@SuperBuilder
public class User extends BaseEntity {
    private String username;
    private String password;
}
