package ru.koregin.fermaclient.actions;

import ru.koregin.fermaclient.input.Input;
import ru.koregin.fermaclient.store.RestClient;

public interface UserAction {

    String name();

    boolean execute(Input input, RestClient client);
}
