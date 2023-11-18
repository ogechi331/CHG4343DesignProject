
public class SinglyLinkedList <E> {
    public static class Node<E> {
        private E element;
        private Node<E> next;

        Node() {
            this(null, null);
        }

        Node(E e, Node<E> next) {
            this.element = e;
            this.next = next;
        }


        public void setElement(E newElem) {
            element = newElem;
        }

        public void setNext(Node<E> newNext) {
            next = newNext;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getNext() {
            return next;
        }
    }

    int size;
    Node<E> head;

    SinglyLinkedList() {
        head = null;
        size = 0;
    }
    public boolean isEmpty(){
        return size == 0;
    }

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

    public void addLast(E e) {
        add(e);
    }

    public void addFirst(E e) {
        Node<E> newNode = new Node<E>(e, head);
        head = newNode;
        size++;
    }

    public Node<E> getFirst() {
        return getAt(0);
    }

    public Node<E> getLast() {
        return getAt((int) this.size - 1);
    }

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

    public boolean deleteAt(int i) {
        if (i < 0 || i > this.size - 1) {
            return false;
        }
        Node<E> current = head;
        int counter = 0;
        while (counter != i - 1) {
            current = current.getNext();
            counter++;
        }
        if (i == this.size-1) {
            current.setNext(null);
        }
        else {
            current.setNext(current.getNext().getNext());
        }
        size--;
        return true;
    }

    public boolean removeFirst() {
        return deleteAt(0);
    }

    public boolean removeLast() {
        return deleteAt(this.size - 1);
    }



}