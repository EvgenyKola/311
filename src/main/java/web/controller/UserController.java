package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import web.Model.*;
import web.services.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    private final ServicesImplements services;

    public UserController(ServicesImplements services) {
        this.services = services;
    }

    @GetMapping(value = "/create")
    public String createUser(ModelMap model, HttpServletRequest request) {
        String name = request.getParameter("name");
        String lastName = request.getParameter("lastName");
        String age = request.getParameter("age");

        if (name == null) {
            throw new IllegalArgumentException("Поле 'name' не найдено");
        }
        if (lastName == null) {
            throw new IllegalArgumentException("Поле 'lastName' не найдено");
        }
        if (age == null) {
            throw new IllegalArgumentException("Поле 'age' не найдено");
        }

        try {
            services.createUser(new User(name, lastName, Byte.parseByte(age)));
            model.addAttribute("message", "Пользователь " + name + " создан");
        } catch (NumberFormatException e) {
            model.addAttribute("message", "Некорректный формат возраста");
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", e.getMessage());
        }

        return "create";
    }

    @GetMapping(value = "/delete")
    public String deleteUsers(ModelMap model, HttpServletRequest request) {
        String idDelete = request.getParameter("id");

        if (idDelete == null || idDelete.isEmpty()) {
            throw new IllegalArgumentException("Поле 'id' не найдено");
        }

        try {
            String result = services.deleteUser(Long.parseLong(idDelete)) == 1 ? "Пользователь удален" : "Пользователь не найден";
            model.addAttribute("delete", result);
        } catch (NumberFormatException e) {
            model.addAttribute("delete", "Некорректный формат ID");
        } catch (IllegalArgumentException e) {
            model.addAttribute("delete", e.getMessage());
        }

        return "delete";
    }

    @GetMapping(value = "/edit")
    public String editUser(ModelMap model, HttpServletRequest request) {
        return services.editUserService (model, request);
    }

    @GetMapping(value = "/")
    public String listUsers(ModelMap model) {
        List<User> users = services.getAllUsers();
        model.addAttribute("users", users);
        return "user";
    }
}
