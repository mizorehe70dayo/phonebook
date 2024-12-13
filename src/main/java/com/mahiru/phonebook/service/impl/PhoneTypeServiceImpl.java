package com.mahiru.phonebook.service.impl;

import com.mahiru.phonebook.mapper.IPhoneTypeMapper;
import com.mahiru.phonebook.mapper.impl.PhoneTypeMapperImpl;
import com.mahiru.phonebook.model.po.PhoneType;
import com.mahiru.phonebook.service.IPhoneTypeService;

import java.util.List;

/**
 * @className PhoneTypeServiceImpl
 * @description 手机类别业务层实现类
 * @author mahiru
 * @date 2024/12/13 17:07
 * @version v1.0.0
**/
public class PhoneTypeServiceImpl implements IPhoneTypeService {

    /**
     * @author mahiru
     * @date 2024/12/13 18:04
     * @methodName getAllPhoneTypes
     * @description 获取所有电话类别
     * @param
     * @return java.util.List<com.mahiru.phonebook.model.po.PhoneType>
     */
    public List<PhoneType> getAllPhoneTypes() {
        IPhoneTypeMapper phoneTypeMapper = new PhoneTypeMapperImpl();
        return phoneTypeMapper.findAll();
    }

    /**
     * @author mahiru
     * @date 2024/12/13 18:04
     * @methodName getPhoneTypeById
     * @description 根据ID获取电话类别
     * @param id
     * @return com.mahiru.phonebook.model.po.PhoneType
     */
    public PhoneType getPhoneTypeById(int id) {
        IPhoneTypeMapper phoneTypeMapper = new PhoneTypeMapperImpl();

        return phoneTypeMapper.getById(id);
    }
}
