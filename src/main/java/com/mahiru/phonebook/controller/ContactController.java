package com.mahiru.phonebook.controller;

import com.mahiru.phonebook.common.Result;
import com.mahiru.phonebook.model.dto.ContactDTO;
import com.mahiru.phonebook.model.enums.StatusCode;
import com.mahiru.phonebook.model.po.Contact;
import com.mahiru.phonebook.service.IContactService;
import com.mahiru.phonebook.service.impl.ContactServiceImpl;

import java.util.List;

/**
 * @className ContactController
 * @description 联系人控制层
 * @author mahiru
 * @date 2024/12/12 16:11
 * @version v1.0.0
**/
public class ContactController {

    /**
     * @author mahiru
     * @date 2024/12/12 17:28
     * @methodName getAllContacts
     * @description 获取所有联系人
     * @param
     * @return java.util.List<com.mahiru.phonebook.model.po.Contact>
     */
    public List<Contact> getAllContacts() {
        IContactService contactService = new ContactServiceImpl();

        return contactService.queryContactsList();
    }

    /**
     * @author mahiru
     * @date 2024/12/12 17:29
     * @methodName addContact
     * @description 添加联系人方法
     * @param contactDTO
     * @return com.mahiru.phonebook.common.Result
     */
    public Result addContact(Contact contactDTO) {
        IContactService contactService = new ContactServiceImpl();

        return contactService.addContact(contactDTO);
    }

    /**
     * @author mahiru
     * @date 2024/12/13 10:50
     * @methodName deleteContact
     * @description 删除联系人方法
     * @param id
     * @return com.mahiru.phonebook.common.Result
     */
    public Result deleteContact(Long id) {
        IContactService contactService = new ContactServiceImpl();

        return contactService.deleteContact(id);
    }

    /**
     * @author mahiru
     * @date 2024/12/13 15:19
     * @methodName getContactById
     * @description 根据id查询联系人详细信息
     * @param id
     * @return com.mahiru.phonebook.model.dto.ContactDTO
     */
    public ContactDTO getContactById(Long id) {
        IContactService contactService = new ContactServiceImpl();

        return contactService.queryContactById(id);
    }

    /**
     * @author mahiru
     * @date 2024/12/13 15:52
     * @methodName updateContact
     * @description 更新联系人信息
     * @param contact
     * @return com.mahiru.phonebook.common.Result
     */
    public Result updateContact(ContactDTO contact) {
        System.out.println(contact.toString());
        IContactService contactService = new ContactServiceImpl();

        Boolean updated = contactService.updateContact(contact);
        if (updated) {
            return Result.success();
        } else {
            return Result.fail(StatusCode.UPDATE_CONTACT_FAIL);
        }
    }

    /**
     * @author mahiru
     * @date 2024/12/13 19:43
     * @methodName getAllContactsByNameKeyWord
     * @description 根据关键字查询联系人
     * @param
     * @return java.util.List<com.mahiru.phonebook.model.po.Contact>
     */
    public Result getAllContactsByNameKeyWord(String keyword) {
        IContactService contactService = new ContactServiceImpl();

        List<Contact> contactList = contactService.queryContactsListByNameKeyWord(keyword);

        return Result.success(contactList);
    }

    public Result<List<ContactDTO>> getAllContactsList() {
        IContactService contactService = new ContactServiceImpl();

        return Result.success(contactService.queryContactsInfoList());
    }
}
