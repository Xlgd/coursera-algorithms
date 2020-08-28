import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private class Node {
        private Item item;
        private Node next;
    }

    private Node first, last;
    private int queueSize;

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
        last = null;
        queueSize = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return queueSize;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item isn't a legal argument");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        queueSize++;
        if (isEmpty()) {
            first = last;
        }
        else oldLast.next = last;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("queue is empty");
        }
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        queueSize--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("queue is empty");
        }
        int randomPosition = StdRandom.uniform(size());
        Node preNode;
        Node oldFirst;
        Item item;
        if (randomPosition == 0) {
            item = dequeue();
        }
        else {
            preNode = first;
            oldFirst = first;
            first = first.next;
            int i = 1;
            while (i < randomPosition) {
                preNode = first;
                first = first.next;
                i++;
            }
            item = first.item;
            preNode.next = first.next;
            first.next = null;
            first = oldFirst;
        }
        queueSize--;
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("queue is empty");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("please not use remove method");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> testQueue = new RandomizedQueue<Integer>();
        int item;
        System.out.println("is empty: " + testQueue.isEmpty());
        System.out.println("add item 0");
        testQueue.enqueue(0);
        System.out.println("add item 1");
        testQueue.enqueue(1);
        System.out.println("is empty: " + testQueue.isEmpty());
        System.out.println("queue items number: " + testQueue.size());
        System.out.println("add item 2");
        testQueue.enqueue(2);
        System.out.println("deque items number: " + testQueue.size());
        for (int i : testQueue) {
            System.out.println(i);
        }
        item = testQueue.dequeue();
        System.out.println("remove first item is: " + item);
        System.out.println("add item 3");
        testQueue.enqueue(3);
        System.out.println("add item 4");
        testQueue.enqueue(4);
        for (int i : testQueue) {
            System.out.println(i);
        }
        item = testQueue.sample();
        System.out.println("remove random item is: " + item);
        System.out.println("deque items number: " + testQueue.size());
        for (int i : testQueue) {
            System.out.println(i);
        }
    }
}
