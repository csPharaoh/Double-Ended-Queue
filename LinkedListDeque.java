public class LinkedListDeque<T> {

    private class IntNode {

        private T item;
        private IntNode next;
        private IntNode previous;

        IntNode(IntNode p, T i, IntNode n) {
            item = i;
            next = n;
            previous = p;
        }
        
    }

    private IntNode sentinel;
    private int size;


    public LinkedListDeque(LinkedListDeque other) {
        size = 0;
        sentinel = new IntNode(null, null, null);

        int i = other.size - 1;

        while (i >= 0) {
            this.addFirst((T) other.get(i));
            i = i - 1;
        }

    }

    public LinkedListDeque() {
        sentinel = new IntNode(null, null, null);
        size = 0;
    }

    public void addFirst(T x) {

        IntNode temp;

        if (size == 0) {
            temp = new IntNode(sentinel, x, sentinel);
            sentinel.next = temp;
            sentinel.previous = temp;
        } else {
            temp = new IntNode(sentinel, x, sentinel.next);
            sentinel.next.previous = temp;
            sentinel.next = temp;
        }

        size += 1;
    }

    public void addLast(T x) {
        IntNode temp;

        if (size == 0) {
            addFirst(x);
        } else {
            temp = new IntNode(sentinel.previous, x, sentinel);
            sentinel.previous.next = temp;
            sentinel.previous = temp;
            size += 1;
        }


    }

    public T get(int index) {
        IntNode cursor = this.sentinel.next;
        while (index > 0) {
            cursor = cursor.next;
            index = index - 1;
        }
        return cursor.item;
    }


    public boolean isEmpty() {

        if (size == 0) {
            return true;
        } 
        return false;
        

    }

    public void printDeque() {
        IntNode cursor = this.sentinel.next;
        while (cursor != sentinel) {
            System.out.print(cursor.item + " ");
            cursor = cursor.next;
        }
        System.out.println("");

    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        T temp = null;
        if (size == 1) {
            temp = this.sentinel.next.item;
            this.sentinel.next = null;
            this.sentinel.previous = null;

        } else {
            temp = this.sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.previous = sentinel;
        }
        size = size - 1;
        return temp;
    }

    public T removeLast() {

        if (isEmpty()) {
            return null;
        }

        T temp = null;

        if (size == 1) {
            return removeFirst();
        } else {
            temp = this.sentinel.previous.item;
            sentinel.previous.previous.next = sentinel;
            sentinel.previous = sentinel.previous.previous;
        }
        size = size - 1;
        return temp;
    }

    public T getRecursive(int index) {

        return getHelper(this.sentinel.next, index);

    }

    private T getHelper(IntNode p, int index) {

        if (index == 0) {
            return p.item;
        }

        return getHelper(p.next, index - 1);

    }

    public int size() {
        return size;
    }

}
