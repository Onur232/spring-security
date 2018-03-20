package com.teamtreehouse.web.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.teamtreehouse.model.Task;
import com.teamtreehouse.model.User;
import com.teamtreehouse.service.TaskService;
import com.teamtreehouse.service.UserService;

@Controller
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	//navbar'da login olan user görüntülemek için principal eklendi. iki yol var. birincisi principal. diğeri spring
	//security dialect. ikinci yolla daha fazla bilgi alınır. pom.xml'e thymeleaf-extras-springsecurity4 eklendi.
	//ikinci yol için templateconfig sınıfı yapıldı.
	@RequestMapping({"/", "/todo"})
	public String taskList(Model model, Principal principal) {
		//logged in user:
		String username = principal.getName();
		Iterable<Task> tasks=taskService.findAll();
		model.addAttribute("tasks", tasks);
		model.addAttribute("newTask", new Task());
		return "todo";
	}
	
	@RequestMapping(value="/mark", method=RequestMethod.POST)
	public String toggleComplete(@RequestParam Long id) {
		Task task=taskService.findOne(id);
		taskService.toggleComplete(id);
		return "redirect:/";
	}
	
	@RequestMapping(value="tasks", method=RequestMethod.POST)
	public String addTask(@ModelAttribute Task task,Principal principal) {
		//current user'ı almak için principal eklendi.
//		User user = userService.findByUsername(principal.getName());
		//yukardaki işlem database'e tekrar bağlantı gerektirir. o yüzden iptal edilip aşağıdaki yazıldı.çünkü daha önce
		//authenticate olmuş olabilir. bilgiler principal'dan alınır. en sonunda user details interface'i implement
		//ettiğimiz object'e cast edilir.
		User user=(User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
		task.setUser(user);
		taskService.save(task);
		return "redirect:/";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
