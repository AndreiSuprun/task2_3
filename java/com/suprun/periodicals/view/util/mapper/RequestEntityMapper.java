package com.suprun.periodicals.view.util.mapper;

import javax.servlet.http.HttpServletRequest;

public interface RequestEntityMapper<T> {

    T mapToObject(HttpServletRequest request);
}
