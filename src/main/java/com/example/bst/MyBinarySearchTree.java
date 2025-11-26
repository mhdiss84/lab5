package com.example.bst;

import java.util.LinkedList;
import java.util.Queue;

public class MyBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T>  {
    private TreeNode<T> root;
    private int size;

    public MyBinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public void insert(T data) {
        root = insertRecursive(root, data);
    }

    private TreeNode<T> insertRecursive(TreeNode<T> node, T data) {
        if (node == null) {
            size++;
            return new TreeNode<>(data);
        }

        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            node.left = insertRecursive(node.left, data);
        } else if (cmp > 0) {
            node.right = insertRecursive(node.right, data);
        }
         return node;
    }

    @Override
    public boolean contains(T data) {
        return containsRecursive(root, data);
    }

    private boolean containsRecursive(TreeNode<T> node, T data) {

        if (node == null) {
            return false;
        }

        int cmp = data.compareTo(node.data);
        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            return containsRecursive(node.left, data);
        } else {
            return containsRecursive(node.right, data);
        }
    }

    @Override
    public void delete(T data){
        root = deleteRecursive(root, data);
    }

    private TreeNode<T> deleteRecursive(TreeNode<T> node, T data) {
        if (node == null) {
            return null;
        }

        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            node.left = deleteRecursive(node.left, data);
        } else if (cmp > 0) {
            node.right = deleteRecursive(node.right, data);
        } else {
            size--;

            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            node.data = findMin(node.right).data;
            node.right = deleteRecursive(node.right, node.data);
            size++;
        }

        return node;
    }

    // === Traversal Methods (Part 3) for SALAM ===





    // === Tree Properties ===
    @Override
    public int height() {
        return heightRecursive(root);
    }
    
    private int heightRecursive(TreeNode<T> node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(heightRecursive(node.left), heightRecursive(node.right));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // === Additional Utility Methods ===

    @Override
    public T min() {
        if (root == null) {
            throw new IllegalStateException("Tree is empty");
        }
        return findMin(root).data;
    }

    @Override
    public T max() {
        if (root == null) {
            throw new IllegalStateException("Tree is empty");
        }
        return findMax(root).data;
    }
    
    private TreeNode<T> findMin(TreeNode<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    
    private TreeNode<T> findMax(TreeNode<T> node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BST[");
        inOrderString(root, sb);
        if (sb.charAt(sb.length() - 1) == ' ') {
            sb.setLength(sb.length() - 1); // Remove trailing space
        }
        sb.append("]");
        return sb.toString();
    }

    private void inOrderString(TreeNode<T> node, StringBuilder sb) {
        if (node != null) {
            inOrderString(node.left, sb);
            sb.append(node.data).append(" ");
            inOrderString(node.right, sb);
        }
    }
}


