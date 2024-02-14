package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
public class Aggregation2A {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation2A(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation2A aggregation2A = container.select(Aggregation2A.class).get();
        System.out.println(aggregation2A.aggregationsService.aggregationComplex1());
    }
}


