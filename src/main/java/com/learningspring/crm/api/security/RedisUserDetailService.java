package com.learningspring.crm.api.security;


import com.learningspring.crm.data.DataStorage;
import com.learningspring.crm.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class RedisUserDetailService implements UserDetailsService {

    private final DataStorage db;

    @Autowired
    public RedisUserDetailService(@Qualifier("redisDataStorage") DataStorage db) {
        this.db = db;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = db.getUserByUsername(s);
        return new UserDetailsImpl(user);
    }

    private static class UserDetailsImpl implements UserDetails {

        private final String username;
        private final String password;
        private final boolean isEnabled = true;
        private final boolean isAccountNotExpired = true;
        private final boolean isAccountNotLocked = true;
        private final boolean isCredentialsNotExpired = true;
        private final Collection<AuthorityImpl> authorities = new ArrayList<>();


        public UserDetailsImpl(User user) {
            this.username = user.getUsername();
            this.password = user.getPassword();

            authorities.addAll(user.getAuthorities()
                    .stream()
                    .map(AuthorityImpl::new)
                    .collect(Collectors.toList()));
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return isAccountNotExpired;
        }

        @Override
        public boolean isAccountNonLocked() {
            return isAccountNotLocked;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return isCredentialsNotExpired;
        }

        @Override
        public boolean isEnabled() {
            return isEnabled;
        }
    }

    private static class AuthorityImpl implements GrantedAuthority {

        private final String authority;

        public AuthorityImpl(String authority) {
            this.authority = authority;
        }

        @Override
        public String getAuthority() {
            return authority;
        }
    }
}
