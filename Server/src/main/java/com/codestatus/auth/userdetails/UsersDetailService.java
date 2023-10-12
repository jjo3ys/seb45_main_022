package com.codestatus.auth.userdetails;

import com.codestatus.auth.utils.CustomAuthorityUtils;
import com.codestatus.domain.user.entity.User;
import com.codestatus.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Component
public class UsersDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CustomAuthorityUtils authorityUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No sign-up information found for email"));
        return new CustomUserDetails(user);
    }
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No sign-up information found for email"));
    }
    public class CustomUserDetails extends User implements UserDetails {

        CustomUserDetails(User user) {
            setUserId(user.getUserId());
            setEmail(user.getEmail());
            setPassword(user.getPassword());
            setRoles(user.getRoles());
            setUserStatus(user.getUserStatus());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> authorities =  authorityUtils.createAuthorities(getRoles());
            return authorities;
        }

        @Override
        public String getUsername() { // 유저 이름
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() { // 계정 만료 여부
            return true;
        }

        @Override
        public boolean isAccountNonLocked() { // 계정 잠금 여부
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() { // 계정 패스워드 만료 여부
            return true;
        }

        @Override
        public boolean isEnabled() { // 계정 활성화 여부
            return true;
        }
    }
}
