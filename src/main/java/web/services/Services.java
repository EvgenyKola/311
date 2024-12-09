package web.services;

import org.springframework.ui.ModelMap;
import web.Model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface Services {

    User getUser(Long userId);

    List<User> getAllUsers();

    Integer deleteUser(Long userId);

    void createUser(User user);

    void editUser(User user);

    String editUserService(ModelMap model, HttpServletRequest request);
}
