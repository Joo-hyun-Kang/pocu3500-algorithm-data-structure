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
        //경계 여기임 index가 유효하지 않으면 아무일도 일어나지 않음
        //더블 포인터 없음

        if (index < 0) {
            return rootOrNull;
        }

        if (rootOrNull == null) {
            return LinkedList.append(rootOrNull, data);
        }

        assert (index >= 0);
        assert (rootOrNull != null);
        //index가 유효하지 않은 경우
        // F T => 무효 tmp == null, count >= 0
        // F F = > 유효
        // T T => 돈다
        // T F => 유효
        Node frontNode = rootOrNull;
        Node backNode = rootOrNull;

        // root 0 ok
        // root -> node 1 ok
        // root -> node 2 ?
        // root -> node 3 no
        int i = 0;
        for (; i < index; i++) {
            if (backNode == null) {
                break;
            }
            frontNode = backNode;
            backNode = backNode.getNextOrNull();
        }

        assert (backNode != null);

        if (backNode != null && i == index) {
            frontNode.setNext(new Node(data));
            Node insertedNode = frontNode.getNextOrNull();
            insertedNode.setNext(backNode);
        }

        return rootOrNull;
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
