package com.userstories.monitoring.util.filter;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import com.userstories.monitoring.service.user.UserActionLoggingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.userstories.monitoring.util.constants.UserActionUrlCodes.EXAMPLE_REQUEST;

/**
 * Фильтр логирования действий пользователей в системе для конкретных URL адресов
 */
@Slf4j
public class UserActionLoggingFilter extends OncePerRequestFilter {

    private static final String UNIVERSAL_URL_PATTERN = "/**/%s";

    private final UserActionLoggingService userActionLoggingService;

    /**
     * Список URL адресов, необходимых для логирования
     */
    private RequestMatcher includedRequests;

    /**
     * Заполнение URL адресов при инициализации фильтра,
     * по которым необходимо логировать действия пользователей
     */
    public UserActionLoggingFilter(UserActionLoggingService userActionLoggingService) {
        this.userActionLoggingService = userActionLoggingService;

        /*
         * Запрос для примера логирования
         */
        RequestMatcher exampleRequest = new AntPathRequestMatcher(String.format(
                UNIVERSAL_URL_PATTERN,
                EXAMPLE_REQUEST
        ));

        includedRequests = new OrRequestMatcher(
                exampleRequest
        );
    }

    /**
     * Основной метод обработки запрсов пользователей
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.debug("Logging user request {} : {}", request.getMethod(), request.getRequestURI());

        /*
         * Вызываем chain фильтрацию для получения данных ответа нашего сервера
         */
        filterChain.doFilter(request, response);

        /*
         * Проверка на совпадение с разрешенными для логирования запросами
         */
        if (includedRequests.matches(request) && !Objects.equals(HttpMethod.OPTIONS, request.getMethod())) {

            /*
             * Бизнес логика логирования действий пользователя
             */
            userActionLoggingService.loggingAction(request, response);
        }

        log.debug(
                "Logging server response with status {} : {} ",
                response.getStatus(),
                response.getOutputStream()
        );
    }

    @Override
    public void destroy() {
    }
}
