package com.mahiru.phonebook.model.po;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @className ContactPhoneType
 * @description 联系人电话与电话类别关系表
 * @author mahiru
 * @date 2024/12/13 11:24
 * @version v1.0.0
**/
@Data
@SuperBuilder
public class ContactPhoneType extends BaseEntity{
    private Long contactPhoneId;
    private Long phoneTypeId;
}
