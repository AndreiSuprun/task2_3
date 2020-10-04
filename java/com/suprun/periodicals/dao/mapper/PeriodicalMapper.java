package com.suprun.periodicals.dao.mapper;

import com.suprun.periodicals.entity.Frequency;
import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.PeriodicalCategory;
import com.suprun.periodicals.entity.Publisher;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeriodicalMapper implements EntityMapper<Periodical> {

    private static final String ID_FIELD = "periodical_id";
    private static final String PERIODICAL_NAME_FIELD = "periodical_name";
    private static final String PERIODICAL_AVAILABILITY_FIELD = "periodical_availability";
    private static final String PERIODICAL_PRICE_FIELD = "periodical_price";
    private static final String PERIODICAL_DESCRIPTION_FIELD = "periodical_description";

    private final EntityMapper<Publisher> publisherMapper;
    private final EntityMapper<Frequency> frequencyMapper;
    private final EntityMapper<PeriodicalCategory> periodicalCategoryMapper;

    public PeriodicalMapper() {
        this(new PublisherMapper(),
                new FrequencyMapper(),
                new PeriodicalCategoryMapper());
    }

    public PeriodicalMapper(EntityMapper<Publisher> publisherMapper,
                            EntityMapper<Frequency> frequencyMapper,
                            EntityMapper<PeriodicalCategory> periodicalCategoryMapper) {
        this.publisherMapper = publisherMapper;
        this.frequencyMapper = frequencyMapper;
        this.periodicalCategoryMapper = periodicalCategoryMapper;
    }

    @Override
    public Periodical mapToObject(ResultSet resultSet, String tablePrefix)
            throws SQLException {
        Publisher tempPublisher = publisherMapper.mapToObject(resultSet);
        Frequency tempFrequency = frequencyMapper.mapToObject(resultSet);
        PeriodicalCategory tempPeriodicalCategory = periodicalCategoryMapper.mapToObject(resultSet);

        return Periodical.newBuilder()
                .setId(resultSet.getLong(
                        tablePrefix + ID_FIELD))
                .setPublisher(tempPublisher)
                .setFrequency(tempFrequency)
                .setPeriodicalCategory(tempPeriodicalCategory)
                .setName(resultSet.getString(
                        tablePrefix + PERIODICAL_NAME_FIELD))
                .setAvailability(Boolean.parseBoolean(resultSet.getString(tablePrefix + PERIODICAL_AVAILABILITY_FIELD)))
                .setPrice(resultSet.getBigDecimal(
                        tablePrefix + PERIODICAL_PRICE_FIELD))
                .setPeriodicalDescription(resultSet.getString(
                        tablePrefix + PERIODICAL_DESCRIPTION_FIELD))
                .build();
    }
}
