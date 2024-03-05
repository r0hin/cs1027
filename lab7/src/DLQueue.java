
public class DLQueue<T> implements QueueADT<T> {
	private int count;
	private DoubleNode<T> front, rear;
	
	public DLQueue () {
		count = 0;
		front = rear = null;
	}
	
	public void enqueue(T element) {
		DoubleNode<T> node = new DoubleNode<T>(element);
		if (isEmpty()) {
			front = node;
		} else {
			rear.setNext(node);
			node.setPrev(rear);
		}

		rear = node;
		count++;
	}

	public T dequeue() throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("Queue");
		} else {
			// Remove front, set front to the next node.
			// Fix the previous pointer of the new front.
			T result = front.getElement();
			front = front.getNext();
			if (front != null) {
				front.setPrev(null);
			} else {
				rear = null;
			}
			count--;
			return result;
		}
	}

	public T first() throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("Queue");
		} else {
			return front.getElement();
		}
	}

	public boolean isEmpty() {
		return (count == 0);
	}

	public int size() {
		return count;
	}

	public String toString() {
		// Queue: a, b, c, d.

		if (isEmpty()) {
			return "The queue is empty.";
		}

		String result = "Queue: ";
		DoubleNode<T> curr = front;
		while (curr != null) {
			result += curr.getElement() + ", ";
			curr = curr.getNext();
		}
		// add a period at the end.
		result = result.substring(0, result.length() - 2) + ".";
		return result;
	}
	
	public DoubleNode<T> getFront () {
		return front;
	}
	
	public DoubleNode<T> getRear () {
		return rear;
	}
	
}
