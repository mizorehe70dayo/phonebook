package com.mahiru.phonebook.mapper;

import com.mahiru.phonebook.model.po.PhoneType;

import java.util.List;

/**
 * @className IPhoneTypeMapper
 * @description 电话类别dao层
 * @author mahiru
 * @date 2024/12/13 16:13
 * @version v1.0.0
**/
public interface IPhoneTypeMapper {
    PhoneType getById(int id);

    List<PhoneType> findAll();
}
