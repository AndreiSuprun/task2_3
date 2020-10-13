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
import com.suprun.periodicals.view.exception.BadRequestException;
import com.suprun.periodicals.view.util.mapper.RequestMapperFactory;
import com.suprun.periodicals.view.util.validator.EntityValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

public class PostEditPeriodicalCommand implements Command {

    private static Logger LOGGER = LogManager.getLogger();
    private final PeriodicalService periodicalService = ServiceFactory.getPeriodicalService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Start editing of periodical");
        Periodical periodicalDTO = RequestMapperFactory.getEditPeriodicalMapper().mapToObject(request);
        Map<String, Boolean> errors = EntityValidatorFactory.getPeriodicalValidator().validate(periodicalDTO);

        if (errors.isEmpty()) {
            Optional<Periodical> periodicalOpt;
            try {
                periodicalOpt = periodicalService.findPeriodicalById(periodicalDTO.getId());
            } catch (ServiceException e) {
                request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
                return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
            }
            if (!periodicalOpt.isPresent() || !periodicalOpt.get().getAvailability()) {
                LOGGER.debug("Periodical with id {} doesn't exist or has suspend status",
                        periodicalDTO.getId());
                throw new BadRequestException();
            }
            try {
                periodicalService.updatePeriodical(periodicalDTO);
            } catch (ServiceException e) {
                request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
                return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
            }
            LOGGER.debug("Periodical was successfully edit");
            return CommandResult.redirect(PagesPath.ADMIN_CATALOG_PATH);
        }

        LOGGER.debug("Invalid editing parameters");
        request.setAttribute(Attributes.PERIODICAL_DTO, periodicalDTO);
        request.setAttribute(Attributes.ERRORS, errors);
        try {
            request.setAttribute(Attributes.PERIODICAL_CATEGORIES, periodicalService.findAllPeriodicalCategory());
            request.setAttribute(Attributes.FREQUENCIES, periodicalService.findAllFrequencies());
            request.setAttribute(Attributes.PUBLISHERS, periodicalService.findAllPublishers());
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        LOGGER.debug("Periodical editing failed");
        return CommandResult.forward(ViewsPath.EDIT_PERIODICAL_VIEW);
    }
}
