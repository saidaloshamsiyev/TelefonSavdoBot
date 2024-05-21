package uz.pdp.repository;

import uz.pdp.model.Phone;
import uz.pdp.model.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserRepository extends BaseRepository<User> {


    public static final UserRepository userRepository = new UserRepository();

    public static UserRepository getInstance() {
        return userRepository;
    }

    public UserRepository() {
        super.path = "src/main/resources/users.json";
        super.type = User.class;
    }


    public Optional<User> findByChatId(Long chatId) {
        return getAll().stream().filter((user) -> Objects.equals(user.getChatId(), chatId)).findFirst();
    }


    public void update(UUID id, User user) {
        ArrayList<User> users = readValue();
        int i = 0;
        for (User user1 : users) {
            if (user1.getId().equals(id)) {
                users.set(i, user);
                break;
            }
            i++;
        }
        writeValue(users);
    }
}
