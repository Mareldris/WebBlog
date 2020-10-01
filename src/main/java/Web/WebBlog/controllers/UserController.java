package Web.WebBlog.controllers;

import Web.WebBlog.Service.UserService;
import Web.WebBlog.repositorys.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAnyAuthority('Admin')")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model) {
        userService.usersList(model);
        return "users/userList";
    }

    @GetMapping("/{id}/edit")
    public String userEditForm(@PathVariable(value = "id") long id, Model model) {
        userService.userListEditForm(id,model);
        return "/users/userEditor";
    }

    @PostMapping("/{id}/edit")
    public String UserSave(@PathVariable(value = "id") long id,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam(name = "roles", required = false) String[] role) {

        userService.userEditFormSave(id, username, password, email, role);

        return "redirect:/user";
    }


}
