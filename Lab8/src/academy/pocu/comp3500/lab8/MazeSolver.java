package academy.pocu.comp3500.lab8;

import academy.pocu.comp3500.lab8.maze.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public final class MazeSolver {
    public static List<Point> findPath(final char[][] maze, final Point start) {
        //가설
        //명제 : 배열을 4방향으로 하여 트리구조로 표현할 수 있다
        //명제 : 트리구조를 왼쪽, 북쪽, 오른쪽, 아래쪽으로 시계방향으로 DST 탐색한다
        //명제 : 단, 지나간 곳은 다시 가지 못하게 표시한다
        //결론 : 그러면 미로를 효율적으로 통과할 수 있다


        // 배열을 트리구조로 표현
        // 가설 : start를 기준으로 왼쪽 오른쪽 북쪽 아래쪽 이넘
        // 전제 : maze 범위 안에 있어야 한다
        // 전제 : 시작포인트가 x가 아니어야 한다

        if (maze == null || maze[0] == null || start == null) {
            System.out.println("Invaild parameter");
            return null;
        }

        int xBoard = maze[0].length;
        int yBoard = maze.length;

        if (start.getX() < 0 || start.getX() > xBoard - 1 || start.getY() < 0 || start.getY() > yBoard - 1) {
            System.out.println("Invaild X, Y");
            return null;
        }

        final int LEFT = -1;
        final int RIGHT = 1;
        final int NORTH = -1;
        final int SOUTH = 1;



        Stack<Point> moves = new Stack<>();

        moves.add(start);
        // 가정: null 체크, 범위체크, start가 x일때 처리를 하지 않아서 예외처리가 되지 않았다 -> 해결
        //      근본 : 처리 등을 했는데 예외처리가 되지 않는 경우가 있을까?
        // 가정: 스택에 넣는 순서가 잘못되어서 while문이 제대로 작동하지 않는다 -> 해결
        //      근본 : 처리를 했는데 while문 제대로 작동하지 않는 경우가 있을까?
        // 가정 : E가 없을 때 처리를 잘 안하면 리턴값이 잘 반환되지 않는다
        //      근본 : E에 대해서 처리를 잘 했는데도 리턴값이 잘 반환되지 않는 경우가 있을까?
        // 가정 : 샛길을 결과에 포함하고 있어서 maze8x6을 통과하지 못한다
        //      근본 : 샛길 결과를 포함하지 않고도 maze8x6을 통과하지 못한다

        //  문제 -> 가설 -> 조치 및 검증 -> 새로운 가설로 근본 원인 ㄱㄱ

        List<Point> points = new ArrayList<>();
        List<Point> result = new ArrayList<>();
        int childCount = 0;
        while (!moves.empty()) {
            Point current = moves.pop();

            int currentX = current.getX();
            int currentY = current.getY();

            if (currentX < 0 || currentX > xBoard - 1 || currentY < 0 || currentY > yBoard - 1) {
                continue;
            }

            if (maze[currentY][currentX] == 'E') {
                points.add(current);
                result.addAll(points);
                break;
            }

            if (maze[currentY][currentX] == 'x') {;
                if (childCount == moves.size()) {
                    points.remove(points.size() - 1);
                }
                continue;
            }

            maze[currentY][currentX] = 'x';
            points.add(current);

            moves.add(new Point(currentX, currentY + SOUTH));
            moves.add(new Point(currentX + RIGHT, currentY));
            moves.add(new Point(currentX, currentY + NORTH));
            moves.add(new Point(currentX + LEFT, currentY));

            childCount = moves.size() - 4;
        }

        return result;
    }
}