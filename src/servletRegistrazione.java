import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.SecureRandom;
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
 * 
 */
@WebServlet("/servletRegistrazione")
public class servletRegistrazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String host = "http://192.168.1.109:8080", url="/provaServer/confermaRegistrazione";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public servletRegistrazione() {
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
	 * @throws IOException 
	 * @return 	(-2) se l'utente è già registrato
	 * 			(-1) se l'utente ha già richiesto la registrazione ma non ha confermato quet'ultima
	 * 			(0)  in caso di errore generico
	 * 			(1)  in caso di avvenuta registrazione
	 * @throws  
	 * 
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {


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
			try{

				JSONObject jsout = new JSONObject();
				j = new JSONObject(sb.toString());
				String 	//nome = request.getParameter("nome"),
				//cognome = request.getParameter("cognome"),
				email = j.getString("email"),
				password = j.getString("password"),
				sesso = "f";




				//response.getWriter().write("qualcosalafa");
				updateDB ins = new updateDB();
				//	queryDB que = new queryDB();
				//	String idateneo = email.substring(0,email.indexOf("@"));
				SecureRandom sec = new SecureRandom();
				queryDB que = new queryDB();
				String urlemail="", codreg="";
				boolean db=true;
				ResultSet res3 = que.query("select count(*) from utente where nomeutente  like '"+email+"'");
				res3.next();
				int contrint = 0;

				if(res3.getInt(1)==0){

					while(db){

						codreg = new BigInteger(130,sec).toString(32);//""+(int)(Math.random()*100000);
						urlemail = host+url+"?codregistrazione="+codreg;


						ResultSet rs = que.query("select count(*) from utente where codregistrazione like '"+urlemail+"'");
						rs.next();
						if(rs.getInt(1)==0)
							db=false;


					}
					URL urll = new URL(urlemail);


					String contr="";


					contr = ins.inserimento("insert into utente(nomeutente,codregistrazione,password) values('"+email+"','"+codreg+"','"+password+"')");//= ins.inserimento("insert into utente(idateneo,nome,cognome,email,password) values('"+idateneo+"','"+nome+"','"+cognome+"','"+email+"','"+password+"')");
					if(contr.equalsIgnoreCase("1")){
						EmailSender te = new EmailSender(email,"Conferma Registrazione", "Clicca sul seguente link per attivare il servizio carpooling "+urlemail);
						te.start();
					}
					jsout.put("contr", 1 );
					contrint = Integer.parseInt(contr);
				}
				else{	
					ResultSet res2 = que.query("select count(nomeutente) from utente where nomeutente like '"+email+"' and stato like 'registrato'");
					res2.next();
					contrint=-1;
					if(res2.getInt(1)==1)
						contrint=-2;
					jsout.put("contr", contrint);
				}
				response.getWriter().write(jsout.toString());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

				response.getWriter().write(new JSONObject().put("contr", 0).toString());

			}

		}catch (JSONException e) {
			// TODO: handle exception
		}
	}
	//System.out.println("sono nella servlet"+contr);

}


