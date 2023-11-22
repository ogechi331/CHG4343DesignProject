//Status: Complete

/**
 * Implementation of a generic queue using a singly linked list.
 *
 * @param <E> the type of elements in the queue.
 */
public class Queue<E>{

    // queue instance should not be accessed. You are forced to access each element in order by peeking or dequeue
    private SinglyLinkedList<E> queue;

    /**
     * Creates a new empty queue.
     */
    public Queue(){
        this.queue = new SinglyLinkedList<>();
    }

    /**
     * Adds an item to the end of the queue.
     *
     * @param item the item to be added to the queue.
     */
    public void enqueue(E item){
        this.queue.addLast(item);
    }

    /**
     * Removes and returns the item from the front of the queue.
     *
     * @return the item removed from the front of the queue.
     * @throws NullPointerException if the queue is empty.
     */
    public E dequeue(){
        if(queue.isEmpty()) throw new NullPointerException();
        return queue.getFirst().getElement();
    }

    /**
     * Retrieves, but does not remove, the item from the front of the queue.
     *
     * @return the item at the front of the queue.
     */
    public E peek(){
        return this.queue.getFirst().getElement();
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue is empty, false otherwise.
     */
    public boolean isEmpty(){return queue.isEmpty();}

}
