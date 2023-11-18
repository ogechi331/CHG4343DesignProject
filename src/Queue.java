public class Queue<E>{

    // queue instance should not be accessed. You are forced to access each element in order by peeking or dequeue
    private SinglyLinkedList<E> queue;
    public Queue(){
        this.queue = new SinglyLinkedList<>();
    }
    public void enqueue(E item){
        this.queue.addLast(item);
    }

    public E dequeue(){
        if(queue.isEmpty()) throw new NullPointerException();
        return queue.getFirst().getElement();
    }

    public E peek(){
        return this.queue.getFirst().getElement();
    }




}
