

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
 * Servlet implementation class servletSpinnerAuto
 */
@WebServlet("/servletSpinnerAuto")
public class servletSpinnerAuto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletSpinnerAuto() {
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
		
		if(Connessione.controlloSessione(request)){
			System.out.println(getClass());
			String ut = request.getSession().getAttribute("utente").toString();
			JSONArray auto = new JSONArray();
			auto.put("--auto");
			queryDB qb = new queryDB();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			
			try {
				ResultSet rs = qb.query("SELECT * FROM autoutente where nomeutente='"+ut+"'");
				while(rs.next()){
					auto.put(rs.getString("targa"));
				}
			//String jss = js.;
			js.put("auto", auto);
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
