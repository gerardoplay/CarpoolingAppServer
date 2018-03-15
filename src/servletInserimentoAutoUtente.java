

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
 * Servlet implementation class servletInserimentoAutoUtente
 */
@WebServlet("/servletInserimentoAutoUtente")
public class servletInserimentoAutoUtente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletInserimentoAutoUtente() {
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

			String utente = request.getSession().getAttribute("utente").toString();
			String targa = 	j.getString("targa");
			String colore =	 j.getString("colore");
			String versione =	j.getString("versione");
			updateDB ud = new updateDB();
			ud.inserimento("insert into autoutente(nomeutente,codversione,targa,colore) values('"+utente+"','"+versione+"','"+targa+"','"+colore+"');");
			JSONObject jj = new JSONObject();
			jj.put("contr", 4);
			
			response.getWriter().write(jj.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}else
			response.getWriter().write("ERRORE");
	}

}
