import java.util.ArrayList;
import java.util.LinkedList;

public class Service {

    private final Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    ArrayList<String> getAllTableNames() {
        LinkedList<String> allTableNames = repository.getAllTableNames();
        ArrayList<String> tables = new ArrayList<>();
        for (int i = 0; i < allTableNames.size(); i++) {
            tables.add((i +1) + " " +  allTableNames.get(i));
        }
        tables.add("");
        tables.add(0 + " Powrót");
        return tables;
    }
}
