import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class servletInserimentoPercorso
 */
@WebServlet("/servletInserimentoPercorso")
public class servletInserimentoPercorso extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public servletInserimentoPercorso() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		System.out.println(this.getClass());
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
		try{
			j = new JSONObject(sb.toString());
			double distanza= j.getDouble("distanza");
			String 		
			data= j.getString("data"),
			orario = j.getString("orario"),
			indirizzo = j.getString("indirizzo"),
			targa = j.getString("targa"),
			posti = j.getString("posti"),
			range = j .getString("range"),
			nutente = (String) request.getSession().getAttribute("utente");
			double  lat = j.getDouble("lat"),
					lon = j.getDouble("lon");
			boolean ar = j.getBoolean("ar");
			double unilat=40.773720 ,unilon= 14.794522;
			int ari;
			if(ar)
				ari=1;
			else
				ari=2;
			//	System.out.println(data+orario+indirizzo+targa+posti+range);
			queryDB qb = new queryDB();
			try {
				ResultSet rsauto = qb.query("Select count(targa) from autoutente where targa='"+targa+"' and nomeutente='"+nutente+"'");
				rsauto.next();
				//System.out.println();
				String indirizzouni = "Via ponte don melillo,fisciano";
				JSONObject jsout = new JSONObject();
				if(rsauto.getInt(1)==1){
					updateDB ub = new updateDB();
					if(ar){ //dist
						String ctr=ub.inserimento("insert into percorso(data,orario,indirizzopart,indirizzodest,destlat,destlon,partlon,partlat,targa,posti,rangekm,nomeutente,ar,distanza) values('"+data+"','"+orario+"','"+
								indirizzo+"','"+indirizzouni+"',"+unilat+","+unilon+","+lon+","+lat+",'"+targa+"',"+posti+","+range+",'"+nutente+"',"+ ari  +","+distanza+" ) ");
						if(ctr.equalsIgnoreCase("1"))
							response.getWriter().write(jsout.put("contr", 1).toString());
						else
							response.getWriter().write(jsout.put("contr", -1).toString());
					}else{
						String ctr=ub.inserimento("insert into percorso(data,orario,indirizzopart,indirizzodest,destlat,destlon,partlon,partlat,targa,posti,rangekm,nomeutente,ar,distanza) values('"+data+"','"+orario+"','"+
								indirizzouni+"','"+indirizzo+"',"+lat+","+lon+","+unilon+","+unilat+",'"+targa+"',"+posti+","+range+",'"+nutente+"',"+ ari  +","+distanza+" ) ");
						if(ctr.equalsIgnoreCase("1"))
							response.getWriter().write(jsout.put("contr", 1).toString());
						else
							response.getWriter().write(jsout.put("contr", -1).toString());
					}

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch(JSONException e1){
			e1.printStackTrace();
		}
	}
	private double getDistanceReal(double lat1, double lon1,double lat2, double lon2) throws SQLException, IOException, JSONException {
		// TODO Auto-generated method stub
		String url ="";
		queryDB qb = new  queryDB();
		String str_origin = "origin="+lat1+","+lon1;
		String str_dest = "destination="+lat2+","+lon2; 

		String sensor = "sensor=false";         
		String parameters = str_origin+"&"+str_dest+"&"+sensor;
		String output = "json";
		url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
		System.out.println(url);
		String all=savePage(url);
		JSONObject jsmap = new JSONObject(all);
		double distance = calcoladistanzareale(jsmap);
		System.out.println(distance);
		return distance; 

	}

	public static String savePage(final String URL) throws IOException {
		String line = "", all = "";
		BufferedReader in = null;
		URL url = new URL(URL);  
		URLConnection urlConnection = (URLConnection) url.openConnection();

		try {

			in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			while ((line = in.readLine()) != null) {
				all += line;
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}

		return all;
	}
	private double calcoladistanzareale(JSONObject jObject){

		JSONArray jRoutes = null;
		JSONArray jLegs = null;

		try {           

			jRoutes = jObject.getJSONArray("routes");
			long d =0;
			for(int i=0;i<jRoutes.length();i++){            
				jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");

				for(int j=0;j<jLegs.length();j++){
					JSONObject distance=((JSONObject) jLegs.get(j)).getJSONObject("distance");
					d+=distance.getLong("value")/1000;
				}
			}
			//JSONObject distance = jLegs.getJSONObject(0).getJSONObject("distance");

			//String sDistance = distance.getString("text");
			return d;

		} catch (JSONException e) {         
			e.printStackTrace();
		}catch (Exception e){  
			e.printStackTrace();
		}
		return -1;



	}   


}
