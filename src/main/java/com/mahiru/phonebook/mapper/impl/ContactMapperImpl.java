package com.mahiru.phonebook.mapper.impl;

import com.mahiru.phonebook.config.UserContext;
import com.mahiru.phonebook.mapper.IContactMapper;
import com.mahiru.phonebook.mapper.IContactTypeMapper;
import com.mahiru.phonebook.mapper.IPhoneTypeMapper;
import com.mahiru.phonebook.model.dto.ContactDTO;
import com.mahiru.phonebook.model.po.Contact;
import com.mahiru.phonebook.model.po.ContactType;
import com.mahiru.phonebook.model.po.PhoneType;
import com.mahiru.phonebook.utils.JDBCUtil;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author mahiru
 * @version v1.0.0
 * @className ContactMapperImpl
 * @description 联系人dao层实现类
 * @date 2024/12/12 16:19
 **/
public class ContactMapperImpl implements IContactMapper {
    /**
     * @param
     * @return java.util.List<com.mahiru.phonebook.model.po.Contact>
     * @author mahiru
     * @date 2024/12/12 16:21
     * @methodName getContactsList
     * @description 获取联系人列表
     */
    @Override
    public List<Contact> getContactsList() {
        String sql = """
                SELECT id, name, phone
                FROM contacts
                WHERE is_deleted = 0
                ORDER BY name ASC
                """;

        return JDBCUtil.selectList(sql, rs -> {
            try {
                return Contact.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .phone(rs.getString("phone"))
                        .build();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    /**
     * @param phone
     * @return com.mahiru.phonebook.model.po.Contact
     * @author mahiru
     * @date 2024/12/12 17:31
     * @methodName selectPhone
     * @description 查询电话号码对应的联系人
     */
    @Override
    public Contact selectContactByPhone(String phone, Long userId) {
        String sql = "SELECT id, name FROM contacts WHERE phone = ? AND is_deleted = 0 AND user_id = ?";

        return JDBCUtil.selectOne(sql, rs -> {
            try {
                return Contact.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .phone(rs.getString("phone"))
                        .build();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }, phone, userId);
    }

    /**
     * @param contactDTO
     * @param userId
     * @return boolean
     * @author mahiru
     * @date 2024/12/12 17:39
     * @methodName insertContact
     * @description 插入联系人
     */
    @Override
    public boolean insertContact(Contact contactDTO, Long userId) {
        String sql = "INSERT INTO contacts (name, phone, user_id) VALUES (?, ?, ?)";

        return JDBCUtil.insertOne(sql, contactDTO.getName(), contactDTO.getPhone(), userId);
    }

    /**
     * @param id
     * @param userId
     * @return boolean
     * @author mahiru
     * @date 2024/12/13 11:01
     * @methodName deleteContact
     * @description 删除联系人
     */
    @Override
    public boolean deleteContact(Long id) {
        String sql = "UPDATE contacts SET is_deleted = 1 WHERE id = ? AND is_deleted = 0";
        return JDBCUtil.update(sql, id);
    }

    /**
     * @param id
     * @return com.mahiru.phonebook.model.dto.ContactDTO
     * @author mahiru
     * @date 2024/12/13 15:26
     * @methodName selectContactById
     * @description 根据id查询联系人
     */
    @Override
    public ContactDTO selectContactById(Long id) {
        String sql = """
                SELECT 
                    c.id AS contact_id, 
                    c.name AS contact_name, 
                    c.phone AS contact_phone, 
                    c.email AS contact_email, 
                    c.address AS contact_address, 
                    c.birthday AS contact_birthday, 
                    c.description AS contact_description,
                    pt.id AS phone_type_id, 
                    ct.id AS contact_type_id,
                    c.created_time
                FROM contacts c
                LEFT JOIN contact_phone_types cpt ON c.id = cpt.contact_id
                LEFT JOIN phone_types pt ON cpt.phone_type_id = pt.id
                LEFT JOIN contact_contact_types cct ON c.id = cct.contact_id
                LEFT JOIN contact_types ct ON cct.contact_type_id = ct.id
                WHERE c.id = ? AND c.is_deleted = 0;
                """;

        return JDBCUtil.selectOne(sql, rs -> {
            try {
                // 从查询结果中获取联系人信息
                int contactId = rs.getInt("contact_id");
                String contactName = rs.getString("contact_name");
                String contactPhone = rs.getString("contact_phone");
                String contactEmail = rs.getString("contact_email");
                String contactAddress = rs.getString("contact_address");
                LocalDateTime contactBirthday = rs.getDate("contact_birthday") != null ? LocalDateTime.from(rs.getDate("contact_birthday").toLocalDate()) : null;
                String contactDescription = rs.getString("contact_description");
                LocalDateTime createdTime = rs.getTimestamp("created_time") != null ? rs.getTimestamp("created_time").toLocalDateTime() : null;

                // 从查询结果中获取类别ID
                Long phoneTypeId = rs.getLong("phone_type_id");
                Long contactTypeId = rs.getLong("contact_type_id");

                // 获取电话类型和联系人类别对象
                IPhoneTypeMapper phoneTypeMapper = new PhoneTypeMapperImpl();
                IContactTypeMapper contactTypeMapper = new ContactTypeMapperImpl();
                PhoneType phoneType = phoneTypeMapper.getById(Math.toIntExact(phoneTypeId));
                ContactType contactType = contactTypeMapper.getById(Math.toIntExact(contactTypeId));

                // 构建并返回DTO对象
                return ContactDTO.builder()
                        .id((long) contactId)
                        .name(contactName)
                        .phone(contactPhone)
                        .email(contactEmail)
                        .address(contactAddress)
                        .birthday(contactBirthday)
                        .description(contactDescription)
                        .phoneType(phoneType)  // 设置PhoneType对象
                        .contactType(contactType)  // 设置ContactType对象
                        .createdTime(createdTime)
                        .build();

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, id);
    }

    /**
     * @param contact
     * @return java.lang.Boolean
     * @author mahiru
     * @date 2024/12/13 18:11
     * @methodName updateContact
     * @description 更新联系人
     */
    @Override
    public Boolean updateContact(ContactDTO contactDTO) {
        System.out.println(contactDTO.toString());

        // 1. 更新联系人基本信息
        String updateContactSql = "UPDATE contacts SET name = ?, phone = ?, email = ?, address = ?, birthday = ?, description = ? WHERE id = ? AND is_deleted = 0";
        boolean isContactUpdated = JDBCUtil.update(updateContactSql,
                contactDTO.getName(),
                contactDTO.getPhone(),
                contactDTO.getEmail(),
                contactDTO.getAddress(),
                contactDTO.getBirthday(),
                contactDTO.getDescription(),
                contactDTO.getId());

        // 2. 更新联系人电话类别
        boolean isPhoneTypeUpdated = true;
        if (contactDTO.getPhoneType() != null) {
            String checkPhoneTypeSql = "SELECT COUNT(*) FROM contact_phone_types WHERE contact_id = ? AND is_deleted = 0";
            int count = JDBCUtil.querySingleResult(checkPhoneTypeSql, contactDTO.getId());

            if (count == 0) {
                String insertPhoneTypeSql = "INSERT INTO contact_phone_types (contact_id, phone_type_id) VALUES (?, ?)";
                isPhoneTypeUpdated = JDBCUtil.update(insertPhoneTypeSql, contactDTO.getId(), contactDTO.getPhoneType().getId());
            } else {
                String updatePhoneTypeSql = "UPDATE contact_phone_types SET phone_type_id = ? WHERE contact_id = ? AND is_deleted = 0";
                isPhoneTypeUpdated = JDBCUtil.update(updatePhoneTypeSql, contactDTO.getPhoneType().getId(), contactDTO.getId());
            }
        }

        // 3. 更新联系人类别
        boolean isContactTypeUpdated = true;
        if (contactDTO.getContactType() != null) {
            String checkContactTypeSql = "SELECT COUNT(*) FROM contact_contact_types WHERE contact_id = ? AND is_deleted = 0";
            int count = JDBCUtil.querySingleResult(checkContactTypeSql, contactDTO.getId());

            if (count == 0) {
                String insertContactTypeSql = "INSERT INTO contact_contact_types (contact_id, contact_type_id) VALUES (?, ?)";
                isContactTypeUpdated = JDBCUtil.update(insertContactTypeSql, contactDTO.getId(), contactDTO.getContactType().getId());
            } else {
                String updateContactTypeSql = "UPDATE contact_contact_types SET contact_type_id = ? WHERE contact_id = ? AND is_deleted = 0";
                isContactTypeUpdated = JDBCUtil.update(updateContactTypeSql, contactDTO.getContactType().getId(), contactDTO.getId());
            }
        }

        System.out.println("isContactTypeUpdated = " + isContactTypeUpdated);
        System.out.println("isPhoneTypeUpdated = " + isPhoneTypeUpdated);
        System.out.println("isContactUpdated = " + isContactUpdated);

        return isContactUpdated && isPhoneTypeUpdated && isContactTypeUpdated;
    }

    /**
     * @param
     * @return java.util.List<com.mahiru.phonebook.model.po.Contact>
     * @author mahiru
     * @date 2024/12/13 19:52
     * @methodName getContactsListByNameKeyWord
     * @description 根据关键字查询联系人列表
     */
    @Override
    public List<Contact> getContactsListByNameKeyWord(String keyword) {
        String sql = "SELECT id, name FROM contacts WHERE name LIKE ? AND is_deleted = 0 AND user_id = ?";

        // 调用通用查询方法，获取符合条件的联系人列表
        return JDBCUtil.selectList(
                sql, rs -> {
                    // 构造 Contact 对象
                    try {
                        return Contact.builder()
                                .id(rs.getLong("id"))
                                .name(rs.getString("name"))
                                .build();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }, "%" + keyword + "%", UserContext.getUserId()
        );
    }

    /**
     * @author mahiru
     * @date 2024/12/13 20:12
     * @methodName getContactsInfoList
     * @description 获得所有联系人详细信息
     * @param
     * @return java.util.List<com.mahiru.phonebook.model.dto.ContactDTO>
     */
    @Override
    public List<ContactDTO> getContactsInfoList() {
        String sql = """
                SELECT 
                    c.id AS contact_id, 
                    c.name AS contact_name, 
                    c.phone AS contact_phone, 
                    c.email AS contact_email, 
                    c.address AS contact_address, 
                    c.birthday AS contact_birthday, 
                    c.description AS contact_description,
                    pt.id AS phone_type_id, 
                    ct.id AS contact_type_id,
                    c.created_time
                FROM contacts c
                LEFT JOIN contact_phone_types cpt ON c.id = cpt.contact_id
                LEFT JOIN phone_types pt ON cpt.phone_type_id = pt.id
                LEFT JOIN contact_contact_types cct ON c.id = cct.contact_id
                LEFT JOIN contact_types ct ON cct.contact_type_id = ct.id
                WHERE user_id = ? AND c.is_deleted = 0;
                """;

        return JDBCUtil.selectList(sql, rs -> {
            try {
                // 从查询结果中获取联系人信息
                int contactId = rs.getInt("contact_id");
                String contactName = rs.getString("contact_name");
                String contactPhone = rs.getString("contact_phone");
                String contactEmail = rs.getString("contact_email");
                String contactAddress = rs.getString("contact_address");
                LocalDateTime contactBirthday = rs.getDate("contact_birthday") != null ? LocalDateTime.from(rs.getDate("contact_birthday").toLocalDate()) : null;
                String contactDescription = rs.getString("contact_description");
                LocalDateTime createdTime = rs.getTimestamp("created_time") != null ? rs.getTimestamp("created_time").toLocalDateTime() : null;

                // 从查询结果中获取类别ID
                Long phoneTypeId = rs.getLong("phone_type_id");
                Long contactTypeId = rs.getLong("contact_type_id");

                // 获取电话类型和联系人类别对象
                IPhoneTypeMapper phoneTypeMapper = new PhoneTypeMapperImpl();
                IContactTypeMapper contactTypeMapper = new ContactTypeMapperImpl();
                PhoneType phoneType = phoneTypeMapper.getById(Math.toIntExact(phoneTypeId));
                ContactType contactType = contactTypeMapper.getById(Math.toIntExact(contactTypeId));

                // 构建并返回DTO对象
                return ContactDTO.builder()
                        .id((long) contactId)
                        .name(contactName)
                        .phone(contactPhone)
                        .email(contactEmail)
                        .address(contactAddress)
                        .birthday(contactBirthday)
                        .description(contactDescription)
                        .phoneType(phoneType)  // 设置PhoneType对象
                        .contactType(contactType)  // 设置ContactType对象
                        .createdTime(createdTime)
                        .build();

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, UserContext.getUserId());
    }
}


