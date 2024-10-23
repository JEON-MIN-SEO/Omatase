package Omatase.omatase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // 로그인 페이지로 이동
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 로그인 성공 후 success.html로 리디렉션
    @GetMapping("/success")
    public String success() {
        return "success";
    }
}
