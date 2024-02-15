package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

public class Aggregation1B {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation1B(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation1B aggregation1B = container.select(Aggregation1B.class).get();
        ObjectId objectId = new ObjectId("65cca33388aedd402cc8da57");
        System.out.println(aggregation1B.aggregationsService.aggregationB(objectId));
    }
}


