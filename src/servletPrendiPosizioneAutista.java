import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
@WebServlet("/servletPrendiPosizioneAutista")
public class servletPrendiPosizioneAutista  extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private String codPercorso="";
    private String nomeAutista="";
    private String lat="";
    private String lng="";
    private String timeStamp="";
 
	
	
	public servletPrendiPosizioneAutista() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//PRENDIAMO I DATI CHE ARRIVANO DALL'APP
		
		
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
		codPercorso = j.getString("codPercorso");
		nomeAutista = j.getString("codAutista");
		lat= j.getString("lat");
		lng= j.getString("lng");
		
		
		//data e ora corrente 
		
		timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		System.out.println(timeStamp);
		System.out.println("risultato servlet prova: "+codPercorso+" "+ nomeAutista+" "+lat+" "+lng+" "+" "+timeStamp);
		}
		catch (Exception e) {e.printStackTrace();
			// TODO: handle exception
		}
		
		// ANDIAMO A CARICARE I DATI APPENA RICEVUTI DENTRO AL DATABASE:
		
		updateDB ub = new updateDB();
		
	    ub.inserimento("UPDATE percorso SET latAutista=" + lat + " WHERE " + "cod=" + codPercorso + " and nomeutente="+"'"+ nomeAutista+"'");
	    ub.inserimento("UPDATE percorso SET lngAutista=" + lng + " WHERE " + "cod=" + codPercorso + " and nomeutente="+"'"+ nomeAutista+"'");
	    ub.inserimento("UPDATE percorso SET ultimaModificaPosizione=" +"'"+ timeStamp+"'" + " WHERE " + "cod=" + codPercorso + " and nomeutente="+"'"+ nomeAutista+"'");
		
	    
	}

}
