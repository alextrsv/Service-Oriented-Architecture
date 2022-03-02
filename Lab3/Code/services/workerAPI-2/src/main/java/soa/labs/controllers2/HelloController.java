package soa.labs.controllers2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

//    @Value("${app.name}")
//    String appName;


    @GetMapping("/hi")
    public String greeting(){
        return "hello from 8001 ";
    }
}
