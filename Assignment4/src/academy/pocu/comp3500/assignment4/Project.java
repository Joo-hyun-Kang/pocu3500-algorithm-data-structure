package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public final class Project {
    private ArrayList<Task> schedule;
    private HashMap<String, Task> isCircle;
    private HashMap<String, Task> transTask;
    private HashMap<String, Task> inOrderTasks;

    //SCC가 있는 지 코사라주 알고리듬으로 찾아낸다
    public Project(final Task[] tasks) {
        HashMap<String, Task> transTask = transposeTask(tasks);
        HashMap<String, Task> discoverd = new HashMap<>();
        LinkedList<Task> firstDSTList = new LinkedList<>();

        for (HashMap.Entry<String, Task> entry : transTask.entrySet()) {
            if (discoverd.containsKey(entry.getKey())) {
                continue;
            }

            topologicalSortRecursive(entry.getValue(), discoverd, firstDSTList);
        }

        HashMap<String, Task> inOrderTasks = new HashMap<>();
        for (Task task : tasks) {
            inOrderTasks.put(task.getTitle(), task);
        }

        ArrayList<Task> sortedList = new ArrayList<>();
        HashMap<String, Task> isCircle = new HashMap<>();
        discoverd.clear();
        while (!firstDSTList.isEmpty()) {
            Task task = inOrderTasks.get(firstDSTList.getFirst().getTitle());
            firstDSTList.removeFirst();

            if (discoverd.containsKey(task.getTitle())) {
                continue;
            }

            final boolean IS_RECURSIVE = false;
            topologicalSortRecursive(task, discoverd, sortedList, isCircle, IS_RECURSIVE);
        }

        this.schedule = sortedList;
        this.isCircle = isCircle;
        this.transTask = transTask;
        this.inOrderTasks = inOrderTasks;
    }

    //task까지 끝내려면 총 몇 개월이 걸리는 지 == 한 달 안에 끝내려면 몇 명이 필요한 지
    //String에 대해서 DST탐색을 해서 모든 Estimate를 더 한다
    public int findTotalManMonths(final String task) {
        Task mildStone = inOrderTasks.get(task);
        HashMap<String, Task> discoverd = new HashMap<>();

        return depthFirstSerach(mildStone, discoverd);
    }

    //task까지의 일감에 각 1명씩 투입했을 떄 최소 몇 달이 걸리는 지
    //String에 대해서 DST탐색을 해서 같은 깊이에 대해서 가장 큰 Estimate만 구한다
    public int findMinDuration(final String task) {
        Task mildStone = inOrderTasks.get(task);
        HashMap<String, Task> discoverd = new HashMap<>();

        return findMaxDurationBranch(mildStone, discoverd);
    }

    //각 일감을 한 달에 마치지만 다른 브런치에 있는 일감은 동시에 진행 가능
    //브런치의 최소 값을 가져온다 && 브런치가 양쪽으로 갈라지는 경우에는 최소 값을 분할해야 함
    //task로부터 뒤를 순환하다가 방문한 적이 있는 경우가 브런치가 갈라지는 경우이고 다른 갈라진 쪽 브런치만큼 빼준다
    public int findMaxBonusCount(final String task) {
        Task mildStone = inOrderTasks.get(task);
        HashMap<String, Integer> discoverd = new HashMap<>();

        return findMaxBonusCountRecursive(mildStone, discoverd);
    }

    private static void topologicalSortRecursive(Task task, HashMap<String, Task> discoverd, LinkedList<Task> linkedList) {
        discoverd.put(task.getTitle(), task);

        for (Task preTask : task.getPredecessors()) {
            if (discoverd.containsKey(preTask.getTitle())) {
                continue;
            }

            topologicalSortRecursive(preTask, discoverd, linkedList);
        }

        linkedList.addFirst(task);
    }

    private static void topologicalSortRecursive(Task task, HashMap<String, Task> discoverd, ArrayList<Task> arrayList, HashMap<String, Task> isCircle, boolean isRecursive) {
        discoverd.put(task.getTitle(), task);

        for (Task preTask : task.getPredecessors()) {
            if (isRecursive) {
                isCircle.put(task.getTitle(), task);
            }

            if (discoverd.containsKey(preTask.getTitle())) {
                continue;
            }

            isCircle.put(task.getTitle(), task);
            topologicalSortRecursive(preTask, discoverd, arrayList, isCircle, true);
        }

        arrayList.add(task);
    }

    private static HashMap<String, Task> transposeTask(final Task[] tasks) {
        HashMap<String, Task> transTask = new HashMap<>();

        for (Task task : tasks) {
            if (!transTask.containsKey(task.getTitle())) {
                transTask.put(task.getTitle(), new Task(task.getTitle(), task.getEstimate()));
            }

            List<Task> predecessors = task.getPredecessors();
            if (predecessors.size() != 0) {
                for (Task preTask : predecessors) {
                    if (transTask.containsKey(preTask.getTitle())) {
                        transTask.get(preTask.getTitle()).addPredecessor(transTask.get(task.getTitle()));
                        continue;
                    }

                    Task temp = new Task(preTask.getTitle(), preTask.getEstimate());
                    temp.addPredecessor(transTask.get(task.getTitle()));
                    transTask.put(temp.getTitle(), temp);
                }
            }
        }

        return transTask;
    }

    private int depthFirstSerach(Task task, HashMap<String, Task> discoverd) {
        int result = task.getEstimate();
        discoverd.put(task.getTitle(), task);

        for (Task preTask : task.getPredecessors()) {
            if (isCircle.containsKey(preTask.getTitle()) || discoverd.containsKey(preTask.getTitle())) {
                continue;
            }

            result += depthFirstSerach(preTask, discoverd);
        }

        return result;
    }

    private int findMaxDurationBranch(Task task, HashMap<String, Task> discoverd) {
        int result = task.getEstimate();
        discoverd.put(task.getTitle(), task);

        int max = 0;
        for (Task preTask : task.getPredecessors()) {
            if (isCircle.containsKey(preTask.getTitle()) || discoverd.containsKey(preTask.getTitle())) {
                continue;
            }

            int ret = findMaxDurationBranch(preTask, discoverd);
            max = Math.max(ret, max);
            discoverd.clear();
        }
        result += max;

        return result;
    }

    private int findMaxBonusCountRecursive(Task task, HashMap<String, Integer> discoverd) {
        int result = task.getEstimate();

        int branchBonusCountTotal = 0;
        for (Task preTask : task.getPredecessors()) {
            if (isCircle.containsKey(preTask.getTitle())) {
                continue;
            }

            if (discoverd.containsKey(preTask.getTitle())) {
                int branchMin = discoverd.get(preTask.getTitle());

                List<Task> nextTasks = transTask.get(preTask.getTitle()).getPredecessors();
                for (Task nextTask : nextTasks) {
                    if (nextTask.getTitle().equals(task.getTitle())) {
                        continue;
                    }

                    if (!discoverd.containsKey(nextTask.getTitle())) {
                        continue;
                    }

                    branchMin -= nextTask.getEstimate();
                }

                branchBonusCountTotal += branchMin;
                continue;
            }

            branchBonusCountTotal += findMaxBonusCountRecursive(preTask, discoverd);
        }

        result = branchBonusCountTotal != 0 ? Math.min(branchBonusCountTotal, result) : result;

        discoverd.put(task.getTitle(), result);

        return result;
    }
}