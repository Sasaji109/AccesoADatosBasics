package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class Aggregation2D {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation2D(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation2D aggregation2D = container.select(Aggregation2D.class).get();
        System.out.println(aggregation2D.aggregationsService.aggregationComplex4());
    }
}


