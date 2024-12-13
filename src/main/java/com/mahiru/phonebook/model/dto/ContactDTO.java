package com.mahiru.phonebook.model.dto;

import com.mahiru.phonebook.model.po.BaseEntity;
import com.mahiru.phonebook.model.po.ContactType;
import com.mahiru.phonebook.model.po.PhoneType;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @className ContactDTO
 * @description 联系人dto
 * @author mahiru
 * @date 2024/12/13 11:25
 * @version v1.0.0
**/
@Data
@SuperBuilder
public class ContactDTO extends BaseEntity {
    private String name;
    private String phone;
    private String email;
    private String address;
    private LocalDateTime birthday;
    private String description;
    private ContactType contactType;
    private PhoneType phoneType;
}
