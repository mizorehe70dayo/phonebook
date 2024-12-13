package com.mahiru.phonebook.service;

import com.mahiru.phonebook.common.Result;
import com.mahiru.phonebook.model.dto.ContactDTO;
import com.mahiru.phonebook.model.po.Contact;

import java.util.List;

/**
 * @className IContactService
 * @description 联系人业务层
 * @author mahiru
 * @date 2024/12/12 16:13
 * @version v1.0.0
**/
public interface IContactService {
    List<Contact> queryContactsList();

    Result addContact(Contact contactDTO);

    Result deleteContact(Long id);

    ContactDTO queryContactById(Long id);

    Boolean updateContact(ContactDTO contact);

    List<Contact> queryContactsListByNameKeyWord(String keyword);

    List<ContactDTO> queryContactsInfoList();
}
