public class ArrayUniquePriorityQueue<T> {
  private T[] queue; // Holds the data
  private double[] priority; // Holds the priority
  private int count; // Number of elements in the queue

  @SuppressWarnings("unchecked") // Suppress unchecked cast warning
  public ArrayUniquePriorityQueue() {
    queue = (T[]) new Object[10]; // Start with 10 elements
    priority = new double[10]; 
    count = 0;
  }

  // Add data to the queue with priority prio
  public void add(T data, double prio) {
    if (contains(data)) {
      return;  // If data already in queue exit
    }

    if (count == queue.length) {
      grow(); // Grow the queue if full
    }

    // Add data such that priority is ordered from lowest to highest. 
    // If exact priority match, add after the last occurrence
    for (int i = 0; i < count; i++) {
      if (prio < priority[i]) { // First occurrence of a higher priority
        for (int j = count; j > i; j--) { // Shift all elements to the right
          queue[j] = queue[j - 1];
          priority[j] = priority[j - 1];
        }
        // Add the new data
        queue[i] = data;
        priority[i] = prio;
        count++;
        return; // Exit
      }
    }

    // If priority is the highest, add at the end
    queue[count] = data;
    priority[count] = prio;
    count++;
  }

  // Increase by 5
  private void grow() {
    @SuppressWarnings("unchecked")
    T[] newQueue = (T[]) new Object[queue.length + 5]; // Grow by 5
    double[] newPriority = new double[priority.length + 5];
    for (int i = 0; i < count; i++) {
      newQueue[i] = queue[i]; // Copy the data
      newPriority[i] = priority[i];
    }
    // Update the queue and priority instance variables
    queue = newQueue;
    priority = newPriority;
  }

  // Check if data is in the queue
  public boolean contains(T data) {
    for (int i = 0; i < count; i++) {
      if (queue[i].equals(data)) {
        return true; // If found, return true
      }
    }
    return false;
  }

  // Return the data with the lowest priority
  public T peek() throws CollectionException {
    if (count == 0) { // If queue is empty, throw exception
      throw new CollectionException("PQ is empty");
    }
    // Find the index of the lowest priority
    int minIndex = 0;
    for (int i = 1; i < count; i++) {
      if (priority[i] < priority[minIndex]) {
        minIndex = i; // Update minIndex
      }
    }

    return queue[minIndex];
  }

  // Remove the data with the lowest priority
  public T removeMin() throws CollectionException {
    if (count == 0) { // If queue is empty, throw exception
      throw new CollectionException("PQ is empty");
    }
    // Find the index of the lowest priority, same as in peek
    int minIndex = 0;
    for (int i = 1; i < count; i++) {
      if (priority[i] < priority[minIndex]) {
        minIndex = i;
      }
    }
    // Remove the data and priority
    T min = queue[minIndex];
    for (int i = minIndex; i < count - 1; i++) {
      queue[i] = queue[i + 1];
      priority[i] = priority[i + 1];
    }
    count--; // Update count
    return min;
  }

  // Update the priority of data to newPrio
  public void updatePriority(T data, double newPrio) throws CollectionException {
    if (!contains(data)) { // Check if even in queue
      throw new CollectionException("Item not found in PQ");
    }

    for (int i = 0; i < count; i++) {
      if (queue[i].equals(data)) {
        // Remove it
        for (int j = i; j < count - 1; j++) {
          queue[j] = queue[j + 1];
          priority[j] = priority[j + 1];
        }

        count--;
        // Add it with the new priority
        add(data, newPrio);
        return;
      }
    }
  }

  // Check if the queue is empty
  public boolean isEmpty() {
    return count == 0;
  }

  // Return the number of elements in the queue
  public int size() {
    return count;
  }

  // Return the length of the queue
  public int getLength() {
    return queue.length;
  }

  // Return a string representation of the queue
  public String toString() {
    String result = "";
    for (int i = 0; i < count; i++) {
      result += queue[i] + " [" + priority[i] + "], ";
    }

    // Remove the last ", "
    if (result.length() > 0) {
      result = result.substring(0, result.length() - 2);
    } else {
      result = "The PQ is empty";
    }

    return result;
  }
}
