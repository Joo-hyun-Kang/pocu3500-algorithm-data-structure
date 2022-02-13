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

        /*
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog1.log"));

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



        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog1.log"));


            Logger.indent();
            Logger.unindent();

            log("this is not indented");
            log("this is not indented");

            Logger.unindent();
            Logger.indent();
            Logger.unindent();
            Logger.indent();

            log("but this is");
            log("so is this");


            log("but not this");

            Logger.printTo(writer);
        }


        /*
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog1.log"));

            Logger.unindent();

            Logger.indent();

            log("first level 1");


            Logger.printTo(writer);
        }

         */

        /*
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog1.log"));

            int x = 10;

            log("first level 1");

            Indent indent = Logger.indent();
            {
                log("second level 1");
                log("second level 2");

                if (x % 2 == 0) {
                    indent.discard();
                }
            }
            Logger.unindent();

            log("first level 2");
            Logger.printTo(writer);
        }


         */
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
