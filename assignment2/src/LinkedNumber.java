// Defines a class to represent numbers using a doubly linked list, allowing numbers in any base
public class LinkedNumber {
  // Stores the base of the number system (e.g., 10 for decimal, 2 for binary)
  private int base;
  // Pointers to the first and last nodes in the doubly linked list
  private DLNode<Digit> front, rear;

  // Constructor to initialize the number from a string representation and its base
  public LinkedNumber (String num, int baseNum) {
    base = baseNum; // Sets the base of the number system
    if (num.length() == 0) { // Checks for an empty string input
      throw new LinkedNumberException("no digits given");
    }

    // Loops through each character in the string, converting it to a Digit and adding it to the list
    for (int i = 0; i < num.length(); i++) {
      char c = num.charAt(i);

      // Creates a new node for each digit
      DLNode<Digit> newNode = new DLNode<Digit>(new Digit(c));
      if (front == null) { // If this is the first node, initializes both front and rear
        front = newNode;
        rear = newNode;
      } else { // Adds new node to the end of the list
        rear.setNext(newNode);
        newNode.setPrev(rear);
        rear = newNode;
      }
    }
  }

  // Overloaded constructor to initialize the number directly from an integer (base 10)
  public LinkedNumber (int num) {
    base = 10; // Default to base 10
    String numStr = Integer.toString(num); // Convert the integer to a string
    // Similar process as the first constructor, for each character in the string
    for (int i = 0; i < numStr.length(); i++) {
      char c = numStr.charAt(i);

      DLNode<Digit> newNode = new DLNode<Digit>(new Digit(c));
      if (front == null) {
        front = newNode;
        rear = newNode;
      } else {
        rear.setNext(newNode);
        newNode.setPrev(rear);
        rear = newNode;
      }
    }
  }

  // Checks if the current number is valid within its base (e.g., no digits >= base)
  public boolean isValidNumber() {
    Boolean valid = true;
    DLNode<Digit> current = front;
    while (current != null) {
      if (current.getElement().getValue() >= base || current.getElement().getValue() < 0 ){
        valid = false;
        break;
      }
      current = current.getNext();
    }
    return valid;
  }

  // Accessor methods for base, front, and rear
  public int getBase() { return base; }
  public DLNode<Digit> getFront() { return front; }
  public DLNode<Digit> getRear() { return rear; }

  // Calculates the total number of digits in the number
  public int getNumDigits() {
    int count = 0;
    DLNode<Digit> current = front;
    while (current != null) {
      count++;
      current = current.getNext();
    }
    return count;
  }

  // Returns a string representation of the number
  public String toString() {
    String str = "";
    DLNode<Digit> current = front;
    while (current != null) {
      str += current.getElement().toString();
      current = current.getNext();
    }
    return str;
  }

  // Compares this LinkedNumber with another for equality, based on base and each digit
  public boolean equals (LinkedNumber other) {
    if (base != other.base) {
      return false;
    }
    if (getNumDigits() != other.getNumDigits()) {
      return false;
    }

    DLNode<Digit> current = front;
    DLNode<Digit> otherCurrent = other.front;
    while (current != null) {
      if (!current.getElement().equals(otherCurrent.getElement())) {
        return false;
      }
      current = current.getNext();
      otherCurrent = otherCurrent.getNext();
    }
    return true;
  }

  // Converts the current number to a new base and returns the result as a new LinkedNumber
  public LinkedNumber convert(int newBase) {
    if (!isValidNumber()) {
      throw new LinkedNumberException("cannot convert invalid number");
    }

    // Converts the current number to an integer (base 10)
    int newNum = 0;
    int power = getNumDigits() - 1;
    DLNode<Digit> current = front;
    while (current != null) {
      newNum += current.getElement().getValue() * (int) Math.pow(base, power);
      power--;
      current = current.getNext();
    }

    // Converts the integer (base 10) to the new base as a string
    String newNumStr = "";
    while (newNum > 0) {
      int remainder = newNum % newBase;
      String formattedRemainder = remainder >= 10 ? String.valueOf((char) (remainder + 55)) : String.valueOf(remainder);
      newNumStr = formattedRemainder + newNumStr;
      newNum = newNum / newBase;
    }

    return new LinkedNumber(newNumStr, newBase);
  }

  // Adds a digit at a specified position within the number
  public void addDigit (Digit digit, int position) {
    if (position < 0 || position > getNumDigits()) {
      throw new LinkedNumberException("invalid position");
    }

    DLNode<Digit> newNode = new DLNode<Digit>(digit);
    if (position == 0) { // Adds to the end of the list
      rear.setNext(newNode);
      newNode.setPrev(rear);
      rear = newNode;
    } else if (position == getNumDigits()) { // Adds to the front of the list
      newNode.setNext(front);
      front.setPrev(newNode);
      front = newNode;
    } else { // Adds to a specific position in the middle
      DLNode<Digit> current = rear;
      for (int i = 0; i < position; i++) {
        current = current.getPrev();
      }
      newNode.setNext(current.getNext());
      newNode.setPrev(current);
      current.getNext().setPrev(newNode);
      current.setNext(newNode);
    }
  }

  // Removes a digit at a specified position and returns its value
  public int removeDigit(int position) {
    if (position < 0 || position >= getNumDigits()) {
      throw new LinkedNumberException("invalid position");
    }

    DLNode<Digit> current = rear;
    for (int i = 0; i < position; i++) {
      current = current.getPrev();
    }

    if (current == front) { // Removes the front digit
      front = front.getNext();
      if (front != null) {
        front.setPrev(null);
      }
    } else if (current == rear) { // Removes the rear digit
      rear = rear.getPrev();
      rear.setNext(null);
    } else { // Removes a middle digit
      current.getPrev().setNext(current.getNext());
      current.getNext().setPrev(current.getPrev());
    }

    return current.getElement().getValue() * (int) Math.pow(base, position);
  }

}
