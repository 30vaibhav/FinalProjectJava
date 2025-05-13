package el.tella.controller;

import el.tella.model.Task;
import el.tella.service.TaskService;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaskController {
    private TaskService service = new TaskService();
    private Scanner scanner = new Scanner(System.in);

    public void createTask() {
        System.out.print("Enter Task ID: ");
        String input = scanner.nextLine();
        int taskId;

        try {
            taskId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Task ID must be an integer.");
            return;
        }

        // Check for duplicate Task ID
        List<Task> tasks = service.loadTasks();
        for (Task existingTask : tasks) {
            if (existingTask.getId().equals(String.valueOf(taskId))) {
                System.out.println("Task ID already exists. Please use a different ID.");
                return;
            }
        }

        System.out.print("Enter Task Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Task Description: ");
        String desc = scanner.nextLine();
        System.out.print("Enter Deadline (yyyy-MM-dd): ");
        String deadline = scanner.nextLine();

        if (!isFutureDate(deadline)) return;

        Task task = new Task(String.valueOf(taskId), title, desc, deadline);
        service.saveTask(task);
        System.out.println("Task saved successfully!");
    }

    public void listTasks() {
        List<Task> tasks = service.loadTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            tasks.forEach(System.out::println);
        }
    }

    public void updateTask() {
        List<Task> tasks = service.loadTasks();
        System.out.print("Enter Task ID to update: ");
        String id = scanner.nextLine();

        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                System.out.print("Enter new title: ");
                task.setTitle(scanner.nextLine());
                System.out.print("Enter new description: ");
                task.setDescription(scanner.nextLine());
                System.out.print("Enter new deadline (yyyy-MM-dd): ");
                String deadline = scanner.nextLine();
                if (!isFutureDate(deadline)) return;
                task.setDeadline(deadline);
                service.overwriteTasks(tasks);
                System.out.println("Task updated successfully!");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public void deleteTask() {
        List<Task> tasks = service.loadTasks();
        System.out.print("Enter Task ID to delete: ");
        String id = scanner.nextLine();

        boolean removed = tasks.removeIf(t -> t.getId().equals(id));
        if (removed) {
            service.overwriteTasks(tasks);
            System.out.println("Task deleted successfully!");
        } else {
            System.out.println("Task not found.");
        }
    }

    public void searchTaskById() {
        List<Task> tasks = service.loadTasks();
        System.out.print("Enter Task ID to search: ");
        String id = scanner.nextLine();

        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                System.out.println("Found: " + task);
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public void sortTasksByTitle() {
        List<Task> tasks = service.loadTasks();
        tasks.sort(Comparator.comparing(Task::getTitle));
        tasks.forEach(System.out::println);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n1. Create Task\n2. List Tasks\n3. Update Task\n4. Delete Task\n5. Search Task\n6. Sort Tasks by Title\n7. Sort Tasks by Deadline\n8. Exit");
            System.out.print("Choose an option: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> createTask();
                case 2 -> listTasks();
                case 3 -> updateTask();
                case 4 -> deleteTask();
                case 5 -> searchTaskById();
                case 6 -> sortTasksByTitle();
                case 7 -> service.sortByDeadline();
                case 8 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private boolean isFutureDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date date = sdf.parse(dateStr);
            if (date.before(new Date())) {
                System.out.println("Deadline must be a future date.");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return false;
        }
    }
}
