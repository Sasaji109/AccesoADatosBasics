package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class Aggregation1I {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation1I(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation1I aggregation1I = container.select(Aggregation1I.class).get();
        System.out.println(aggregation1I.aggregationsService.aggregationI());
    }
}


