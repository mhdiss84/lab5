package com.example.bst;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of Binary Search Tree for Lab 5 Part 2.
 *
 * <p>Time Complexity:
 * - Insertion: O(log n) average, O(n) worst-case (unbalanced tree)
 * - Deletion: O(log n) average, O(n) worst-case
 * - Search: O(log n) average, O(n) worst-case
 * - Traversals: O(n) for all traversal types</p>
 *
 * <p>Space Complexity: O(n) for storing nodes, O(h) for recursive traversals
 * where h is the height of the tree</p>
 *
 * @param <T> the type of elements maintained by this BST, must be Comparable
 */

public class MyBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T>  {
    private TreeNode<T> root;
    private int size;

    /**
     * Constructs an empty Binary Search Tree.
     */
    public MyBinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public void insert(T data) {
        root = insertRecursive(root, data);
    }

    /**
     * Recursive helper method for insertion.
     *
     * @param node the current node in recursion
     * @param data the data to insert
     * @return the root of the modified subtree
     */
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

    /**
     * Recursive helper method for search.
     *
     * @param node the current node in recursion
     * @param data the data to search for
     * @return true if data is found, false otherwise
     */
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

    /**
     * Recursive helper method for deletion.
     * Handles three cases: no children, one child, two children.
     *
     * @param node the current node in recursion
     * @param data the data to delete
     * @return the root of the modified subtree
     */
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

    // === Traversal Methods ===
    @Override
    public void inOrderTraversal() {
        System.out.print("In-order: ");
        inOrderRecursive(root);
        System.out.println();
    }

    private void inOrderRecursive(TreeNode<T> node) {
        if (node != null) {
            inOrderRecursive(node.left);
            System.out.print(node.data + " ");
            inOrderRecursive(node.right);
        }
    }

    @Override
    public void preOrderTraversal() {
        System.out.print("Pre-order: ");
        preOrderRecursive(root);
        System.out.println();
    }

    private void preOrderRecursive(TreeNode<T> node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrderRecursive(node.left);
            preOrderRecursive(node.right);
        }
    }

    @Override
    public void postOrderTraversal() {
        System.out.print("Post-order: ");
        postOrderRecursive(root);
        System.out.println();
    }

    private void postOrderRecursive(TreeNode<T> node) {
        if (node != null) {
            postOrderRecursive(node.left);
            postOrderRecursive(node.right);
            System.out.print(node.data + " ");
        }
    }

    @Override
    public void levelOrderTraversal() {
        System.out.print("Level-order: ");
        if (root == null) {
            System.out.println();
            return;
        }

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode<T> current = queue.poll();
            System.out.print(current.data + " ");

            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
        System.out.println();
    }

    // === Tree Properties ===
    @Override
    public int height() {
        return heightRecursive(root);
    }

    /**
     * Recursive helper method for height calculation.
     * Height of empty tree is -1, single node tree is 0.
     *
     * @param node the current node in recursion
     * @return the height of the subtree
     */
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

    /**
     * Finds the node with minimum value in the given subtree.
     *
     * @param node the root of the subtree
     * @return the node with minimum value
     */
    private TreeNode<T> findMin(TreeNode<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Finds the node with maximum value in the given subtree.
     *
     * @param node the root of the subtree
     * @return the node with maximum value
     */
    private TreeNode<T> findMax(TreeNode<T> node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    /**
     * Returns a string representation of the tree (in-order traversal).
     *
     * @return string representation of the tree
     */
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

