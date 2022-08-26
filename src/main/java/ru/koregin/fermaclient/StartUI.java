package ru.koregin.fermaclient;

import ru.koregin.fermaclient.actions.*;
import ru.koregin.fermaclient.input.ConsoleInput;
import ru.koregin.fermaclient.input.Input;
import ru.koregin.fermaclient.input.ValidateInput;
import ru.koregin.fermaclient.output.ConsoleOutput;
import ru.koregin.fermaclient.output.Output;
import ru.koregin.fermaclient.store.RestClient;

import java.util.List;

public class StartUI {

    private final Output out;

    public StartUI(Output out) {
        this.out = out;
    }

    public void init(Input input, RestClient client, List<UserAction> actions) {
        boolean run = true;
        while (run) {
            if (client.getCurrentUser() == null) {
                out.println("Пользователь ещё не зарегистрирован");
            } else {
                out.println("Текущий пользователь: " + client.getCurrentUser().getUsername());
            }
            this.showMenu(actions);
            int select = input.askInt("Select: ");
            if (select < 0 || select >= actions.size()) {
                out.println("Wrong input, you can select: 0 .. "
                        + (actions.size() - 1));
                continue;
            }
            UserAction action = actions.get(select);
            run = action.execute(input, client);
        }
    }

    private void showMenu(List<UserAction> actions) {
        out.println("Menu:");
        for (int i = 0; i < actions.size(); i++) {
            out.println(i + ". " + actions.get(i).name());
        }
    }

    public static void main(String[] args) {
        Output output = new ConsoleOutput();
        Input input = new ValidateInput(output, new ConsoleInput());

        RestClient client = new RestClient();
        List<UserAction> actions = List.of(
                new ExitAction(output),
                new UserRegisterAction(output),
                new TaskCreateAction(output),
                new FindAllAction(output),
                new FindTaskByIdAction(output)
        );
        new StartUI(output).init(input, client, actions);
    }
}
