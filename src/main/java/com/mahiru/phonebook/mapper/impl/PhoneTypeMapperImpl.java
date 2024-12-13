package com.mahiru.phonebook.mapper.impl;

import com.mahiru.phonebook.mapper.IPhoneTypeMapper;
import com.mahiru.phonebook.model.po.PhoneType;
import com.mahiru.phonebook.utils.JDBCUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @className PhoneTypeMapperImpl
 * @description 电话类别dao层
 * @author mahiru
 * @date 2024/12/13 16:14
 * @version v1.0.0
**/
public class PhoneTypeMapperImpl implements IPhoneTypeMapper {
    /**
     * @author mahiru
     * @date 2024/12/13 16:15
     * @methodName getById
     * @description 根据id查询电话类别
     * @param id
     * @return com.mahiru.phonebook.model.po.PhoneType
     */
    @Override
    public PhoneType getById(int id) {
        String sql = "SELECT * FROM phone_types WHERE id = ?";
        return JDBCUtil.selectOne(sql, this::mapRowToPhoneType, id);
    }

    /**
     * @author mahiru
     * @date 2024/12/13 18:05
     * @methodName findAll
     * @description 查询所有电话类别
     * @param
     * @return java.util.List<com.mahiru.phonebook.model.po.PhoneType>
     */
    @Override
    public List<PhoneType> findAll() {
        String sql = "SELECT * FROM phone_types";
        return JDBCUtil.selectList(sql, this::mapRowToPhoneType);
    }

    // 映射查询结果到PhoneType对象
    private PhoneType mapRowToPhoneType(ResultSet rs) {
        try {
            return PhoneType.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
