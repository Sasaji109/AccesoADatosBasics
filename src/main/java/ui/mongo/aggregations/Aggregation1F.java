package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class Aggregation1F {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation1F(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation1F aggregation1F = container.select(Aggregation1F.class).get();
        System.out.println(aggregation1F.aggregationsService.aggregationF());
    }
}


