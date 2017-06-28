package autobike.stanley.idv.android_autobike_v7.tab.rentbike;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Stanley_NB on 2017/6/22.
 */

public class Motor {
    private String motno;
    private String modtype;
    private String plateno;
    private String engno;
    private Timestamp manudate;
    private Integer mile;
    private String locno;
    private String status;
    private String note;

    public Motor() {
    }

    public String getMotno() {
        return motno;
    }

    public void setMotno(String motno) {
        this.motno = motno;
    }

    public String getModtype() {
        return modtype;
    }

    public void setModtype(String modtype) {
        this.modtype = modtype;
    }

    public String getPlateno() {
        return plateno;
    }

    public void setPlateno(String plateno) {
        this.plateno = plateno;
    }

    public String getEngno() {
        return engno;
    }

    public void setEngno(String engno) {
        this.engno = engno;
    }

    public Timestamp getManudate() {
        return manudate;
    }

    public void setManudate(Timestamp manudate) {
        this.manudate = manudate;
    }

    public Integer getMile() {
        return mile;
    }

    public void setMile(Integer mile) {
        this.mile = mile;
    }

    public String getLocno() {
        return locno;
    }

    public void setLocno(String locno) {
        this.locno = locno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFormatedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return dateFormat.format(new Date(manudate.getTime()));
    }
}
