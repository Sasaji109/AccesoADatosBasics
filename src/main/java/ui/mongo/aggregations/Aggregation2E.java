package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class Aggregation2E {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation2E(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation2E aggregation2E = container.select(Aggregation2E.class).get();
        System.out.println(aggregation2E.aggregationsService.aggregationComplex5());
    }
}


