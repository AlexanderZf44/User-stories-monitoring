package com.userstories.monitoring.domain.users;

import javax.persistence.Entity;
import com.userstories.monitoring.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserRole extends BaseEntity {

    /**
     * Наименование ролей
     */
    private String name;

    /**
     * Описание ролей
     */
    private String description;
}
