package poliana.data.models.bill;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bills")
public class Bill {

    @Id
    private String billId;

    private BillNode[] yea;
    private BillNode[] nay;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public BillNode[] getYea() {
        return yea;
    }

    public void setYea(BillNode[] yea) {
        this.yea = yea;
    }

    public BillNode[] getNay() {
        return nay;
    }

    public void setNay(BillNode[] nay) {
        this.nay = nay;
    }

    public Bill() {
        super();
    }

    public Bill(String billId) {
        this.billId = billId;
    }

    public Bill(BillNode[] yea, BillNode[] nay, String billId) {
        this.yea = yea;
        this.nay = nay;
        this.billId = billId;
    }

    @Override
    public String toString() {
        return "bill";
    }

}