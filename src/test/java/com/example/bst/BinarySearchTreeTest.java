package com.example.bst;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for Binary Search Tree implementation (Part 2).
 * Tests all core operations, edge cases, and traversal methods.
 */
class BinarySearchTreeTest {

    private BinarySearchTree<Integer> bst;
    private BinarySearchTree<Integer> emptyBst;

    @BeforeEach
    void setUp() {
        bst = new MyBinarySearchTree<>();
        emptyBst = new MyBinarySearchTree<>();

        // Set up a sample tree for testing
        // Tree structure:
        //       50
        //      /  \
        //     30   70
        //    / \   / \
        //  20  40 60  80
        //  /    \       \
        // 10    45      90
        int[] values = {50, 30, 70, 20, 40, 60, 80, 10, 45, 90};
        for (int value : values) {
            bst.insert(value);
        }
    }
    

    @Test
    @DisplayName("Insert should add elements and maintain BST property")
    void testInsert() {
        BinarySearchTree<Integer> testBst = new MyBinarySearchTree<>();

        
        assertTrue(testBst.isEmpty());
        testBst.insert(50);
        assertFalse(testBst.isEmpty());
        assertEquals(1, testBst.size());
        
        testBst.insert(30);
        testBst.insert(70);
        assertEquals(3, testBst.size());
        
        testBst.insert(50);
        assertEquals(3, testBst.size()); 
    }

    @Test
    @DisplayName("Contains should return true for existing elements and false for non-existing")
    void testContains() {
        
        assertTrue(bst.contains(50));
        assertTrue(bst.contains(30));
        assertTrue(bst.contains(70));
        assertTrue(bst.contains(10));
        assertTrue(bst.contains(90));
        
        assertFalse(bst.contains(100));
        assertFalse(bst.contains(25));
        assertFalse(bst.contains(-5));
        
        assertFalse(emptyBst.contains(50));
    }

    @Test
    @DisplayName("Delete should remove elements and maintain BST property")
    void testDelete() {
        int initialSize = bst.size();
        
        bst.delete(10);
        assertFalse(bst.contains(10));
        assertEquals(initialSize - 1, bst.size());
        
        bst.delete(80);
        assertFalse(bst.contains(80));
        assertTrue(bst.contains(90));
        assertEquals(initialSize - 2, bst.size());
        
        bst.delete(30);
        assertFalse(bst.contains(30));
        assertTrue(bst.contains(20)); 
        assertTrue(bst.contains(40)); 
        assertEquals(initialSize - 3, bst.size());
        
        bst.delete(50);
        assertFalse(bst.contains(50));
        assertTrue(bst.contains(70)); 
    }

    @Test
    @DisplayName("Delete non-existing element should not change tree")
    void testDeleteNonExisting() {
        int initialSize = bst.size();

        bst.delete(999);
        assertEquals(initialSize, bst.size());
        
        assertTrue(bst.contains(50));
        assertTrue(bst.contains(30));
        assertTrue(bst.contains(70));
    }

    @Test
    @DisplayName("Delete from empty tree should not throw exception")
    void testDeleteFromEmptyTree() {
        assertDoesNotThrow(() -> emptyBst.delete(50));
        assertEquals(0, emptyBst.size());
    }
    

    @Test
    @DisplayName("Size should return correct number of elements")
    void testSize() {
        assertEquals(10, bst.size());

        BinarySearchTree<Integer> testBst = new MyBinarySearchTree<>();
        assertEquals(0, testBst.size());

        testBst.insert(10);
        assertEquals(1, testBst.size());

        testBst.insert(20);
        assertEquals(2, testBst.size());

        testBst.delete(10);
        assertEquals(1, testBst.size());
    }

    @Test
    @DisplayName("IsEmpty should return true for empty tree and false otherwise")
    void testIsEmpty() {
        assertTrue(emptyBst.isEmpty());
        assertFalse(bst.isEmpty());

        emptyBst.insert(10);
        assertFalse(emptyBst.isEmpty());

        emptyBst.delete(10);
        assertTrue(emptyBst.isEmpty());
    }

    @Test
    @DisplayName("Height should return correct tree height")
    void testHeight() {
        
        assertEquals(3, bst.height());
        
        assertEquals(-1, emptyBst.height());
        
        emptyBst.insert(10);
        assertEquals(0, emptyBst.height());
        
        emptyBst.insert(20);
        assertEquals(1, emptyBst.height());
    }

    @Test
    @DisplayName("Min should return smallest element in tree")
    void testMin() {
        assertEquals(10, bst.min());
        
        assertThrows(IllegalStateException.class, () -> emptyBst.min());
        
        emptyBst.insert(5);
        assertEquals(5, emptyBst.min());
        
        bst.delete(10);
        assertEquals(20, bst.min());
    }

