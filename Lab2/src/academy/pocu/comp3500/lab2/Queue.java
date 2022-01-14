package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public class Queue {
    Node root;
    int size;

    public Queue() {
    }

    public void enqueue(final int data) {
        this.root = LinkedList.append(root, data);
        size++;
    }

    public int peek() {
        return root.getData();
    }

    public int dequeue() {
        int result = root.getData();
        this.root = LinkedList.removeAt(root, 0);
        this.size--;
        return result;
    }

    public int getSize() {
        return this.size;
    }
}
