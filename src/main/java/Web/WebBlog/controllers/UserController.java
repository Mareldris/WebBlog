package Web.WebBlog.controllers;

import Web.WebBlog.models.Role;
import Web.WebBlog.models.User;
import Web.WebBlog.repositorys.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String blogLogin(Model model) {

        return "users/login";
    }

    @PostMapping("/login")
    public String blogUserLogin(@RequestParam String username, @RequestParam String password,
                                Model model) {
        User user = new User(username,password);
        userRepository.save(user);

        return "redirect:/";
    }

    @GetMapping("/registration")
    public String blogRegistration(Model model) {

        return "users/registration";
    }

    @PostMapping("/registration")
    public String blogUserAdd(@RequestParam String username, @RequestParam String password,
                              Model model) {
        User usr = new User(username,password);
        usr.setActive(true);
        usr.setRoles(Collections.singleton(Role.User));
        userRepository.save(usr);

        return "redirect:/blog";
    }
}
