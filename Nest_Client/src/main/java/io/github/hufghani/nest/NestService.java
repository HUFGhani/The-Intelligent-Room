package io.github.hufghani.nest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by hamzaghani on 13/02/2017.
 */
public class NestService {
    ObjectMapper mapper = new ObjectMapper();
    Client client = Client.create();
    String baseURL = "https://developer-api.nest.com/devices/";
    String auth = "c.qOL8PQR370XkE3F2oQbSSIwBENrVMYN1DhAQJRpmxDnuzd23e2Z0Ru4fsUKgzc20pHbBkhumPXp9qgwNUJuNJXSeif7By" +
            "D2SpcHRiEoX2sNecb7SvwmK3d1X91JGwJxRxMl2fTvDa3m3aa1W";
    String device ="0KD654moZP0wgM2qlRxQxFcNidHWg5j2";
    private boolean getautomated;


    public NestService() {
        super();
    }

    public String getAllString(){
        NestThermostat nestThermostat = null;
        try {
            WebResource webResource = client.resource(baseURL + "?auth=" + auth);
            ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
            String stingJson = response.getEntity(String.class);
            nestThermostat = mapper.readValue(stingJson, NestThermostat.class);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        nestThermostat.getThermostats().getThermostatElements().setAutomated(getautomated);
        return String.valueOf(nestThermostat.getThermostats().getThermostatElements().getStatus());
    }

    public void setNestTemputure(double temputure, boolean getautomated) {
        this.getautomated = getautomated;
        double temp = 0;
        if (temp == temputure) {
            System.out.println("it the same");
        } else {
            if (temputure > 0 && temputure >= 9 && temp != temputure) {
                System.out.println(temputure);
                temp = temputure;
                try {
                    WebResource webResource = client.resource(baseURL + "thermostats/" + device
                            + "/target_temperature_c?auth=" + auth);
                    ClientResponse response = webResource.type(MediaType.TEXT_PLAIN_TYPE)
                            .put(ClientResponse.class, String.valueOf(temputure));
                    if (response.getStatus() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + response.getStatus());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
