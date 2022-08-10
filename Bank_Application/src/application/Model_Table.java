package application;
import application.Transaction_History_Table_Controller;
import javafx.fxml.FXML;

import javax.swing.text.TableView;
import java.sql.Timestamp;

public class Model_Table
{
    int ID,Trans_ID_From,Trans_ID_To;
    long Prev, With,Depo,Current,Trans_With,Trans_Depo;
    java.sql.Timestamp Date_Time;

    public Model_Table(int ID,long Prev, long With, long Depo,long Trans_With,long Trans_Depo, long Current,int Trans_ID_From,int Trans_ID_To,Timestamp Date_Time)
    {
        this.ID=ID;
        this.Prev=Prev;
        this.With=With;
        this.Depo=Depo;
        this.Trans_With=Trans_With;
        this.Trans_Depo=Trans_Depo;
        this.Trans_ID_From=Trans_ID_From;
        this.Trans_ID_To=Trans_ID_To;
        this.Current=Current;
        this.Date_Time=Date_Time;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getTrans_ID_From() {
        return Trans_ID_From;
    }

    public void setTrans_ID_From(int trans_ID_From) {
        Trans_ID_From = trans_ID_From;
    }

    public int getTrans_ID_To() {
        return Trans_ID_To;
    }

    public void setTrans_ID_To(int trans_ID_To) {
        Trans_ID_To = trans_ID_To;
    }

    public long getPrev() {
        return Prev;
    }

    public void setPrev(long prev) {
        Prev = prev;
    }

    public long getWith() {
        return With;
    }

    public void setWith(long with) {
        With = with;
    }

    public long getDepo() {
        return Depo;
    }

    public void setDepo(long depo) {
        Depo = depo;
    }

    public long getCurrent() {
        return Current;
    }

    public void setCurrent(long current) {
        Current = current;
    }

    public long getTrans_With() {
        return Trans_With;
    }

    public void setTrans_With(long trans_With) {
        Trans_With = trans_With;
    }

    public long getTrans_Depo() {
        return Trans_Depo;
    }

    public void setTrans_Depo(long trans_Depo) {
        Trans_Depo = trans_Depo;
    }

    public Timestamp getDate_Time() {
        return Date_Time;
    }

    public void setDate_Time(Timestamp date_Time) {
        Date_Time = date_Time;
    }
}
