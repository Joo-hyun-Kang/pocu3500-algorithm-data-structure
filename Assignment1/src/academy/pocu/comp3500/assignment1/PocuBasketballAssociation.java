package academy.pocu.comp3500.assignment1;

import academy.pocu.comp3500.assignment1.pba.Player;
import academy.pocu.comp3500.assignment1.pba.GameStat;

public final class PocuBasketballAssociation {
    private PocuBasketballAssociation() {
    }

    public static void processGameStats(final GameStat[] gameStats, final Player[] outPlayers) {
        if (gameStats == null || outPlayers == null) {
            return;
        }

        //Sorting by PlayerName
        ArrayUtils.gameStateQuickSort(gameStats);

        int index = 0;
        int gameCount = 0;

        outPlayers[index].setName(gameStats[0].getPlayerName());

        int pointByStats = 0;
        int assistByStats = 0;
        int passByStats = 0;
        int goalSuccessByStats = 0;
        int goalAttemptByStats = 0;

        for (int j = 0; j < gameStats.length; j++) {
            if (!outPlayers[index].getName().equals(gameStats[j].getPlayerName())) {
                setPlayerState(outPlayers, index, pointByStats, assistByStats, passByStats, goalSuccessByStats, goalAttemptByStats, gameCount);

                pointByStats = 0;
                assistByStats = 0;
                passByStats = 0;
                goalSuccessByStats = 0;
                goalAttemptByStats = 0;

                index++;
                gameCount = 0;
            }

            gameCount++;

            outPlayers[index].setName(gameStats[j].getPlayerName());

            pointByStats += gameStats[j].getPoints();
            assistByStats += gameStats[j].getAssists();
            passByStats += gameStats[j].getNumPasses();
            goalSuccessByStats += gameStats[j].getGoals();
            goalAttemptByStats += gameStats[j].getGoalAttempts();
        }

        setPlayerState(outPlayers, index, pointByStats, assistByStats, passByStats, goalSuccessByStats, goalAttemptByStats, gameCount);
    }

    private static void setPlayerState(final Player[] outPlayers, int index, int point, int assist, int pass, int goalSuccess, int goalAttempt, int gameCount) {
        outPlayers[index].setPointsPerGame(point / gameCount);

        outPlayers[index].setAssistsPerGame(assist / gameCount);

        outPlayers[index].setPassesPerGame(pass / gameCount);

        outPlayers[index].setShootingPercentage((int) (goalSuccess / (double) goalAttempt * 100));
    }

    public static Player findPlayerPointsPerGame(final Player[] players, int targetPoints) {
        if (players == null) {
            return null;
        }

        return players[ArrayUtils.binarySearchPoint(players, targetPoints)];
    }

    public static Player findPlayerShootingPercentage(final Player[] players, int targetShootingPercentage) {
        if (players == null) {
            return null;
        }

        return players[ArrayUtils.binarySearchShooting(players, targetShootingPercentage)];
    }

