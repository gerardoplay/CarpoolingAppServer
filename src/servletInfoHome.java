import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class servletInfoHome
 */
@WebServlet("/servletInfoHome")
public class servletInfoHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletInfoHome() {
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
	queryDB qdb = new queryDB();
	try {
		ResultSet rs = qdb.query("select sum(poolingani), sum(poolingcos),sum(unpoolingani),sum(unpoolingcos) from sistema");
		JSONObject js = new JSONObject();
		rs.next();
		DecimalFormat df = new DecimalFormat("00");
		js.put("anidride",df.format(rs.getDouble("sum(unpoolingani)")- rs.getDouble("sum(poolingani)")));
		js.put("costo", df.format(rs.getDouble("sum(unpoolingcos)")-rs.getDouble("sum(poolingcos)")));
		response.getWriter().write(js.toString());
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
