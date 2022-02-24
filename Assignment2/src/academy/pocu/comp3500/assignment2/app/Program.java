package academy.pocu.comp3500.assignment2.app;

import academy.pocu.comp3500.assignment2.Indent;
import academy.pocu.comp3500.assignment2.Logger;
import academy.pocu.comp3500.assignment2.datastructure.Sort;
import academy.pocu.comp3500.assignment2.datastructure.Stack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static academy.pocu.comp3500.assignment2.Logger.*;


public class Program {

    public static void main(String[] args) throws IOException {

        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog1.log"));

            Logger.unindent();
            Logger.unindent();

            log("0 Level");

            Logger.indent().discard();

            log("1 Level");

            Logger.indent();

            log("2 Level");
            log("2 Level");

            Logger.unindent();

            log("1 Level");
            log("1 Level");

            Logger.unindent();

            log("0 Level");
            log("0 Level");

            Logger.printTo(writer);
        }

        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog1.log"));

            Logger.unindent();
            Logger.unindent();

            log("but this is");
            log("but this is");

            Logger.indent();

            log("indent() contents");

            Logger.unindent();

            log("but this is");
            log("but this is");

            Logger.unindent();

            log("but this is");
            log("but this is");

            Logger.unindent();

            log("but this is");
            log("but this is");

            Logger.printTo(writer);
        }


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


        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog1.log"));

            Logger.unindent();

            Logger.indent();

            log("first level 1");


            Logger.printTo(writer);
        }



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


        // 종욱님의 테스트 코드


        {
            // clear works correctly
            log("Hello");

            clear();

            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog.log"));
            printTo(writer);
            String actual = new String(Files.readAllBytes(Path.of("mylog.log")));
            assert "".equals(actual);
        }

        {
            // log and printTo works correctly when log once
            clear();
            log("Hello");
            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog.log"));
            printTo(writer);

            String actual = new String(Files.readAllBytes(Path.of("mylog.log")));
            assert "Hello\n".equals(actual);
        }


        {
            // log and printTo works correctly when log multiple times
            clear();
            log("Hello");
            log("world");
            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog.log"));
            printTo(writer);

            String actual = new String(Files.readAllBytes(Path.of("mylog.log")));
            assert "Hello\nworld\n".equals(actual);
        }

        {
            // indent works correctly when indent once
            clear();
            log("Hello");

            indent();
            log("world");

            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog.log"));
            printTo(writer);
            String actual = new String(Files.readAllBytes(Path.of("mylog.log")));
            assert "Hello\n  world\n".equals(actual);
        }

        {
            // indent works correctly when indent multiple times
            clear();
            log("Hello");

            indent();
            log("world");
            indent();
            log("Goodbye");
            indent();
            log("world");

            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog.log"));
            printTo(writer);
            String actual = new String(Files.readAllBytes(Path.of("mylog.log")));
            assert "Hello\n  world\n    Goodbye\n      world\n".equals(actual);
        }

        {
            // unindent works correctly when indent once
            clear();
            indent();
            log("Hello");

            unindent();
            log("world");

            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog.log"));
            printTo(writer);
            String actual = new String(Files.readAllBytes(Path.of("mylog.log")));
            assert "  Hello\nworld\n".equals(actual);
        }

        {
            // discard works correctly when discard all logs
            clear();
            Indent indent = indent();
            log("Hello");

            indent.discard();

            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog.log"));
            printTo(writer);
            String actual = new String(Files.readAllBytes(Path.of("mylog.log")));
            assert "".equals(actual);
        }

        {
            // discard works correctly when discard some logs
            clear();
            log("Hello");
            Indent indent = indent();
            log("world");

            indent.discard();

            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog.log"));
            printTo(writer);
            String actual = new String(Files.readAllBytes(Path.of("mylog.log")));
            assert "Hello\n".equals(actual);
        }

        {
            // only discard that indent when there is other same level indent
            clear();
            log("Hello");
            Indent indent1 = indent();
            log("world");
            unindent();
            indent();
            log("Goodbye");

            indent1.discard();

            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog.log"));
            printTo(writer);
            String actual = new String(Files.readAllBytes(Path.of("mylog.log")));
            assert "Hello\n  Goodbye\n".equals(actual);

            clear();
        }

        {
            // child indent is removed when discard parent indent
            clear();
            log("Hello");
            Indent indent1 = indent();
            log("world");
            Indent indent2 = indent();
            log("Goodbye");

            indent1.discard();

            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog.log"));
            printTo(writer);
            String actual = new String(Files.readAllBytes(Path.of("mylog.log")));
            assert "Hello\n".equals(actual);

            clear();
        }

        {
            // discard works correctly when discard empty text indent
            log("Hello");
            Indent indent1 = indent();
            indent();
            log("world");

            indent1.discard();

            StringWriter out = new StringWriter();
            BufferedWriter writer = new BufferedWriter(out);
            printTo(writer);
            String actual = out.toString();
            assert "Hello\n".equals(actual);
            clear();
        }

        {
            // discard works correctly when there is same log with removing log
            log("Hello");
            Indent indent1 = indent();
            log("world");
            unindent();
            log("Goodbye");
            Indent indent2 = indent();
            log("world");

            indent2.discard();

            StringWriter out = new StringWriter();
            BufferedWriter writer = new BufferedWriter(out);
            printTo(writer);
            String actual = out.toString();
            assert "Hello\n  world\nGoodbye\n".equals(actual);
            clear();
        }

        clear();

        // Official Test Cases
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("mylog1.log"));

            log("hello");
            log("world");
            log("this is logging at the top level");

            Logger.indent();
            {
                log("using indent, you can indent to organize your logs");
                log("call unindent() to decrease the indentation level");
            }
            Logger.unindent();

            Indent indent = Logger.indent();
            {
                log("whatever I say here");
                log("is discarded!");
                log("too bad!");

                indent.discard();
            }
            Logger.unindent();

            Logger.indent();
            {
                log("this won't be discarded");
                log("it's true!");

                doMagic();
            }
            Logger.unindent();

            log("back to the top level!");
            log("and let's print the logs");

            Logger.printTo(writer);

            Logger.clear();

            log("log was just cleared");
            log("so you start logging from the top level again");

            Logger.printTo(writer);

            writer.close();
        }


        Logger.clear();

        {
            final BufferedWriter writer1 = new BufferedWriter(new FileWriter("quicksort1.log"));
            final BufferedWriter writer2 = new BufferedWriter(new FileWriter("quicksort2.log"));

            int[] nums = new int[]{30, 10, 80, 90, 50, 70, 40};

            Sort.quickSort(nums);

            Logger.printTo(writer1);

            Logger.printTo(writer2, "90");

            writer1.close();
            writer2.close();
        }
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
