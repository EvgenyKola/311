package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import web.user.*;
import web.services.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class UserController {

    private final Services services;

    public UserController(Services services) {
        this.services = services;
    }

    @GetMapping(value = "/create")
    public String createUser(ModelMap model, HttpServletRequest request) {

        try {
            String name = request.getParameter("name");
            String lastName = request.getParameter("lastName");
            String age = request.getParameter("age");

            if (name != null && lastName != null && age != null) {
                services.createUser(new User(name, lastName, Byte.parseByte(age)));
                model.addAttribute("message", "Пользователь " + name + " создан");
            }


        }
        catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Пользователь не создан");
        }

        return "create";
    }

    @GetMapping(value = "/delete")
    public String deleteUsers(ModelMap model, HttpServletRequest request) {

        String idDelete = request.getParameter("id");

        if (idDelete != null && !idDelete.isEmpty()) {
            String result = services.deleteUser(Long.parseLong(idDelete)) == 1 ? "Пользователь удален" : "Пользователь не найден";
            model.addAttribute("delete", result);
        }

        return "delete";
    }

    @GetMapping(value = "/edit")
    public String editUser(ModelMap model, HttpServletRequest request) {

        try {
            String idParam = request.getParameter("id");
            if (idParam == null) {
                model.addAttribute("message", "ID пользователя отсутствует");
                return "edit";
            }
            Long id = Long.parseLong(idParam);

            String update = request.getParameter("update");
            if (update == null) {
                User user = services.getUser(id);
                if (user != null) {
                    model.addAttribute("user", user);
                } else {
                    model.addAttribute("message", "Пользователь не найден");
                }
            } else {

                String name = request.getParameter("name");
                String lastName = request.getParameter("lastName");
                String ageParam = request.getParameter("age");

                if (name != null && lastName != null && ageParam != null) {
                    try {
                        Byte age = Byte.parseByte(ageParam);
                        services.editUser(new User(id, name, lastName, age));
                        User user = services.getUser(id);
                        model.addAttribute("user", user);
                        model.addAttribute("message", "Пользователь обновлен");
                    } catch (NumberFormatException e) {
                        model.addAttribute("message", "Некорректный формат возраста");
                    }
                } else {
                    model.addAttribute("message", "Отсутствуют данные для обновления");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Возникла ошибка при обработке запроса");
        }

        return "edit";
    }

    @GetMapping(value = "/")
    public String deleteUsers(ModelMap model) {

        List<User> users = services.getAllUsers();

        model.addAttribute("users", users);

        return "user";
    }

}

