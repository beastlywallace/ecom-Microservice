package com.example.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstanceController {
    @Value("${server.port}")
    public String port;
    private final String instanceId = java.util.UUID.randomUUID().toString();
    @GetMapping("/instance-info")
    public String getInstanceInfo(){
        System.out.println(" runnning "+ port);
    return "instance serce bu port "+ port + "instandI D" + instanceId;

    };
}
