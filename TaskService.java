package el.tella.service;

import el.tella.model.Task;
import java.io.*;
import java.util.*;

public class TaskService {
    private static final String FILE_PATH = "data/taskData.txt";

    public TaskService() {
        File file = new File(FILE_PATH);
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error initializing file: " + e.getMessage());
        }
    }

    public void saveTask(Task task) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.write(task.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving task: " + e.getMessage());
        }
    }

    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = Task.fromString(line);
                if (task != null) tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    public void overwriteTasks(List<Task> tasks) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Task task : tasks) {
                writer.write(task.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing tasks: " + e.getMessage());
        }
    }

    public void sortByDeadline() {
        List<Task> tasks = loadTasks();
        tasks.sort(Comparator.comparing(Task::getDeadline));
        tasks.forEach(System.out::println);
    }
}
