package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class Aggregation1A {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation1A(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation1A aggregation1A = container.select(Aggregation1A.class).get();
        System.out.println(aggregation1A.aggregationsService.aggregationA());
    }
}


