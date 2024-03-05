
public class DoubleNode<T> {

	private DoubleNode<T> next, prev;
    private T element;

    public DoubleNode () {
        next = null;
        element = null;
    }
    
    public DoubleNode (T elem) {
        next = null;
        element = elem;
    }

    public DoubleNode<T> getNext() {
        return next;
    }

    public void setNext (DoubleNode<T> node) {
        next = node;
    }
    
    public DoubleNode<T> getPrev () {
        return prev;
    }
    
    public void setPrev (DoubleNode<T> node) {
        prev = node;
    }

    public T getElement() {
        return element;
    }

    public void setElement (T elem) {
        element = elem;
    }
}
