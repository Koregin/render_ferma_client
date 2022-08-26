package ru.koregin.fermaclient.actions;

import ru.koregin.fermaclient.input.Input;
import ru.koregin.fermaclient.output.Output;
import ru.koregin.fermaclient.store.RestClient;

public class ExitAction implements UserAction {
    private final Output out;

    public ExitAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Выход";
    }

    public boolean execute(Input input, RestClient client) {
        out.println("=== Выход из программы ===");
        return false;
    }
}
