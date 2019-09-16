package com.userstories.monitoring.service.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserActionLoggingService {

    /**
     * Логирование действия пользователя
     *
     * @param clientRequest  запрос клиента
     * @param serverResponse ответ сервера
     */
    void loggingAction(HttpServletRequest clientRequest, HttpServletResponse serverResponse);
}
