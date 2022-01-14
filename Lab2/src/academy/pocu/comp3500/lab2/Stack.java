package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class Stack {
    Node root;
    int size;

    public Stack() {
    }

    public void push(final int data) {
        this.root = LinkedList.prepend(root, data);
        this.size++;
    }

    public int peek() {
        return root.getData();
    }

    public int pop() {
        int pop = root.getData();
        this.root = LinkedList.removeAt(root, 0);
        this.size--;
        return pop;
    }

    public int getSize() {
        return this.size;
    }
}
