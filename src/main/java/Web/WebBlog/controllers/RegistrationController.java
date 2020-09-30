package Web.WebBlog.controllers;

import Web.WebBlog.Service.MailService;
import Web.WebBlog.Service.UserService;
import Web.WebBlog.models.Role;
import Web.WebBlog.models.User;
import Web.WebBlog.repositorys.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.UUID;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final MailService mailService;

    public RegistrationController(UserRepository userRepository, UserService userService, MailService mailService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
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
    public String blogUserAdd(@RequestParam String email,@RequestParam String username, @RequestParam String password,
                              Model model) {
        User usr = new User(email,username,password);
        User userFromDb = userRepository.findByUsername(usr.getUsername());

        if (userFromDb != null){
            model.addAttribute("message", "Пользователь существует");
            return "users/registration";
        }
        usr.setRoles(Collections.singleton(Role.User));
        usr.setActive(true);
        usr.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(usr);
        if (!StringUtils.isEmpty(usr.getEmail())){

            String message = String.format("Здравствуйте, %s! \n" + "Добро пожаловать! " +
                    "Пожалуйста, перейдите по этой ссылке для потверждения регистрации, " +
                    "http://localhost:8080/activate/%s",
            usr.getUsername(),
            usr.getActivationCode()
            );


        mailService.send(usr.getEmail(), "Код активации", message);
        }

        return "redirect:/blog";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated){
            model.addAttribute("message", "Аккаунт активирован!");
        }else {
            model.addAttribute("message","Введен не верный код активации");
        }
        return "/users/login";
    }
}
