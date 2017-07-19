package autobike.stanley.idv.android_autobike_v7.tab.rentbike;

import java.io.Serializable;

/**
 * Created by Stanley_NB on 2017/7/18.
 */

public class EmtCate implements Serializable {
    private String ecno;
    private String type;
    private Integer price;

    public String getEcno() {
        return ecno;
    }

    public void setEcno(String ecno) {
        this.ecno = ecno;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
