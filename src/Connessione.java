

import java.sql.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Connessione {

	private static Connection connection;

	public static boolean testConnection() {
		try {
			Connection con = getConnection();

			System.out.println("Connessione OK \n");
			con.close();
		} catch (SQLException e) {
			System.out.println("Connessione Fallita \n");
			System.out.println(e);
			return false;
		}

		return true;
	} // end main

	public static Connection getConnection() throws SQLException {
		
		
		
		if (connection == null || connection.isClosed()) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				String url = "jdbc:mysql://localhost:3306/carpooling";
				connection = DriverManager.getConnection(url, "root", "");
			} catch (java.lang.ClassNotFoundException e) {
				throw new SQLException(e);
			} catch (IllegalAccessException e) {
				throw new SQLException(e);
			} catch (InstantiationException e) {
				throw new SQLException(e);
			} catch (SQLException e) {
				throw new SQLException(e);
			}
		}

		return connection;
	}


	public static boolean controlloSessione(HttpServletRequest r) {
		// TODO Auto-generated method stub
		HttpSession sess=r.getSession(false);

		if(sess==null)
			return false;
		else
			if(sess.getAttribute("utente")==null)
				return false;
			else{
				//System.out.println(sess.getAttribute("utente"));

				return true;
			}



	}
	public static String checkSession(long secure){
		try{
			queryDB qdb = new queryDB();
			ResultSet res = qdb.query("select nomeutente, count(nomeutente) from utente where codregistrazione='"+secure+"' AND stato like 'registrato'");
			res.next();

			int numcount =res.getInt(2);

			if(numcount==1)

				return res.getString(1);

			else
				return "INVALID";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "INVALID";
	}
}
