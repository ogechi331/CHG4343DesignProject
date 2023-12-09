/**
 * Implementation of a generic queue using a singly linked list.
 *
 * @param <E> the type of elements in the queue.
 * @author Ogechi
 */
public class Queue<E>{

    // queue instance should not be accessed. You are forced to access each element in order by peeking or dequeue
    private SinglyLinkedList<E> queue;

    /**
     * Creates a new empty queue.
     * @author Ogechi
     */
    public Queue(){
        this.queue = new SinglyLinkedList<>();
    }

    /**
     * Adds an item to the end of the queue.
     *
     * @param item the item to be added to the queue.
     * @author Ogechi
     */
    public void enqueue(E item){
        this.queue.addLast(item);
    }

    /**
     * Removes and returns the item from the front of the queue.
     *
     * @return the item removed from the front of the queue.
     * @throws NullPointerException if the queue is empty.
     * @author Ogechi
     */
    public E dequeue(){
        if(queue.isEmpty()) throw new NullPointerException();
        E elem = queue.getFirst().getElement();
        queue.deleteAt(0);
        return elem;
    }

    /**
     * Retrieves, but does not remove, the item from the front of the queue.
     *
     * @return the item at the front of the queue.
     * @author Ogechi
     */
    public E peek(){
        return this.queue.getFirst().getElement();
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue is empty, false otherwise.
     * @author Ogechi
     */
    public boolean isEmpty(){return queue.isEmpty();}

    /** Copy constructor for queue
     *
     * @param source source queue to copy
     * @author Ogechi
     */
    public Queue(Queue<E> source){
        this.queue = new SinglyLinkedList<>(source.queue);
    }
    public Queue<E> clone(){
        return new Queue<E>(this);
    }

    /** Equals method for queue
     *
     * @param comparator queue to compare
     * @return true if equal, false if not
     * @author Ogechi
     */
    @Override
    public boolean equals(Object comparator) {
        if (comparator == null || comparator.getClass() != this.getClass()) return false;
        try {
            return this.queue.equals(((Queue<?>) comparator).queue);
        }
        catch (ClassCastException e){
            return false;
        }
    }

}
