

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
 * Servlet implementation class servletFeedRichiesta
 */
@WebServlet("/servletFeedRichiesta")
public class servletFeedRichiesta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletFeedRichiesta() {
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

			String utente = request.getSession().getAttribute("utente").toString();
			String cod = 	j.getString("cod");
			int feed = j.getInt("feed");
			queryDB qdb = new queryDB();
			updateDB updb = new updateDB();

			ResultSet rs; 
			rs=qdb.query("select codpercorso  from richiesta where cod="+cod+"");
			rs.next();
			String codp = rs.getString("codpercorso");
			rs =  qdb.query("select count(cod) from feedback where codpercorso="+codp);
			rs.next();
			JSONObject jj = new JSONObject();
			if(rs.getInt(1)==0){	
				rs = qdb.query("select nomeutente from percorso where cod="+codp+"");
				rs.next();
				String utentef = rs.getString("nomeutente");

				updb.inserimento("insert into feedback(utenter,utentef,feed,codpercorso,codrichiesta)values ('"+utente+"','"+utentef+"',"+feed +","+ codp+","+cod+")");
				ResultSet rs3 = qdb.query("select * from utente where nomeutente='"+utentef+"'");
				rs3.next();
				if(feed==0){
				double f = rs3.getDouble("feedr")-1;
				updb.inserimento("update utente set feedr="+f+ " where nomeutente='"+utentef+"'");
				}
				jj.put("contr", 5);
			}else{
				jj.put("contr",6);
			}
			response.getWriter().write(jj.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

}
