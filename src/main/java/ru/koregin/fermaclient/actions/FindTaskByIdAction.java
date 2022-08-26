package ru.koregin.fermaclient.actions;

import ru.koregin.fermaclient.input.Input;
import ru.koregin.fermaclient.model.Task;
import ru.koregin.fermaclient.output.Output;
import ru.koregin.fermaclient.store.RestClient;

import java.time.format.DateTimeFormatter;

public class FindTaskByIdAction implements UserAction {

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final Output out;

    public FindTaskByIdAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Вывести историю задачи по id";
    }

    @Override
    public boolean execute(Input input, RestClient store) {
        out.println("=== История задачи  ===");
        String ls = System.lineSeparator();
        if (store.getCurrentUser() != null) {
            int id = input.askInt("Введите id: ");
            Task task = store.findById(id);
            if (task != null) {
                out.println("Задача ID: " + task.getId() + ls +
                        "STATUS: " + task.getStatus() + ls +
                        "CREATED: " + task.getCreated().format(FORMATTER) + ls +
                        "COMPLETED: " + (task.getCompleted() != null ? task.getCompleted().format(FORMATTER) : "Задача выполняется"));
            } else {
                out.println("Задача с введенным id: " + id + " не найдена.");
            }
        }
        return true;
    }
}
