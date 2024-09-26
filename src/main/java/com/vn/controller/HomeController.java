package com.vn.controller;

import com.vn.auth.CustomUserDetail;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
//@SessionAttributes("userLogged")
public class HomeController {

//    @ModelAttribute("userLogged")
//    public CustomUserDetail getDetail(){
//        Object userDetail = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(userDetail instanceof CustomUserDetail){
//            return (CustomUserDetail)userDetail;
//        }
//        return null;
//    }

    @GetMapping("/")
    public String getHomeUI() {
        return "home";
    }
}
