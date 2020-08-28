import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item item;
        private Node next;
        private Node pre;
    }

    private Node first;
    private Node last;
    private int dequeSize;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        dequeSize = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return dequeSize;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item isn't a legal argument");
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.pre = null;

        if (last == null) {
            last = first;
        }
        else {
            oldFirst.pre = first;
        }
        dequeSize++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item isn't a legal argument");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.pre = oldLast;
        last.next = null;

        if (first == null) {
            first = last;
        }
        else {
            oldLast.next = last;
        }
        dequeSize++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("deque is empty");
        }
        Item item = first.item;
        if (first.equals(last)) {
            first = null;
            last = null;
        }
        else {
            first.next.pre = null;
            first = first.next;
        }
        dequeSize--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("deque is empty");
        }
        Item temp = last.item;
        if (first.equals(last)) {
            first = null;
            last = null;
        }
        else {
            last.pre.next = null;
            last = last.pre;

        }
        dequeSize--;
        return temp;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("deque is empty");
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
        Deque<Integer> testDeque = new Deque<Integer>();
        int item;
        System.out.println("is empty: " + testDeque.isEmpty());
        System.out.println("add first item 0");
        testDeque.addFirst(0);
        System.out.println("add last item 1");
        testDeque.addLast(1);
        System.out.println("is empty: " + testDeque.isEmpty());
        System.out.println("deque items number: " + testDeque.size());
        System.out.println("add first item -1");
        testDeque.addFirst(-1);
        System.out.println("add last item 2");
        testDeque.addLast(2);
        System.out.println("deque items number: " + testDeque.size());
        for (int i : testDeque) {
            System.out.println(i);
        }
        item = testDeque.removeFirst();
        System.out.println("remove first item is: " + item);
        item = testDeque.removeLast();
        System.out.println("remove last item is: " + item);
        System.out.println("deque items number: " + testDeque.size());

        for (int i : testDeque) {
            System.out.println(i);
        }
    }
}
