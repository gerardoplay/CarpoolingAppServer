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
 * Servlet implementation class servletSpinnerModello
 */
@WebServlet("/servletSpinnerModello")
public class servletSpinnerModello extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletSpinnerModello() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * 
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
			j = new JSONObject(sb.toString());
			String str = j.getString("marca");
	
		
		
		if(Connessione.controlloSessione(request)){

			JSONObject js = new JSONObject();
			JSONArray modelli = new JSONArray();
			JSONArray codici = new JSONArray();
			//marche.put("--marche");
			
			queryDB qb = new queryDB();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			
			
				

      //  Gson js = new Gson();
		try {
			ResultSet rs = qb.query("SELECT * FROM modello where codma='"+str+"'");
			while(rs.next()){
				modelli.put(rs.getString("nome"));
				codici.put(rs.getInt("cod"));
			}
			//String jss = js.;
			js.put("dati",modelli);
			js.put("codici", codici);
			js.put("contr", 1);
			response.getWriter().write(js.toString());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else{
			response.getWriter().write("ERRORE");
		}
	}catch(JSONException e1){
			
		}
	}
	

}
