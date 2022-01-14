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

        if (rootOrNull == null && index == 0) {
            return LinkedList.prepend(rootOrNull, data);
        }

        Node frontNode = rootOrNull;

        int i = 1;
        while (frontNode != null && i < index) {
            frontNode = frontNode.getNextOrNull();
            i++;
        }

        if (frontNode != null && i == index) {
            Node tmp = frontNode.getNextOrNull();
            frontNode.setNext(new Node(data));
            frontNode.getNextOrNull().setNext(tmp);
        }

        return rootOrNull;
    }

    public static Node removeAt(final Node rootOrNull, final int index) {
        if (index < 0) {
            return rootOrNull;
        }

        if (rootOrNull != null && index == 0) {
            Node result = rootOrNull.getNextOrNull();
            rootOrNull.setNext(null);
            return result;
        }

        Node frontNode = rootOrNull;

        int i = 1;
        while (frontNode != null && i < index) {
            frontNode = frontNode.getNextOrNull();
            i++;
        }

        if (frontNode != null && i == index) {
            Node removeAtNode = frontNode.getNextOrNull();

            if (removeAtNode != null) {
                frontNode.setNext(removeAtNode.getNextOrNull());
                removeAtNode.setNext(null);
            }
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

        return tmp;
    }

    public static Node reverse(final Node rootOrNull) {
        if (rootOrNull == null) {
            return rootOrNull;
        }

        Node front = rootOrNull;
        Node back = rootOrNull.getNextOrNull();

        while (back != null) {
            Node tmp = back.getNextOrNull();
            back.setNext(front);
            front = back;
            back = tmp;
        }

        /* Now rootOrNull is last node */
        rootOrNull.setNext(null);
        return front;
    }

    public static Node interleaveOrNull(final Node root0OrNull, final Node root1OrNull) {
        Node p0 = root0OrNull;
        Node p1 = root1OrNull;

        while (p0 != null || p1 != null) {
            p0 = switchRootBranch(p0, p1);
            p1 = switchRootBranch(p1, p0);
        }

        return root0OrNull != null ? root0OrNull : root1OrNull;
    }

    private static Node switchRootBranch(Node root, Node newBranch) {
        if (root == null) {
            return root;
        }

        Node oldBranch = root.getNextOrNull();

        if (newBranch != null) {
            root.setNext(newBranch);
        }

        return oldBranch;
    }
}