import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class studentdata extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection con = null;
        Statement stmt = null;
        String s1 = request.getParameter("Name");
        String s2 = request.getParameter("Regno");
        String s3 = request.getParameter("Dept");
        String s4 = request.getParameter("Year");
        String s5 = request.getParameter("username");
        String s6 = request.getParameter("password");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/user", "root", "gokul");
            stmt = con.createStatement();

            // Check if the registration number already exists
            if (isRegistrationNumberExists(stmt, s2)) {
                // If registration number exists, display an error message
                out.println("<h1 align=center style=color:red;font-weight:bold>Data already exists!</h1>");
            } else {
                // If registration number doesn't exist, insert the data into the database
                stmt.executeUpdate("insert into student values('" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "','" + s5 + "','" + s6 + "')");
                out.println("<br>");
                out.println("<br>");
                out.println("<br>");
                out.println("<br>");
                out.println("<h1 align=center style=color:deepskyblue;font-weight:bold>Registered Successfully</h1>");
            }

        } catch (Exception e) {
            out.println("Sorry!!! Try again later.");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        out.println("<head>");
        out.println("<style> body { background-color: rgb(255, 208, 252); }</style>");

        out.println("</head>");
    }

    private boolean isRegistrationNumberExists(Statement stmt, String regNo) throws Exception {
        String query = "SELECT * FROM student WHERE Reg_No='" + regNo + "'";
        return stmt.executeQuery(query).next();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
