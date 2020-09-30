package Web.WebBlog.controllers;

import Web.WebBlog.models.Post;
import Web.WebBlog.models.Role;
import Web.WebBlog.models.User;
import Web.WebBlog.repositorys.UserRepository;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAnyAuthority('Admin')")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/userList";
    }

    @GetMapping("/{id}/edit")
    public String userEditForm(@PathVariable(value = "id") long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> result = new ArrayList<>();
        user.ifPresent(result::add);
        model.addAttribute("user", result);
        model.addAttribute("roles", Role.values());
        return "/users/userEditor";
    }

    @PostMapping("/{id}/edit")
    public String UserSave(@PathVariable(value = "id") long id,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam(name = "roles", required = false) String[] role,
                           //@RequestParam("userId") User user,
                           Model model) {

        User user = userRepository.findById(id).orElseThrow(IllegalAccessError::new);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        user.getRoles().clear();

        if (role != null) {
            Arrays.stream(role).forEach(r -> user.getRoles().add(Role.valueOf(r)));
        }

        userRepository.save(user);
        return "redirect:/user";
    }


}
