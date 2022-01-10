package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class LinkedList {
    private LinkedList() { }

    public static Node append(final Node rootOrNull, final int data) {
        Node tmp = rootOrNull;

        while (tmp != null) {
            tmp = tmp.getNextOrNull();
        }

        assert (tmp == null);
        tmp = new Node(data);

        assert (rootOrNull != null);
        return rootOrNull;
    }

    public static Node prepend(final Node rootOrNull, final int data) {
        Node newRoot = new Node(data);
        newRoot.setNext(rootOrNull);

        return newRoot;
    }

    public static Node insertAt(final Node rootOrNull, final int index, final int data) {
        return null;
    }

    public static Node removeAt(final Node rootOrNull, final int index) {
        return null;
    }

    public static int getIndexOf(final Node rootOrNull, final int data) {
        return -1;
    }

    public static Node getOrNull(final Node rootOrNull, final int index) {
        return null;
    }

    public static Node reverse(final Node rootOrNull) {
        return null;
    }

    public static Node interleaveOrNull(final Node root0OrNull, final Node root1OrNull) {
        return null;
    }
}
