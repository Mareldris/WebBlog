package Web.WebBlog.controllers;

import Web.WebBlog.Service.UserService;
import Web.WebBlog.models.User;
import Web.WebBlog.repositorys.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/user")

public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PreAuthorize("hasAnyAuthority('Admin')")
    @GetMapping
    public String userList(Model model) {
        userService.usersList(model);
        return "users/userList";
    }
    @PreAuthorize("hasAnyAuthority('Admin')")
    @GetMapping("/{id}/edit")
    public String userEditForm(@PathVariable(value = "id") long id,
                               Model model) {
        userService.userListEditForm(id,model);
        return "/users/userEditor";
    }
    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping("/{id}/edit")
    public String UserSave(@PathVariable(value = "id") long id,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam(name = "roles", required = false) String[] role) {

        userService.userEditFormSave(id, username, password, email, role);

        return "redirect:/user";
    }


    @GetMapping("/{id}/profile")
    public String userProfile(@AuthenticationPrincipal User user,
                              Model model) {

        model.addAttribute("username",user.getUsername());
        model.addAttribute("email",user.getEmail());
        model.addAttribute("password",user.getPassword());

        return "/users/profile";
    }

    @PostMapping("/{id}/profile")
    public String UserProfileSave(
                           @AuthenticationPrincipal User user,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email) {
        userService.userProfileSave(user, username, password, email);
        return "redirect:/messageActivateEmail";
    }


}
