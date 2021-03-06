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
 * Servlet implementation class servletAnnullaRichiesta
 */
@WebServlet("/servletAnnullaRichiesta")
public class servletAnnullaRichiesta extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public servletAnnullaRichiesta() {
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
			updateDB qdb = new updateDB();
			qdb.inserimento("delete from richiesta where cod='"+cod+"' and nomeutenterichiedente='"+utente+"'");
			JSONObject jj = new JSONObject();
			jj.put("contr", 4);

			response.getWriter().write(jj.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();



		}
	}

}
