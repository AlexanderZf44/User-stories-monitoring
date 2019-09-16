package com.userstories.monitoring.service.user.impl;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.userstories.monitoring.domain.users.User;
import com.userstories.monitoring.domain.users.UserAction;
import com.userstories.monitoring.repository.UserActionRepository;
import com.userstories.monitoring.repository.UserRepository;
import com.userstories.monitoring.service.user.UserActionLoggingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserActionLoggingServiceImpl implements UserActionLoggingService {

    private final UserRepository       userRepository;
    private final UserActionRepository userActionRepository;

    @Override
    public void loggingAction(HttpServletRequest clientRequest, HttpServletResponse serverResponse) {

        final User currentUser = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .map(userRepository::findByUsername)
                .orElse(null);

        if (Objects.isNull(currentUser)) {
            return;
        }

        final UserAction userAction = new UserAction();

        userAction.setUserId(currentUser.getId())
                .setRequest(String.format("%s : %s", clientRequest.getMethod(), clientRequest.getRequestURI()))
                .setSessionId(clientRequest.getRequestedSessionId())
                .setResponseStatus((long) serverResponse.getStatus())
                .setActionTime(LocalDateTime.now());

        userActionRepository.saveAndFlush(userAction);
    }
}
