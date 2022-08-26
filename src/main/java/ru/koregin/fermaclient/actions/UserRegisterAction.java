package ru.koregin.fermaclient.actions;

import ru.koregin.fermaclient.input.Input;
import ru.koregin.fermaclient.model.User;
import ru.koregin.fermaclient.output.Output;
import ru.koregin.fermaclient.store.RestClient;

public class UserRegisterAction implements UserAction {

    private final Output out;

    public UserRegisterAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Зарегистрировать пользователя";
    }

    @Override
    public boolean execute(Input input, RestClient store) {
        out.println("=== Регистрация пользователя ===");
        String name = input.askStr("Enter name: ");
        String password = input.askStr("Enter password: ");
        User user = new User(name, password);
        if(store.userRegister(user)) {
            out.println("Зарегистрирован новый пользователь: " + user);
        }
        return true;
    }
}
