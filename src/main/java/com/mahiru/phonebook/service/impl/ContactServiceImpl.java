package com.mahiru.phonebook.service.impl;

import com.mahiru.phonebook.common.Result;
import com.mahiru.phonebook.config.UserContext;
import com.mahiru.phonebook.mapper.IContactMapper;
import com.mahiru.phonebook.mapper.impl.ContactMapperImpl;
import com.mahiru.phonebook.model.dto.ContactDTO;
import com.mahiru.phonebook.model.enums.StatusCode;
import com.mahiru.phonebook.model.po.Contact;
import com.mahiru.phonebook.service.IContactService;

import java.util.List;

/**
 * @author mahiru
 * @version v1.0.0
 * @className ContactServiceImpl
 * @description 联系人业务层实现类
 * @date 2024/12/12 16:14
 **/
public class ContactServiceImpl implements IContactService {

    /**
     * @param
     * @return java.util.List<com.mahiru.phonebook.model.po.Contact>
     * @author mahiru
     * @date 2024/12/12 16:17
     * @methodName queryContactsList
     * @description 查询所有联系人列表
     */
    @Override
    public List<Contact> queryContactsList() {
        IContactMapper contactMapper = new ContactMapperImpl();

        return contactMapper.getContactsList();
    }

    /**
     * @param contactDTO
     * @return com.mahiru.phonebook.common.Result
     * @author mahiru
     * @date 2024/12/12 17:29
     * @methodName addContact
     * @description 添加联系人
     */
    @Override
    public Result addContact(Contact contactDTO) {
        IContactMapper contactMapper = new ContactMapperImpl();

        Contact contact = contactMapper.selectContactByPhone(contactDTO.getPhone(), UserContext.getUserId());
        if (contact != null) {
            return Result.fail(StatusCode.ADD_CONTACT_FAIL, "号码已存在");
        }

        if (!contactMapper.insertContact(contactDTO, UserContext.getUserId())) {
            return Result.fail(StatusCode.ADD_CONTACT_FAIL);
        }
        return Result.success();
    }

    /**
     * @author mahiru
     * @date 2024/12/13 10:52
     * @methodName deleteContact
     * @description 删除联系人
     * @param id
     * @return com.mahiru.phonebook.common.Result
     */
    @Override
    public Result deleteContact(Long id) {
        IContactMapper contactMapper = new ContactMapperImpl();

        // TODO:可能需要相关类别页做删除，目前只做联系人表的删除
        if (!contactMapper.deleteContact(id)) {
            return Result.fail(StatusCode.DELETE_CONTACT_FAIL);
        }

        return Result.success();
    }

    /**
     * @author mahiru
     * @date 2024/12/13 15:25
     * @methodName queryContactById
     * @description 根据id查询联系人
     * @param id
     * @return com.mahiru.phonebook.model.dto.ContactDTO
     */
    @Override
    public ContactDTO queryContactById(Long id) {
        IContactMapper contactMapper = new ContactMapperImpl();

        return contactMapper.selectContactById(id);
    }


    /**
     * @author mahiru
     * @date 2024/12/13 18:10
     * @methodName updateContact
     * @description 更新联系人
     * @param contact
     * @return java.lang.Boolean
     */
    @Override
    public Boolean updateContact(ContactDTO contact) {
        IContactMapper contactMapper = new ContactMapperImpl();

         return contactMapper.updateContact(contact);
    }

    /**
     * @author mahiru
     * @date 2024/12/13 19:51
     * @methodName queryContactsListByNameKeyWord
     * @description 根据关键字查询联系人列表
     * @param keyword
     * @return java.util.List<com.mahiru.phonebook.model.po.Contact>
     */
    @Override
    public List<Contact> queryContactsListByNameKeyWord(String keyword) {
        IContactMapper contactMapper = new ContactMapperImpl();

        return contactMapper.getContactsListByNameKeyWord(keyword);
    }

    /**
     * @author mahiru
     * @date 2024/12/13 20:11
     * @methodName queryContactsInfoList
     * @description 查询所有联系人详细信息
     * @param
     * @return java.util.List<com.mahiru.phonebook.model.dto.ContactDTO>
     */
    @Override
    public List<ContactDTO> queryContactsInfoList() {
        IContactMapper contactMapper = new ContactMapperImpl();

        return contactMapper.getContactsInfoList();
}}
