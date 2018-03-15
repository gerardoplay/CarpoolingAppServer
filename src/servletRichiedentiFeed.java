

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
 * Servlet implementation class servletRichiedentiFeed
 */
@WebServlet("/servletRichiedentiFeed")
public class servletRichiedentiFeed extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletRichiedentiFeed() {
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
	    	JSONObject js = new JSONObject(sb.toString());
	    String cod= js.getString("cod");
		queryDB qdb = new queryDB();
		ResultSet rs = qdb.query("select * from richiesta where codpercorso="+cod);
		JSONObject jsout = new JSONObject();
		JSONArray arrcod = new JSONArray();
		JSONArray text = new JSONArray();
		JSONArray arrun = new JSONArray();
		while(rs.next()){
			arrun.put(rs.getString("nomeutenterichiedente"));
			arrcod.put(rs.getString("cod"));
			String utente = rs.getString("nomeutenterichiedente");
			System.out.println(utente);
			ResultSet rs2= qdb.query("select * from utente where nomeutente='"+utente+"'");
			rs2.next();
			text.put(rs.getString("nomeutenterichiedente")+":   ["+rs2.getString("feedr")+"/"+rs2.getString("nrric")+"]");
		}
		jsout.put("text", text);
		jsout.put("cod",arrcod);
		jsout.put("utenti", arrun);
		jsout.put("contr", 1);
		response.getWriter().write(jsout.toString());
	    }catch(JSONException e){
	    	e.printStackTrace();
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
