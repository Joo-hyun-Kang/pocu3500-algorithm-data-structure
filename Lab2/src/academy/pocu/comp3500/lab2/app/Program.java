package academy.pocu.comp3500.lab2.app;

import academy.pocu.comp3500.lab2.LinkedList;
import academy.pocu.comp3500.lab2.datastructure.Node;
import academy.pocu.comp3500.lab2.Queue;
import academy.pocu.comp3500.lab2.Stack;

public class Program {

    public static void main(String[] args) {

        {
            //LinkedList.append Test
            Node root = null;
            root = LinkedList.append(root, 10);
            root = LinkedList.append(root, 20);
            root = LinkedList.append(root, 40);
            root = LinkedList.append(root, 80);
        }

        {
            //LinkdedList.insertAt Test
            Node root = LinkedList.append(null, 10);

            root = LinkedList.insertAt(root, 0, 11);

            assert (root.getData() == 11);

            root = LinkedList.insertAt(root, 0, 12);

            assert (root.getData() == 11);

            Node next = root.getNextOrNull();

            assert (next.getData() == 12);

            next = next.getNextOrNull();

            assert (next.getData() == 10);
        }
    }
}
