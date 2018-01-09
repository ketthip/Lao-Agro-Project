package com.example.service;

import com.example.model.Task;
import com.example.repository.TaskRepository;
import org.aspectj.weaver.AnnotationTargetKind;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * method to show list of task
     */
    public List<Task> findAll(){
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findAll()){
            tasks.add(task);
        }
        return tasks;
    }

    /**
     * Save method which connects to database
     */
    public void save(Task task){
        taskRepository.save(task);
    }

    /**
     * Delete method which connects to database
     */

    public void delete(int id) {
        taskRepository.delete(id);
    }

    /**
     * findTask methode which connects to database
     */
    public Task findTask(int id){
        return taskRepository.findOne(id);
    }

}
