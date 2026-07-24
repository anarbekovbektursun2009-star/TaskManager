package storage;

import entity.Task;
import enums.Priority;
import enums.Status;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskFileStorage {

    public void save(List<Task> tasks, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Task task : tasks) {
                String safeName = task.getName().replace(";", ",");
                String safeDesc = task.getDescription().replace(";", ",");

                writer.write(String.format("%d;%s;%s;%s;%s\n",
                        task.getId(),
                        safeName,
                        safeDesc,
                        task.getStatus().name(),
                        task.getPriority().name()));
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public List<Task> load(String fileName) {
        List<Task> loadedTasks = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            return loadedTasks;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(";", 5);

                if (parts.length == 5) {
                    long id = Long.parseLong(parts[0]);

                    String name = parts[1].replace(",", ";");
                    String description = parts[2].replace(",", ";");
                    Status status = Status.valueOf(parts[3]);
                    Priority priority = Priority.valueOf(parts[4]);

                    loadedTasks.add(new Task(id, name, description, status, priority));
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return loadedTasks;
    }
}
