import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        ConsolePrinter consolePrinter = new ConsolePrinter();
        InputCollector inputCollector = new InputCollector();
        Repository repository = new Repository(connection);
        Service service = new Service(repository);
        Options options = new Options(consolePrinter, inputCollector, service);

        options.mainLoop();

        assert connection != null;
        connection.close();
    }

    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sql_store",
                    "root",
                    "admin");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
