package poliana.data.models;

import java.util.List;
import java.util.Map;

public abstract class AbstractThriftTable {

    private String[][] table;

    public AbstractThriftTable() {}

    public AbstractThriftTable(List<String> table) {
        //Get the height of the table
        int height = table.size();
        //Split a record of the tables to get the width
        int width = split(table.get(0)).length;

        this.table = new String[height][width];

        int i = 0; //initialize index
        for(String record : table) {
            this.table[i] = split(record);
        }
    }

    public AbstractThriftTable(List<String> table, Map columns) {}

    private String[] split(String record) {
        return record.split("\\t");
    }
    private String[] split(String record, String delim) {
        return record.split(delim);
    }

    public String[][] getTable() {
        return table;
    }

    public void setTable(String[][] table) {
        this.table = table;
    }
}
