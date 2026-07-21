import entity.Task;
import enums.Priority;
import enums.Status;
import repo.TaskRepository;
import repo.impl.TaskRepositoryImpl;

import java.util.List;
import java.util.Scanner;

//TIP Чтобы <b>запустить</b> код, нажмите <shortcut actionId="Run"/> или
// нажмите на значок <icon src="AllIcons.Actions.Execute"/> на полях.
public class Main {
    public static void main(String[] args) {
        TaskRepository repository = new TaskRepositoryImpl();
        repository.loadFromFile("tasks.txt");

        System.out.println();
        System.out.println("Task list:");
        printTasks(repository.findAll());

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== TASK MANAGER ===");
            System.out.println("1. Add task");
            System.out.println("2. Get All tasks");
            System.out.println("3. Mark as completed");
            System.out.println("4. Delete task");
            System.out.println("5. Sort by priority");
            System.out.println("6. Save task to file and stop process");
            System.out.print("Choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.print("Name: ");
                        String name = scanner.nextLine();

                        System.out.print("Description: ");
                        String desc = scanner.nextLine();

                        System.out.print("Priority LOW, MEDIUM, HIGH: ");
                        Priority priority = Priority.valueOf(scanner.nextLine().trim().toUpperCase());

                        Task newTask = new Task(null, name, desc, Status.TODO, priority);

                        repository.addTask(newTask);

                        System.out.println("Task has been added!");
                        break;

                    case 2:
                        printTasks(repository.findAll());
                        break;

                    case 3:
                        System.out.print("Enter the task ID: ");
                        Long compId = Long.parseLong(scanner.nextLine());
                        if (repository.completeTask(compId)) {
                            System.out.println("Task completed!");
                        } else {
                            System.out.println("Task with id: "+ compId +" not found!");
                        }
                        break;

                    case 4:
                        System.out.print("Enter the task ID to delete: ");
                        Long delId = Long.parseLong(scanner.nextLine());
                        if (repository.deleteTask(delId)) {
                            System.out.println("Task has been deleted!");
                        } else {
                            System.out.println("Task with id: "+delId+" not found!");
                        }
                        break;

                    case 5:
                        printTasks(repository.getAllTasksSortedByPriority());
                        break;

                    case 6:
                        repository.saveToFile("tasks.txt");
                        System.out.println("Task has been saved to file!");
                        return;

                    default:
                        System.out.println("Invalid input!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error:Enter only numbers");
            } catch (IllegalArgumentException e) {
                System.out.println("Error:Choose from LOW, MEDIUM, HIGH");
            }
        }
    }
    private static void printTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Task list is empty!");
            return;
        }

        System.out.println("ID | Name | Description | Priority | Status");
        System.out.println("------------------------------------------------");

        for (Task task : tasks) {
            System.out.println(task.getId() + " | " +
                    task.getName() + " | " +
                    task.getDescription() + " | " +
                    task.getPriority() + " | " +
                    task.getStatus());
        }
    }

}