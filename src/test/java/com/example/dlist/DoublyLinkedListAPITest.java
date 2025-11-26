package com.example.dlist;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class DoublyLinkedListAPITest {

    DoublyLinkedList<String> strList;
    DoublyLinkedList<Integer> intList;

    @BeforeEach
    void setUp() {
        strList = new DoublyLinkedList<>();
        intList = new DoublyLinkedList<>();
    }

    @Test
    void testAddFirstString() {
        strList.addFirst("B");
        strList.addFirst("A");
        assertEquals("A", strList.getFirst());
        assertEquals(2, strList.size());
    }

    @Test
    void testAddLastString() {
        strList.addLast("A");
        strList.addLast("B");
        assertEquals("B", strList.getLast());
        assertEquals(2, strList.size());
    }

    @Test
    void testInsertAtString() {
        strList.addLast("A");
        strList.addLast("C");
        strList.insertAt(1, "B");
        assertEquals("B", strList.getAt(1));
    }

    @Test
    void testInsertAtOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> strList.insertAt(5, "X"));
    }

    @Test
    void testRemoveFirstString() {
        strList.addLast("A");
        strList.addLast("B");
        assertEquals("A", strList.removeFirst());
        assertEquals("B", strList.getFirst());
    }

    @Test
    void testRemoveFirstEmpty() {
        assertThrows(java.util.NoSuchElementException.class,
                () -> strList.removeFirst());
    }

    @Test
    void testRemoveLastString() {
        strList.addLast("A");
        strList.addLast("B");
        assertEquals("B", strList.removeLast());
        assertEquals("A", strList.getLast());
    }

    @Test
    void testRemoveLastEmpty() {
        assertThrows(java.util.NoSuchElementException.class,
                () -> strList.removeLast());
    }

    @Test
    void testRemoveAtString() {
        strList.addLast("A");
        strList.addLast("B");
        strList.addLast("C");
        assertEquals("B", strList.removeAt(1));
        assertEquals(2, strList.size());
    }

    @Test
    void testRemoveAtInvalidIndexString() {
        strList.addLast("A");
        assertThrows(IndexOutOfBoundsException.class,
                () -> strList.removeAt(5));
    }

    @Test
    void testGetFirstString() {
        strList.addLast("A");
        assertEquals("A", strList.getFirst());
    }

    @Test
    void testGetLastString() {
        strList.addLast("A");
        strList.addLast("B");
        assertEquals("B", strList.getLast());
    }

    @Test
    void testGetAtString() {
        strList.addLast("A");
        strList.addLast("B");
        assertEquals("B", strList.getAt(1));
    }

    @Test
    void testGetAtOutOfBoundsString() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> strList.getAt(0));
    }


    @Test
    void testAddIntegerOperations() {
        intList.addFirst(10);
        intList.addLast(20);
        intList.insertAt(1, 15);
        assertEquals(3, intList.size());
        assertEquals(10, intList.getFirst());
        assertEquals(20, intList.getLast());
        assertEquals(15, intList.getAt(1));
    }

    @Test
    void testRemoveIntegerOperations() {
        intList.addLast(5);
        intList.addLast(10);
        intList.addLast(15);

        assertEquals(5, intList.removeFirst());
        assertEquals(15, intList.removeLast());
        assertEquals(10, intList.removeAt(0));
        assertTrue(intList.isEmpty());
    }

    @Test
    void testContainsInteger() {
        intList.addLast(1);
        intList.addLast(2);
        assertTrue(intList.contains(1));
        assertFalse(intList.contains(3));
    }

    @Test
    void testIndexOfInteger() {
        intList.addLast(7);
        intList.addLast(8);
        intList.addLast(9);
        assertEquals(1, intList.indexOf(8));
        assertEquals(-1, intList.indexOf(100));
    }


    @Test
    void testPointerIntegrityAfterAddAndRemove() {
        strList.addLast("A");
        strList.addLast("B");
        strList.addLast("C");
        strList.removeAt(1); // remove "B"

        assertEquals("A", strList.getAt(0));
        assertEquals("C", strList.getAt(1));
        assertEquals(2, strList.size());

        assertEquals("A", strList.getFirst());
        assertEquals("C", strList.getLast());
    }

    @Test
    void testPointerIntegrityAfterClear() {
        strList.addLast("A");
        strList.addLast("B");
        strList.clear();

        assertTrue(strList.isEmpty());
        assertEquals(0, strList.size());
        assertThrows(java.util.NoSuchElementException.class, () -> strList.getFirst());
    }


    @Test
    void testSingleElementOperations() {
        strList.addLast("A");
        assertEquals("A", strList.getFirst());
        assertEquals("A", strList.getLast());

        assertEquals("A", strList.removeFirst());
        assertTrue(strList.isEmpty());
    }

    @Test
    void testOutOfBoundsOnEmptyList() {
        assertThrows(IndexOutOfBoundsException.class, () -> strList.getAt(0));
        assertThrows(IndexOutOfBoundsException.class, () -> strList.removeAt(0));
        assertThrows(IndexOutOfBoundsException.class, () -> strList.insertAt(1, "X"));
    }

    @Test
    void testAddNull() {
        strList.addLast(null);
        assertNull(strList.getFirst());
        assertTrue(strList.contains(null));
        assertEquals(0, strList.indexOf(null));
    }

    @Test
    void testMixedTypesIndependent() {
        strList.addLast("A");
        intList.addLast(100);

        assertEquals("A", strList.getFirst());
        assertEquals(100, intList.getFirst());
    }


    @Test
    void testClearRemovesAllNodes() {
        strList.addLast("A");
        strList.addLast("B");
        strList.addLast("C");
        strList.clear();

        assertTrue(strList.isEmpty());
        assertEquals(0, strList.size());
        assertEquals(-1, strList.indexOf("A"));
    }
}
