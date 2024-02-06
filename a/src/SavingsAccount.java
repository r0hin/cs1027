public class SavingsAccount extends BankAccount {
    double interestRate;

    public SavingsAccount(double initialAmount, double rate) {
        super(initialAmount);
        interestRate = rate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void calculateInterest() {
        double interest = getBalance() * interestRate;
        deposit(interest);
    }

    public String toString(){
        return ("SavingsAccount: balance $" + getBalance() + ", interest rate " + interestRate);
    }
}