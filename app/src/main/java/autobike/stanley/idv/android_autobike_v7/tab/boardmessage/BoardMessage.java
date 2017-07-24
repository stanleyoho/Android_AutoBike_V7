package autobike.stanley.idv.android_autobike_v7.tab.boardmessage;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Stanley_NB on 2017/6/29.
 */

public class BoardMessage implements Serializable{
    private String mesno;
    private String memno;
    private Timestamp date;
    private String cont;
    private String status;
    private byte[] pic;
    public byte[] getPic() {
        return pic;
    }
    public void setPic(byte[] pic) {
        this.pic = pic;
    }
    public BoardMessage() {
        super();
    }
    public BoardMessage(String mesno,String memno,Timestamp dadte ,
                      String cont,String status){
        this.mesno = mesno;
        this.memno = memno;
        this.date = date;
        this.cont = cont;
        this.status = status;
    }
    public String getMesno() {
        return mesno;
    }
    public void setMesno(String mesno) {
        this.mesno = mesno;
    }
    public String getMemno() {
        return memno;
    }
    public void setMemno(String memno) {
        this.memno = memno;
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
