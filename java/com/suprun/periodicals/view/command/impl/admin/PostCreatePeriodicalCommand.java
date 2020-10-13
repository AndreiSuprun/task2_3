package com.suprun.periodicals.view.command.impl.admin;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.service.PeriodicalService;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.PagesPath;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.util.mapper.RequestMapperFactory;
import com.suprun.periodicals.view.util.validator.EntityValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PostCreatePeriodicalCommand implements Command {

    private static Logger LOGGER = LogManager.getLogger(PostCreatePeriodicalCommand.class);
    private final PeriodicalService periodicalService =
            ServiceFactory.getPeriodicalService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Start of new periodical creation");
        Periodical periodicalDTO = RequestMapperFactory.getCreatePeriodicalMapper().mapToObject(request);

        Map<String, Boolean> errors = EntityValidatorFactory.getPeriodicalValidator().validate(periodicalDTO);

        if (errors.isEmpty()) {
            try {
                periodicalService.createPeriodical(periodicalDTO);
            } catch (ServiceException e) {
                request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
                return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
            }
            LOGGER.debug("Periodical was successfully create");
            return CommandResult.redirect(PagesPath.ADMIN_CATALOG_PATH);
        }

        LOGGER.debug("Invalid creation parameters");
        request.setAttribute(Attributes.ERRORS, errors);
        request.setAttribute(Attributes.PERIODICAL_DTO, periodicalDTO);
        try{
        request.setAttribute(Attributes.PERIODICAL_CATEGORIES, periodicalService.findAllPeriodicalCategory());
        request.setAttribute(Attributes.FREQUENCIES, periodicalService.findAllFrequencies());
        request.setAttribute(Attributes.PUBLISHERS, periodicalService.findAllPublishers());
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        LOGGER.debug("Periodical creation fail");
        return CommandResult.forward(ViewsPath.CREATE_PERIODICAL_VIEW);
    }
}
