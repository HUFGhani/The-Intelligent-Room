package io.github.hufghani;

import io.github.hufghani.nest.NestService;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class NestServiceTest {

    @Test
    public void Temp(){
        NestService nestService = new NestService() ;
        nestService.setNestTemputure(30,true);
        Assert.assertEquals("{ \"target_temperature_c\":30.0,\"automated\":true}",nestService.getAllString());
    }

    @Test
    public void Temp2(){
        NestService nestService = new NestService() ;
        nestService.setNestTemputure(9,true);
        Assert.assertNotEquals("{ \"target_temperature_c\":30.0,\"automated\":true}",nestService.getAllString());
    }

}
