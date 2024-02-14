package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class Aggregation1J {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation1J(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation1J aggregation1J = container.select(Aggregation1J.class).get();
        System.out.println(aggregation1J.aggregationsService.aggregationJ());
    }
}


