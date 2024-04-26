package treinamento1.treinamento1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/greeting/{content}")
    public String greetingGet(@PathVariable(name = "content") String content){
        return "Olá " + content + "!";
    }
}
