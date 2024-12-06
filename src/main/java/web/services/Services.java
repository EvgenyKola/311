package web.services;

import org.springframework.stereotype.Component;
import web.user.*;

import java.util.List;

@Component
public class Services {

    private final UserDAO userDao;

    public Services(UserDAO userDao) {
        this.userDao = userDao;
    }

    public User getUser(Long userId){
        return userDao.getUser(userId);
    };
    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    };
    public Integer deleteUser(Long userId){
        return userDao.deleteUser(userId);
    };
    public void createUser(User user) {
        userDao.createUser(user);
    };
    public void editUser(User user) {
        userDao.editUser(user);
    };

}
