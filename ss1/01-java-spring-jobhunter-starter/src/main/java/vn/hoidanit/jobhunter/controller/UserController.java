package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.anotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    @ApiMessage("Fetching all user")
    public ResponseEntity<ResultPaginationDTO> getListUser(@Filter Specification<User> spec, Pageable pageable) {
        // String sCurrentOptional = currentOptional.isPresent() ? currentOptional.get()
        // : "";
        // String sPageSize = pageSizeOptional.isPresent() ? pageSizeOptional.get() :
        // "";
        // Integer current = Integer.parseInt(sCurrentOptional);
        // Integer page = Integer.parseInt(sPageSize);

        // Pageable pageable = PageRequest.of(current - 1, page);
        // Page<User> uList = this.userService.getAllUser(spec, pageable);
        ResultPaginationDTO rs = this.userService.getAllUser(spec, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(rs);

    }

    @PostMapping("/users/create/")
    @ApiMessage("Fetching create user")
    public ResponseEntity<User> getMethodName(@RequestBody User user) {
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        User userCreate = this.userService.handleCreateUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreate);
    }

    @DeleteMapping("/users/delete/{id}")
    @ApiMessage("Fetching delete user")
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