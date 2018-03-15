import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/servletTrasportoAlternativo")
public class servletTrasportoAlternativo  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String USER_AGENT = "Mozilla/5.0";
	private String host = "http://192.168.1.109:8080", url="/serverAutobus/Provola";
	String cod="";
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
		System.out.println("prova servlet 2"); 
		//String utente=(String) request.getSession().getAttribute("utente");
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
		System.out.println("risultato servlet prova: "+cod );
		}
		catch (Exception e) {e.printStackTrace();
			// TODO: handle exception
		}
		
		SecureRandom sec = new SecureRandom();
		String urlemail="", codreg="";
		
		
		urlemail = host+url+"?codregistrazione="+cod;
		
		System.out.println(urlemail);
		
		
		
		URL obj = new URL(urlemail);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		
		
		
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

		//print result
		System.out.println(response1.toString());
		try {
			jsout.put("cod", response1.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().write(jsout.toString());
		
		
		
		
		/*try {
			qb.query("select * from richiesta where nomeutenterichiedente='"+ utente +"' and codpercorso in (select cod from percorso where data<now())");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
