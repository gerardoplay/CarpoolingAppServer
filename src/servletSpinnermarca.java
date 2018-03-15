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
 * Servlet implementation class servletSpinnermarca
 */
@WebServlet("/servletSpinnermarca")
public class servletSpinnermarca extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletSpinnermarca() {
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
		if(Connessione.controlloSessione(request)){
			System.out.println("ServletSpinnermarca");

			//ArrayList<String> marche = new ArrayList<String>();
			JSONObject js = new JSONObject();
			JSONArray marche = new JSONArray();
			JSONArray codici = new JSONArray();
			//marche.put("--marche");
			
			queryDB qb = new queryDB();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			//Gson js = new Gson();
			try {
				ResultSet rs = qb.query("SELECT * FROM marca");
				while(rs.next()){
					//marche.add(rs.getInt("cod")+"%c%"+rs.getString("nome"));
					marche.put(rs.getString("nome"));
					codici.put(rs.getInt("cod"));
				}
			//String jss = js.;
			js.put("dati",marche);
			js.put("codici", codici);
			js.put("contr", 0);
			response.getWriter().write(js.toString());
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (JSONException e1) {
			// TODO: handle exception
		}
		}else{
			response.getWriter().write("ERRORE");
		}
			
	}

}
