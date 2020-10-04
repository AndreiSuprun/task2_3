package com.suprun.periodicals.view.command;

import com.suprun.periodicals.view.util.RequestMethod;
import com.suprun.periodicals.view.command.impl.DefaultCommand;
import com.suprun.periodicals.view.command.impl.HomeCommand;
import com.suprun.periodicals.view.constants.PagesPath;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private final static String DELIMITER = ":";

    private final Command DEFAULT_COMMAND = new DefaultCommand();
    private final Map<String, Command> commands = new HashMap<>();

    private CommandFactory() {
        init();
    }

    private void init() {
        commands.put(buildKey(PagesPath.HOME_PATH, RequestMethod.GET),
                new HomeCommand());
        commands.put(buildKey(PagesPath.SIGN_IN_PATH, RequestMethod.GET),
                new GetSignInCommand());
        commands.put(buildKey(PagesPath.SIGN_IN_PATH, RequestMethod.POST),
                new PostSignInCommand());
        commands.put(buildKey(PagesPath.REGISTER_PATH, RequestMethod.GET),
                new GetRegisterCommand());
        commands.put(buildKey(PagesPath.REGISTER_PATH, RequestMethod.POST),
                new PostRegisterCommand());
        commands.put(buildKey(PagesPath.SIGN_OUT_PATH, RequestMethod.GET),
                new SignOutCommand());
        commands.put(buildKey(PagesPath.PERIODICAL_PATH, RequestMethod.GET),
                new GetPeriodicalCommand());
        commands.put(buildKey(PagesPath.CATALOG_PATH, RequestMethod.GET),
                new GetCatalogCommand());
    }

    public Command getCommand(String path, RequestMethod method) {
        return commands.getOrDefault(buildKey(path, method), DEFAULT_COMMAND);
    }

    private String buildKey(String path, RequestMethod method) {
        return method.name() + DELIMITER + path;
    }

    public static class Singleton {
        private final static CommandFactory INSTANCE = new CommandFactory();
    }

    public static CommandFactory getInstance() {
        return Singleton.INSTANCE;
    }
}
