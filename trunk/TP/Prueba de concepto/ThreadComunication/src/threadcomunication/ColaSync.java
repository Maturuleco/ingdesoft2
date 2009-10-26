/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package threadcomunication;

import java.util.LinkedList;

/**
 *
 * @author tas
 */
public class ColaSync<T> {
    LinkedList<T> queue = new LinkedList<T>();

    // Add work to the work queue
    public synchronized void put(T object) {
        queue.addLast(object);
        notify();
    }

    // Retrieve work from the work queue; block if the queue is empty
    public synchronized T get() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ex) { }
        }
        return queue.removeFirst();
    }


}

