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

        /*
        {
            log("but this is");
            log("but this is");
            log("but this is");

            Logger.indent();
            Logger.unindent();
            Logger.indent();
            Logger.unindent();

            Logger.indent();


            log("but not this");

            Logger.unindent();

            log("but this is");
            log("but this is");

            Logger.printTo(writer);
        }

         */

         /*
        {
            Logger.unindent();
            Logger.indent();

            log("this is not indented");

            Logger.unindent();
            Logger.indent();
            Logger.unindent();

            log("but this is");
            log("so is this");


            log("but not this");
            Logger.printTo(writer);
        }
         */
        Logger.indent();
        log("first level 1");
        log("first level 1");
        log("first level 1");

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
