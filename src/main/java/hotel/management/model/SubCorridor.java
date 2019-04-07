package hotel.management.model;

public class SubCorridor {
    public SubCorridor(boolean light, boolean ac) {
        this.light=light;
        this.ac=ac;
    }

    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
    }

    public boolean isAc() {
        return ac;
    }

    public void setAc(boolean ac) {
        this.ac = ac;
    }

    private boolean light;
    private boolean ac;
}
