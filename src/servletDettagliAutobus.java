import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/servletDettagliAutobus")
public class servletDettagliAutobus extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private final String USER_AGENT = "Mozilla/5.0";
	private String host = "http://192.168.1.4:8080", url="/serverAutobus/ServletTrovaDettagliAutobus";
	private String cod="";

	JSONObject jsout = new JSONObject();
	
	public servletDettagliAutobus() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("prova servlet 1"); 
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// PRENDIAMO IL CODICE CHE ARRIVA DALL'APP
				queryDB qb = new queryDB();
			
				StringBuilder sb = new StringBuilder();
				BufferedReader reader = request.getReader();
				try {
					String line;
					while ((line = reader.readLine()) != null) {
						sb.append(line).append('\n');
					}
				} finally {
					reader.close();
				}
				
				JSONObject j;
				try {
				j= new JSONObject(sb.toString());
				cod = j.getString("cod");
				}
				catch (Exception e) {e.printStackTrace();
					// TODO: handle exception
				}	
				
				
				
				// MANDIAMO I DATI AL SERVER AUTOBUS 
				SecureRandom sec = new SecureRandom();
				String urlemail="";
				urlemail = host+url+"?cod="+cod;
				URL obj = new URL(urlemail);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();

				// optional default is GET
				con.setRequestMethod("GET");
				
				//add request header
				con.setRequestProperty("User-Agent", USER_AGENT);
				int responseCode = con.getResponseCode();
	
				// PRENDIAMO LA RISPOSTA DEL SERVER AUTOBUS CHE SI CHIAMA RESPONSE1
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response1 = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response1.append(inputLine);
				}
				in.close();
				
				//INVIAMO LA RISPOSTA ALL'APP 
				try {
					
					JSONObject js = new JSONObject(response1.toString());
					//System.out.println("jssssssssss  "+js.toString());
					if(js.toString().equals("{}")) {
					response.getWriter().write("niente");
					}
					else {
						
					JSONArray indFermata = js.getJSONArray("indFermata");
					JSONArray oraFermata =js.getJSONArray("oraFermata");
					JSONArray latFermata =js.getJSONArray("latFermata");
					JSONArray lonFermata = js.getJSONArray("lonFermata");
					
					jsout.put("indFermata", indFermata);
					jsout.put("oraFermata", oraFermata);
					jsout.put("latFermata", latFermata);
					jsout.put("lonFermata", lonFermata);
				
					response.getWriter().write(jsout.toString());
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
	
	}
}
