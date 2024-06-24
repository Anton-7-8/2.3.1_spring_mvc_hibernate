package web.dao;
import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> getUserById(long id);

    List<User> showAllUsers();

    void createNewUser(User user);

    void updateUser(User user);

    void removeUserById(long id);
}
