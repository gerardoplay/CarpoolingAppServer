

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class confermaRegistrazione
 */
@WebServlet("/confermaRegistrazione")
public class confermaRegistrazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public confermaRegistrazione() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @return 	(registrazione conclusa con successo) se la registrazione è andata a buonfine
	 * 			(Utente già registrato) se l'utente risulta già registrato con account attivo
	 * 			(coderr)	altrimenti
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("hello0");
		//String email = request.getParameter("email");
		String codreg = request.getParameter("codregistrazione");
		//System.out.println(email+"   "+codreg);
		updateDB ins = new updateDB();
		String contr = ins.inserimento("update utente set stato='registrato' where codregistrazione like '"+codreg+"' and stato like 'inattivo'");
		if(contr.equalsIgnoreCase("1")){
			response.getWriter().write("Registrazione conclusa con successo");
		}else
			if(contr.equalsIgnoreCase("0")){
				response.getWriter().write("Utente già registrato");
			}else
				response.getWriter().write("CodErrore: "+contr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
