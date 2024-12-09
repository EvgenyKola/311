package web.services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import web.Model.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
public class ServicesImplements implements Services {

    private final UserDAO userDao;

    public ServicesImplements(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public User getUser(Long userId){
        return userDao.getUser(userId);
    };

    @Transactional
    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    };

    @Transactional
    public Integer deleteUser(Long userId){
        return userDao.deleteUser(userId);
    };

    @Transactional
    public void createUser(User user) {
        userDao.createUser(user);
    };

    @Transactional
    public void editUser(User user) {
        userDao.editUser(user);
    };

    @Transactional
    public String editUserService (ModelMap model, HttpServletRequest request) {
        String idParam = request.getParameter("id");

        if (idParam == null) {
            model.addAttribute("message", "ID пользователя отсутствует");
            return "edit";
        }

        Long id;
        try {
            id = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            model.addAttribute("message", "Некорректный формат ID");
            return "edit";
        }

        String update = request.getParameter("update");
        if (update == null) {
            User user = userDao.getUser(id);
            if (user != null) {
                model.addAttribute("user", user);
            } else {
                model.addAttribute("message", "Пользователь не найден");
            }
        } else {
            String name = request.getParameter("name");
            String lastName = request.getParameter("lastName");
            String ageParam = request.getParameter("age");

            if (name == null) {
                throw new IllegalArgumentException("Поле 'name' не найдено");
            }
            if (lastName == null) {
                throw new IllegalArgumentException("Поле 'lastName' не найдено");
            }
            if (ageParam == null) {
                throw new IllegalArgumentException("Поле 'age' не найдено");
            }

            try {
                Byte age = Byte.parseByte(ageParam);
                userDao.editUser(new User(id, name, lastName, age));
                User user = userDao.getUser(id);
                model.addAttribute("user", user);
                model.addAttribute("message", "Пользователь обновлен");
            } catch (NumberFormatException e) {
                model.addAttribute("message", "Некорректный формат возраста");
            } catch (IllegalArgumentException e) {
                model.addAttribute("message", e.getMessage());
            }
        }

        return "edit";
    }
}
