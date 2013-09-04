package poliana.data.models.bill;

import java.util.List;

public class Industry implements Contributor{
    private String name;
    private double mainTotal;
    private int mainTouched;
    private boolean winner;
    private String industryId;
    private double offTotal;
    private int offTouched;
    private List<Recipient> mainChildren;
    private List<Recipient> offChildren;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMainTotal() {
        return mainTotal;
    }

    public void setMainTotal(double mainTotal) {
        this.mainTotal = mainTotal;
    }

    public int getMainTouched() {
        return mainTouched;
    }

    public void setMainTouched(int mainTouched) {
        this.mainTouched = mainTouched;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public double getOffTotal() {
        return offTotal;
    }

    public void setOffTotal(double offTotal) {
        this.offTotal = offTotal;
    }

    public int getOffTouched() {
        return offTouched;
    }

    public void setOffTouched(int offTouched) {
        this.offTouched = offTouched;
    }

    public List<Recipient> getMainChildren() {
        return mainChildren;
    }

    public void setMainChildren(List<Recipient> mainChildren) {
        this.mainChildren = mainChildren;
    }

    public List<Recipient> getOffChildren() {
        return offChildren;
    }

    public void setOffChildren(List<Recipient> offChildren) {
        this.offChildren = offChildren;
    }
}
