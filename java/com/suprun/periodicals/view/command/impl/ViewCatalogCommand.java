package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.SubscriptionPeriod;
import com.suprun.periodicals.service.PeriodicalService;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.service.SubscriptionService;
import com.suprun.periodicals.util.Resource;
import com.suprun.periodicals.view.util.PageManager;
import com.suprun.periodicals.view.util.ViewUtil;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.RequestParameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewCatalogCommand implements Command {
    private final static long RECORDS_PER_PAGE = 5;
    private final PeriodicalService periodicalService = ServiceFactory.getPeriodicalService();
    private final SubscriptionService subscriptionService = ServiceFactory.getSubscriptionService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        PageManager pageManager = new PageManager(RECORDS_PER_PAGE);
        long rowsCount = periodicalService.getPeriodicalsCountByStatus(true);
        long skip = pageManager.manage(request, rowsCount);
        List<Periodical> periodicals =
                periodicalService.findAllPeriodicalsByStatus(true, skip, pageManager.getRecordsPerPage());
        request.setAttribute(Attributes.CATALOG, periodicals);
        if (!periodicals.isEmpty()) {
            List<SubscriptionPeriod> subscriptionPlans = subscriptionService.findAllSubscriptionPeriods();
            request.setAttribute(Attributes.SUBSCRIPTION_PERIODS, subscriptionPlans);
        }
        ViewUtil.checkErrorParameter(request, RequestParameters.ERROR_ATTRIBUTE);
        return CommandResult.forward(Resource.VIEW.getProperty("view.folder") + Resource.VIEW.getProperty("view.catalog"));
    }

}
