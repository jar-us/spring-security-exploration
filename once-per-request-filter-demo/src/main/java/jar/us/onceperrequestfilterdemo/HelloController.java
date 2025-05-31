package jar.us.onceperrequestfilterdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello from OncePerRequestFilter Demo!";
    }

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint in OncePerRequestFilter Demo!";
    }

    @GetMapping("/private")
    public String privateEndpoint() {
        return "This is a private endpoint in OncePerRequestFilter Demo, accessible after login!";
    }
}