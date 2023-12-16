import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/user", "root", "gokul");

            // Corrected SQL query to match the actual table name
            stmt = con.prepareStatement("SELECT * FROM student WHERE username=? AND password=?");

            // Assuming these parameters are obtained from a form submission
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            stmt.setString(1, username);
            stmt.setString(2, password);

            rs = stmt.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("<style>");
            out.println("table { width: 50%; margin: 20px auto; border-collapse: collapse; background-color: rgb(109, 206, 150); }");
            out.println("th, td { padding: 10px; text-align: left; }");
            out.println("th { background-color: #db9fe0; color: white; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body bgcolor=' #ffff66'>");

            out.println("<h2 align='center' style='color: blue;'>Student Data</h2>");

            // Display data in tabular form
            out.println("<table border='1'>");

            while (rs.next()) {
                String name = rs.getString("Name");
                String regno = rs.getString("Reg_No");
                String dept = rs.getString("Dept");
                String year = rs.getString("Year");

                // Display data in the specified format
                out.println("<tr>");
                out.println("<th>Name</th>");
                out.println("<td>" + name + "</td>");
                out.println("</tr>");

                out.println("<tr>");
                out.println("<th>Registration Number</th>");
                out.println("<td>" + regno + "</td>");
                out.println("</tr>");

                out.println("<tr>");
                out.println("<th>Department</th>");
                out.println("<td>" + dept + "</td>");
                out.println("</tr>");

                out.println("<tr>");
                out.println("<th>Year</th>");
                out.println("<td>" + year + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            out.println("</body>");
            out.println("</html>");

        } catch (ClassNotFoundException | SQLException e) {
            out.println("<h1 align='center' style='color:red;'>Database Connection Error</h1>");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
