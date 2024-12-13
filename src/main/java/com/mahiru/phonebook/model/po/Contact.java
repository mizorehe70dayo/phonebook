package com.mahiru.phonebook.model.po;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @className Contact
 * @description 联系人表
 * @author mahiru
 * @date 2024/12/11 20:03
 * @version v1.0.0
**/
@Data
@SuperBuilder
public class Contact extends BaseEntity {
    private String name;
    private String phone;
    private String email;
    private String address;
    private LocalDateTime birthday;
    private String description;
}
