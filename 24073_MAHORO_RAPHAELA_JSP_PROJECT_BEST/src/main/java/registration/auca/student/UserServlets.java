package registration.auca.student;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.ConsoleHandler;

public class UserServlets extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(StudentRegistration.class.getName());
    
    private static final String URL = "jdbc:postgresql://localhost:5432/testdb";
    private static final String USER = "mahoro";
    private static final String PASSWORD = "Auca@2020";

    public UserServlets() {
        super();
        initializeLogger();
    }

    private void initializeLogger() {
        try {
            // Set up console handler
            ConsoleHandler consoleLogHandler = new ConsoleHandler();
            consoleLogHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(consoleLogHandler);

            // Set up file handler
            FileHandler fileLogHandler = new FileHandler("application.log", true);
            fileLogHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileLogHandler);
        } catch (IOException logSetupException) {
            logger.severe("Failed to set up file handler: " + logSetupException.getMessage());
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");

        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO students (firstname, lastname) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }

        doGet(request, response); // Call doGet to display the table after inserting
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (Connection conn = getConnection(); PrintWriter out = response.getWriter()) {
            String sql = "SELECT * FROM users ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            out.println("<html><body>");
            out.println("<h2>User Details</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>First Name</th><th>Last Name</th></tr>");

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                out.println("<tr><td>" + id + "</td><td>" + firstname + "</td><td>" + lastname + "</td></tr>");
            }

            out.println("</table>");
            out.println("</body></html>");

        } catch (SQLException e) {
        	logger.severe("Database access error " + e.getMessage());
            throw new ServletException("Database access error", e);
        }
    }

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
        	logger.severe("driver not found " + e.getMessage());
            throw new SQLException(" driver not found", e);
        }
    }
}