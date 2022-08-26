package ru.koregin.fermaclient.store;

import ru.koregin.fermaclient.model.Task;
import ru.koregin.fermaclient.model.User;

import java.util.List;

public interface Store {

    Task addTask(Task Task);

    List<Task> findAll();

    boolean userRegister(User user);

    Task findById(int id);
}
