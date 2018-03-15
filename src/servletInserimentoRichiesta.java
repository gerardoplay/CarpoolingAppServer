

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class servletInserimentoRichiesta
 */
@WebServlet("/servletInserimentoRichiesta")
public class servletInserimentoRichiesta extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public servletInserimentoRichiesta() {
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

		JSONObject js;
		try {
			js = new JSONObject(sb.toString());
			String utente = (String) request.getSession().getAttribute("utente");
			String codpercorso=(String) js.getString("codpercorso");
			String indirizzo=(String) js.getString("indirizzo");
			double lat = js.getDouble("lat");
			double lon = js.getDouble("lon");
			int ari =1;
			boolean ar = js.getBoolean("ar");
			if(ar)
				ari=2;

			updateDB updb = new updateDB();

			String contr=updb.inserimento("insert into richiesta(codpercorso,nomeutenterichiedente,indirizzo,ar,indlat,indlon) " +
					"values('"+codpercorso+"','"+utente+"','"+indirizzo+"',"+ari+","+lat+","+lon+")");
			JSONObject jsout = new JSONObject();
			if(contr.equalsIgnoreCase("1")){
				jsout.put("contr", 1 );

			}
			else
				jsout.put("contr",-1 );

			response.getWriter().write(jsout.toString());
		}

		catch(JSONException e){
			e.printStackTrace();
		}

	}
	

}
