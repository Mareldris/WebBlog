package Web.WebBlog.Service;

import Web.WebBlog.models.Role;
import Web.WebBlog.models.User;
import Web.WebBlog.repositorys.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;

    private final MailService mailService;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, MailService mailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    public  String userRegistration(@RequestParam String email,
                                    @RequestParam String username,
                                    @RequestParam String password,
                                    Model model) {
        
        User user = new User(email,username,password);
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null){
            model.addAttribute("message", "Пользователь существует");
            return "users/registration";
        }

        user.setRoles(Collections.singleton(Role.User));
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        sendMessage(user);
        userRepository.save(user);

        return "redirect:/blog";
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean activateUser(String code) {

        User user = userRepository.findByActivationCode(code);

        if (user==null){
            return false;
        }
        user.setActive(true);

        user.setActivationCode(null);

        userRepository.save(user);
        return true;
    }

    public void newUser(String username, String password) {
        User user = new User(username,password);
        userRepository.save(user);
    }

    public void isActivated(String code,
                            Model model)
    {
        boolean isActivated = activateUser(code);
        if (isActivated){
            model.addAttribute("message", "Аккаунт активирован!");
        }else {
            model.addAttribute("message","Введен не верный код активации");
        }
    }

    public void usersList(Model model) {
        model.addAttribute("users", userRepository.findAll());
    }

    public void userListEditForm(long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> result = new ArrayList<>();
        user.ifPresent(result::add);
        model.addAttribute("user", result);
        model.addAttribute("roles", Role.values());
    }

    public void userEditFormSave(long id, String username, String password, String email, String[] role) {
        User user = userRepository.findById(id).orElseThrow(IllegalAccessError::new);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        user.getRoles().clear();

        if (role != null) {
            Arrays.stream(role).forEach(r -> user.getRoles().add(Role.valueOf(r)));
        }

        userRepository.save(user);
    }

    public String userProfileSave(User user, String username, String password, String email) {

        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged){
            user.setEmail(email);
            if (!StringUtils.isEmpty(email)){
                user.setActivationCode(UUID.randomUUID().toString());
            }
            user.setActive(false);
            sendMessage(user);
        }

        if (!StringUtils.isEmpty(password)){
                user.setPassword(password);
            }
        if (!StringUtils.isEmpty(username)){
            user.setUsername(username);
        }

        userRepository.save(user);
        if (isEmailChanged){
            return "/users/messageActivateEmail";
        }
        return "/users/profile";

    }

    public void sendMessage(User user){
        if (!StringUtils.isEmpty(user.getEmail())){

            String message = String.format("Здравствуйте, %s! \n" + "Добро пожаловать! " +
                            "Пожалуйста, перейдите по этой ссылке для потверждения регистрации, " +
                            "http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailService.send(user.getEmail(), "Код активации", message);
        }
    }
}


