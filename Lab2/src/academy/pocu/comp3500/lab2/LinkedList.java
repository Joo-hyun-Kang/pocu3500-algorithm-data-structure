package academy.pocu.comp3500.lab2;

import academy.pocu.comp3500.lab2.datastructure.Node;

public final class LinkedList {
    private LinkedList() {
    }

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

        // root - node - node
        // front   back

        Node front = null;
        Node back = rootOrNull;

        int i = 0;
        while (back != null && i < index) {
            front = back;
            back = back.getNextOrNull();
            i++;
        }
        //case
        // back == null i < index -> no..
        // back != null i == index ok
        // back == null i == index no

        // what happen when front == null
        // root

        // root is not null but i = 0
        // root - node - node
        // back
        //

        // root is not null but i = 1
        // root  - null
        // front - back

        if (back != null) {
            if (front == null) {
                return back.getNextOrNull();
            }

            front.setNext(back.getNextOrNull());
        }

        return rootOrNull;
    }

    public static int getIndexOf(final Node rootOrNull, final int data) {
        if (rootOrNull == null) {
            return -1;
        }

        Node tmp = rootOrNull;
        int index = 0;

        while (tmp != null) {
            if (tmp.getData() == data) {
                break;
            }
            tmp = tmp.getNextOrNull();
            index++;
        }

        return tmp != null ? index : -1;
    }

    public static Node getOrNull(final Node rootOrNull, final int index) {
        if (index < 0) {
            return null;
        }

        Node tmp = rootOrNull;
        int i = 0;
        while (tmp != null && i < index) {
            tmp = tmp.getNextOrNull();
            i++;
        }
        // root node 1 ok ret node
        // root null 1 ok ret null
        // root null 2 ok ret null
        // root node node
        return tmp;
    }

    public static Node reverse(final Node rootOrNull) {
        return null;
    }

    public static Node interleaveOrNull(final Node root0OrNull, final Node root1OrNull) {
        return null;
    }
}
