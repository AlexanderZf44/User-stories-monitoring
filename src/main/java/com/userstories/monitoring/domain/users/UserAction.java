package com.userstories.monitoring.domain.users;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import com.userstories.monitoring.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Объект для сбора статистики действий пользователей
 */
@Data
@Entity
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserAction extends BaseEntity {

    /**
     * Идентификатор пользователя
     */
    private Long userId;

    /**
     * Запрос пользователя
     */
    private String request;

    /**
     * Статус ответа сервера
     */
    private Long responseStatus;

    /**
     * Идентификатор сессии пользователя
     */
    private String sessionId;

    /**
     * Время действия пользователя
     */
    private LocalDateTime actionTime;
}
