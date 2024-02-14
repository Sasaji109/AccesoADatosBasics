package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class Aggregation1K {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation1K(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation1K aggregation1K = container.select(Aggregation1K.class).get();
        System.out.println(aggregation1K.aggregationsService.aggregationK());
    }
}


