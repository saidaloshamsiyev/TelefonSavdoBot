package uz.pdp.service;


import uz.pdp.exception.DataNotFoundException;
import uz.pdp.model.Phone;
import uz.pdp.model.User;
import uz.pdp.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class UserService extends BaseService<User, UserRepository> {

    public static final UserService UserService = new UserService();

    public static UserService getInstance() {
        return UserService;
    }

    public UserService() {
        super(UserRepository.getInstance());
    }

    public User findByChatId(Long chatId) {
        Optional<User> userOptional = repository.findByChatId(chatId);

        return userOptional.orElseThrow(() -> {
            return new DataNotFoundException("user with this chat {} is not found");
        });
    }

   public void update(UUID id,User user){
        repository.update(id,user);
   }
}

