package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class Aggregation1E {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation1E(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation1E aggregation1E = container.select(Aggregation1E.class).get();
        System.out.println(aggregation1E.aggregationsService.aggregationE());
    }
}


