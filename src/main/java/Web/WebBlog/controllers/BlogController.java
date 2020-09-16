package Web.WebBlog.controllers;

import Web.WebBlog.models.Post;
import Web.WebBlog.models.User;
import Web.WebBlog.repositorys.PostRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    private final PostRepository postRepository;

    public BlogController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


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
                           Model model) {
        Post post = new Post(title,anons,full_text,user);
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
                               @RequestParam String title,@RequestParam String anons,@RequestParam String full_text,
                               Model model) {
        Post post = postRepository.findById(id).orElseThrow(IllegalAccessError::new);
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String BlogEditRemove(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow(IllegalAccessError::new);
        postRepository.delete(post);
        return "redirect:/blog";
    }


}
