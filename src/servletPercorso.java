

import java.io.BufferedReader;
import java.io.IOException;
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
 * Servlet implementation class servletPercorso
 */
@WebServlet("/servletPercorso")
public class servletPercorso extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public servletPercorso() {
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
		try {
			j= new JSONObject(sb.toString());

			//String utente = request.getSession().getAttribute("utente").toString();
			String cod = j.getString("cod"),
					type= j.getString("type");
			queryDB qdb = new queryDB();
			JSONObject js = new JSONObject();
			JSONArray lat = new JSONArray();
			JSONArray lon = new JSONArray();
			boolean ar = false ;
			if(type.equalsIgnoreCase("ric")){

				ResultSet rs = qdb.query("select codpercorso,ar from richiesta where cod='"+cod+"'");
				rs.next();
				String codp = rs.getString("codpercorso");
				int arint = rs.getInt("ar");
				if(arint==2)
					ar=true;
				else
					ar=false;
				
				ResultSet rs1 = qdb.query("select * from percorso where cod='"+codp+"'");
				rs1.next();
				
				lat.put(rs1.getDouble("partlat"));
				lon.put(rs1.getDouble("partlon"));
				rs = qdb.query("select * from richiesta where codpercorso='"+codp+"'");
				while(rs.next()){
					lat.put(rs.getDouble("indlat"));
					lon.put(rs.getDouble("indlon"));
				}

				lat.put(rs1.getDouble("destlat"));
				lon.put(rs1.getDouble("destlon"));
			}
			if(type.equalsIgnoreCase("per")){

				ResultSet rs1 = qdb.query("select * from percorso where cod='"+cod+"'");
				rs1.next();
				int arint = rs1.getInt("ar");
				if(arint==2)
					ar=true;
				else
					ar=false;
				lat.put(rs1.getDouble("partlat"));
				lon.put(rs1.getDouble("partlon"));
				ResultSet rs = qdb.query("select * from richiesta where codpercorso='"+cod+"'");
				while(rs.next()){
					lat.put(rs.getDouble("indlat"));
					lon.put(rs.getDouble("indlon"));
				}
				lat.put(rs1.getDouble("destlat"));
				lon.put(rs1.getDouble("destlon"));
			}

			js.put("lat", lat);
			js.put("lon", lon);
			js.put("ar", ar);
			js.put("contr",10);
			for(int i=0;i<lat.length();i++){
				System.out.println(lat.get(i)+" "+lon.get(i));
			}
			response.getWriter().write(js.toString());
		}catch(JSONException e){
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
