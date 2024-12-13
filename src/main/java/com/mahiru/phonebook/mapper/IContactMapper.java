package com.mahiru.phonebook.mapper;

import com.mahiru.phonebook.model.dto.ContactDTO;
import com.mahiru.phonebook.model.po.Contact;

import java.util.List;

/**
 * @interfaceName IContactMapper
 * @description 联系人dao层
 * @author biers 
 * @date 2024/12/12 16:19
 * @version v1.0.0
**/
public interface IContactMapper {
    List<Contact> getContactsList();

    Contact selectContactByPhone(String phone,Long userId);

    boolean insertContact(Contact contactDTO, Long userId);

    boolean deleteContact(Long id);

    ContactDTO selectContactById(Long id);

    Boolean updateContact(ContactDTO contact);

    List<Contact> getContactsListByNameKeyWord(String keyword);

    List<ContactDTO> getContactsInfoList();
}
