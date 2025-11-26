package com.example.dlist;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A generic doubly linked list that uses head and tail sentinel nodes.
 *
 * @param <T> the type of elements stored in the list
 */

public final class DoublyLinkedList<T> implements DoublyLinkedListAPI<T> {

    /**
     * Doubly-linked node containing an element and references to previous and next nodes.
     *
     * @param <T> the type of element stored in this node
     */
    private static final class Node<T> {
        T data;
        DoublyLinkedList.Node<T> prev;
        DoublyLinkedList.Node<T> next;

        /**
         * Constructs a new node with the specified data and links.
         *
         * @param data the element to store in this node
         * @param prev reference to the previous node
         * @param next reference to the next node
         */
        Node(T data, DoublyLinkedList.Node<T> prev, DoublyLinkedList.Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    private final DoublyLinkedList.Node<T> head; // sentinel head (does not store an element)
    private final DoublyLinkedList.Node<T> tail; // sentinel tail (does not store an element)

    private int size;
    private int modCount; // for fail-fast iterators

    /** Constructs an empty list with head/tail sentinels linked together. */
    public DoublyLinkedList() {
        head = new DoublyLinkedList.Node<>(null, null, null);
        tail = new DoublyLinkedList.Node<>(null, null, null);
        head.next = tail;
        tail.prev = head;
        size = 0;
        modCount = 0;
    }

    // --------------- Add operations ---------------

    /**
     * Inserts the specified element at the beginning of this list.
     *
     * @param data the element to add
     */
    @Override
    public void addFirst(T data) {
        linkBetween(data, head, head.next);
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param data the element to add
     */
    @Override
    public void addLast(T data) {
        linkBetween(data, tail.prev, tail);
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param data element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index > size()})
     */
    @Override
    public void insertAt(int index, T data) {
        checkPositionIndex(index);
        if (index == size) {
            linkBetween(data, tail.prev, tail);
        } else {
            DoublyLinkedList.Node<T> target = nodeAt(index);
            linkBetween(data, target.prev, target);
        }
    }

    // --------------- Remove operations ---------------

    /**
     * Removes and returns the first element from this list.
     *
     * @return the first element from this list
     * @throws NoSuchElementException if this list is empty
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("List is empty");
        return unlink(head.next);
    }

    /**
     * Removes and returns the last element from this list.
     *
     * @return the last element from this list
     * @throws NoSuchElementException if this list is empty
     */
    @Override
    public T removeLast() {
        if (isEmpty()) throw new NoSuchElementException("List is empty");
        return unlink(tail.prev);
    }

    /**
     * Removes the element at the specified position in this list. Shifts any
     * subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index >= size()})
     */
    @Override
    public T removeAt(int index) {
        checkElementIndex(index);
        return unlink(nodeAt(index));
    }

    /**
     * Removes all elements from this list. The list will be empty
     * after this call returns.
     */
    @Override
    public void clear() {
        // Unlink all nodes to help GC
        for (DoublyLinkedList.Node<T> x = head.next; x != tail; ) {
            DoublyLinkedList.Node<T> next = x.next;
            x.data = null;
            x.prev = null;
            x.next = null;
            x = next;
        }
        head.next = tail;
        tail.prev = head;
        size = 0;
        modCount++;
    }

    // --------------- Element access ---------------

    /**
     * Returns the first element in this list.
     *
     * @return the first element in this list
     * @throws NoSuchElementException if this list is empty
     */
    @Override
    public T getFirst() {
        if (isEmpty()) throw new NoSuchElementException("List is empty");
        return head.next.data;
    }

    /**
     * Returns the last element in this list.
     *
     * @return the last element in this list
     * @throws NoSuchElementException if this list is empty
     */
    public T getLast() {
        if (isEmpty()) throw new NoSuchElementException("List is empty");
        return tail.prev.data;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index >= size()})
     */
    public T getAt(int index) {
        checkElementIndex(index);
        return nodeAt(index).data;
    }

    // --------------- Basic queries ---------------

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if this list contains no elements.
     *
     * @return {@code true} if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns {@code true} if this list contains the specified element.
     *
     * @param data element whose presence in this list is to be tested
     * @return {@code true} if this list contains the specified element
     */
    @Override
    public boolean contains(T data) {
        return indexOf(data) != -1;
    }

    // --------------- Search ---------------

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     *
     * @param data element to search for
     * @return the index of the first occurrence of the specified element in
     *         this list, or -1 if this list does not contain the element
     */
    @Override
    public int indexOf(T data) {
        int idx = 0;
        for (DoublyLinkedList.Node<T> x = head.next; x != tail; x = x.next) {
            if (Objects.equals(data, x.data)) return idx;
            idx++;
        }
        return -1;
    }

    // --------------- Helpers ---------------

    /**
     * Links a new node containing the specified element between the given
     * previous and next nodes. Updates the size and modification count.
     *
     * @param data the element to insert
     * @param prev the node that will precede the new node
     * @param next the node that will follow the new node
     */
    private void linkBetween(T data, DoublyLinkedList.Node<T> prev, DoublyLinkedList.Node<T> next) {
        DoublyLinkedList.Node<T> n = new DoublyLinkedList.Node<>(data, prev, next);
        prev.next = n;
        next.prev = n;
        size++;
        modCount++;
    }

    /**
     * Checks if the specified index is a valid position index (for add operations).
     * A position index is valid if it is between 0 (inclusive) and size (inclusive).
     *
     * @param index the index to check
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    private void checkPositionIndex(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    /**
     * Returns the node at the specified index. Optimizes traversal by starting
     * from the nearest end (head or tail) based on the index position.
     *
     * @param index the index of the node to return (must be valid)
     * @return the node at the specified index
     */
    private DoublyLinkedList.Node<T> nodeAt(int index) {
        // index is assumed valid
        if (index < (size >> 1)) {
            DoublyLinkedList.Node<T> x = head.next;
            for (int i = 0; i < index; i++) x = x.next;
            return x;
        } else {
            DoublyLinkedList.Node<T> x = tail.prev;
            for (int i = size - 1; i > index; i--) x = x.prev;
            return x;
        }
    }

    /**
     * Unlinks the specified node from the list. Updates the size and modification
     * count, and helps garbage collection by nulling out references.
     *
     * @param n the node to unlink
     * @return the element that was stored in the unlinked node
     */
    private T unlink(DoublyLinkedList.Node<T> n) {
        DoublyLinkedList.Node<T> prev = n.prev;
        DoublyLinkedList.Node<T> next = n.next;
        prev.next = next;
        next.prev = prev;
        T item = n.data;
        // Help GC
        n.data = null;
        n.prev = null;
        n.next = null;
        size--;
        modCount++;
        return item;
    }

    /**
     * Checks if the specified index is a valid element index (for get/set/remove
     * operations). An element index is valid if it is between 0 (inclusive) and
     * size (exclusive).
     *
     * @param index the index to check
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    private void checkElementIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
}
