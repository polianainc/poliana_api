package poliana.data.models.bill;

public class Contribution {
    private String billIntroduction;
    private String bioguideId;
    private String recipientExtId;
    private String firstName;
    private String lastName;
    private String industryId;
    private String transactionId;
    private long amount;
    private String dates;

    public String getBillIntroduction() {
        return billIntroduction;
    }

    public void setBillIntroduction(String billIntroduction) {
        this.billIntroduction = billIntroduction;
    }

    public String getBioguideId() {
        return bioguideId;
    }

    public void setBioguideId(String bioguideId) {
        this.bioguideId = bioguideId;
    }

    public String getRecipientExtId() {
        return recipientExtId;
    }

    public void setRecipientExtId(String recipientExtId) {
        this.recipientExtId = recipientExtId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String toString() {
        return "\n" +
            "billIntroduction: " + billIntroduction + "\n" +
            "bioguideId: " + bioguideId + "\n" +
            "recipientExtId: " + recipientExtId + "\n" +
            "firstName: " + firstName + "\n" +
            "lastName: " + lastName + "\n" +
            "industryId: " + industryId + "\n" +
            "transactionId: " + transactionId + "\n" +
            "amount: " + amount + "\n" +
            "dates: " + dates;
    }

}