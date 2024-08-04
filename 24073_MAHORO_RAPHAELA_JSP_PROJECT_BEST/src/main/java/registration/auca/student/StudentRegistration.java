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


public class StudentRegistration extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(StudentRegistration.class.getName());

    private static final String URL = "jdbc:postgresql://localhost:5433/testdb";
    private static final String USER = "mahoro";
    private static final String PASSWORD = "Auca@2020";

    public StudentRegistration() {
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


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        logger.info("Received POST request with firstname: " + firstname + ", lastname: " + lastname);

        try (Connection conn = getConnection()) {
            // Insert data into the users table
            String insertSql = "INSERT INTO users (firstname, lastname) VALUES (?, ?)";
            logger.info("Executing SQL: " + insertSql);
            PreparedStatement insertStmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, firstname);
            insertStmt.setString(2, lastname);
            int rowsInserted = insertStmt.executeUpdate();

            if (rowsInserted > 0) {
                // Retrieve generated keys (including the id)
                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    logger.info("Data inserted successfully. Redirecting to UserServlets with ID: " + id);
                    response.sendRedirect("UserServlets?id=" + id);
                } else {
                	 logger.warning("Failed to retrieve generated keys.");
                    response.getWriter().println("<h1>Failed to retrieve from database");
                }
            } else {
            	logger.warning("No rows inserted.");
                response.getWriter().println("<h1>Opps insert data failed</h1>");
            }
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
            throw new SQLException("PostgreSQL JDBC driver not found", e);
        }
    }
}