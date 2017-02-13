package io.github.hufghani;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import io.github.hufghani.Nest.NestThermostat;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class App {
    ObjectMapper mapper = new ObjectMapper();
    public static void main( String[] args ) {
        App app = new App();
            app.getData();
           // app.setTemputure(15);

    }

    public void getData() {

        try {
            Client client = Client.create();
            WebResource webResource = client
                    .resource("https://developer-api.nest.com/devices/?auth=c.qOL8PQR370XkE3F2oQbSSIwBENrVMYN1DhAQJRpmxDnuzd23e2Z0Ru4fsUKgzc20pHbBkhumPXp9qgwNUJuNJXSeif7ByD2SpcHRiEoX2sNecb7SvwmK3d1X91JGwJxRxMl2fTvDa3m3aa1W");
            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
            String output = response.getEntity(String.class);
            NestThermostat nestThermostat = mapper.readValue(output,NestThermostat.class);
            System.out.println(nestThermostat.getThermostats().getThermostatElements());
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public void setTemputure(int temputure) {
    try {
        Client client = Client.create();
        WebResource webResource = client.resource("https://developer-api.nest.com/devices/thermostats/0KD654moZP0wgM2qlRxQxFcNidHWg5j2/target_temperature_c?auth=c.qOL8PQR370XkE3F2oQbSSIwBENrVMYN1DhAQJRpmxDnuzd23e2Z0Ru4fsUKgzc20pHbBkhumPXp9qgwNUJuNJXSeif7ByD2SpcHRiEoX2sNecb7SvwmK3d1X91JGwJxRxMl2fTvDa3m3aa1W");

        ClientResponse response = webResource.type("text/plain").put(ClientResponse.class, String.valueOf(temputure));

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        System.out.println("Output from Server .... \n");
        String output = response.getEntity(String.class);
        System.out.println(output);

    } catch (Exception e) {

        e.printStackTrace();

    }
}

}