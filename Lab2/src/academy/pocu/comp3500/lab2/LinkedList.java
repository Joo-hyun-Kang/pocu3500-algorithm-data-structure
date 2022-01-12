package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class LinkedList {
    private LinkedList() { }

    public static Node append(final Node rootOrNull, final int data) {
        Node newRoot = rootOrNull;

        if (newRoot == null) {
            newRoot = new Node(data);
            return newRoot;
        }

        Node tmp = newRoot;
        while (tmp.getNextOrNull() != null) {
            tmp = tmp.getNextOrNull();
        }

        assert (tmp != null);
        assert (tmp.getNextOrNull() == null);

        tmp.setNext(new Node(data));

        return newRoot;
    }

    public static Node prepend(final Node rootOrNull, final int data) {
        Node newRoot = new Node(data);
        newRoot.setNext(rootOrNull);

        return newRoot;
    }

    public static Node insertAt(final Node rootOrNull, final int index, final int data) {
        if (index < 0) {
            return rootOrNull;
        }

        Node frontNode = null;
        Node backNode = rootOrNull;

        int i = 0;
        while (backNode != null && i < index) {
            frontNode = backNode;
            backNode = backNode.getNextOrNull();
            i++;
        }

        if (i == index) {
            if (frontNode == null) {
                return LinkedList.prepend(rootOrNull, data);
            }

            Node insertedNode = prepend(backNode, data);
            frontNode.setNext(insertedNode);
        }

        return rootOrNull;
    }

    public static Node removeAt(final Node rootOrNull, final int index) {
        if (index < 0) {
            return rootOrNull;
        }

        //

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
