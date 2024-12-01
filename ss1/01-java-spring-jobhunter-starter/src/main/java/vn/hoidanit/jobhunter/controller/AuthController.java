package vn.hoidanit.jobhunter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.dto.LoginDTO;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.error.SecurityUtil;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;
    private SecurityUtil securityUtil;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, UserService userService,
            SecurityUtil securityUtil) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        if (loginDTO.getUsername().isEmpty() && loginDTO.getPassword().isEmpty()
                || loginDTO.getUsername() != null && loginDTO.getPassword().isEmpty()
                || loginDTO.getUsername().isEmpty() && loginDTO.getPassword() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginDTO);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        securityUtil.createToken(authentication);
        // User user = this.userService.handleGetUserByUsername(loginDTO.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(loginDTO);
    }
}
