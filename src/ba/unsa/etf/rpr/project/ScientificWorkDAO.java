package ba.unsa.etf.rpr.project;

public class ScientificWorkDAO {
    private static ScientificWorkDAO instance;

    public static ScientificWorkDAO getInstance() {
        if (instance == null) instance = new ScientificWorkDAO();
        return instance;
    }
    private ScientificWorkDAO() {}

}
