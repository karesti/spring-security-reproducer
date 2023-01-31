package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class WebController {

   @GetMapping("/test")
   public String testRest(HttpServletResponse response){
      response.setHeader("hello", "world");
      return "ok";
   }

}
