import java.util.Scanner;

public class BankMachine {

	public static void main(String[] args) {

		BankAccount newAcc;

		Scanner input = new Scanner(System.in);

		System.out.println("Enter your desired account type: 'c' for checking or 's' for savings.");
		String accountType = input.next();

		if (accountType.equals("c")) {
			newAcc = new CheckingAccount(100);
		} else if (accountType.equals("s")) {
			newAcc = new SavingsAccount(100, 0.14);
		} else {
			System.out.println("Invalid account type.");
			return; // If bad input, then end program.
		}

		String accountInfo = newAcc.toString();

		System.out.println("Your new account has been successfully created.");
		System.out.println(accountInfo);


		if (newAcc instanceof CheckingAccount newChecking) {
            System.out.println("Transactions: " + newChecking.getTransactionCount());
		}

		if (newAcc instanceof SavingsAccount newSavings) {
            System.out.println("InterestRate: " + newSavings.getInterestRate());
		}


		
		// Close input stream.
		input.close();
	}
}
