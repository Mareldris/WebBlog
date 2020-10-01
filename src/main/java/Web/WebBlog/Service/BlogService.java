package Web.WebBlog.Service;

import Web.WebBlog.models.Post;
import Web.WebBlog.models.User;
import Web.WebBlog.repositorys.PostRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class BlogService {


    private final PostRepository postRepository;

    public BlogService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;


    public void filter(String filter, Model model) {
        Iterable<Post> posts;
        if (filter != null && !filter.isEmpty()){
            posts= postRepository.findByTitle(filter); }
        else{
            posts = postRepository.findAll();
        }
        model.addAttribute("posts",posts);
    }

    public String ListPosts(Long id,Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("post",result);
        return "blog-details";
    }

    public void deleteFile(Post post){
        if (post.getFilename() != null) {
            File pictureFile = new File(uploadPath + "/" + post.getFilename());
            if (!post.getFilename().isEmpty()) {
                pictureFile.delete();
            }
        }
    }

    public void AddFile(@RequestParam("file") MultipartFile file, Post post) throws IOException {
        if (file!=null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);

            if (uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();

            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File( uploadPath + "/" + resultFileName));

            post.setFilename(resultFileName);
        }
    }

    public void blogFindAll(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
    }

    public void blogSave(Post post) {
        postRepository.save(post);
    }

    public void blogEditSave(@PathVariable(value = "id") long id,
                             @RequestParam String title,
                             @RequestParam String anons,
                             @RequestParam String full_text,
                             @RequestParam("file") MultipartFile file) throws IOException {

        Post post = postRepository.findById(id).orElseThrow(IllegalAccessError::new);
        deleteFile(post);
        AddFile(file, post);
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        blogSave(post);
    }

    public void blogEditRemove(long id) {
        Post post = postRepository.findById(id).orElseThrow(IllegalAccessError::new);
        deleteFile(post);
        postRepository.delete(post);
    }

    public void blogAddPost(@AuthenticationPrincipal User user,
                            @RequestParam String title,
                            @RequestParam String anons,
                            @RequestParam String full_text,
                            @RequestParam("file") MultipartFile file) throws IOException {
        Post post = new Post(title,anons,full_text,user);
        AddFile(file, post);
        blogSave(post);
    }
}
