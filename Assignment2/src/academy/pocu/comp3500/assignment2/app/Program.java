package academy.pocu.comp3500.assignment2.app;

import academy.pocu.comp3500.assignment2.Indent;
import academy.pocu.comp3500.assignment2.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static academy.pocu.comp3500.assignment2.Logger.*;


public class Program {

    public static void main(String[] args) throws IOException {

            BufferedWriter writer = new BufferedWriter(new FileWriter("test1.log"));

            log("Depth 0");
            log("Depth 1");

            //indent.discard();

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
