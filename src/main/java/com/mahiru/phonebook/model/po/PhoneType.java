package com.mahiru.phonebook.model.po;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @className PhoneType
 * @description 电话号码类别
 * @author mahiru
 * @date 2024/12/11 20:04
 * @version v1.0.0
**/
@Data
@SuperBuilder
public class PhoneType extends BaseEntity {
    private String name;
}
