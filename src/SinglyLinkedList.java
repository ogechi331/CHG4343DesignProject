//Status: Complete

/**
 * Implementation of a Generic singly linked list.
 *
 * @param <E> the type of elements in the linked list.
 * @author Ogechi
 */
public class SinglyLinkedList <E> {

    /**
     * Represents a node in the linked list.
     *
     * @param <E> the type of element in the node.
     * @author Ogechi
     */
    public static class Node<E> {
        private E element;
        private Node<E> next;

        /**
         * Creates a new node with null element and next node.
         * @author Ogechi
         */
        Node() {
            this(null, null);
        }

        /**
         * Creates a new node with the given element and next node.
         *
         * @param e     the element of the node.
         * @param next  the next node in the linked list.
         * @author Ogechi
         */
        Node(E e, Node<E> next) {
            this.element = e;
            this.next = next;
        }

        /**
         * Sets the element of the node.
         *
         * @param newElem the new element to be set.
         * @author Ogechi
         */
        public void setElement(E newElem) {
            element = newElem;
        }

        /**
         * Sets the next node in the linked list.
         *
         * @param newNext the new next node.
         * @author Ogechi
         */
        public void setNext(Node<E> newNext) {
            next = newNext;
        }

        /**
         * Gets the element of the node.
         *
         * @return the element of the node.
         * @author Ogechi
         */
        public E getElement() {
            return element;
        }

        /**
         * Gets the next node in the linked list.
         *
         * @return the next node.
         * @author Ogechi
         */
        public Node<E> getNext() {
            return next;
        }
    }

    int size;
    Node<E> head;

    /**
     * Creates an empty singly linked list.
     * @author Ogechi
     */
    SinglyLinkedList() {
        head = null;
        size = 0;
    }

    /**
     * Checks if the linked list is empty.
     *
     * @return true if the linked list is empty, false otherwise.
     * @author Ogechi
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Prints the elements of the linked list.
     * @author Ogechi
     */
    public void print() {
        if (size == 0) {
            System.out.println("Empty linked list");
            return;
        }
        Node<E> current = head;
        int counter = 0;
        while (current != null) {
            System.out.println("Node " + counter + " with content " + current.getElement());
            counter++;
            current = current.getNext();
        }
    }

    /**
     * Adds a new element to the end of the linked list.
     *
     * @param e the element to be added.
     * @author Ogechi
     */
    public void add(E e) {
        Node<E> newNode = new Node<E>(e, null);
        Node<E> current = head;
        if (head == null) {
            head = newNode;
            size++;
            return;
        }
        while (current.getNext() != null) {
            current = current.getNext();
        }
        current.setNext(newNode);
        size++;
    }

    /**
     * Adds a new element to the end of the linked list (alias for add method).
     *
     * @param e the element to be added.
     * @author Ogechi
     */
    public void addLast(E e) {
        add(e);
    }

    /**
     * Adds a new element to the beginning of the linked list.
     *
     * @param e the element to be added.
     * @author Ogechi
     */
    public void addFirst(E e) {
        Node<E> newNode = new Node<E>(e, head);
        head = newNode;
        size++;
    }

    /**
     * Gets the first node in the linked list.
     *
     * @return the first node.
     * @author Ogechi
     */
    public Node<E> getFirst() {
        return getAt(0);
    }

    /**
     * Gets the last node in the linked list.
     *
     * @return the last node.
     * @author Ogechi
     */
    public Node<E> getLast() {
        return getAt((int) this.size - 1);
    }

    /**
     * Gets the node at the specified index.
     *
     * @param i the index.
     * @return the node at the specified index or null if the index is out of bounds.
     * @author Ogechi
     */
    public Node<E> getAt(int i) {
        if (i < 0 || i > this.size - 1) {
            return null;
        }
        Node<E> current = head;
        int counter = 0;
        while (counter != i) {
            current = current.getNext();
            counter++;
        }
        return current;
    }

    /**
     * Sets the element at the specified index.
     *
     * @param i the index.
     * @param e the new element.
     * @return true if the operation is successful, false otherwise.
     * @author Ogechi
     */
    public boolean setAt(int i, E e) {
        if (i < 0 || i > this.size - 1) {
            return false;
        }
        Node<E> current = head;
        int counter = 0;
        while (counter != i) {
            current = current.getNext();
            counter++;
        }
        current.setElement(e);
        return true;
    }

    /**
     * Deletes the node at the specified index.
     *
     * @param i the index.
     * @return true if the operation is successful, false otherwise.
     * @author Ogechi
     */
    public boolean deleteAt(int i) {
        if (i < 0 || i >= this.size) {
            return false;
        }
        if (i == 0) {
            head = head.getNext();
        } else {
            Node<E> current = head;
            int counter = 0;
            while (counter != i - 1 && current != null) {
                current = current.getNext();
                counter++;
            }
            current.setNext(current.getNext().getNext());
        }
        size--;
        return true;
    }

    /**
     * Removes the first node in the linked list.
     *
     * @return true if the operation is successful, false otherwise.
     * @author Ogechi
     */
    public boolean removeFirst() {
        return deleteAt(0);
    }

    /**
     * Removes the last node in the linked list.
     *
     * @return true if the operation is successful, false otherwise.
     * @author Ogechi
     */
    public boolean removeLast() {
        return deleteAt(this.size - 1);
    }



}