package com.mahiru.phonebook.service;

import com.mahiru.phonebook.model.po.PhoneType;

import java.util.List;

/**
 * @interfaceName IPhoneTypeService
 * @description 手机类别业务层
 * @author biers 
 * @date 2024/12/13 17:06
 * @version v1.0.0
**/
public interface IPhoneTypeService {
    List<PhoneType> getAllPhoneTypes();

    PhoneType getPhoneTypeById(int id);
}
