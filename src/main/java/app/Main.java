package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        ConsolePrinter consolePrinter = new ConsolePrinter();
        InputCollector inputCollector = new InputCollector();
        RepositoryService repositoryService = new RepositoryService();
        Repository repository = new Repository(connection, repositoryService);
        Service service = new Service(repository);
        MainOptionsMenu mainOptions = new MainOptionsMenu(consolePrinter, inputCollector, service);

        mainOptions.mainLoop();

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
