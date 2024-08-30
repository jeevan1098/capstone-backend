package ust.capstone.User_Service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ust.capstone.User_Service.exception.UserNotFoundException;
import ust.capstone.User_Service.feign.OrderClient;
import ust.capstone.User_Service.model.User;
import ust.capstone.User_Service.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrderClient orderClient;

    public List<Map<String, Object>> getOrdersByUserId(String userId) {
        return orderClient.getOrdersByUserId(userId);
    }
    public User registerUser(User user) {
        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        return user;
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new UserNotFoundException("Invalid email or password");
    }

    public User updateUser(String email, User userDetails) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setUsername(userDetails.getUsername());
            user.setPassword(passwordEncoder.encode(userDetails.getPassword())); // Re-encrypt the new password
            user.setAddress(userDetails.getAddress());
            // Update other fields as needed
            return userRepository.save(user);
        }
        throw new UserNotFoundException("User with email " + email + " not found");
    }

    public boolean deleteUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        throw new UserNotFoundException("User with email " + email + " not found");
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }
}
