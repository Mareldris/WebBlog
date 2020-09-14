package Web.WebBlog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {

    @GetMapping("/blog")
    public String BlogPage(Model model) {
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String BlogAdd(Model model) {
        return "blog-add";
    }


}
