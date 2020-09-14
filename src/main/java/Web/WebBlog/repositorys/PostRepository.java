package Web.WebBlog.repositorys;

import Web.WebBlog.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post,Long> {

    List<Post> findByTitle(String filter);
}
