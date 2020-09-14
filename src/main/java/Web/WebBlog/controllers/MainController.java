package Web.WebBlog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String defaultPage(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }
    @GetMapping("/blog")
    public String blogMain(Model model) {
        model.addAttribute("title", "Привет!");
        return "/blog-main";
    }

}