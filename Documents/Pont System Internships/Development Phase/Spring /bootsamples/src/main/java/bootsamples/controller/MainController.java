package bootsamples.controller;

import bootsamples.model.Task;
import bootsamples.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class MainController {

    @Autowired
    private TaskService taskService;



    @RequestMapping("/all-tasks")
    public String allTasks(HttpServletRequest request){
        request.setAttribute("tasks", taskService.findAll());
        request.setAttribute("mode", "MODE_TASKS");
        return "index";
    }

    @RequestMapping("/new-task")
    public String newTask(HttpServletRequest request){
        request.setAttribute("mode", "MODE_NEW");
        return "index";
    }

    @RequestMapping("/update-task")
    public String updateTask(@RequestParam int id, HttpServletRequest request) {
        request.setAttribute("task", taskService.findTask(id));
        request.setAttribute("mode", "MODE_UPDATE");
        return "index";
    }

    @RequestMapping("/delete-task")
    public String deleteTask(@RequestParam int id, HttpServletRequest request) {
        taskService.delete(id);
        request.setAttribute("tasks", taskService.findAll());
        request.setAttribute("mode", "MODE_TASKS");
        return "index";
    }

    @RequestMapping("/save-task")
    public String saveTask(@ModelAttribute Task task, BindingResult bindingResult, HttpServletRequest request) {
        task.setDateCreated(new Date());
        taskService.save(task);
        request.setAttribute("tasks", taskService.findAll());
        request.setAttribute("mode", "MODE_TASKS");
        return "index";
    }

    @RequestMapping("/login-task")
    public String loginTask(){

        return "index";
    }

}
