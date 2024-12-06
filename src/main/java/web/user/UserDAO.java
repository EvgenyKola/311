package web.user;

import org.springframework.stereotype.Component;

import java.util.List;

public interface UserDAO {
    public User getUser(Long userId);
    public List<User> getAllUsers();
    public Integer deleteUser(Long userId);
    public void createUser(User user);
    public void editUser(User user);
}
