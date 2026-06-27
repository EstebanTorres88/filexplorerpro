

package com.mycompany.filexplorer.estructuras;

import java.util.Comparator;
/**
 *
 * @author Dilan Rojas Vargas
 * @author Esteban Torres Jiménez
 */

public class LinkedListGeneric<T> {
    private ListNode<T> head;
    private int size = 0;

    public void add(T data) {
        ListNode<T> newNode = new ListNode<>(data);
        if (head == null)
            head = newNode;
        else {
            ListNode<T> currentNode = head;
            while (currentNode.next != null)
                currentNode = currentNode.next;
            currentNode.next = newNode;
        }
        size++;
    }

    public boolean remove(T data) {
        if (head == null)
            return false;
        if (head.data == null ? data == null : head.data.equals(data)) {
            head = head.next;
            size--;
            return true;
        }
        ListNode<T> currentNode = head;
        while (currentNode.next != null) {
            if (currentNode.next.data == null ? data == null : currentNode.next.data.equals(data)) {
                currentNode.next = currentNode.next.next;
                size--;
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    public T get(int index) {
        if (index < 0 || index >= size)
            return null;
        ListNode<T> currentNode = head;
        for (int i = 0; i < index; i++)
            currentNode = currentNode.next;
        return currentNode.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void sort(Comparator<T> comparator) {
        head = mergeSort(head, comparator);
    }

    private ListNode<T> mergeSort(ListNode<T> node, Comparator<T> comparator) {
        if (node == null || node.next == null)
            return node;

        ListNode<T> temp = node;
        int half = 0;
        while (temp != null) {
            half++;
            temp = temp.next;
        }

        half = half / 2;
        ListNode<T> current = node;
        for (int i = 0; i < half - 1; i++) {
            current = current.next;
        }

        ListNode<T> secondHalf = current.next;
        current.next = null;

        ListNode<T> left = mergeSort(node, comparator);
        ListNode<T> right = mergeSort(secondHalf, comparator);

        return merge(left, right, comparator);
    }

    private ListNode<T> merge(ListNode<T> left, ListNode<T> right, Comparator<T> comparator) {
        ListNode<T> aux = new ListNode<>(null);
        ListNode<T> current = aux;

        while (left != null && right != null) {
            if (comparator.compare(left.data, right.data) <= 0) {
                current.next = left;
                left = left.next;
            } else {
                current.next = right;
                right = right.next;
            }
            current = current.next;
        }

        current.next = (left != null) ? left : right;
        return aux.next;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ListNode<T> currentNode = head;
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
