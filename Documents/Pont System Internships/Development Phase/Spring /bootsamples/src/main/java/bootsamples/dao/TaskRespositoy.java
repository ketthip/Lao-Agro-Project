package bootsamples.dao;

import bootsamples.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRespositoy extends CrudRepository<Task, Integer>{

    Task findOne(int id);
}
