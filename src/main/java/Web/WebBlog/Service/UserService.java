package Web.WebBlog.Service;

import Web.WebBlog.models.User;
import Web.WebBlog.repositorys.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        user.setActivationCode(null);

        userRepository.save(user);
        return true;
    }
}


