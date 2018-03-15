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
@WebServlet("/servletSpinnerversione")
public class servletSpinnerversione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletSpinnerversione() {
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
		if(Connessione.controlloSessione(request)){
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
				String mdl = j.getString("modello");
		
			
			
			if(Connessione.controlloSessione(request)){

				JSONObject js = new JSONObject();
				JSONArray versioni = new JSONArray();
				JSONArray codici = new JSONArray();
				//marche.put("--marche");
				
				queryDB qb = new queryDB();
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/plain");
				
				
					

	      //  Gson js = new Gson();
			try {
				ResultSet rs = qb.query("SELECT * FROM versione where codmo='"+mdl+"'");
				while(rs.next()){
					versioni.put(rs.getString("nome"));
					codici.put(rs.getInt("cod"));
				}
				//String jss = js.;
				js.put("dati",versioni);
				js.put("codici", codici);
				js.put("contr", 2);
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
/*
		String modello = request.getParameter("modello");
		System.out.println("ServletSpinnermarca");
		ArrayList<String> marche = new ArrayList<String>();
		marche.add("--versioni");
		queryDB qb = new queryDB();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
       // System.out.println(str);
        Gson js = new Gson();
		try {
			ResultSet rs = qb.query("SELECT * FROM versione where codmo='"+modello+"'");
			while(rs.next()){
				marche.add(rs.getString("cod")+"%c%"+rs.getString("nome"));
			}
			//String jss = js.;
			
			response.getWriter().write(js.toJson(marche));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else{
			response.getWriter().write("ERRORE");
		}*/
	}
	

}
