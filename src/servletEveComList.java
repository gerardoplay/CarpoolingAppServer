import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Servlet implementation class servletRichiesteIn
 */
@WebServlet("/servletEveComList")
public class servletEveComList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String utente;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletEveComList() {
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
		try{
			System.out.println(this.getClass());
			utente = (String) request.getSession().getAttribute("utente");
			queryDB db = new queryDB();

			ResultSet rs,rs1;			
			
			JSONArray perindirizzo = new JSONArray();
			JSONArray perdata = new JSONArray();
			JSONArray perorario = new JSONArray();
			JSONArray percod = new JSONArray();
			JSONArray perautista = new JSONArray();
			JSONArray jscoddd = new JSONArray();
			JSONArray jsperautista2 = new JSONArray();
			
			
			
			//JSONArray perstato = new JSONArray();
			
			JSONArray ricindirizzo = new JSONArray();	
			JSONArray ricdata = new JSONArray();
			JSONArray ricorario = new JSONArray();
			JSONArray riccod = new JSONArray();
			//JSONArray ricstato = new JSONArray();
			JSONObject js = new JSONObject();

			try {
				rs = db.query("select * from richiesta where nomeutenterichiedente='"+utente+"'");
				rs1 = db.query("select * from percorso where nomeutente ='"+utente+"'");
				while(rs1.next()){
					//System.out.println(rs.getString("indirizzo"));

					GregorianCalendar gc = new GregorianCalendar();
					String dateper = rs1.getString("data");

					GregorianCalendar gcper = new GregorianCalendar(Integer.parseInt(dateper.substring(6,10)),
							Integer.parseInt(dateper.substring(3,5))-1, 
							Integer.parseInt(dateper.substring(0, 2)));

					if(gc.compareTo(gcper)>0){
						perindirizzo.put(rs1.getString("indirizzopart"));
						perdata.put(rs1.getString("data"));
						perorario.put(rs1.getString("orario"));
						//per.put(rs1.getString("data")+": "+rs1.getString("indirizzopart"));
						percod.put(rs1.getInt("cod"));
						jsperautista2.put(rs1.getString("nomeutente"));
					}
				}
				ResultSet rsdata;
				while(rs.next()){
					GregorianCalendar gc = new GregorianCalendar();
					String codp = rs.getString("codpercorso");
					rsdata= db.query("select data,nomeutente,cod, orario from percorso where cod='"+codp+"'");
					rsdata.next();
					String dateper = rsdata.getString("data"),
							orarioper = rsdata.getString("orario"),
							autista=rsdata.getString("nomeutente"),
                            coddd=rsdata.getString("cod");
					
					GregorianCalendar gcper = new GregorianCalendar(Integer.parseInt(dateper.substring(6,10)),
							Integer.parseInt(dateper.substring(3,5))-1, 
							Integer.parseInt(dateper.substring(0, 2)));
					
					if(gc.compareTo(gcper)>0){
						ricindirizzo.put(rs.getString("indirizzo"));
						ricdata.put(dateper);
						ricorario.put(orarioper);
						perautista.put(autista);
						jscoddd.put(coddd);
						//ric.put(dateper+": "+rs.getString("indirizzo"));
						riccod.put(rs.getInt("cod"));
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(riccod.length()>0 || percod.length()>0){
				js.put("contr", 0);
				js.put("perindirizzo", perindirizzo);
				js.put("percodici", percod);
				js.put("perdata", perdata);				
				js.put("perorario", perorario);
				js.put("perautista",jsperautista2);
				
				
				
				js.put("autista", perautista);
				js.put("coddd", jscoddd);
				js.put("ricindirizzo", ricindirizzo);
				js.put("riccodici", riccod);
				js.put("ricdata", ricdata);
				js.put("ricorario", ricorario);
				
				
				response.getWriter().write(js.toString());
			}else{
				js.put("contr", -1);
				//System.out.println("nessun evento");
				response.getWriter().write(js.toString());
			}
		}catch(JSONException e ){

		}
	}


}
