package vn.hoidanit.jobhunter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.dto.Meta;
import vn.hoidanit.jobhunter.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.dto.user.UserListDTO;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // pageAble.getnumber||getPageSize -> định nghĩa số trang ở FE và data json
    // pageList.getnumber||getsize -> lấy số trang ở DB mặc định page 0
    public ResultPaginationDTO getAllUser(Specification<User> spec, Pageable pageAble) {
        // TODO Auto-generated method stub
        Page<User> pageList = this.userRepository.findAll(spec, pageAble);
        List<User> uList = pageList.getContent();
        List<UserListDTO> uDTOList = new ArrayList<>();
        for (int i = 0; i < uList.size(); i++) {
            UserListDTO user = new UserListDTO();
            user.setId(uList.get(i).getId());
            user.setName(uList.get(i).getName());
            user.setEmail(uList.get(i).getEmail());
            user.setGender(uList.get(i).getGender());
            user.setAddress(uList.get(i).getAddress());
            user.setAge(uList.get(i).getAge());
            user.setCreatedAt(uList.get(i).getCreatedAt());
            uDTOList.add(user);
        }
        Page<UserListDTO> pageDTOList = new PageImpl<>(uDTOList);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        Meta mt = new Meta();
        mt.setPage(pageAble.getPageNumber() + 1);
        mt.setPageSize(pageAble.getPageSize());
        // mt.setPage(pageList.getNumber() + 1);
        // mt.setPageSize(pageList.getSize());
        mt.setPages(pageDTOList.getTotalPages());
        mt.setTotal(pageDTOList.getTotalElements());
        resultPaginationDTO.setMeta(mt);
        resultPaginationDTO.setResult(pageDTOList.getContent());
        return resultPaginationDTO;
    }

    public User handleCreateUser(User user) {
        // TODO Auto-generated method stub
        User userCreateService = this.userRepository.save(user);
        return userCreateService;
    }

    public User fetchAllUserById(Integer id) {
        // TODO Auto-generated method stub
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UnsupportedOperationException("Unimplemented method 'fetchAllUserById'");
    }

    public void handleDeleteUser(Integer id) {
        // TODO Auto-generated method stub
        User user = this.fetchAllUserById(id);
        this.userRepository.deleteById(user.getId());

    }

    public User handleGetUserByUsername(String username) {
        // TODO Auto-generated method stub
        return this.userRepository.findByEmail(username);

    }

}
