package dio.taskmanager.application;

import dio.taskmanager.domain.TaskId;
import dio.taskmanager.domain.TaskNotFoundExeption;
import dio.taskmanager.domain.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteTaskUseCase {
    private final TaskRepository repository;

    public DeleteTaskUseCase(TaskRepository taskRepository) {
        this.repository = taskRepository;
    }

    public void execute(TaskId taskId) {
        if (repository.findById(taskId).isEmpty()){
            throw new TaskNotFoundExeption(taskId);
        }
        repository.delete(taskId);
    }
}
