package hql.UDFs;

import org.apache.hadoop.hive.ql.udf.UDFUnixTimeStamp;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

@Description(
        name = "months_previous_timestamp",
        value = "_FUNC_(str) - gets unix timestamp in either yyyy-MM-dd HH:mm:ss or yyyy-MM-dd format - returns null if input is null",
        extended = "Example:\n" +
                "  > SELECT a.* FROM srcpart a WHERE _FUNC_ (a.hr) < unix_timestamp() LIMIT 1;\n"
)
public class MonthsPreviousTimestamp extends UDFUnixTimeStamp {

    public LongWritable evaluate(Text datestring, Integer months) {
        if(datestring != null) {
            if(datestring != null && datestring.find(" ") == -1)
                datestring = new Text(datestring.toString() + " 00:00:00");
        }
        long date = super.evaluate(datestring).get();
        return new LongWritable(date - (months * 2629743));
    }
    public LongWritable evaluate(Text datestring) {
        if(datestring != null && datestring.find(" ") == -1)
            datestring = new Text(datestring.toString() + " 00:00:00");
        long date = super.evaluate(datestring).get();
        return new LongWritable(date - 2629743);
    }
}
