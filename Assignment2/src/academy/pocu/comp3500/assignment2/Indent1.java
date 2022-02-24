package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;

import java.io.BufferedWriter;
import java.io.IOException;

public final class Indent {
    private final ArrayList<String> logs = new ArrayList<>();
    private final int spaceCount;

    public Indent(int spaceCount) {
        this.spaceCount = spaceCount;
    }

    public void discard() {
        Logger.discardLogs(this);
    }

    public void addLog(String log) {
        this.logs.add(log);
    }

    public void print(final BufferedWriter writer) throws IOException {
        for (String log : this.logs) {
            for (int j = 0; j < this.spaceCount; j++) {
                writer.write("  ");
            }
            writer.write(log + System.lineSeparator());
        }
    }

    public void print(final BufferedWriter writer, final String filter) throws IOException {
        for (String log : this.logs) {
            if (log.contains(filter)) {
                for (int i = 0; i < this.spaceCount; i++) {
                    writer.write("  ");
                }
                writer.write(log + System.lineSeparator());
            }
        }
    }

    public int getSpaceCount() {
        return this.spaceCount;
    }
}