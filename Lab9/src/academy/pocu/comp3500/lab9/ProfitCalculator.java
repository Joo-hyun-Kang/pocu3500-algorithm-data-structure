package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.Task;

public class ProfitCalculator {
    public static int findMaxProfit(final Task[] tasks, final int[] skillLevels) {
        int sum = 0;
        for (int s : skillLevels) {
            int max = Integer.MIN_VALUE;
            int index = -1;
            //가장 큰 skillLevel부터 가져온다
            for (int i = 0; i < tasks.length; i++) {
                if (tasks[i].getDifficulty() <= s && max <= tasks[i].getProfit()) {
                    index = i;
                    max = tasks[i].getProfit();
                }
            }
            if (index != -1) {
                sum += tasks[index].getProfit();
            }
        }
        return sum;
    }
}