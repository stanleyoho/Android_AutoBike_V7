package autobike.stanley.idv.android_autobike_v7.tab.rentbike;

import java.io.Serializable;

/**
 * Created by Stanley_NB on 2017/7/18.
 */

public class MotorModel implements Serializable{

    private String modtype;
    private String brand;
    private Integer displacement;
    private String name;
    private Integer renprice;
    private Integer saleprice;

    public String getModtype() {
        return modtype;
    }

    public void setModtype(String modtype) {
        this.modtype = modtype;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getDisplacement() {
        return displacement;
    }

    public void setDisplacement(Integer displacement) {
        this.displacement = displacement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRenprice() {
        return renprice;
    }

    public void setRenprice(Integer renprice) {
        this.renprice = renprice;
    }

    public Integer getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(Integer saleprice) {
        this.saleprice = saleprice;
    }
}
