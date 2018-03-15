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
 * Servlet implementation class servletPercorsiDisponibili
 */
@WebServlet("/servletPercorsiDisponibili")
public class servletPercorsiDisponibili extends HttpServlet {
	private static final double earthRadius = 6371;
	private static final long serialVersionUID = 1L;
	private boolean debug=false; 
	private double lat;
	private double lon;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public servletPercorsiDisponibili() {
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


		try {
			JSONObject jsin = new JSONObject(sb.toString());
			String data = jsin.getString("data");
			lat = jsin.getDouble("lat");  lon = jsin.getDouble("lon");
			boolean ar = jsin.getBoolean("ar");
			int ari;
			if(ar)
				ari=1;
			else
				ari=2;
			queryDB db = new queryDB();
			//ArrayList<String> al = new ArrayList<String>();
			JSONObject js = new JSONObject();
			JSONArray dati = new JSONArray();
			JSONArray cod = new JSONArray();
			ResultSet rs;
			try {
				String utente=(String) request.getSession().getAttribute("utente");
				//System.out.println(data+" "+ari);
				rs = db.query("select * from percorso where data='"+data+"' and nomeutente!='"+utente+"' and ar="+ari+"  and posti > (select count(cod) from richiesta where richiesta.codpercorso = percorso.cod)");



				while(rs.next()){
					if(debug){
						ResultSet rs2 = db.query("select * from utente where nomeutente='"+rs.getString("nomeutente")+"'");
						rs2.next();
						dati.put(rs.getString("nomeutente")+" ["+rs2.getString("feedp")+"/"+rs2.getString("nrper")+"]  alle ore  "+rs.getString("orario"));
						cod.put(rs.getInt("cod"));
					}else{
						//Pair<Float, Float> centro = getCenter()
						double cenlon=(rs.getDouble("destlon")+rs.getDouble("partlon"))/2;
						double cenlat=(rs.getDouble("destlat")+rs.getDouble("partlat"))/2;

						if(isValidForDistance(cenlat, cenlon, lat, lon, rs.getDouble("distanza")+rs.getDouble("rangekm"))){
							double realDist = getDistanceReal(rs);
							if(realDist<rs.getDouble("distanza")+rs.getDouble("rangekm")){
								ResultSet rs2 = db.query("select * from utente where nomeutente='"+rs.getString("nomeutente")+"'");
								rs2.next();
								dati.put(rs.getString("nomeutente")+" ["+rs2.getString("feedp")+"/"+rs2.getString("nrper")+"]  alle ore  "+rs.getString("orario"));
								cod.put(rs.getInt("cod"));
							}
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(dati.length()>0){
				js.put("contr", 0);

			}else{
				js.put("contr", -1);

			}
			js.put("dati", dati);
			js.put("codici", cod);
			response.getWriter().write(js.toString());
		}catch(JSONException e){
			e.printStackTrace();
		}
	}


	private boolean isValidForDistance(double cenlat, double cenlon, double lat, double lon,double d) {
		double distanceLat = Math.toRadians(lat-cenlat);
		double distanceLng = Math.toRadians(lon-cenlon);
		double a = Math.sin(distanceLat/2) * Math.sin(distanceLat/2) +
				Math.cos(Math.toRadians(cenlat)) * Math.cos(Math.toRadians(lat)) *
				Math.sin(distanceLng/2) * Math.sin(distanceLng/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		float dist = (float) (earthRadius * c);
		if(dist>d)
			return false;
		else
			return true;
	}



	private double getDistanceReal(ResultSet rs) throws SQLException, IOException, JSONException {
		// TODO Auto-generated method stub
		String url ="";
		queryDB qb = new  queryDB();
		int codperc = rs.getInt("cod");
		ResultSet rs2 = qb.query("select * from richiesta where codpercorso="+codperc);
		String str_origin = "origin="+rs.getDouble("partlat")+","+rs.getDouble("partlon");
		String str_dest = "destination="+rs.getDouble("destlat")+","+rs.getDouble("destlon"); 

		String waipoints="waypoints=optimize:true|"+lat+","+lon+"|";
		while(rs2.next()){
			if(!rs2.last())
				waipoints+=rs2.getDouble("indlat")+","+rs2.getDouble("indlon")+"|" ;
			else
				waipoints+=rs2.getDouble("indlat")+","+rs2.getDouble("indlon");


		}



		String sensor = "sensor=false";         
		String parameters = str_origin+"&"+str_dest+"&"+waipoints+"&"+sensor;
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
			JSONObject distance = jLegs.getJSONObject(0).getJSONObject("distance");

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
