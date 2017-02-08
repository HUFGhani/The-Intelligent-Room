package io.github.hufghani;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class App 
{
    public static void main( String[] args ) {
        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("https://developer-api.nest.com/devices/thermostats?auth=c.qOL8PQR370XkE3F2oQbSSIwBENrVMYN1DhAQJRpmxDnuzd23e2Z0Ru4fsUKgzc20pHbBkhumPXp9qgwNUJuNJXSeif7ByD2SpcHRiEoX2sNecb7SvwmK3d1X91JGwJxRxMl2fTvDa3m3aa1W");

            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            String output = response.getEntity(String.class);

            System.out.println("Output from Server .... \n");
            System.out.println(output);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}