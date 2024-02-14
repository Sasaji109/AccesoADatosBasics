package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
public class Aggregation2C {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation2C(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation2C aggregation2C = container.select(Aggregation2C.class).get();
        System.out.println(aggregation2C.aggregationsService.aggregationComplex3());
    }
}


