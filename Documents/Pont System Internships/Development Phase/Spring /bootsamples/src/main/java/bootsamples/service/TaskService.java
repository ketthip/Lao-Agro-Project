package bootsamples.service;

import bootsamples.dao.TaskRespositoy;
import bootsamples.model.Task;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TaskService {
    private TaskRespositoy taskRespositoy;

    public TaskService(TaskRespositoy taskRespositoy) {
        this.taskRespositoy = taskRespositoy;
    }

    /**
     * methd to show list of tasks
     */

    public List<Task> findAll(){
        List<Task> tasks = new ArrayList();
        for(Task task : taskRespositoy.findAll()){
            tasks.add(task);
        }
        return tasks;
    }

    /**
     * Save method which combinate with database
     */
    public void save(Task task){
        taskRespositoy.save(task);
    }
    /**
     * delete method which combinate with database
     */
    public void delete(int id){
        taskRespositoy.delete(id);
    }

    /**
     * findtask method which combinate with database
     */
    public Task findTask(int id){
        return taskRespositoy.findOne(id);
    }



}
