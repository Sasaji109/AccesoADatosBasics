package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class Aggregation1L {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation1L(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation1L aggregation1L = container.select(Aggregation1L.class).get();
        System.out.println(aggregation1L.aggregationsService.aggregationL());
    }
}


