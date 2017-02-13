package io.github.hufghani;

import io.github.hufghani.mqtt.Nest_Publish;
import io.github.hufghani.mqtt.Nest_Subscribe;
import io.github.hufghani.nest.NestService;

public class App {

    public static void main(String[] args ) {
        new App();
    }

    public App() {
        NestService nestService = new NestService();
        Nest_Publish nest_publish = new Nest_Publish();
        Nest_Subscribe nest_subscribe = new Nest_Subscribe();

        for (;;){
            try {
                Thread.sleep(10000);
                nest_publish.publish(nestService.getAllString());

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }


}