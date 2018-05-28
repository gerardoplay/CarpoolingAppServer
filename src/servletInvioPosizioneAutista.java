import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/servletInvioPosizioneAutista")
public class servletInvioPosizioneAutista  extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String codPercorso="";
	    private String nomeAutista="";
	    private String lat="";
	    private String lng="";
	    private String data="";
	    

	    JSONObject jsout = new JSONObject();
		
	public servletInvioPosizioneAutista() {
		super();
		// TODO Auto-generated constructor stub
	}

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(this.getClass());
		//PRENDIAMO I DATI CHE ARRIVANO DALL'APP
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
		codPercorso = j.getString("percodice");
		nomeAutista = j.getString("codAutista");
		//System.out.println("risultato servlet prova: "+ nomeAutista+" "+codPercorso);
		
		}
		catch (Exception e) {e.printStackTrace();
			// TODO: handle exception
		}
		
		// ANDIAMO A CERCARE I DATI LAT,LNG E ultimaModificaPosizione DENTRO AL DATABASE:
		
		queryDB db = new queryDB();
		ResultSet rs;
		try {
			rs = db.query("SELECT latAutista, lngAutista, ultimaModificaPosizione from percorso where cod="+ codPercorso +" and nomeutente="+"'"+ nomeAutista+"'");         
			rs.next();
			lat=rs.getString("latAutista");
			lng=rs.getString("lngAutista");
			data=rs.getString("ultimaModificaPosizione");
			}
	 catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		
		//ADESSO INVIAMO I DATI ALL'APP
	//	System.out.println("risultati query: "+ lat+ " "+ lng+" "+ data);
		try {
			if(lat!=null) {
			jsout.put("lat", lat.toString());
			jsout.put("lng", lng.toString());
			jsout.put("data", data.toString());
			}
			else {
				jsout.put("lat", 0);
				jsout.put("lng", 0);
				jsout.put("data", 0);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		response.getWriter().write(jsout.toString());
		
		
	}
	}

