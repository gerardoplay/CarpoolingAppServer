import java.io.BufferedReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class servletPrendiPosizioneAutista {
	
    private String codPercorso="";
    private String nomeAutista="";
    private String lat="";
    private String lng="";
    private String data="";
 
	
	
	public servletPrendiPosizioneAutista() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//PRENDIAMO I DATI CHE ARRIVANO DALL'APP
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
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//2016/11/16 12:08:43
		Calendar cal = Calendar.getInstance();
		data=dateFormat.format(cal);
		
		System.out.println("risultato servlet prova: "+codPercorso+" "+ nomeAutista+" "+lat+" "+lng+" "+" "+data);
		}
		catch (Exception e) {e.printStackTrace();
			// TODO: handle exception
		}
		
		// ANDIAMO A CARICARE I DATI APPENA RICEVUTI DENTRO AL DATABASE:
		
		updateDB ub = new updateDB();
		
	    ub.inserimento("UPDATE percorso SET latAutista=" + lat + "WHERE" + "cod=" + codPercorso + "and nomeutente="+ nomeAutista);
	    ub.inserimento("UPDATE percorso SET lngAutista=" + lng + "WHERE" + "cod=" + codPercorso + "and nomeutente="+ nomeAutista);
	    ub.inserimento("UPDATE percorso SET ultimaModificaPosizione=" + data + "WHERE" + "cod=" + codPercorso + "and nomeutente="+ nomeAutista);
		
	}

}
