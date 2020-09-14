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
    @GetMapping("/about")
    public String blogMain(Model model) {
        model.addAttribute("title", "Привет!");
        return "about";
    }

}