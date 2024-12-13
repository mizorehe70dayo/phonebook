package com.mahiru.phonebook.model.po;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @className BaseEntity
 * @description 实体类公共基层
 * @author mahiru
 * @date 2024/12/11 19:54
 * @version v1.0.0
**/
@Data
@SuperBuilder
public class BaseEntity {
    private Long id;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private Boolean isDeleted;
}
