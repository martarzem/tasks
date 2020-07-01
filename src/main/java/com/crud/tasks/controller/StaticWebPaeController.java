package com.crud.tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class StaticWebPaeController {

    @RequestMapping("/")
    public String index(Map<String, Object> model) {
        model.put("variable", "My Thymeleaf variable");
        model.put("two", 2);
        model.put("multiply", "*");
        model.put("plus", "+");
        model.put("minus", "-");
        model.put("equals", "=");
        return "index";
    }
}
