package com.jonhdevelop.springbootapifacturas.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.Locale;

@Controller
public class LoginController {


    @Autowired
    private MessageSource messageSource;

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error,
                        @RequestParam(name = "logout", required = false) String logout,
                        Model model, Principal principal, RedirectAttributes flash, Locale locale) throws IOException {

        if(principal != null){
            flash.addFlashAttribute("info",  messageSource.getMessage("login.init", null, locale));
            return "redirect:/";
        }
        if(error != null){
            model.addAttribute("error", messageSource.getMessage("login.error", null, locale));
        }
        if(logout != null){
            model.addAttribute("success",  messageSource.getMessage("login.cerrar", null, locale));
        }
        return "login";
    }
}
