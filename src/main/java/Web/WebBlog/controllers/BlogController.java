package Web.WebBlog.controllers;

import Web.WebBlog.Service.BlogService;
import Web.WebBlog.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }


    @GetMapping
    public String BlogPage(Model model) {
        blogService.blogFindAll(model);
        return "blog-main";
    }

    @GetMapping("/add")
    public String BlogAdd() {
        return "blog-add";
    }

    @PostMapping("/add")
    public String BlogAddd(@AuthenticationPrincipal User user,
                           @RequestParam String title,
                           @RequestParam String anons,
                           @RequestParam String full_text,
                           @RequestParam("file") MultipartFile file) throws IOException
    {
        blogService.blogAddPost(user, title, anons, full_text, file);
        return "redirect:/blog";
    }

    @PostMapping("/filter")
    public String BlogFilter(@RequestParam String filter,  Model model) {
        blogService.filter(filter,model);
        return "blog-main";
    }

    @GetMapping("/{id}")
    public String BlogDetails(@PathVariable(value = "id") long id, Model model) {
        blogService.ListPosts(id,model);
        return "blog-details";
    }

    @GetMapping("/{id}/edit")
    public String BlogEdit(@PathVariable(value = "id") long id, Model model) {
        blogService.ListPosts(id,model);
        return "blog-edit";
    }

    @PostMapping("/{id}/edit")
    public String BlogEditSave(@PathVariable(value = "id") long id,
                               @RequestParam String title,
                               @RequestParam String anons,
                               @RequestParam String full_text,
                               @RequestParam("file") MultipartFile file) throws IOException {
        blogService.blogEditSave(id, title, anons, full_text, file);
        return "redirect:/blog";
    }


    @PostMapping("/{id}/remove")
    public String BlogEditRemove(@PathVariable(value = "id") long id) {
        blogService.blogEditRemove(id);
        return "redirect:/blog";
    }
}
