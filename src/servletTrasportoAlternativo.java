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

@WebServlet("/servletTrasportoAlternativo")
public class servletTrasportoAlternativo  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String USER_AGENT = "Mozilla/5.0";
	private String host = "http://192.168.1.4:8080", url="/serverAutobus/ServletTrovaPercorsiAutobus";
	private String cod="";
	private String indirizzo="";
	private String data="";
	private String ora="";
	private String ar="";
	private String indlat="";
	private String indlon="";
	
	
	
	
	JSONObject jsout = new JSONObject();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletTrasportoAlternativo() {
		// TODO Auto-generated constructor stub
	
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
		// TODO Auto-generated method stub
		
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
		indirizzo= j.getString("indirizzo");
		data= j.getString("data");
		ora= j.getString("ora");
		ar=j.getString("ar");
		indlat=j.getString("indlat");
		indlon=j.getString("indlon");
		
		//System.out.println("risultato servlet prova: "+cod+" "+data+" "+ora+" "+indirizzo+" "+ar+" "+indlat+" "+indlon);
		}
		catch (Exception e) {e.printStackTrace();
			// TODO: handle exception
		}		
		
		// MANDIAMO I DATI AL SERVER AUTOBUS 
		SecureRandom sec = new SecureRandom();
		String urlemail="", codreg="";
		urlemail = host+url+"?ar="+ar+"&data="+data+"&ora="+ora+"&indlat="+indlat+"&indlon="+indlon;
		URL obj = new URL(urlemail);
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

		
		//INVIAMO LA RISPOSTA ALL'APP 
		//print result
		//System.out.println(response1.toString());
		try {
			
			JSONObject js = new JSONObject(response1.toString());
			System.out.println("jssssssssss  "+js.toString());
			if(js.toString().equals("{}")) {
			response.getWriter().write("niente");
			}
			else {
			JSONArray codPercorsi=js.getJSONArray("codPercorsi");
			JSONArray dataPercorsi= js.getJSONArray("dataPercorsi");
			JSONArray oraArrivoPercorsi=js.getJSONArray("oraArrivo");
			JSONArray oraPartenzaPercorsi=js.getJSONArray("oraPartenza");
			JSONArray numeroPullman=js.getJSONArray("numero");


			//System.out.println("eeeeeeee: "+codPercorsi.getString(0));
			
			jsout.put("codPercorsi", codPercorsi);
			jsout.put("dataPercorsi", dataPercorsi);
			jsout.put("oraArrivo", oraArrivoPercorsi);
			jsout.put("oraPartenza", oraPartenzaPercorsi);
			jsout.put("numero", numeroPullman);
			response.getWriter().write(jsout.toString());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		
		
		
		
		/*try {
			qb.query("select * from richiesta where nomeutenterichiedente='"+ utente +"' and codpercorso in (select cod from percorso where data<now())");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
