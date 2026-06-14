/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.filexplorer.estructuras;

/**
 *
 * @author jwd
 */

public class LinkedListGeneric<T> {
    private ListNode<T> head;
    private int size = 0;

    public void add(T data) {
        ListNode<T> newNode = new ListNode<>(data);
        if (head == null) head = newNode;
        else {
            ListNode<T> currentNode = head;
            while (currentNode.next != null) currentNode = currentNode.next;
            currentNode.next = newNode;
        }
        size++;
    }

    public boolean remove(T data) {
        if (head == null) return false;
        if (head.data == null ? data == null : head.data.equals(data)) {
            head = head.next; size--; return true;
        }
        ListNode<T> currentNode = head;
        while (currentNode.next != null) {
            if (currentNode.next.data == null ? data == null : currentNode.next.data.equals(data)) {
                currentNode.next = currentNode.next.next; size--; return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    public T get(int index) {
        if (index < 0 || index >= size) return null;
        ListNode<T> currentNode = head;
        for (int i = 0; i < index; i++) currentNode = currentNode.next;
        return currentNode.data;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ListNode<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.data != null) stringBuilder.append(currentNode.data.toString());
            else stringBuilder.append("null");
            if (currentNode.next != null) stringBuilder.append(", ");
            currentNode = currentNode.next;
        }
        return stringBuilder.toString();
    }
}
