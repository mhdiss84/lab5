package com.example.bst;

/**
 * Node class for BST.
 * 
 * @param <T> the type of element stored in this node
 */

public class TreeNode<T> {
    public T data;
    public TreeNode<T> left;
    public TreeNode<T> right;

    /**
     * Constructs a new node with the specified data and no children.
     * 
     * @param data the element to store in this node
     */
    
    public TreeNode(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}


