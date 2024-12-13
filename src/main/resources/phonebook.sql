use phonebook;

CREATE TABLE IF NOT EXISTS users (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志（0未删除，1已删除)'
    );

CREATE TABLE phone_types (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(50) NOT NULL COMMENT '手机号类型名称',
                             created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志（0未删除，1已删除)'
);

CREATE TABLE contact_types (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               name VARCHAR(50) NOT NULL COMMENT '联系人类别名称',
                               created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志（0未删除，1已删除)'
);

CREATE TABLE contacts (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          user_id INT NOT NULL COMMENT '所属用户 ID',
                          name VARCHAR(100) NOT NULL COMMENT '联系人姓名',
                          phone VARCHAR(20) NOT NULL COMMENT '联系人电话',
                          email VARCHAR(100) COMMENT '联系人邮箱',
                          address VARCHAR(255) COMMENT '联系人地址',
                          birthday DATE COMMENT '联系人生日',
                          description TEXT COMMENT '联系人描述',
                          created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志（0未删除，1已删除)',
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE contact_phone_types (
                                     contact_id INT NOT NULL COMMENT '联系人 ID',
                                     phone_type_id INT NOT NULL COMMENT '手机号类型 ID',
                                     created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志（0未删除，1已删除)',
                                     PRIMARY KEY (contact_id, phone_type_id),
                                     FOREIGN KEY (contact_id) REFERENCES contacts(id) ON DELETE CASCADE,
                                     FOREIGN KEY (phone_type_id) REFERENCES phone_types(id)
);
CREATE TABLE contact_contact_types (
                                       contact_id INT NOT NULL COMMENT '联系人 ID',
                                       contact_type_id INT NOT NULL COMMENT '联系人类别 ID',
                                       created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志（0未删除，1已删除)',
                                       PRIMARY KEY (contact_id, contact_type_id),
                                       FOREIGN KEY (contact_id) REFERENCES contacts(id) ON DELETE CASCADE,
                                       FOREIGN KEY (contact_type_id) REFERENCES contact_types(id)
);
INSERT INTO phone_types (name) VALUES
                                   ('个人'),
                                   ('工作');
