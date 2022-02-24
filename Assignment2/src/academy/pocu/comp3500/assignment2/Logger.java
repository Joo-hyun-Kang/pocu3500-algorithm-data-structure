package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;

import java.io.BufferedWriter;
import java.io.IOException;

// 최초 구현
    /*
    - 구현
    로그를 Logger에서 관리
    indent는 단순하게 Logger의 indent부분을 가지고 있는 지만 체크
    start와 end로 로그에서 들여쓰기를 판단하는 부분이 어려웠음

    - 통과하지 못한 이유
	내가 D00-D05를 통과하지 못한 이유는 clear()함수를 제대로 구현하지 않아서
	clear()에서 로그만 날라가고 indents, stack 배열이 여전히 남아 있던게 문제였음
    */

// 새로운 구현
    /*
    - 구현
    로그를 indent 개체에서 관리
    unindent도 독립적인 indent 개체로 관리
    unindent도 로그를 가지고 있다
    discard를 구현할 때 더 편리
    printTo()에서는 indent배열을 훝으며 출력
            start와 end로 로그에서 들여쓰기를 판단하는 부분을
            개체지향적으로 간단한 로직으로 처리함

    - 개선할 점
    Logger1, Indent1 참고
    더 개체지향적으로 print하는 건 로그를 가지고 있는 indent 클래스에서 처리
    더 개체지향적으로 discard된 개체는 indent 개체에서 Logger에 있는 indents 배열에서 해당 indent 배열들을 삭제

    Logger2, Indent2 참고
    더 개체지향적으로 String과 Indent 모두 개체이기 때문에 Object 배열에 넣고 instanceof로 쉽게 구현
     */

public final class Logger {

    private static ArrayList<Indent> indents = new ArrayList<>();
    private static Indent cursor;
    private static int whitespaceCount = -1;

    public static void log(final String text) {
        if (indents.getSize() == 0) {
            Indent initialIndent = new Indent(++whitespaceCount);
            indents.add(initialIndent);
            cursor = initialIndent;
        }

        cursor.addLog(text);
    }

    public static void printTo(final BufferedWriter writer) {
        try {
            int discardIndentLevel = Integer.MAX_VALUE;

            boolean isDiscardIndentSubIndent = false;

            for (Indent indent : indents) {
                // 상위 indent에 discard가 되면 하위 indent도 출력되지 않아야 한다
                // 1. 가정 : whitespace가 discard된 것과 큰 것은 모두 제외한다.
                //
                // 2. 가정 : 상위 discard된 레벨에 다시 도달하면 discard 효력이 없어진다

                int currentIndentLevel = indent.getWhitespaceCount();

                isDiscardIndentSubIndent = currentIndentLevel > discardIndentLevel;

                if (indent.isDiscardOn() == false && isDiscardIndentSubIndent == false) {
                    for (String log : indent.getLoggingText()) {
                        writer.write(indent.getDelimiter());
                        writer.write(log);
                        writer.write("\n");
                    }
                }

                // discard가 될 때 하위 indent를 생략
                if (indent.isDiscardOn())   {
                    discardIndentLevel = indent.getWhitespaceCount();
                }

                // 같은 indent 레벨에서 discardIndentLevel이 초기화
                if (!indent.isDiscardOn() && currentIndentLevel == discardIndentLevel)   {
                    discardIndentLevel = Integer.MAX_VALUE;
                }
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printTo(final BufferedWriter writer, final String filter) {
        try {
            int discardIndentLevel = Integer.MAX_VALUE;

            boolean isDiscardIndentSubIndent = false;

            for (Indent indent : indents) {
                int currentIndentLevel = indent.getWhitespaceCount();

                isDiscardIndentSubIndent = currentIndentLevel > discardIndentLevel;

                if (indent.isDiscardOn() == false && isDiscardIndentSubIndent == false) {
                    for (String log : indent.getLoggingText()) {
                        if (log.contains(filter)) {
                            writer.write(indent.getDelimiter());
                            writer.write(log);
                            writer.write("\n");
                        }
                    }
                }

                // discard가 될 때 하위 indent를 생략
                if (indent.isDiscardOn())   {
                    discardIndentLevel = indent.getWhitespaceCount();
                }

                // 같은 indent 레벨에서 discardIndentLevel이 초기화
                if (!indent.isDiscardOn() && currentIndentLevel == discardIndentLevel)   {
                    discardIndentLevel = Integer.MAX_VALUE;
                }
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clear() {
        indents.clear();
        whitespaceCount = -1;
    }

    public static Indent indent() {
        if (indents.getSize() == 0) {
            Indent initialIndent = new Indent(++whitespaceCount);
            indents.add(initialIndent);
            cursor = initialIndent;
        }

        Indent newIndent = new Indent(++whitespaceCount);
        indents.add(newIndent);
        cursor = newIndent;

        return cursor;
    }

    public static void unindent() {
        //최초에 여러번 unindent 호출 방지
        if (indents.getSize() < 2) {
            return;
        }

        // 더 이상 들여쓰기 할 수 없을 때
        if (whitespaceCount <= 0) {
            return;
        }

        Indent unindent = new Indent(--whitespaceCount);
        indents.add(unindent);
        cursor = unindent;
    }
}