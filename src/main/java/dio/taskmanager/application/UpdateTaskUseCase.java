package dio.taskmanager.application;

import dio.taskmanager.application.input.UpdateTaskInput;
import dio.taskmanager.application.output.TaskOutput;
import dio.taskmanager.domain.TaskId;
import dio.taskmanager.domain.TaskNotFoundExeption;
import dio.taskmanager.domain.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateTaskUseCase {
    private final TaskRepository repository;

    public UpdateTaskUseCase(TaskRepository taskRepository) {
        this.repository = taskRepository;
    }

    public TaskOutput execute(TaskId id, UpdateTaskInput input){
        var task = repository.findById(id).orElseThrow(() -> new TaskNotFoundExeption(id));
        task.update(input.title(), input.description(), input.status());
        var updated = repository.save(task);
        return TaskOutput.from(updated);
    }
}
