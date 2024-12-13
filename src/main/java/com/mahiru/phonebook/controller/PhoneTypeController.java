package com.mahiru.phonebook.controller;

import com.mahiru.phonebook.common.Result;
import com.mahiru.phonebook.model.enums.StatusCode;
import com.mahiru.phonebook.model.po.PhoneType;
import com.mahiru.phonebook.service.IPhoneTypeService;
import com.mahiru.phonebook.service.impl.PhoneTypeServiceImpl;

import java.util.List;

/**
 * @className PhoneTypeController
 * @description 手机类别控制层
 * @author mahiru
 * @date 2024/12/13 17:01
 * @version v1.0.0
**/
public class PhoneTypeController {
    /**
     * @author mahiru
     * @date 2024/12/13 18:02
     * @methodName fetchAllPhoneTypes
     * @description 获取所有电话类别
     * @param
     * @return java.util.List<com.mahiru.phonebook.model.po.PhoneType>
     */
    public List<PhoneType> fetchAllPhoneTypes() {
        IPhoneTypeService phoneTypeService = new PhoneTypeServiceImpl();
        return phoneTypeService.getAllPhoneTypes();
    }


    /**
     * @author mahiru
     * @date 2024/12/13 18:03
     * @methodName findPhoneTypeById
     * @description 根据ID获取电话类别
     * @param id
     * @return com.mahiru.phonebook.common.Result
     */
    public Result findPhoneTypeById(int id) {
        IPhoneTypeService phoneTypeService = new PhoneTypeServiceImpl();

        PhoneType phoneType = phoneTypeService.getPhoneTypeById(id);
        if (phoneType != null) {
            return Result.success(phoneType);
        } else {
            return Result.fail(StatusCode.UPDATE_CONTACT_FAIL, "电话类别不存在");
        }
    }
}
