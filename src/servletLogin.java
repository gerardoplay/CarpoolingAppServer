import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;





@WebServlet("/servletLogin")
public class servletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
	}

/**
 * @return 	(1) login avvenuto con successo
 * 			(0) login fallito
 * 			(-1) utente non attivo
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

		
		//System.out.println(js.get());
		
		    
		
		String password="";
		String username="";
		try {
			password =   js.get("password").toString();
			username =  js.get("username").toString();
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        try {
			//Connection con = Connessione.getConnection();
			//Statement st = con.createStatement();
			//ResultSet res = st.executeQuery("select COUNT(idateneo) from utente where idateneo='"+username+"' AND password='"+password+"'");
			queryDB qdb = new queryDB();
			ResultSet res = qdb.query("select COUNT(nomeutente) from utente where nomeutente='"+username+"' AND password='"+password+"' AND stato like 'registrato'");
        	res.next();
        	int numcount =res.getInt(1);
			
        	if(numcount==1){
				//HttpSession sessio1 = response.
				HttpSession session = request.getSession();
				session.setAttribute("utente", username);
				session.setMaxInactiveInterval(100000);
				//System.out.println(session.getId());
				JSONObject j = new JSONObject();
				j.put("contr", 1);
				response.getWriter().write(j.toString());
				System.out.println("Login   "+username);
			}
			else{

				if(numcount==0){
					ResultSet res2 = qdb.query("select COUNT(nomeutente) from utente where nomeutente='"+username+"' AND password='"+password+"' AND stato like 'inattivo'");
					res2.next();
					JSONObject j= new JSONObject();
					if(res2.getInt(1)==1){
						j.put("contr", -1);
						//System.out.println("utente non attivo ");
					}else{
						j.put("contr", 0);
						//System.out.println("Login fallito ");
					}
					response.getWriter().write(j.toString());
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

}
