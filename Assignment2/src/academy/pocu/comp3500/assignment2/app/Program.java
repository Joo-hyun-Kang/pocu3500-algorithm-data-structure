package academy.pocu.comp3500.assignment2.app;

import academy.pocu.comp3500.assignment2.Indent;
import academy.pocu.comp3500.assignment2.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static academy.pocu.comp3500.assignment2.Logger.*;


public class Program {

    public static void main(String[] args) throws IOException {

        //D00

        BufferedWriter writer = new BufferedWriter(new FileWriter("mylog1.log"));

        log("this is not indented");

        Logger.indent();
        {
            log("but this is");
            log("so is this");
        }
        Logger.unindent();

        log("but not this");
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
