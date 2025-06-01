package jar.us.databaseexplorationdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // This will resolve to src/main/resources/templates/home.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // This will resolve to src/main/resources/templates/login.html
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // Secured page
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin"; // Admin-only page
    }
}