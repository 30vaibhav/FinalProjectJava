package el.tella;

import el.tella.controller.TaskController;

public class Main {
    public static void main(String[] args) {
        TaskController controller = new TaskController();
        controller.showMenu();
    }
}
