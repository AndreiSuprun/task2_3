package com.suprun.periodicals.view.util.mapper;

import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.view.constants.RequestParameters;

import javax.servlet.http.HttpServletRequest;

public class SignInRequestMapper implements RequestEntityMapper<User> {

    @Override
    public User mapToObject(HttpServletRequest request) {
        return User.newBuilder()
                .setEmail(request.getParameter(RequestParameters.USER_EMAIL))
                .setPassword(request.getParameter(RequestParameters.USER_PASSWORD))
                .build();
    }
}
