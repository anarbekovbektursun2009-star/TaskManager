package repo.impl;

import entity.Task;
import enums.Priority;
import enums.Status;
import repo.TaskRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskRepositoryImpl implements TaskRepository {

    private final List<Task> tasks=new ArrayList<>();
    private Long currentId = 1L;

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }

    @Override
    public boolean completeTask(Long id) {
        for(Task task:tasks){
            if(task.getId().equals(id)){
                task.setStatus(Status.DONE);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteTask(Long id) {
        return tasks.removeIf(task -> task.getId().equals(id));
    }

    @Override
    public List<Task> getAllTasksSortedByPriority() {
        return tasks.stream().sorted
                (Comparator.comparing(Task::getPriority).reversed()).collect(Collectors.toList());
    }

    @Override
    public void addTask(Task task) {
        task.setId(currentId++);
        tasks.add(task);
    }

    @Override
    public void saveToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Task task : tasks) {
                writer.write(String.format("%d;%s;%s;%s;%s\n",
                        task.getId(),
                        task.getName(),
                        task.getDescription(),
                        task.getStatus().name(),
                        task.getPriority().name()));
            }
        }
        catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void loadFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists())
            return;

        tasks.clear();
        long maxId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(";",5);
                if (parts.length == 5) {
                    long id = Long.parseLong(parts[0]);
                    String name = parts[1];
                    String description = parts[2];
                    Status status = Status.valueOf(parts[3]);
                    Priority priority = Priority.valueOf(parts[4]);

                    tasks.add(new Task(id, name, description, status, priority));
                    if (id > maxId) maxId = id;
                }
            }
            currentId = maxId + 1;
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
