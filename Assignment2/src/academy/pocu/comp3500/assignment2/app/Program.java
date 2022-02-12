package academy.pocu.comp3500.assignment2.app;

import academy.pocu.comp3500.assignment2.Indent;
import academy.pocu.comp3500.assignment2.Logger;
import academy.pocu.comp3500.assignment2.datastructure.Stack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static academy.pocu.comp3500.assignment2.Logger.*;


public class Program {

    public static void main(String[] args) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("mylog1.log"));

        log("first level 1");

        Logger.indent();
        {
            log("second level 1");
            log("second level 2");

            doMagic();

            log("second level 3");
        }

        Logger.unindent();

        log("first level 2");


        log("first level 1");

        Logger.indent();
        {
            log("second level 1");
            log("second level 2");

            doMagic();

            log("second level 3");
        }

        Logger.unindent();

        log("first level 2");

        Logger.printTo(writer);

    }

    public static void doMagic() {
        Logger.indent();
        {
            log("third level 1");
            log("third level 2");
        }
        Logger.unindent();
    }
}
