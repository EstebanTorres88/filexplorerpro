
/**
 *
 * @author Esteban Torres jimenez
 * @author Dilan Rojas Vargas
 */
public class OperationQueue {
    private OperationNode front;
    private OperationNode rear;
    private int size;

    public OperationQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    public void enqueue(Operation operation) {
        if (operation == null) {
            return;
        }
        OperationNode node = new OperationNode(operation);
        if (rear == null) {
            front = node;
            rear = node;
        } else {
            rear.setNext(node);
            rear = node;
        }
        size += 1;
    }

    public Operation dequeue() {
        if (isEmpty()) {
            return null;
        }
        OperationNode node = front;
        front = node.getNext();
        if (front == null) {
            rear = null;
        }
        size -= 1;
        return node.getOperation();
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }
}