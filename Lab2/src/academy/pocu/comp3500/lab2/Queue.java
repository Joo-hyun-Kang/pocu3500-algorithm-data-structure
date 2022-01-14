package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public class Queue {
    Node root;

    public Queue() {
    }

    public void enqueue(final int data) {
        this.root = LinkedList.append(root, data);
    }

    public int peek() {
        return root.getData();
    }

    public int dequeue() {
        int result = root.getData();
        this.root = LinkedList.removeAt(root, 0);
        return result;
    }

    public int getSize() {
        int size = 0;
        Node tmp = root;

        while (tmp != null) {
            tmp = tmp.getNextOrNull();
            size++;
        }

        return size;
    }
}
