package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
// @RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public List<User> getListUser() {
        List<User> uList = userService.getAllUser();
        return uList;

    }

    @PostMapping("/users/create/")
    public ResponseEntity<User> getMethodName(@RequestBody User user) {
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        User userCreate = this.userService.handleCreateUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreate);
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<String> deleteMethod(@PathVariable("id") Integer id) throws IdInvalidException {
        if (id >= 1500) {
            throw new IdInvalidException("Id khong ton tai");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/users/findUser/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable("id") Integer id) throws IdInvalidException {

        User userFindId = this.userService.fetchAllUserById(id);
        if (userFindId.getId() >= 1500) {
            throw new IdInvalidException("Id invalid");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userFindId);

    }
}