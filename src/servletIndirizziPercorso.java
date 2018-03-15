

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
 * Servlet implementation class servletIndirizziPercorso
 */
@WebServlet("/servletIndirizziPercorso")
public class servletIndirizziPercorso extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public servletIndirizziPercorso() {
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
			String cod = j.getString("cod");
			//type= j.getString("type");
			queryDB qdb = new queryDB();
			JSONObject js = new JSONObject();
			JSONArray lat = new JSONArray();

			boolean ar = false ;



			ResultSet rs1 = qdb.query("select * from percorso where cod='"+cod+"'");
			rs1.next();
			//				int arint = rs1.getInt("ar");

			ResultSet rs = qdb.query("select * from richiesta where codpercorso='"+cod+"'");
			while(rs.next()){
				ResultSet rs3 = qdb.query("select * from utente where nomeutente='"+rs.getString("nomeutenterichiedente")+"'");
				rs3.next();
				lat.put(rs.getString("nomeutenterichiedente")+" ["+rs3.getInt("feedr")+"/"+rs3.getString("nrric")+"] :  "+rs.getString("indirizzo")+"");

			}


			js.put("indirizzi", lat);

			js.put("ar", ar);
			js.put("contr",12);

			response.getWriter().write(js.toString());
		}catch(JSONException e){
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
