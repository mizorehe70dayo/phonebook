package com.mahiru.phonebook.mapper.impl;

import com.mahiru.phonebook.mapper.IContactTypeMapper;
import com.mahiru.phonebook.model.po.ContactType;
import com.mahiru.phonebook.utils.JDBCUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author mahiru
 * @version v1.0.0
 * @className ContactTypeMapperImpl
 * @description 联系人类别dao层
 * @date 2024/12/13 16:15
 **/
public class ContactTypeMapperImpl implements IContactTypeMapper {
    /**
     * @param id
     * @return com.mahiru.phonebook.model.po.ContactType
     * @author mahiru
     * @date 2024/12/13 16:17
     * @methodName getById
     * @description 根据id获取联系人类别
     */
    @Override
    public ContactType getById(int id) {
        String sql = "SELECT * FROM contact_types WHERE id = ?";

        return JDBCUtil.selectOne(sql, this::mapRowToContactType, id);
    }

    /**
     * @param
     * @return java.util.List<com.mahiru.phonebook.model.po.ContactType>
     * @author mahiru
     * @date 2024/12/13 17:24
     * @methodName findAll
     * @description 查询所有联系人类别
     */
    @Override
    public List<ContactType> findAll() {
        String sql = "SELECT * FROM contact_types";
        return JDBCUtil.selectList(sql, this::mapRowToContactType);
    }


    // 映射查询结果到ContactType对象
    private ContactType mapRowToContactType(ResultSet rs) {
        try {
            return ContactType.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
