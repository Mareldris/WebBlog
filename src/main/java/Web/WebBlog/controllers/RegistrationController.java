package Web.WebBlog.controllers;

import Web.WebBlog.Service.MailService;
import Web.WebBlog.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final MailService mailService;

    public RegistrationController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }



    @GetMapping("/login")
    public String blogLogin() { return "users/login"; }

    @PostMapping("/login")
    public String blogUserLogin(@RequestParam String username, @RequestParam String password) {

        userService.newUser(username,password);
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String blogRegistration() { return "users/registration"; }

    @PostMapping("/registration")
    public String blogUserAdd(@RequestParam String email,
                              @RequestParam String username,
                              @RequestParam String password,
                              Model model) {

        userService.userRegistration(email, username, password, model);
        return "/users/messageActivateEmail";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code,
                           Model model) {
        userService.isActivated(code,model);
        return "users/messageIsActivatedEmail";
    }


    @GetMapping("/messageActivateEmail")
    public String activateCodeMessageEmail() {
        return "/users/messageActivateEmail";
    }
}
