package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class Aggregation1C {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation1C(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation1C aggregation1C = container.select(Aggregation1C.class).get();
        System.out.println(aggregation1C.aggregationsService.aggregationC());
    }
}


