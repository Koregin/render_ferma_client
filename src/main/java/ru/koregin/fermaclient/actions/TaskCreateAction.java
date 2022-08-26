package ru.koregin.fermaclient.actions;

import ru.koregin.fermaclient.input.Input;
import ru.koregin.fermaclient.model.Task;
import ru.koregin.fermaclient.output.Output;
import ru.koregin.fermaclient.store.RestClient;

public class TaskCreateAction implements UserAction {

    private final Output out;

    public TaskCreateAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Создать новую задачу";
    }

    @Override
    public boolean execute(Input input, RestClient store) {
        out.println("=== Создание новой задачи ===");
        if (store.getCurrentUser() != null) {
            String taskName = input.askStr("Введите название: ");
            Task createdTask = store.addTask(new Task(taskName));
            out.println("Создана новая задача: " + createdTask);
        }
        return true;
    }
}
