package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class Aggregation1D {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation1D(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation1D aggregation1D = container.select(Aggregation1D.class).get();
        System.out.println(aggregation1D.aggregationsService.aggregationD());
    }
}


