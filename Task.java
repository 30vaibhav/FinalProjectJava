package el.tella.model;

public class Task {
    private String id;
    private String title;
    private String description;
    private String deadline;

    public Task(String id, String title, String description, String deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDeadline() { return deadline; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setDeadline(String deadline) { this.deadline = deadline; }

    public String toString() {
        return id + "," + title + "," + description + "," + deadline;
    }

    public static Task fromString(String line) {
        String[] parts = line.split(",", 4);
        if (parts.length == 4) {
            return new Task(parts[0], parts[1], parts[2], parts[3]);
        }
        return null;
    }
}
