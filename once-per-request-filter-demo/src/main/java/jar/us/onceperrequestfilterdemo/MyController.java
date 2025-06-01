package jar.us.onceperrequestfilterdemo;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/")
    public String home() {
        System.out.println("Controller: Handling / request.");
        return "forwarded"; // This will internally forward to /forwarded
    }

    @GetMapping("/forwarded")
    public String forwardedPage() {
        System.out.println("Controller: Handling /forwarded request (after internal forward).");
        return "forwarded"; // Return the name of the Thymeleaf template
    }
}