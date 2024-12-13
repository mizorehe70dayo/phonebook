package com.mahiru.phonebook.service.impl;

import com.mahiru.phonebook.mapper.IContactMapper;
import com.mahiru.phonebook.mapper.IContactTypeMapper;
import com.mahiru.phonebook.mapper.impl.ContactMapperImpl;
import com.mahiru.phonebook.mapper.impl.ContactTypeMapperImpl;
import com.mahiru.phonebook.model.po.ContactType;
import com.mahiru.phonebook.service.IContactTypeService;

import java.util.List;

/**
 * @className ContactTypeServiceImpl
 * @description 联系人类别业务层
 * @author mahiru
 * @date 2024/12/13 17:07
 * @version v1.0.0
**/
public class ContactTypeServiceImpl implements IContactTypeService {
    /**
     * @author mahiru
     * @date 2024/12/13 17:23
     * @methodName getAllContactTypes
     * @description 获取所有联系人类别
     * @param
     * @return java.util.List<com.mahiru.phonebook.model.po.ContactType>
     */
    @Override
    public List<ContactType> getAllContactTypes() {
        IContactTypeMapper contactTypeMapper = new ContactTypeMapperImpl();

        return contactTypeMapper.findAll();
    }



    /**
     * @author mahiru
     * @date 2024/12/13 17:30
     * @methodName getContactTypeById
     * @description 根据ID获取联系人类别
     * @param id
     * @return com.mahiru.phonebook.model.po.ContactType
     */
    @Override
    public ContactType getContactTypeById(int id) {
        IContactTypeMapper contactTypeMapper = new ContactTypeMapperImpl();

        return contactTypeMapper.getById(id);
    }
}
