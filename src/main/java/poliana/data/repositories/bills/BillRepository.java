package poliana.data.repositories.bills;


import java.util.List;

public interface BillRepository {
    List<String> allContributions(String billId);
    List<String> allContributions(String billId, Integer limit);
    List<String> selectColumn(String billId, String column);
    List<String> selectColumn(String billId, String column, Integer limit);
    List<String> selectColumns(String billId, List<String> columns);
    List<String> selectColumns(String billId, List<String> columns, Integer limit);
}
