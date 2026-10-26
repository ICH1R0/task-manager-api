package dio.taskmanager.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskRepositoryTest {
    TaskRepository repository;

    protected abstract TaskRepository createRepository();

    @BeforeEach
    public void setUp() {
        repository = createRepository();
    }

    @Test
    void should_save_and_retrieve_task_by_id() {
        var task = new Task("Passar na Padaria", Optional.empty());

        var saved =  repository.save(task);
        Optional<Task> result = repository.findById(saved.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(task.getId());
        assertThat(result.get().getDescription()).isEqualTo(task.getDescription());
        assertThat(result.get().getStatus()).isEqualTo(task.getStatus());
    }

    @Test
    void should_find_all_tasks() {
        var task1 = new Task("Arrumar chuveiro", Optional.of("Comprar chuveiro novo"));
        var task2 = new Task("Trocar interruptor", Optional.of("Encontrar Chave de fenda"));

        repository.save(task1);
        repository.save(task2);

        List<Task> tasks = repository.findAll();

        assertThat(tasks).hasSize(2);
        assertThat(tasks).extracting(Task::getId).containsExactlyInAnyOrder(task1.getId(), task2.getId());
    }

    @Test
    void should_return_empty_optional_when_searching_non_existent_task() {

        var nonExistentId = new TaskId();

        Optional<Task> result = repository.findById(nonExistentId);

        assertThat(result).isEmpty();
    }

    @Test
    void should_update_task_status_successfully() {

        var task = repository.save(new Task("Atualizar carteira de habilitação", Optional.empty()));

        task.setDescription(Optional.of("Não expirou ainda"));
        task.setStatus(TaskStatus.IN_PROGRESS);

        repository.save(task);
        Optional<Task> result = repository.findById(task.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getDescription()).isEqualTo(Optional.of("Não expirou ainda"));
        assertThat(result.get().getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
    }

}