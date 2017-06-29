package autobike.stanley.idv.android_autobike_v7.tab.boardmessage;

import java.sql.Timestamp;

/**
 * Created by Stanley_NB on 2017/6/29.
 */

public class BoardMessage {
    private String mesno;
    private String memno;
    private Timestamp date;
    private String cont;
    private String status;

    public BoardMessage() {
        super();
    }

    public BoardMessage(String memno , Timestamp date , String cont){
        this.memno = memno;
        this.date = date;
        this.cont = cont;
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
