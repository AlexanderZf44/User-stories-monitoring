package com.userstories.monitoring.service.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import com.userstories.monitoring.domain.users.UserRole;
import com.userstories.monitoring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
@RequiredArgsConstructor
public class DbUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(final String username) {
        com.userstories.monitoring.domain.users.User user = userRepository.findByUsername(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }

        List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
        Set<GrantedAuthority> setAuth = new HashSet<>();

        for (UserRole userRole : userRoles) {
            setAuth.add(new SimpleGrantedAuthority(userRole.getName()));
        }

        return new ArrayList<>(setAuth);
    }

    private User buildUserForAuthentication(
            com.userstories.monitoring.domain.users.User user,
            List<GrantedAuthority> authorities
    ) {
        return new User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserById(final String userId) {

        final com.userstories.monitoring.domain.users.User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User %s not found",
                        userId
                )));

        List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }
}
