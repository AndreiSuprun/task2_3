package com.suprun.periodicals.view;

import com.suprun.periodicals.dao.connection.ConnectionPool;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandFactory;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.util.RedirectType;
import com.suprun.periodicals.view.util.RequestMethod;
import com.suprun.periodicals.view.util.ViewUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    private CommandFactory commandFactory;

    @Override
    public void init() throws ServletException {
        super.init();
        commandFactory = CommandFactory.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.debug("GET: {}", request.getPathInfo());
        processRequest(request, response, RequestMethod.GET);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.debug("POST: {}", request.getPathInfo());
        processRequest(request, response, RequestMethod.POST);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response, RequestMethod method)
            throws ServletException, IOException {
        Command command = commandFactory.getCommand(getPath(request), method);
        CommandResult commandResult = command.execute(request, response);
        LOGGER.debug("Path of response: {}", commandResult.getPagePath());
        if (commandResult.getRedirectType() == RedirectType.REDIRECT) {
            ViewUtil.redirectTo(request, response, commandResult.getPagePath());
        } else {
            request.getRequestDispatcher(commandResult.getPagePath()).forward(request, response);
        }
    }

    private String getPath(HttpServletRequest request) {
        return request.getPathInfo();
    }
}

