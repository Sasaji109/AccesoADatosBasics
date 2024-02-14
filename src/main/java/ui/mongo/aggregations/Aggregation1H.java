package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class Aggregation1H {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation1H(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation1H aggregation1H = container.select(Aggregation1H.class).get();
        System.out.println(aggregation1H.aggregationsService.aggregationH());
    }
}


