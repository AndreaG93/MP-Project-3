package adapter;

import android.graphics.drawable.Drawable;


public class StatisticsListViewResultAdapterObject {

    private Drawable icon;
    private String name;
    private String quantity;
    private String points;

    public StatisticsListViewResultAdapterObject(){
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }


}
