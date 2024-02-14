package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class Aggregation2B {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation2B(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation2B aggregation2B = container.select(Aggregation2B.class).get();
        System.out.println(aggregation2B.aggregationsService.aggregationComplex2());
    }
}


