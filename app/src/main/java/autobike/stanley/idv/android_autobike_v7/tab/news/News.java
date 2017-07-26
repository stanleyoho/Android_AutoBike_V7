package autobike.stanley.idv.android_autobike_v7.tab.news;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Stanley_NB on 2017/6/28.
 */

public class News implements Serializable{
    private String newsno;
    private String admno;
    private Timestamp date;
    private String cont;
    private byte[] pic;
    private String title;

    public String getNewsno() {
        return newsno;
    }

    public void setNewsno(String newsno) {
        this.newsno = newsno;
    }

    public String getAdmno() {
        return admno;
    }

    public void setAdmno(String admno) {
        this.admno = admno;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
}
