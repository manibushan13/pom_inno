package HA.Utilities;

import HA.Component.*;

public class Fetch_PageLocaters {

	public Fetch_PageLocaters() {
		try{
			LoginPage.getAllLocaters();
			GmailPage.getAllLocaters();
			Homepage.getAllLocaters();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


}
