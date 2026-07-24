package repo;

import entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    void addTask(Task task);

    List<Task> findAll();

    Optional<Task> findById(Long id);

    boolean deleteTask(Long id);

    void replaceAll(List<Task> tasks);
}
