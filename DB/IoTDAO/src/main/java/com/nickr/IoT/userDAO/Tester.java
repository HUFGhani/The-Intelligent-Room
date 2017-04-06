package com.nickr.IoT.userDAO;

import java.util.ArrayList;
import java.util.Date;

import com.nickr.IoT.user.model.RegistrationRequest;
import com.nickr.IoT.user.model.Sensor;


public class Tester {
	
	public Tester() {
		
	}

	public static void main(String[] args) {
		projectDAO dao = new projectDAO();
		ArrayList<Sensor> allSensors = dao.getSensors(1);

//		for (int i=0;i<allSensors.size(); i++){
//			System.out.println("DEBUG: All Sensors "+ i +" " + allSensors.get(i).getSensorName());
//		} 
//		
//		System.out.println(dao.getHouseId("nick12345@hotmail.co.uk", "User123"));
		
		System.out.println(dao.logIn("nick12345@hotmail.co.uk", "User123").toString());
		
		RegistrationRequest request;
		
		
		
		
	
//		
//		oneCourse = cdao.getCourse("Programming");
//		System.out.println("DEBUG: Grabbing just one course " +oneCourse.toString());
//		
//		Sensor testSensor = new Sensor(1, "test", "location", 2, 16/02/12, 3 );
//		int result = dao.insertSensors(testSensor);
//		System.out.println(result);
//
	}
}
