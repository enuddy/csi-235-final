/**
 * Created by Nick on 3/31/2016.
 */
public class Card {
    private String cName, cColor, cCost;
    private int cCMC;

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcColor() {
        return cColor;
    }

    public void setcColor(String cColor) {
        this.cColor = cColor;
    }

    public int getcCMC() {
        return cCMC;
    }

    public void setcCMC(int cCMC) {
        this.cCMC = cCMC;
    }

    public Card(String cName, String color, int cmc, String cost) {
        this.cName = cName;
        this.cColor = color;
        this.cCMC = cmc;
        this.cCost = cost;
    }

    public String toString()
    {
        return this.cName + "~" + "Converted Mana Cost: " +this.cCMC + "~" + "Cost: " + this.cCost + "~" + this.cColor;
    }


}
