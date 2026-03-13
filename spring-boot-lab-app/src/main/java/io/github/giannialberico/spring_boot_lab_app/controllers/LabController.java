package io.github.giannialberico.spring_boot_lab_app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class LabController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World, from Spring Boot Lab App!";
    }

    @PostMapping("/echo")
    public String echo(@RequestBody String payload) {
        return payload;
    }

    @GetMapping("/slow")
    public String slow() throws InterruptedException {
        Thread.sleep(3000);
        return "Hello World! (processed with a 3s delay)";
    }
}
