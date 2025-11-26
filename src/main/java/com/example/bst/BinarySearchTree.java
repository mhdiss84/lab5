package com.example.bst;

/**
 * A generic Binary Search Tree interface with core operations and Traversals.
 * 
 * @param <T> the type of elements in this tree, must be Comparable.
 */

public interface BinarySearchTree<T extends Comparable<T>> {
    // Core operations
    void insert(T data);
    boolean contains(T data);
    void delete(T data);
    
    // Traversals
    void inorderTraversal();
    void preorderTraversal();
    void postOrderTraversal();
    void levelOrderTraversal();
    
    // Properties
    int height();
    int size();
    boolean isEmpty();
    
    // Utility Methods 
    T min();
    T max();
}
