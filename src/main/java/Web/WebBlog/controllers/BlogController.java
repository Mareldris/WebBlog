package Web.WebBlog.controllers;

import Web.WebBlog.models.Post;
import Web.WebBlog.models.User;
import Web.WebBlog.repositorys.PostRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Controller
public class BlogController {

    private final PostRepository postRepository;

    public BlogController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;


    @GetMapping("/blog")
    public String BlogPage(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String BlogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String BlogAddd(@AuthenticationPrincipal User user,
                           @RequestParam String title,
                           @RequestParam String anons,
                           @RequestParam String full_text,
                           @RequestParam("file") MultipartFile file,
                           Model model) throws IOException {
        Post post = new Post(title,anons,full_text,user);

        AddFile(file, post);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("filter")
    public String BlogFilter(@RequestParam String filter,  Model model) {
        Iterable<Post> posts;
        if (filter != null && !filter.isEmpty()){
            posts= postRepository.findByTitle(filter); }
        else{
            posts = postRepository.findAll();
        }
        model.addAttribute("posts",posts);
        return "blog-main";
    }

    @GetMapping("/blog/{id}")
    public String BlogDetails(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("post",result);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String BlogEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("post",result);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String BlogEditSave(@PathVariable(value = "id") long id,
                               @RequestParam String title,
                               @RequestParam String anons,
                               @RequestParam String full_text,
                               @RequestParam("file") MultipartFile file,
                               Model model) throws IOException {
        Post post = postRepository.findById(id).orElseThrow(IllegalAccessError::new);
        deleteFile(post);
        AddFile(file, post);

        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }


    @PostMapping("/blog/{id}/remove")
    public String BlogEditRemove(@PathVariable(value = "id") long id,
                                 Model model) {

        Post post = postRepository.findById(id).orElseThrow(IllegalAccessError::new);

        deleteFile(post);

        postRepository.delete(post);
        return "redirect:/blog";
    }

    private void AddFile(@RequestParam("file") MultipartFile file, Post post) throws IOException {
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

    private void deleteFile(Post post){
        File pictureFile = new File(uploadPath+"/"+post.getFilename());
        if (!post.getFilename().isEmpty()){
            pictureFile.delete();}
    }
}
