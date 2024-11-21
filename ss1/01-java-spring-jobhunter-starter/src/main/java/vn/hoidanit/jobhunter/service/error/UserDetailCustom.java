package vn.hoidanit.jobhunter.service.error;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;

@Component(value = "userDetailService")
public class UserDetailCustom implements UserDetailsService {
    private final UserService userService;

    public UserDetailCustom(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        User user=this.userService.handleGetUserByUsername(username);
return new user(user.getEmail(),
user.getPassword,
Collections.singleton(new SimpleGrantedAuthority("Role")))
    }

}
