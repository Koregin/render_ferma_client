package ru.koregin.fermaclient.actions;

import ru.koregin.fermaclient.input.Input;
import ru.koregin.fermaclient.model.Task;
import ru.koregin.fermaclient.output.Output;
import ru.koregin.fermaclient.store.RestClient;

import java.util.List;

public class FindAllAction implements UserAction {

    private final Output out;

    public FindAllAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Вывести все задачи";
    }

    @Override
    public boolean execute(Input input, RestClient store) {
        out.println("=== Найти все задачи  ===");
        if (store.getCurrentUser() != null) {
            List<Task> tasks = store.findAll();
            if (tasks.size() > 0) {
                for (Task task : tasks) {
                    out.println("Задача ID: " + task.getId() + ",  STATUS: " + task.getStatus());
                }
            } else {
                out.println("Не было создано ещё не одной задачи для пользователя " + store.getCurrentUser().getUsername());
            }
        }
        return true;
    }
}
