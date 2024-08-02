package registration.auca.student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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
import org.apache.log4j.Logger;

//@WebServlet("/DatabaseUtil")
public class DatabaseUtil extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String URL = "jdbc:postgresql://localhost:5433/testdb";
    private static final String USER = "mahoro";
    private static final String PASSWORD = "Auca@2020";

    public DatabaseUtil() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");

        try (Connection conn = getConnection()) {
            // Insert data into the users table
            String insertSql = "INSERT INTO users (firstname, lastname) VALUES (?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, firstname);
            insertStmt.setString(2, lastname);
            int rowsInserted = insertStmt.executeUpdate();

            if (rowsInserted > 0) {
                // Retrieve generated keys (including the id)
                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    // Redirect to UserServlet to display the inserted user
                    response.sendRedirect("UserServlets?id=" + id);
                } else {
                    response.getWriter().println("<h1>Failed to retrieve from database");
                }
            } else {
                response.getWriter().println("<h1>Opps insert data</h1>");
            }
        } catch (SQLException e) {
        	logger.error("Database access error");
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