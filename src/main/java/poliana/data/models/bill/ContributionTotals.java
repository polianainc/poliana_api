package poliana.data.models.bill;

public class ContributionTotals {

    private String industryId;
    private int totalYea;
    private int totalNay;
    private int distinctYea;
    private int distinctNay;
    private int sumYea;
    private int sumNay;

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public int getTotalYea() {
        return totalYea;
    }

    public void setTotalYea(int totalYea) {
        this.totalYea = totalYea;
    }

    public int getTotalNay() {
        return totalNay;
    }

    public void setTotalNay(int totalNay) {
        this.totalNay = totalNay;
    }

    public int getDistinctYea() {
        return distinctYea;
    }

    public void setDistinctYea(int distinctYea) {
        this.distinctYea = distinctYea;
    }

    public int getDistinctNay() {
        return distinctNay;
    }

    public void setDistinctNay(int distinctNay) {
        this.distinctNay = distinctNay;
    }

    public int getSumYea() {
        return sumYea;
    }

    public void setSumYea(int sumYea) {
        this.sumYea = sumYea;
    }

    public int getSumNay() {
        return sumNay;
    }

    public void setSumNay(int sumNay) {
        this.sumNay = sumNay;
    }

    public String toString() {
        return "\n" +
        "industryId: " + industryId + "\n" +
        "totalYea: " + totalYea + "\n" +
        "distinctYea: " + distinctYea + "\n" +
        "distinctNay: " + distinctNay + "\n" +
        "sumYea: " + sumYea + "\n" +
        "sumNay: " + industryId;
    }

}
