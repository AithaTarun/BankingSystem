package application;

import javafx.scene.layout.HBox;

public class Model_Table_Login
{
    int ID;
    String Fname,Lname,Mail,DOB,Address;
    Long Phone;
    HBox hb;
    HBox hb_txt;

    public Model_Table_Login(int ID,String Fname,String Lname,Long Phone,String DOB,String Mail,String Address,HBox hb,HBox hb_txt)
    {
        this.ID=ID;
        this.Fname=Fname;
        this.Lname=Lname;
        this.Phone=Phone;
        this.DOB=DOB;
        this.Mail=Mail;
        this.Address= Address;
        this.hb=hb;
        this.hb_txt=hb_txt;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Long getPhone() {
        return Phone;
    }

    public void setPhone(Long phone) {
        Phone = phone;
    }

    public HBox getHb() {
        return hb;
    }

    public void setHb(HBox hb) {
        this.hb = hb;
    }

    public HBox getHb_txt() {
        return hb_txt;
    }

    public void setHb_txt(HBox hb_txt) {
        this.hb_txt = hb_txt;
    }
}
