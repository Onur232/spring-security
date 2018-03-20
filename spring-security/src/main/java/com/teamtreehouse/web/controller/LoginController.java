package com.teamtreehouse.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.teamtreehouse.model.User;

@Controller
public class LoginController {

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginForm(Model model,HttpServletRequest request) {
		model.addAttribute("user", new User());
		
		try {
			Object flash=request.getSession().getAttribute("flash");
			model.addAttribute("flash", flash);
			//sayfa refresh ettiğinde flash mesajın gösterilmesine gerek yok.
			request.getSession().removeAttribute("flash");
			
		} catch (Exception e) {
			// "flash" session attribute must not exist. do nothing and proceed
		}
		
		return "login";
		
	}
	
	@RequestMapping("/access_denied")
	public String accessDenied() {
		return "access_denied";
	}
}
