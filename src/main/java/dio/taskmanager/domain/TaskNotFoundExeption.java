package dio.taskmanager.domain;

public class TaskNotFoundExeption extends RuntimeException {
    public TaskNotFoundExeption(TaskId taskId) {
        super("Task with id " + taskId + " not found");
    }
}
