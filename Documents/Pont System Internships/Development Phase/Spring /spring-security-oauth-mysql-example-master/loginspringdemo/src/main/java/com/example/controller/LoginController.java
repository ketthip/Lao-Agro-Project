package com.example.controller;

import com.example.model.Task;
import com.example.model.User;
import com.example.service.TaskService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.rmi.MarshalledObject;
import java.util.Date;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private TaskService taskService;

	/**
	 * Mapp to Login page
	 * @return
	 */

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	/**
	 * Mapp to registration warning in the login page and it connects to registration page
	 * @return
	 */
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	/**
	 * Mapping to registration page and show the object to the view in jsp page
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");
			
		}
		return modelAndView;
	}

	@RequestMapping(value = "goTask", method = RequestMethod.POST)
	public ModelAndView goToManagePage(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/all-tasks");
		return modelAndView;
	}

	/**
	 * Mapping to Home page when user can login to system
	 * @return
	 */
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}

	/**
	 * Registrations page for new partner
	 */
//		@RequestMapping(value = "/admin/registrations/new")
//		public ModelAndView newRegistrations(Model model){
//					model.addAttribute("registrations", new )
//				return modelAndView;
//}
	/**
	 * Mapping to the Task managing page
	 */
	@PostMapping("/login-task")
	public String loginTask(){
		return "index";
	}

	/**
	 * Mapping to the task saving information to database in the t_tasks table whihc in put from new task page
	 */
	@PostMapping("/save-task")
	public String saveTask(@ModelAttribute Task task, BindingResult bindingResult, HttpServletRequest request){

		task.setDateCreated(new Date());
		taskService.save(task);
		request.setAttribute("tasks", taskService.findAll());
		request.setAttribute("mode", "MODE_TASKS");

		return "index";

	}

//	/**
//	* Mapping to the new task page
//	 */
//	@GetMapping("/new-task")
//	public String newTask(HttpServletRequest request){
//		request.setAttribute("mode","MODE_NEW");
//		return "index";
//	}
//
//	@GetMapping("/all-tasks")
//	public String allTasks(HttpServletRequest request){
//		request.setAttribute("tasks", taskService.findAll());
//		request.setAttribute("mode", "MODE_TASKS");
//		return "index";
//	}
//
//
//	@GetMapping("/update-task")
//	public String updateTask(@RequestParam int id, HttpServletRequest request) {
//		request.setAttribute("task", taskService.findTask(id));
//		request.setAttribute("mode", "MODE_UPDATE");
//		return "index";
//	}
//
//	@GetMapping("/delete-task")
//	public String deleteTask(@RequestParam int id, HttpServletRequest request) {
//		taskService.delete(id);
//		request.setAttribute("tasks", taskService.findAll());
//		request.setAttribute("mode", "MODE_TASKS");
//		return "index";
//	}

	

}