    public static long find3ManDreamTeam(final Player[] players, final Player[] outPlayers, final Player[] scratch) {
        // 풀이법
        /*
        느낀점: 문제 해결하는데 있어 가정의 힘이 굉장하다

        만약 선수 i가 팀에 속해 있고 어시스트수가 팀내에서 제일 낮다고 가정을 합시다.
        그렇다면 다음과 같은 결론을 내릴 수 있습니다.
        1. i와 함께 팀에 속할 수 있는 선수들은 모두 i보다 높은 어시스트수를 가져야 함
        2. 그 선수들을 사용하여 팀의 패스수를 최대화 해야 함. 그래야 팀워크가 최대화 됨

        1번을 쉽게 보장하는 방법은 어시스트수로 선수들을 내림차순으로 정렬하는 것입니다.
        그렇다면 i번째 선수가 팀에 들어 있을 때 i와 i이전에 있는 선수들만 사용하여 패스수를 최대화 시키면 되는것이죠

        내림 차순으로 정렬해서 top 3에서 top3가 어시스트 수의 최대값이 될 수 있는데 이때는 항상 top1, top2가 있을 때만 가능
        어시스트를 내림차순으로 정렬했기 때문에 그 다음에 들어올 선수에 따라서 항상 어시스트는 최소값이 된다
        이 선수가 들어올 자리를 빼야 하는데 그 자리는 패스 수가 가장 적은 애를 빼면 된다.

        위 내용을 토대로 잘 생각을 해보면 이런 알고리듬이 나옵니다.
        1. players를 어시스트 내림차순으로 정렬 - O(nlogn)
        2. 반복문으로 각 선수 i를 방문
        3. 팀에 자리가 있다면 선수 i를 넣고 팀워크 계산해주고 최대 팀워크 업데이트
        4. 팀에 자리가 없다면 i를 제외한 패스수가 제일 낮은 선수를 팀에서 뺌. 그 자리에 i를 넣음. 팀워크 계산해주고 최대 팀워크 업데이트 - O(k)

        반복문의 각 iteration에서 O(k)라는 시간을 소모하고 총 n번 반복 되기 때문에 O(nk)
        결국 O(nlogn) + O(nk)라는 runtime이 나옵니다. (edited)

        한가지 더하자면 패스수로 정렬해서 비슷한 runtime이 나올 수 있는 것으로 압니다 (이전 학기에서 그렇게 하신 분들을 몇분 봤습니다). 아마 위의 설명과 비슷한 접근을 하는 것으로 기억합니다
        */

        //어시스트 순서로 내림 차순 정렬한 후 3명씩 스택에 넣는다
        ArrayUtils.playerAssistQuickSort(players);

        int stackIndex = 0;
        int stackCount = 0;

        int teamWork = 0;
        int passMin = Integer.MAX_VALUE;

        for (int i = 0; i < players.length; i++) {
            scratch[stackIndex] = players[i];
            stackIndex++;
            stackCount++;

            if (stackCount >= 3) {
                int passTotal = scratch[0].getPassesPerGame() + scratch[1].getPassesPerGame() + scratch[2].getPassesPerGame();

                int assistMin = players[i].getAssistsPerGame();

                int temp = passTotal * assistMin;

                if (temp > teamWork) {
                    teamWork = temp;
                    outPlayers[0] = scratch[0];
                    outPlayers[1] = scratch[1];
                    outPlayers[2] = scratch[2];
                }

                //패스 숫자가 가장 적은 얘를 빼고 다음 1명을 넣는다
                for (int j = 0; j < scratch.length; j++) {
                    if (scratch[j].getPassesPerGame() < passMin) {
                        passMin = scratch[j].getPassesPerGame();
                        stackIndex = j;
                    }
                }

                stackCount--;
                passMin = Integer.MAX_VALUE;
            }
        }

        return teamWork;
    }

    public static long findDreamTeam(final Player[] players, int k, final Player[] outPlayers, final Player[] scratch) {
        ArrayUtils.playerAssistQuickSort(players);

        int back = 0;
        int stackCount = 0;

        int teamWork = 0;
        int passMin = Integer.MAX_VALUE;

        for (int i = 0; i < players.length; i++) {
            scratch[back] = players[i];
            back++;
            stackCount++;

            if (stackCount >= k) {

                int passTotal = 0;
                for (int j = 0; j < scratch.length; j++) {
                    if (scratch[j].getPassesPerGame() < passMin) {
                        passMin = scratch[j].getPassesPerGame();
                        back = j;
                    }

                    passTotal += scratch[j].getPassesPerGame();
                }

                int assistMin = players[i].getAssistsPerGame();

                int temp = passTotal * assistMin;

                if (temp > teamWork) {
                    teamWork = temp;
                    for (int j = 0; j < scratch.length; j++) {
                        outPlayers[j] = scratch[j];
                    }
                }

                stackCount--;
                passMin = Integer.MAX_VALUE;
            }
        }

        return teamWork;
    }

