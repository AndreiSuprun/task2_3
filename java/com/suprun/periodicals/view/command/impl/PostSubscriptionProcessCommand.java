package com.suprun.periodicals.view.command.impl;

import com.suprun.periodicals.entity.Subscription;
import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.service.SubscriptionBinService;
import com.suprun.periodicals.service.SubscriptionService;
import com.suprun.periodicals.service.entity.SubscriptionBin;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.PagesPath;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.util.ViewUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

public class PostSubscriptionProcessCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final SubscriptionService subscriptionService = ServiceFactory.getSubscriptionService();
    private final SubscriptionBinService subscriptionBinService = ServiceFactory.getSubscriptionBinService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Attempt to process new subscriptions");
        SubscriptionBin shoppingCart = ViewUtil.getSubscriptionBin(request.getSession());
        try {
            subscriptionBinService.updateSubscriptionBinItemsFromDatabase(shoppingCart);
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }

        User user = ViewUtil.getAuthorizedUser(request.getSession());
        List<Subscription> subscriptions = shoppingCart.getItems();
        BigDecimal totalPrice = shoppingCart.getTotalPrice();

        try {
            subscriptionService.processSubscriptions(user, subscriptions, totalPrice);
        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage());
            return CommandResult.redirect(PagesPath.BIN_PATH);
        }

        subscriptionBinService.removeAllItemFromBin(shoppingCart);
        LOGGER.debug("New subscriptions processed successfully");
        return CommandResult.redirect(PagesPath.BIN_PATH);
    }
}
