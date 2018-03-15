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

/**
 * Servlet implementation class servletVisualizzaInformazioniPercorso
 */
@WebServlet("/servletVisualizzaInformazioniPercorso")
public class servletVisualizzaInformazioniPercorso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletVisualizzaInformazioniPercorso() {
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
		//if(Connessione.controlloSessione(request)){
		
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
		
			try {
				@SuppressWarnings("unused")
				String utente = (String) request.getSession().getAttribute("utente");
				String cod = (String) js.getString("cod");
				queryDB qdb = new queryDB();
				//System.out.println("visualizzainformazioni  "+cod);
				ResultSet rs =qdb.query("select * from percorso where cod='"+cod+"'");
				rs.next();
				//System.out.println(rs.getString("indirizzopart"));
				JSONObject jsout = new JSONObject();
				jsout.put("indirizzo",rs.getString("indirizzopart"));
				jsout.put("contr", 0);
				response.getWriter().write(jsout.toString());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		}catch(JSONException e){
			
		}
		}
	//}

}
