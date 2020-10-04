package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.util.Resource;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultCommand implements Command {
    private static final Logger LOGGER =
            LogManager.getLogger(DefaultCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.warn("Wrong request path");
        return CommandResult.redirect(Resource.PATH.getProperty("path.home"));
    }
}
