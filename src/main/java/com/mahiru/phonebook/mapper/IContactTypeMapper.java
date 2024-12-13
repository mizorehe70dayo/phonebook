package com.mahiru.phonebook.mapper;

import com.mahiru.phonebook.model.po.ContactType;

import java.util.List;

/**
 * @className IContactTypeMapper
 * @description 联系人类别dao层
 * @author mahiru
 * @date 2024/12/13 16:14
 * @version v1.0.0
**/
public interface IContactTypeMapper {
    ContactType getById(int id);

    List<ContactType> findAll();
}
