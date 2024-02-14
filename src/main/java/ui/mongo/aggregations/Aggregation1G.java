package ui.mongo.aggregations;

import domain.services.mongo.AggregationsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

public class Aggregation1G {

    private final AggregationsService aggregationsService;

    @Inject
    public Aggregation1G(AggregationsService aggregationsService) {
        this.aggregationsService = aggregationsService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        Aggregation1G aggregation1G = container.select(Aggregation1G.class).get();
        ObjectId objectId = new ObjectId("65c6057ed28e0f3d316b88c3");
        System.out.println(aggregation1G.aggregationsService.aggregationG(objectId));
    }
}


