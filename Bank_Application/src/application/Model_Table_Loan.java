package application;

import javafx.scene.layout.HBox;

public class Model_Table_Loan
{
    int Customer_ID;
    HBox hb_Loan_ID;
    long Loan_Amount;
    HBox hb_Interest;
    int Loan_Tenture;
    HBox hb;
    HBox hb_txt;
    public Model_Table_Loan(int Customer_ID,HBox hb_Loan_ID,long Loan_Amount,HBox hb_Interest,int Loan_Tenture,HBox hb,HBox hb_txt)
    {
        this.Customer_ID=Customer_ID;
        this.hb_Loan_ID=hb_Loan_ID;
        this.Loan_Amount=Loan_Amount;
        this.hb_Interest=hb_Interest;
        this.Loan_Tenture=Loan_Tenture;
        this.hb=hb;
        this.hb_txt=hb_txt;
    }

    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    public HBox getHb_Loan_ID() {
        return hb_Loan_ID;
    }

    public void setHb_Loan_ID(HBox hb_Loan_ID) {
        this.hb_Loan_ID = hb_Loan_ID;
    }

    public int getLoan_Tenture() {
        return Loan_Tenture;
    }

    public void setLoan_Tenture(int loan_Tenture) {
        Loan_Tenture = loan_Tenture;
    }

    public long getLoan_Amount() {
        return Loan_Amount;
    }

    public void setLoan_Amount(long loan_Amount) {
        Loan_Amount = loan_Amount;
    }

    public HBox getHb_Interest() {
        return hb_Interest;
    }

    public void setHb_Interest(HBox hb_Interest) {
        this.hb_Interest = hb_Interest;
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
