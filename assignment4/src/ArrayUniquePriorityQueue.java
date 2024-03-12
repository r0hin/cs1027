public class ArrayUniquePriorityQueue<T> {
  // generic type T queue
  private T[] queue;
  private double[] priority;
  private int count;

  @SuppressWarnings("unchecked")
  public ArrayUniquePriorityQueue() {
    queue = (T[]) new Object[10];
    priority = new double[10];
    count = 0;
  }

  public void add(T data, double prio) {
    // If data already in queue exit
    if (contains(data)) {
      return;
    }

    if (count == queue.length) {
      grow();
    }

    // Add data such that priority is ordered from lowest to highest. If exact priority match, add after the last occurrence
    for (int i = 0; i < count; i++) {
      if (prio < priority[i]) {
        for (int j = count; j > i; j--) {
          queue[j] = queue[j - 1];
          priority[j] = priority[j - 1];
        }
        queue[i] = data;
        priority[i] = prio;
        count++;
        return;
      }
    }

    // If priority is the highest, add at the end
    queue[count] = data;
    priority[count] = prio;
    count++;
  }

  private void grow() {
    // Increase by 5
    @SuppressWarnings("unchecked")
    T[] newQueue = (T[]) new Object[queue.length + 5];
    double[] newPriority = new double[priority.length + 5];
    for (int i = 0; i < count; i++) {
      newQueue[i] = queue[i];
      newPriority[i] = priority[i];
    }
    queue = newQueue;
    priority = newPriority;
  }

  public boolean contains(T data) {
    for (int i = 0; i < count; i++) {
      if (queue[i].equals(data)) {
        return true;
      }
    }
    return false;
  }

  public T peek() throws CollectionException {
    if (count == 0) {
      throw new CollectionException("PQ is empty");
    }
    int minIndex = 0;
    for (int i = 1; i < count; i++) {
      if (priority[i] < priority[minIndex]) {
        minIndex = i;
      }
    }
    return queue[minIndex];
  }

  public T removeMin() throws CollectionException {
    if (count == 0) {
      throw new CollectionException("PQ is empty");
    }
    int minIndex = 0;
    for (int i = 1; i < count; i++) {
      if (priority[i] < priority[minIndex]) {
        minIndex = i;
      }
    }
    T min = queue[minIndex];
    for (int i = minIndex; i < count - 1; i++) {
      queue[i] = queue[i + 1];
      priority[i] = priority[i + 1];
    }
    count--;
    return min;
  }

  public void updatePriority(T data, double newPrio) throws CollectionException {
    // Check if even in queue
    if (!contains(data)) {
      throw new CollectionException("Item not found in PQ");
    }

    // Remove it and add it again with new priority
    for (int i = 0; i < count; i++) {
      if (queue[i].equals(data)) {
        for (int j = i; j < count - 1; j++) {
          queue[j] = queue[j + 1];
          priority[j] = priority[j + 1];
        }
        count--;
        add(data, newPrio);
        return;
      }
    }
  }

  public boolean isEmpty() {
    return count == 0;
  }

  public int size() {
    return count;
  }

  public int getLength() {
    return queue.length;
  }

  public String toString() {
    String result = "";
    for (int i = 0; i < count; i++) {
      result += queue[i] + " [" + priority[i] + "], ";
    }
    
    if (result.length() > 0) {
      result = result.substring(0, result.length() - 2);
    } else {
      result = "The PQ is empty";
    }

    return result;
  }
}
