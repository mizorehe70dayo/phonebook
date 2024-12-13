package com.mahiru.phonebook.model.po;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @className ContactContactType
 * @description 联系人与联系人类别关系表
 * @author mahiru
 * @date 2024/12/13 11:23
 * @version v1.0.0
**/
@Data
@SuperBuilder
public class ContactContactType extends BaseEntity{
    private Long contactId;
    private Long contactTypeId;
}
