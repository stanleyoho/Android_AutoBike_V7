package autobike.stanley.idv.android_autobike_v7.navigationlayout;

import java.sql.Timestamp;

/**
 * Created by Stanley_NB on 2017/7/10.
 */

public class SellOrder {

    private String sono;
    private String memno;
    private String motorno;
    private Timestamp buildtime;
    private String status;


    public String getSono() {
        return sono;
    }

    public void setSono(String sono) {
        this.sono = sono;
    }

    public String getMemno() {
        return memno;
    }

    public void setMemno(String memno) {
        this.memno = memno;
    }

    public String getMotorno() {
        return motorno;
    }

    public void setMotorno(String motorno) {
        this.motorno = motorno;
    }

    public Timestamp getBuildtime() {
        return buildtime;
    }

    public void setBuildtime(Timestamp buildtime) {
        this.buildtime = buildtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
