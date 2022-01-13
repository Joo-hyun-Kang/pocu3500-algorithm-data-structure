package academy.pocu.comp3500.lab2.app;

import academy.pocu.comp3500.lab2.LinkedList;
import academy.pocu.comp3500.lab2.datastructure.Node;
import academy.pocu.comp3500.lab2.Queue;
import academy.pocu.comp3500.lab2.Stack;

import java.io.LineNumberReader;

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

        {
            //LinkedList.getIndexof
            Node root = null;

            int index = LinkedList.getIndexOf(root, 10);
            assert (index == -1);

            root = LinkedList.append(root, 10);
            root = LinkedList.append(root, 20);
            root = LinkedList.append(root, 30);

            int index1 = LinkedList.getIndexOf(root, 10);
            int index2 = LinkedList.getIndexOf(root, 20);
            int index3 = LinkedList.getIndexOf(root, 30);
            int index4 = LinkedList.getIndexOf(root, 40);
            assert (index1 == 0);
            assert (index2 == 1);
            assert (index3 == 2);
            assert (index4 == -1);
        }

        {
            //LinkedList.getOrNull
            Node root = LinkedList.append(null, 10);

            root = LinkedList.append(root, 11);

            Node node = LinkedList.getOrNull(root, 0);

            assert (node.getData() == 10);

            node = LinkedList.getOrNull(root, 1);

            assert (node.getData() == 11);
        }

        {
            //LinkedList.reverse
            Node root = LinkedList.append(null, 10);

            root = LinkedList.append(root, 11);
            root = LinkedList.append(root, 12);
            root = LinkedList.append(root, 13);
            root = LinkedList.append(root, 14);

            root = LinkedList.reverse(root); // root: 14, list: 14 -> 13 -> 12 -> 11 -> 10

            assert (root.getData() == 14);

            Node next = root.getNextOrNull();

            assert (next.getData() == 13);

            next = next.getNextOrNull();

            assert (next.getData() == 12);

            next = next.getNextOrNull();

            assert (next.getData() == 11);

            next = next.getNextOrNull();

            assert (next.getData() == 10);
        }


    }
}
