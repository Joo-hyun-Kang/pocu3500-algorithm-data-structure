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
            Node root = null;
            root = LinkedList.insertAt(root, 1, 11);
            root = LinkedList.insertAt(root, 0, 100);
            root = LinkedList.insertAt(root, 1, 12);
            root = LinkedList.insertAt(root, 2, 200);
            root = LinkedList.insertAt(root, 5, 200);
            root = LinkedList.insertAt(root, -1, 200);
        }

        {
            //LinkdedList.RemoveAt Test
            Node root = LinkedList.append(null, 10);

            root = LinkedList.append(root, 11);
            root = LinkedList.append(root, 12);
            root = LinkedList.append(root, 13);

            root = LinkedList.removeAt(root, 0);

            assert (root.getData() == 11);

            root = LinkedList.removeAt(root, 1);

            assert (root.getData() == 11);

            Node next = root.getNextOrNull();

            assert (next.getData() == 13);
        }
    }
}
