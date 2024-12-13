package com.mahiru.phonebook.controller;

import com.mahiru.phonebook.common.Result;
import com.mahiru.phonebook.model.enums.StatusCode;
import com.mahiru.phonebook.model.po.ContactType;
import com.mahiru.phonebook.service.IContactTypeService;
import com.mahiru.phonebook.service.impl.ContactTypeServiceImpl;

import java.util.List;

/**
 * @className ContactTypeController
 * @description 联系人类别控制层
 * @author mahiru
 * @date 2024/12/13 17:00
 * @version v1.0.0
**/
public class ContactTypeController {
    /**
     * @author mahiru
     * @date 2024/12/13 17:05
     * @methodName fetchAllContactTypes
     * @description 获取所有联系人类别
     * @param
     * @return java.util.List<com.mahiru.phonebook.model.po.ContactType>
     */
    public List<ContactType> fetchAllContactTypes() {
        IContactTypeService contactTypeService = new ContactTypeServiceImpl();
        return contactTypeService.getAllContactTypes();
    }

    /**
     * @author mahiru
     * @date 2024/12/13 17:28
     * @methodName findContactTypeById
     * @description 根据ID查找联系人类别
     * @param id
     * @return com.mahiru.phonebook.common.Result
     */
    public Result findContactTypeById(int id) {
        IContactTypeService contactTypeService = new ContactTypeServiceImpl();

        if (contactTypeService.getContactTypeById(id) == null) {
            return Result.fail(StatusCode.UPDATE_CONTACT_FAIL,"未查找到联系人类别ID");
        }
        return Result.success(contactTypeService.getContactTypeById(id));
    }
}