    public static int findDreamTeamSize(final Player[] players, final Player[] scratch) {
        /*
            가정 : 최대 어시스트를 가진 사람이 최소 패스 수를 가진다고 하자

            player수가 1명일 때는 그냥 1명
            player수가 2명일 때는
            -1개씩 묶을 때
            최대 어시스트 수를 가진 사람이 최대 값이 되는 경우 : 유효
            최대 어시스트 수를 가진 사람이 최대 값이 되지 않는 경우 : 무효
                    - 팀워크가 패스 수 * 어시스트 최소값인데 어시스트 최소값이 아니더라도 최대값이 된다면
                    - player수를 늘려서 패스 수를 더하면 1개씩 묶을 때보다 더 큰 팀워크 수를 가지게 된다

            -2개씩 묶을 때
            1개 팀워크랑 비교해서 2개일 때 걍 비교하면 됌

            player수가 3명일 때는
            -1개씩 묶을 때
            최대 어시스트 수를 가진 사람이 최대 값이 되는 경우 : 유효
            최대 어시스트 수를 가진 사람이 최대 값이 되지 않는 경우 : 무효
                    - 팀워크가 패스 수 * 어시스트 최소값인데 어시스트 최소값이 아니더라도 최대값이 된다면
                    - player수를 늘려서 패스 수를 더하면 1개씩 묶을 때보다 더 큰 팀워크 수를 가지게 된다

            -2개씩 묶을 때
            최대 어시스트 수를 가진 사람이 최대 값이 되는 경우 : 유효
            최대 어시스트 수를 가진 사람이 최대 값이 되지 않는 경우 : 무효
                    - 팀워크가 패스 수 * 어시스트 최소값인데 어시스트 최소값이 아니더라도 최대값이 된다면
                    - player수를 늘려서 패스 수를 더하면 1개씩 묶을 때보다 더 큰 팀워크 수를 가지게 된다

            따라서 최대 팀워크에 맞는 팀 사이즈를 구할 때는 최대 어시스트를 가진 사람만 비교하면 된다!
         */

        //Sorting Assist by descending
        ArrayUtils.playerAssistQuickSort(players);

        int maxTeamwork = -1;
        int dreamTeamSize = -1;

        int totalPass = 0;
        for (int i = 0; i < players.length; i++) {
            int assist = players[i].getAssistsPerGame();

            totalPass += players[i].getPassesPerGame();

            int teamwork =  totalPass * assist;

            if (teamwork > maxTeamwork) {
                maxTeamwork = teamwork;
                dreamTeamSize = i + 1;
            }
        }

        return dreamTeamSize;

        // Time Complex not optimize
        /*
        ArrayUtils.playerAssistQuickSort(players);

        long teamWork = 0;
        int teamSize = 0;

        for (int i = players.length; i > 0; i--) {
            long temp = findDreamTeamSizeK(players, i, scratch);

            if (temp > teamWork) {
                teamWork = temp;
                teamSize = i;
            }
        }

        return teamSize;
         */
    }

    private static long findDreamTeamSizeK(final Player[] players, int k, final Player[] scratch) {
        int back = 0;
        int stackCount = 0;

        int teamWork = 0;
        int passMin = Integer.MAX_VALUE;

        for (int i = 0; i < players.length; i++) {
            scratch[back] = players[i];
            back++;
            stackCount++;


            if (stackCount >= k) {

                int passTotal = 0;
                for (int j = 0; j < k; j++) {
                    if (scratch[j].getPassesPerGame() < passMin) {
                        passMin = scratch[j].getPassesPerGame();
                        back = j;
                    }

                    passTotal += scratch[j].getPassesPerGame();
                }

                int assistMin = players[i].getAssistsPerGame();

                int temp = passTotal * assistMin;

                if (temp > teamWork) {
                    teamWork = temp;
                }

                stackCount--;
                passMin = Integer.MAX_VALUE;
            }
        }

        return teamWork;
    }
}
