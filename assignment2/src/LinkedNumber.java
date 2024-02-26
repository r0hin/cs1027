public class LinkedNumber {
  private int base;
  private DLNode<Digit> front;
  private DLNode<Digit> rear;

  public LinkedNumber (String num, int baseNum) {
    base = baseNum;
    if (num.length() == 0) {
      throw new LinkedNumberException("no digits given");
    }

    for (int i = 0; i < num.length(); i++) {
      char c = num.charAt(i);

      DLNode<Digit> newNode = new DLNode<Digit>(new Digit(c));
      if (front == null) { // first node
        front = newNode; // set as front and rear
        rear = newNode;
      } else { // already node
        rear.setNext(newNode); // set as next of old rear
        newNode.setPrev(rear); // set old rear as prev of new rear
        rear = newNode;
      }
    }
  }

  public LinkedNumber (int num) {
    base = 10;
    String numStr = Integer.toString(num);
    for (int i = 0; i < numStr.length(); i++) {
      char c = numStr.charAt(i);

      DLNode<Digit> newNode = new DLNode<Digit>(new Digit(c));
      if (front == null) { // first node
        front = newNode; // set as front and rear
        rear = newNode;
      } else { // already node
        rear.setNext(newNode); // set as next of old rear
        newNode.setPrev(rear); // set old rear as prev of new rear
        rear = newNode;
      }
    }
  }

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

  public int getBase() {
    return base;
  }

  public DLNode<Digit> getFront() {
    return front;
  }

  public DLNode<Digit> getRear() {
    return rear;
  }

  public int getNumDigits() {
    int count = 0;
    DLNode<Digit> current = front;
    while (current != null) {
      count++;
      current = current.getNext();
    }
    return count;
  }

  public String toString() {
    String str = "";
    DLNode<Digit> current = front;
    while (current != null) {
      // 11111111 base2, converted to base 16 should return FF
      str += current.getElement().toString();
      current = current.getNext();
    }

    return str;
  }

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

  public LinkedNumber convert(int newBase) {
    if (!isValidNumber()) {
      throw new LinkedNumberException("cannot convert invalid number");
    }

    int newNum = 0;
    int power = getNumDigits() - 1;
    DLNode<Digit> current = front;
    while (current != null) {
      newNum += current.getElement().getValue() * (int) Math.pow(base, power);
      power--;
      current = current.getNext();
    }

    // newNum is base 10, convert to newBase
    String newNumStr = "";
    while (newNum > 0) {
      int remainder = newNum % newBase;

      String formattedRemainder = "";
      // if remainder is 10 or more, convert to letter
      if (remainder >= 10) {
        formattedRemainder = String.valueOf((char) (remainder + 55));
      } else {
        formattedRemainder = String.valueOf(remainder);
      }

      newNumStr = formattedRemainder + newNumStr;
      newNum = newNum / newBase;
    }

    return new LinkedNumber(newNumStr, newBase);
  }

  public void addDigit (Digit digit, int position) {
    if (position < 0 || position > getNumDigits()) {
      throw new LinkedNumberException("invalid position");
    }

    DLNode<Digit> newNode = new DLNode<Digit>(digit);
    if (position == 0) { // add after rear
      rear.setNext(newNode);
      newNode.setPrev(rear);
      rear = newNode;
    } else if (position == getNumDigits()) { // add before front
      newNode.setNext(front);
      front.setPrev(newNode);
      front = newNode;
    } else { // add to middle, position is num of digits from the rear
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

  public int removeDigit(int position) {
    if (position < 0 || position >= getNumDigits()) {
      throw new LinkedNumberException("invalid position");
    }

    // Position is given as num of digits from the rear
    DLNode<Digit> current = rear;
    for (int i = 0; i < position; i++) {
      current = current.getPrev();
    }

    if (current == front) { // remove front
      front = front.getNext();
      front.setPrev(null);
    } else if (current == rear) { // remove rear
      rear = rear.getPrev();
      rear.setNext(null);
    } else { // remove from middle
      current.getPrev().setNext(current.getNext());
      current.getNext().setPrev(current.getPrev());
    }

    // Removing 4 from 7492 should return 400.
    return current.getElement().getValue() * (int) Math.pow(base, position);
  }

}