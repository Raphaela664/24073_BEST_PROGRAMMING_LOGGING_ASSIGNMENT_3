//package registration.auca.student;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.ConnectException;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//public class FirstClass extends HttpServlet {
//	 private static final long serialVersionUID = 1L;
//	    
//	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	        // Handle GET requests (display the form)
//	        response.setContentType("text/html");
//	        PrintWriter out = response.getWriter();
//	        
//	        out.println("<!DOCTYPE html>");
//	        out.println("<html lang=\"en\">");
//	        out.println("<head>");
//	        out.println("    <meta charset=\"UTF-8\">");
//	        out.println("    <title>Student Information</title>");
//	        out.println("</head>");
//	        out.println("<body>");
//	        out.println("    <h2>Enter Student Information</h2>");
//	        out.println("    <form action=\"StudentInfoServlet\" method=\"post\">");
//	        out.println("        <label for=\"student_id\">Student ID:</label><br>");
//	        out.println("        <input type=\"text\" id=\"student_id\" name=\"student_id\" required><br><br>");
//	        out.println("        <label for=\"first_name\">First Name:</label><br>");
//	        out.println("        <input type=\"text\" id=\"first_name\" name=\"first_name\" required><br><br>");
//	        out.println("        <label for=\"last_name\">Last Name:</label><br>");
//	        out.println("        <input type=\"text\" id=\"last_name\" name=\"last_name\" required><br><br>");
//	        out.println("        <input type=\"submit\" value=\"Submit\">");
//	        out.println("    </form>");
//	        out.println("</body>");
//	        out.println("</html>");
//	    }
//	    
//	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	        // Handle POST requests (process form submission)
//	        response.setContentType("text/html");
//	        PrintWriter out = response.getWriter();
//	        
//	        String studentId = request.getParameter("student_id");
//	        String firstName = request.getParameter("first_name");
//	        String lastName = request.getParameter("last_name");
//	        
//	        out.println("<!DOCTYPE html>");
//	        out.println("<html lang=\"en\">");
//	        out.println("<head>");
//	        out.println("    <meta charset=\"UTF-8\">");
//	        out.println("    <title>Student Information</title>");
//	        out.println("</head>");
//	        out.println("<body>");
//	        out.println("    <h2>Student Information</h2>");
//	        out.println("    <p><strong>Student ID:</strong> " + studentId + "</p>");
//	        out.println("    <p><strong>Name:</strong> " + firstName + " " + lastName + "</p>");
//	        out.println("</body>");
//	        out.println("</html>");
//	    }
	    
//	    private static final String JDBC_URL = "jdbc:postgresql://localhost:5433/yourdatabase";
//	    private static final String JDBC_USER = "postgres";
//	    private static final String JDBC_PASSWORD = "Auca@2020";
//
//	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	        response.setContentType("text/html");
//	        PrintWriter out = response.getWriter();
//	        
//	        String studentId = request.getParameter("student_id");
//	        String firstName = request.getParameter("first_name");
//	        String lastName = request.getParameter("last_name");
//	        
//	        ConnectException conn = null;
//	        PreparedStatement stmt = null;
//	        
//	        try {
//	            // Register PostgreSQL JDBC driver
//	            Class.forName("org.postgresql.Driver");
//	            
//	            // Open a connection
//	            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
//	            
//	            // Execute SQL insert statement
//	            String sql = "INSERT INTO students (student_id, first_name, last_name) VALUES (?, ?, ?)";
//	            stmt = conn.prepareStatement(sql);
//	            stmt.setString(1, studentId);
//	            stmt.setString(2, firstName);
//	            stmt.setString(3, lastName);
//	            
//	            int rowsAffected = stmt.executeUpdate();
//	            
//	            // Display success message
//	            out.println("<!DOCTYPE html>");
//	            out.println("<html lang=\"en\">");
//	            out.println("<head>");
//	            out.println("    <meta charset=\"UTF-8\">");
//	            out.println("    <title>Student Information Saved</title>");
//	            out.println("</head>");
//	            out.println("<body>");
//	            out.println("    <h2>Student Information Saved</h2>");
//	            out.println("    <p><strong>Student ID:</strong> " + studentId + "</p>");
//	            out.println("    <p><strong>Name:</strong> " + firstName + " " + lastName + "</p>");
//	            out.println("    <p>Rows affected in database: " + rowsAffected + "</p>");
//	            out.println("</body>");
//	            out.println("</html>");
//	            
//	        } catch (ClassNotFoundException | SQLException e) {
//	            e.printStackTrace();
//	            out.println("Error: " + e.getMessage());
//	        } finally {
//	            // Close resources
//	            try {
//	                if (stmt != null) {
//	                    stmt.close();
//	                }
//	                if (conn != null) {
//	                    conn.close();
//	                }
//	            } catch (SQLException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//}
