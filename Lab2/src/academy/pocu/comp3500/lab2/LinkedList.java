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

        Node front = null;
        Node back = rootOrNull;

        int i = 0;
        while (back != null && i < index) {
            front = back;
            back = back.getNextOrNull();
            i++;
        }

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

        assert (front != null);
        return front;
    }

    public static Node interleaveOrNull(final Node root0OrNull, final Node root1OrNull) {

        /*
            root1 - node1 - node1
            tmp

            root2 - node2 - node2
            tmp

            1. root1 뒤에 root2를 연결한다
            2. root2 뒤에 node1을 연결한다
            즉, tmp를 이용한다
         */
        Node p0 = root0OrNull;
        Node p1 = root1OrNull;
        Node tmp;

        // root1 - node
        // root2
        assert (p0 != null && p1 != null);

        while (p0 != null || p1 != null) {
            // 함수로 빼셈
            if (p0 != null && p1 != null) {
                tmp = p0.getNextOrNull();
                p0.setNext(p1);
                p0 = tmp;
            }

            if (p0 != null && p1 != null) {
                tmp = p1.getNextOrNull();
                p1.setNext(p0);
                p1 = tmp;
            }
        }

        return root0OrNull != null ? root0OrNull : root1OrNull;
    }
}
