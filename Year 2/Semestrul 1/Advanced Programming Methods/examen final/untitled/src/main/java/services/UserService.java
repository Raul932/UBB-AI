package services;

import domain.User;
import repositories.UserRepository;

import java.util.List;

public class UserService {

    public static List<User> getAllUsers() {
        return UserRepository.getAllUsers();
    }
}
