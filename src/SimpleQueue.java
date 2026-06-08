/**
 *
 * @author Esteban Torres jimenez
 * @author Dilan Rojas Vargas
 */
public class SimpleQueue<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;

    public SimpleQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    public void enqueue(T element) {
        if (element == null) {
            return;
        }
        Node<T> newNode = new Node<>(element);
        if (rear == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        Node<T> node = front;
        front = node.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return node.data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    /**
     * Inner class para los nodos de la cola
     */
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
}
