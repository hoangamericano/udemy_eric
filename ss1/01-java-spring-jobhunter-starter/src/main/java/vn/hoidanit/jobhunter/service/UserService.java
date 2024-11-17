package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUser() {
        // TODO Auto-generated method stub
        return this.userRepository.findAll();
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
        this.userRepository.deleteById(id);

        throw new UnsupportedOperationException("Unimplemented method 'handleDeleteUser'");
    }

}
