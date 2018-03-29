import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class transit {
	private final String USER_AGENT = "Mozilla/5.0";
	private String host="https://maps.googleapis.com",url="/maps/api/directions/json";
	//	?origin=Mountain%20View&destination=1600%20Amphitheatre%20Parkway&mode=transit&transit_mode=bus&transit_routing_preference=fewer_transfers&key=YOUR_API_KEY.

	public transit() {
		super();
	
	}

		public void cacca() throws IOException, JSONException {
		// MANDIAMO I DATI AL SERVER AUTOBUS 
		//SecureRandom sec = new SecureRandom();
		String urlemail="", codreg="";
		urlemail = host+url+"?departure_time=now&origin=Napoli&destination=Fisciano&mode=TRANSIT&key=AIzaSyBdpa0bBULmR-ILjQ8wF_FCJ3OLKRPnQB8";
	
		
		
		 //https://maps.googleapis.com/maps/api/directions/json?departure_time=now&destination=place_id%3AChIJp4QhcgzyGGARZaBIPuJzfpg&mode=TRANSIT&origin=place_id%3AChIJlyOpErWHGGAR0156e32g1Xs&key=API_KEY
	
			URL	obj = new URL("https://maps.googleapis.com/maps/api/directions/json?departure_time=now&origin=Carpineto,+SA&destination=Fisciano+Universita,+Fisciano,+SA&mode=transit&key=AIzaSyBdpa0bBULmR-ILjQ8wF_FCJ3OLKRPnQB8");
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");
		
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		
		
		
		int responseCode = con.getResponseCode();
		
		
		// PRENDIAMO LA RISPOSTA DEL SERVER AUTOBUS CHE SI CHIAMA RESPONSE1
		//System.out.println("\nSending 'GET' request to URL : " + url);
		//System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response1 = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response1.append(inputLine);
		}
		in.close();
		
		
		
		
		JSONObject objj = new JSONObject(response1.toString());

		List<String> list = new ArrayList<String>();
		
		JSONArray array1 = objj.getJSONArray("routes");
		
		JSONObject objj3 = array1.getJSONObject(0);
		System.out.println("è lungo :"+objj3.length());
		//System.out.println(objj3.toString());
		JSONArray objj4 = objj3.getJSONArray("northeast");
				
		
		//System.out.println(objj4.getString(0));
		/*
		JSONArray array2 = array1.getJSONArray("northeast");
		
		//JSONObject objj2 = objj.getJSONObject("routes");
		JSONObject objj3 = objj2.getJSONObject("bounds");
		
		
			JSONArray array1 = objj3.getJSONArray("northeast");
		
		    String[] lat = new String[array1.length()];
		    String[] lng = new String[array1.length()];
		    for(int i = 0 ; i < array1.length() ; i++) {
		       // lat[i] = array1.getString("birthday");
		       // lng[i] = array1.getString("name");
		    }
		
		
		*/
		
	
		
		
		
		
		//System.out.println(list.get(0).toString());
		
		
	
		//System.out.println(response1.toString());
		
		}
	
	
	
}
