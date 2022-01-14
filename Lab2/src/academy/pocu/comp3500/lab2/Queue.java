package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public class Queue {
    Node root;
    int size;

    public Queue() {
    }

    public void enqueue(final int data) {
        this.root = LinkedList.prepend(root, data);
        size++;
    }

    public int peek() {
        return LinkedList.getOrNull(root, size - 1).getData();
    }

    public int dequeue() {
        int result = LinkedList.getOrNull(root, size - 1).getData();

        this.root = LinkedList.removeAt(root, size - 1);

        size--;

        return result;
    }

    public int getSize() {
        return this.size;
    }
}
