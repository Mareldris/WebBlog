package Web.WebBlog.Service;

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
}
/*
<div sec:authorize="isAuthenticated()">
<form th:action="@{/logout}" method="post" sec:authorize="isAuthenticated()">
<input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}"/>
<div  sec:authentication="name">Unknown</div>
<button class="btn btn-outline-success" type="submit">Выйти</button>
</form>
</div>
<div sec:authorize="!isAuthenticated()">
<a class="btn btn-outline-success" href="/login">Авторизоваться</a> <!-- Сделать Выход -->
</div>*/