    @Test
    @DisplayName("Max should return largest element in tree")
    void testMax() {
        assertEquals(90, bst.max());
        
        assertThrows(IllegalStateException.class, () -> emptyBst.max());
        
        emptyBst.insert(5);
        assertEquals(5, emptyBst.max());

        // Test after deletions and insertions
        bst.delete(90);
        assertEquals(80, bst.max());
    }
    

    @Test
    @DisplayName("In-order traversal should produce sorted output")
    void testInOrderTraversal() {
        // Capture output by redirecting System.out
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        bst.inOrderTraversal();
        String output = outContent.toString().trim();

        // In-order should be sorted: 10 20 30 40 45 50 60 70 80 90
        assertTrue(output.contains("10 20 30 40 45 50 60 70 80 90"));

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    @DisplayName("Pre-order traversal should visit root first")
    void testPreOrderTraversal() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        bst.preOrderTraversal();
        String output = outContent.toString().trim();
        
        assertTrue(output.startsWith("Pre-order: 50"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Post-order traversal should visit root last")
    void testPostOrderTraversal() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        bst.postOrderTraversal();
        String output = outContent.toString().trim();
        
        assertTrue(output.endsWith("50"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Level-order traversal should visit nodes level by level")
    void testLevelOrderTraversal() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        bst.levelOrderTraversal();
        String output = outContent.toString().trim();
        
        assertTrue(output.contains("50 30 70 20 40 60 80 10 45 90"));

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Traversals on empty tree should not throw exceptions")
    void testTraversalsOnEmptyTree() {
        assertDoesNotThrow(() -> emptyBst.inOrderTraversal());
        assertDoesNotThrow(() -> emptyBst.preOrderTraversal());
        assertDoesNotThrow(() -> emptyBst.postOrderTraversal());
        assertDoesNotThrow(() -> emptyBst.levelOrderTraversal());
    }
    

    @Test
    @DisplayName("Tree should handle duplicate insertions gracefully")
    void testDuplicateInsertions() {
        int initialSize = bst.size();
        
        bst.insert(50);
        assertEquals(initialSize, bst.size()); 
        
        assertTrue(bst.contains(50));
        assertTrue(bst.contains(30));
        assertTrue(bst.contains(70));
    }

    @Test
    @DisplayName("Tree should handle sequential insertions correctly")
    void testSequentialInsertions() {
        BinarySearchTree<Integer> sequentialBst = new MyBinarySearchTree<>();

        // Insert in sorted order (creates a degenerated tree)
        for (int i = 1; i <= 5; i++) {
            sequentialBst.insert(i);
        }

        assertEquals(5, sequentialBst.size());
        assertEquals(4, sequentialBst.height());
        assertEquals(1, sequentialBst.min());
        assertEquals(5, sequentialBst.max());
    }

    @Test
    @DisplayName("Tree should handle negative numbers")
    void testNegativeNumbers() {
        BinarySearchTree<Integer> negativeBst = new MyBinarySearchTree<>();

        negativeBst.insert(-10);
        negativeBst.insert(-5);
        negativeBst.insert(-20);
        negativeBst.insert(0);
        negativeBst.insert(5);

        assertEquals(5, negativeBst.size());
        assertEquals(-20, negativeBst.min());
        assertEquals(5, negativeBst.max());
        assertTrue(negativeBst.contains(-10));
        assertTrue(negativeBst.contains(0));
    }

    @Test
    @DisplayName("Tree should maintain BST property after multiple operations")
    void testBstPropertyMaintenance() {
        // Perform mixed operations
        bst.delete(30);
        bst.insert(35);
        bst.delete(70);
        bst.insert(75);
        
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        bst.inOrderTraversal();
        String output = outContent.toString().trim();
        String[] numbers = output.replace("In-order: ", "").split(" ");

        // Check if output is sorted
        for (int i = 0; i < numbers.length - 1; i++) {
            int current = Integer.parseInt(numbers[i]);
            int next = Integer.parseInt(numbers[i + 1]);
            assertTrue(current <= next, "In-order traversal should be sorted");
        }

        System.setOut(System.out);
    }

    @Test
    @DisplayName("Tree should handle deletion of all elements")
    void testDeleteAllElements() {
        
        int[] values = {10, 20, 45, 40, 30, 60, 90, 80, 70, 50};
        for (int value : values) {
            bst.delete(value);
        }

        assertTrue(bst.isEmpty());
        assertEquals(0, bst.size());
        assertEquals(-1, bst.height());
        
        bst.insert(100);
        assertFalse(bst.isEmpty());
        assertEquals(1, bst.size());
    }

    @Test
    @DisplayName("String representation should show in-order traversal")
    void testToString() {
        String representation = bst.toString();
        
        assertTrue(representation.contains("10"));
        assertTrue(representation.contains("50"));
        assertTrue(representation.contains("90"));
        assertTrue(representation.startsWith("BST["));
        assertTrue(representation.endsWith("]"));
    }
}
