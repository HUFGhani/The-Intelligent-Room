package io.github.hufghani;

import io.github.hufghani.mqtt.Nest_Publish;
import io.github.hufghani.mqtt.Nest_Subscribe;
import io.github.hufghani.nest.NestService;
import io.github.hufghani.nest.properties.NestProperties;

public class App {

    public static void main(String[] args ) {
        new App();
    }

    public App() {
        NestProperties nestProperties = new NestProperties();
        NestService nestService = new NestService();
        Nest_Publish nest_publish = new Nest_Publish();
        Nest_Subscribe nest_subscribe = new Nest_Subscribe();
        nest_publish.setTopic(nestProperties.getHouseID());
        nest_subscribe.setTopic(nestProperties.getHouseID());

        for (;;){
            try {
                nest_publish.publish(nestService.getAllString());
                nest_subscribe.subscribe();
                nestService.setNestTemputure(nest_subscribe.getTemperature());
                Thread.sleep(10000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}