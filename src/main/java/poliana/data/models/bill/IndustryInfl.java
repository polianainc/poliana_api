package poliana.data.models.bill;

import java.util.List;

public class IndustryInfl {
    private int mainTotal;
    private int mainTouched;
    private List<Industry> mainChildren;
    private double offTotal;
    private int offTouched;
    private List<Industry> offChildren;

    public int getMainTotal() {
        return mainTotal;
    }

    public void setMainTotal(int mainTotal) {
        this.mainTotal = mainTotal;
    }

    public int getMainTouched() {
        return mainTouched;
    }

    public void setMainTouched(int mainTouched) {
        this.mainTouched = mainTouched;
    }

    public List<Industry> getMainChildren() {
        return mainChildren;
    }

    public void setMainChildren(List<Industry> mainChildren) {
        this.mainChildren = mainChildren;
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

    public List<Industry> getOffChildren() {
        return offChildren;
    }

    public void setOffChildren(List<Industry> offChildren) {
        this.offChildren = offChildren;
    }
}
