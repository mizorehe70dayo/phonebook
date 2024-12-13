package com.mahiru.phonebook.service;

import com.mahiru.phonebook.common.Result;
import com.mahiru.phonebook.model.po.ContactType;

import java.util.List;

/**
 * @interfaceName IContactTypeService
 * @description 联系人类别业务层
 * @author biers 
 * @date 2024/12/13 17:06
 * @version v1.0.0
**/
public interface IContactTypeService {
    List<ContactType> getAllContactTypes();

    ContactType getContactTypeById(int id);

}
