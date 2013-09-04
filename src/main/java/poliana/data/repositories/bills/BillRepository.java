package poliana.data.repositories.bills;


import java.util.List;

public interface BillRepository {
    List<Object> allContributions(String billId);
    List<Object> allContributions(String billId, Integer limit);
    List<Object> selectColumn(String billId, String column);
    List<Object> selectColumn(String billId, String column, Integer limit);
    List<Object> selectColumns(String billId, List<String> columns);
    List<Object> selectColumns(String billId, List<String> columns, Integer limit);
}
