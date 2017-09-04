/**
 * <h1>Fall 2017 Short Project 2-5</h1>
 * <p>
 * Implement array-based, bounded-sized queues, that support the following
 * operations: offer, poll, peek, isEmpty (same behavior as in Java's Queue
 * interface).  In addition, implement the method resize(), which doubles
 * the queue size if the queue is mostly full (over 90%, say), or halves it
 * if the queue is mostly empty (less then 25% occupied, say).  Let the
 * queue have a minimum size of 16, at all times.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.1
 * @since 2017-09-03
 */
package cs6301.g16;


public class ArrayQueue<T> {
    /**
     * Implement array-based, bounded-sized queues, and its offer, poll, peek, isEmpty, resize operations.
     * @param <T> : The element type in the array.
     * @param arr : Array to be stored.
     * @param minSize : The queue's minimum size.
     * @param fullBoundary : The boundary need to double size.
     * @param emptyBoundary : The boundary need to halves size.
     * @param corrocc : Current ocupied of array.
     * @param front : The index of queue's head.
     * @param rear :  The index of queue's tail.
     *
     */
    private static final int minSize = 16;
    private static final double fullBoundary = 0.9;
    private static final double emptyBoundary = 0.25;
    private T[] array = (T[]) new Object[minSize];
    private int currocc=0;
    private int front=0;
    private int rear=0;

    /**
     * Resize the array when reach full or empty boundary, and make sure array size should over 16 at all times.
     * Double size when occupied over fullBoundary, halves size when occupied less than emptyBoundary.
     */
    private  void resize(){
        if(currocc > array.length * fullBoundary){   //resize when over fullBoundary occupied
            T[] items = (T[])new Object[array.length*2];

            for(int i=0;i<currocc;i++){
                items[i]=array[(i+front)%array.length];
            }
            array = items;
            items = null;
            front = 0;
            rear = currocc;
        }
        if(array.length>16 && currocc < array.length * emptyBoundary){   //resize when less than emptyBoundary occupied
            T[] items;
            if(array.length/2 >= minSize){
                items = (T[])new Object[array.length/2];
                for(int i=0;i<currocc;i++){
                    items[i] = array[(i+front)%array.length];
                }
                array = items;
                items = null;
                front = 0;
                rear = currocc;
            }
        }
    }

    /**
     * Compute array's total space.
     */
    private int size(){    //the array size
        return array.length;
    }

    /**
     * Add element to array. When front reach the end of array, it will change to 0 and add from the beginning of array,like a circle.
     * @param element : Object should be added to array.
     */
    private boolean offer(T element){

        rear = (front+currocc)%array.length;
        array[rear++] =element;
        currocc++;
        resize();
        return true;
    }

    /**
     * Delete element from array. If array become empty after poll element, let next element store from the beginning of array.
     */
    private T poll(){
        if(currocc == 0){   // Poll empty.
            front=0;
            rear=0;
            return null;
        }
        T item= array[front];
        array[front]= null;
        front = (front+1)%array.length;
        currocc--;
        if(currocc == 0){   // Poll last element.
            front=0;
            rear=0;
        }
        resize();
        return item;
    }

    /**
     * Get the object on the queue's front.
     */
    private T peek(){
        return array[front];
    }

    /**
     * Determine the array is empty or not.
     */
    private  boolean isEmpty(){
        return (front==rear);
    }

    /**
     * Get the element on array[index]
     * @param index : The element's position need to get.
     */
    private T get(int index){
        return array[index];
    }

    /**
     * Main function to test.
     */
    public static void main(String[] args){
        ArrayQueue<Integer> queue = new ArrayQueue<Integer>();
        System.out.println("-------------- Test offer/poll/peek/isEmpty --------------");
        System.out.println(queue.offer(-1));
        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.isEmpty());
        System.out.println("-------------- Initial Queue --------------");
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.poll();
        queue.poll();
        queue.poll();
        for(int i=5;i<=17;i++)
            queue.offer(i);
        for(int i=0; i<queue.size();i++)
            System.out.print(queue.get(i)+" ");
        System.out.println();
        System.out.println("Original size: "+queue.size());
        System.out.println("-------------- Test Double Size --------------");
        queue.offer(18);
        for(int i=0; i<queue.size();i++)
            System.out.print(queue.get(i)+" ");
        System.out.println();
        System.out.println("New size: "+queue.size());
        System.out.println("-------------- Test Halve Size ---------------");
        for(int i=0;i<8;i++)
            queue.poll();
        for(int i=0; i<queue.size();i++)
            System.out.print(queue.get(i)+" ");
        System.out.println();
        System.out.println("New size: "+queue.size());
    }

}