package ust.capstone.User_Service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ust.capstone.User_Service.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}
