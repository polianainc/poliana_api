package poliana.data.models.bill;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "billnodes")
public class BillNode {

    @Id
    private String id;

    private String billId;
    private String name;
    private Integer amountContributed;
    private Integer numPolsTouched;
    private BillNode[] children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmountContributed() {
        return amountContributed;
    }

    public void setAmountContributed(Integer amountContributed) {
        this.amountContributed = amountContributed;
    }

    public Integer getNumPolsTouched() {
        return numPolsTouched;
    }

    public void setNumPolsTouched(Integer numPolsTouched) {
        this.numPolsTouched = numPolsTouched;
    }

    public BillNode[] getChildren() {
        return children;
    }

    public void setChildren(BillNode[] children) {
        this.children = children;
    }

}