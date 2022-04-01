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

    //String에 대해서 DST탐색을 해서 모든 Estimate를 더 한다
    public int findTotalManMonths(final String task) {
        Task mildStone = inOrderTasks.get(task);
        HashMap<String, Task> discoverd = new HashMap<>();

        return depthFirstSerach(mildStone, discoverd);
    }

    public int findMinDuration(final String task) {
        //String에 대해서 DST탐색을 해서 같은 깊이에 대해서 가장 큰 Estimate만 구한다
        //DST + max
        Task mildStone = inOrderTasks.get(task);
        HashMap<String, Task> discoverd = new HashMap<>();

        return findMaxDurationBranch(mildStone, discoverd);
    }

    public int findMaxBonusCount(final String task) {
        return -1;
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
}