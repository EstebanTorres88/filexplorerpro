
package com.mycompany.filexplorer.estructuras;

/**
 *
 * @author jwd
 * @author Esteban Torres Jiménez
 */

public class StackGeneric<T> {
    private ListNode<T> top;
    private int size = 0;

    public void push(T data) {
        ListNode<T> newNode = new ListNode<>(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public T pop() {
        if (top == null)
            return null;
        T dataToReturn = top.data;
        top = top.next;
        size--;
        return dataToReturn;
    }

    public T peek() {
        return top == null ? null : top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    public void clear() {
        top = null;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ListNode<T> currentNode = top;
        while (currentNode != null) {
            if (currentNode.data != null)
                stringBuilder.append(currentNode.data.toString());
            else
                stringBuilder.append("null");
            if (currentNode.next != null)
                stringBuilder.append(", ");
            currentNode = currentNode.next;
        }
        return stringBuilder.toString();
    }
}
