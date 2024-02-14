package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class Aggregation1M {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation1M(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation1M aggregation1M = container.select(Aggregation1M.class).get();
        System.out.println(aggregation1M.aggregationsService.aggregationM());
    }
}


