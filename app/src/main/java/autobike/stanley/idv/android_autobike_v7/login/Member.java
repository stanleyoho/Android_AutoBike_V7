package autobike.stanley.idv.android_autobike_v7.login;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Stanley_NB on 2017/7/6.
 */

public class Member implements Serializable{

    private String memno;
    private String memname;
    private String sex;
    private Timestamp birth;
    private String mail;
    private String phone;
    private String addr;
    private String acc;
    private String pwd;
    private Timestamp credate;
    private String status;

    public String getMemno() {
        return memno;
    }

    public void setMemno(String memno) {
        this.memno = memno;
    }

    public String getMemname() {
        return memname;
    }

    public void setMemname(String memname) {
        this.memname = memname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Timestamp getBirth() {
        return birth;
    }

    public void setBirth(Timestamp birth) {
        this.birth = birth;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Timestamp getCredate() {
        return credate;
    }

    public void setCredate(Timestamp credate) {
        this.credate = credate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
