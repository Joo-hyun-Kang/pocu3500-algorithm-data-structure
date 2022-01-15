package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public class Queue {
    Node front;
    Node back;
    int size;

    public Queue() {
    }

    public void enqueue(final int data) {
        if (size == 0) {
            back = new Node(data);
            front = back;
        } else {
            back.setNext(new Node(data));
            back = back.getNextOrNull();
        }
        size++;
    }

    public int peek() {
        return front.getData();
    }

    public int dequeue() {
        int result = front.getData();
        front = LinkedList.removeAt(front, 0);

        size--;
        if (size == 0) {
            back = null;
        }

        return result;
    }

    public int getSize() {
        return this.size;
    }
}
