package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.service.UserService;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.PagesPath;
import com.suprun.periodicals.view.constants.ViewsPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;


public class PostSignInCommand implements Command {
    private static Logger LOGGER = LogManager.getLogger();
    private UserService userService = ServiceFactory.getUserService();
    private static  final String ADMIN = "admin";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Start of sign in process");
        User userDTO = RequestMapperFactory.getSignInMapper().mapToObject(request);
        Map<String, Boolean> errors = ValidatorManagerFactory.getSignInValidator().validate(userDTO);

        if (errors.isEmpty()) {
            LOGGER.debug("Try to sign in");
            Optional<User> userOpt =
                    userService.signIn(userDTO.getEmail(), userDTO.getPassword());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                request.getSession().setAttribute(Attributes.USER, user);
                LOGGER.debug("User successfully signed in");
                if (user.getRole().getName().equalsIgnoreCase(ADMIN)) {
                    return CommandResult.redirect(PagesPath.ADMIN_CATALOG_PATH);
                } else {
                    return CommandResult.redirect(PagesPath.CATALOG_PATH);
                }
            } else {
                LOGGER.debug("Email and password don't matches");
                errors.put(Attributes.ERROR_AUTHENTICATION, true);
            }
        } else {
            LOGGER.debug("Invalid authentication parameters");
        }
        request.setAttribute(Attributes.ERRORS, errors);
        request.setAttribute(Attributes.USER_DTO, userDTO);
        LOGGER.debug("User fail sign in");
        return CommandResult.forward(ViewsPath.SIGN_IN_VIEW);
    }
}
