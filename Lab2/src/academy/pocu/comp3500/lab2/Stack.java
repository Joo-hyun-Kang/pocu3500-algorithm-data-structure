package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Stack {
    Node root;

    public Stack() {
    }

    public void push(final int data) {
        this.root = LinkedList.prepend(root, data);
    }

    public int peek() {
        return root.getData();
    }

    public int pop() {
        int pop = root.getData();
        this.root = LinkedList.removeAt(root, 0);

        return pop;
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
