import java.util.Scanner;

public class Postfix {

	private ArrayStack<String> expStack;
	private PostfixEvaluator evaluator;
	
	public Postfix () {
		expStack = new ArrayStack<String>();
		evaluator = new PostfixEvaluator();
	}

	public void run () {
		String expression, action = "e";
		int result;

		try {
			Scanner in = new Scanner(System.in);
      
			do {
				if (action.equals("e")) {
					System.out.print("Enter a valid postfix expression:  ");
					expression = in.nextLine();
	
					result = evaluator.evaluate(expression.trim());
					System.out.println("The result is " + result);
	
					expStack.push(expression);
				} else if (action.equals("r")) {
					if (expStack.size() > 2) {
						showRecent(3);
					}
					else {
						showRecent(expStack.size());
					}
				}

				System.out.println("\nWhat do you want to do?");
				System.out.println("\tType 'e' if you want to evaluate another postfix expression.");
				System.out.println("\tType 'r' if you want to see the recent expressions.");
				System.out.println("\tType any other key to quit.");
				action = in.nextLine();
				System.out.println();
			} while (action.equalsIgnoreCase("e") || action.equalsIgnoreCase("r"));
    	} catch (Exception IOException) {
    		System.out.println("Input exception reported");
    	}

	}
	
	private void showRecent (int numToShow) {
		ArrayStack<String> tmp = new ArrayStack<String>();

		System.out.println("Recent Expressions:");

		// Copy the top numToShow elements to a temporary stack
		for (int i = 0; i < numToShow; i++) {
			if (expStack.isEmpty()) {
				break;
			}
			tmp.push(expStack.pop());
		}

		// Display the top numToShow elements
		for (int i = 0; i < numToShow; i++) {
			if (tmp.isEmpty()) {
				break;
			}
			System.out.println(tmp.peek());
			expStack.push(tmp.pop());
		}
	}
	
	
	public static void main (String[] args) {
		Postfix pf = new Postfix();
		pf.run();
	}
}
