package Web.WebBlog.repositorys;

import Web.WebBlog.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

}
