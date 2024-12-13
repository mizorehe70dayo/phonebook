package com.mahiru.phonebook.model.po;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @className ContactType
 * @description 联系人类别
 * @author mahiru
 * @date 2024/12/11 20:04
 * @version v1.0.0
**/
@Data
@SuperBuilder
public class ContactType extends BaseEntity {
    private String name;
}
