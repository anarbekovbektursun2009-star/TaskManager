package repo;

import entity.Task;

import java.util.List;

public interface TaskRepository {

    List<Task> findAll();

    boolean completeTask(Long id);

    boolean deleteTask(Long id);

    List<Task> getAllTasksSortedByPriority();

    void addTask(Task task);

    void saveToFile(String fileName);

    void loadFromFile(String fileName);

}
