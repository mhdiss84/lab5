package com.example.dlist;

/**
 * A generic Doubly Linked List interface for Lab 5.
 * Students must implement all methods. */
public interface DoublyLinkedListAPI<T> {
    //Basic operations
    void addFirst(T data);  // Insert at the head

    void addLast(T data);  // Insert at the tail

    void insertAt(int index, T data);  // Insert at a given index

    // Deletion
    T removeFirst();  // Remove from head

    T removeLast();  // Remove from tail

    T removeAt(int index);  // Remove from a given index

    //Access
    T getFirst();      // Get head element

    T getLast();       // Get tail element

    T getAt(int index);  // Get element at index

    // Utility
    int size();      // Number of elements

    boolean isEmpty();      // Check if empty

    void clear();      // Remove all elements

    //Search
    boolean contains(T data);  // Check existence

    int indexOf(T data);  // First index of element
}

