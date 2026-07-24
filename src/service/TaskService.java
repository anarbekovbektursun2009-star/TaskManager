package service;

import entity.Task;
import enums.Priority;
import enums.Status;
import repo.TaskRepository;
import storage.TaskFileStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService {
    private final TaskRepository repository;
    private final TaskFileStorage fileStorage;
    private Long currentId = 1L;

    public TaskService(TaskRepository repository, TaskFileStorage fileStorage) {
        this.repository = repository;
        this.fileStorage = fileStorage;
    }

    public void addTask(String name, String description, Priority priority) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Task name cannot be empty!");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty!");
        }

        Task task = new Task(currentId++, name, description, Status.TODO, priority);
        repository.addTask(task);
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public boolean completeTask(Long id) {
        return repository.findById(id).map(task -> {
            if (task.getStatus() == Status.DONE) {
                return false;
            }
            task.setStatus(Status.DONE);
            return true;
        }).orElse(false);
    }

    public boolean deleteTask(Long id) {
        return repository.deleteTask(id);
    }

    public List<Task> getSortedByPriority() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Task::getPriority).reversed())
                .collect(Collectors.toList());
    }

    public void saveToFile(String fileName) {
        fileStorage.save(repository.findAll(), fileName);
    }

    public void loadFromFile(String fileName) {
        List<Task> tasks = fileStorage.load(fileName);
        repository.replaceAll(tasks);

        long maxId = tasks.stream()
                .mapToLong(Task::getId)
                .max()
                .orElse(0L);
        currentId = maxId + 1;
    }
}
