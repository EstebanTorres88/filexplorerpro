

package com.mycompany.filexplorer.estructuras;
/**
 *
 * @author Dilan Rojas Vargas
 * @author Esteban Torres Jiménez
 */

public class QueueGeneric<T> {
    private ListNode<T> head;
    private ListNode<T> tail;
    private int size = 0;

    public void enqueue(T data) {
        ListNode<T> newNode = new ListNode<>(data);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public T dequeue() {
        if (head == null) return null;
        T dataToReturn = head.data;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return dataToReturn;
    }

    public T peek() { return head == null ? null : head.data; }
    public boolean isEmpty() { return head == null; }
    public int size() { return size; }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ListNode<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.data != null) stringBuilder.append(currentNode.data.toString()); else stringBuilder.append("null");
            if (currentNode.next != null) stringBuilder.append(", ");
            currentNode = currentNode.next;
        }
        return stringBuilder.toString();
    }
}
