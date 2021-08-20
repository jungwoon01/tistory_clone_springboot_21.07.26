package com.jungwoon.tistory_clone_springboot.web;

import com.jungwoon.tistory_clone_springboot.resolver.ClientIp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test/ip")
    public String ipTest(@ClientIp String ip) {

        System.out.println("ip : " + ip);

        return null;
    }
}
