package application;

public class Model_Table_Loan_Details
{
    int Year;
    Double Principle;
    Double Interest;
    Double Total_Payment;
    Double Balance;
    public  Model_Table_Loan_Details(int Year,Double Principle,Double Interest, Double Total_Payment, Double Balance)
    {
        this.Year=Year;
        this.Principle=Principle;
        this.Interest=Interest;
        this.Total_Payment=Total_Payment;
        this.Balance=Balance;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public Double getPrinciple() {
        return Principle;
    }

    public void setPrinciple(Double principle) {
        Principle = principle;
    }

    public Double getInterest() {
        return Interest;
    }

    public void setInterest(Double interest) {
        Interest = interest;
    }

    public Double getTotal_Payment() {
        return Total_Payment;
    }

    public void setTotal_Payment(Double total_Payment) {
        Total_Payment = total_Payment;
    }

    public Double getBalance() {
        return Balance;
    }

    public void setBalance(Double balance) {
        Balance = balance;
    }
}
