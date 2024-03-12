import java.util.Iterator;
import java.util.Scanner;

public class ContactList {
	
	public static char sortBy;

	public ContactList (Person[] contacts, char sortBy) {
		ArrayOrderedList<Person> sortedContacts = new ArrayOrderedList<Person>();
		
		ContactList.sortBy = sortBy; // Possible values are 'n' or 'e' or 'c'
		
		for (int i = 0; i < contacts.length; i++) {
			sortedContacts.add(contacts[i]);
		}
		
		printContacts(sortedContacts);	
	}
	
	public static void printContacts (ArrayOrderedList<Person> list) {
Iterator<Person> iter = list.iterator();
		System.out.println("Name\t\t\t\t\t\tEmail\t\t\t\t\t\tCity");
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
	
		Person[] contacts = new Person[] {
			new Person("Artem Kyrzikov", "the.art.guy@gmail.com", "Toronto"),
			new Person("Tina Lawson", "tlawson@uwo.ca", "London"),
			new Person("Clara Kinkaid", "kinkaid_1997@msn.com", "Vancouver"),
			new Person("Johnny Smith", "jsmith57@uwo.ca", "London"),
			new Person("Rebecca Cortez", "beckybrowneyes@gmail.com", "London"),
			new Person("Ahmed Morrah", "king_ahmed_83@gmail.com", "Toronto"),
			new Person("Vanessa Hedberg", "vlhedberg14@hotmail.com", "Vancouver"),
			new Person("Megan McIsaac", "mandm_madness@gmail.com", "Toronto"),
			new Person("Ann Hoffer", "ahoffe61@uwo.ca", "London"),
			new Person("Robert Kane", "rkane79@uwo.ca", "London")
		};
		
		String msg = "Type 'n' to sort by name, 'e' to sort by email, or 'c' to sort by city. Type any other letter to quit.";
		
		
		Scanner input = new Scanner(System.in);
		System.out.println(msg);
		char sortBy = input.next().charAt(0);

		while (sortBy == 'n' || sortBy == 'e' || sortBy == 'c') {
			ContactList cl = new ContactList(contacts, sortBy);;
			System.out.println(msg);
			sortBy = input.next().charAt(0);
		}	

		input.close();
	}

}
