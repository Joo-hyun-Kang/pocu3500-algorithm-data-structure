package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.*;


public final class Project {
    ArrayList<Task> schedule;
    HashSet<String> isCircle;
    HashMap<String, Task> transTask;
    HashMap<String, Task> inOrderTasks;

    public Project(final Task[] tasks) {
        //먼저 정상인거 먼저 구함
        HashMap<String, Task> transTask = transposeTask(tasks);
        HashSet<Task> discoverd = new HashSet<>();
        LinkedList<Task> dstReveseList = new LinkedList<>();

        for (HashMap.Entry<String, Task> entry : transTask.entrySet()) {
            if (discoverd.contains(entry.getValue())) {
                continue;
            }

            topologicalSortRecursive(entry.getValue(), discoverd, dstReveseList);
        }

        HashMap<String, Task> inOrderTasks = new HashMap<>();
        for (Task task : tasks) {
            inOrderTasks.put(task.getTitle(), task);
        }

        ArrayList<Task> sortedList = new ArrayList<>();
        HashSet<String> isCircle = new HashSet<>();
        discoverd.clear();
        while (!dstReveseList.isEmpty()) {
            Task task = inOrderTasks.get(dstReveseList.getFirst().getTitle());
            dstReveseList.removeFirst();
            assert (task != null);
            if (discoverd.contains(task)) {
                continue;
            }

            topologicalSortRecursive(task, discoverd, sortedList, isCircle, false);
        }

        this.schedule = sortedList;
        this.isCircle = isCircle;
        this.transTask = transTask;
        this.inOrderTasks = inOrderTasks;
    }

    public int findTotalManMonths(final String task) {
        //String에 대해서 DST탐색을 해서 모든 Estimate를 더 한다
        int totalEstimate = 0;
        Task mildStone = inOrderTasks.get(task);
        HashMap<String, Task> discoverd = new HashMap<>();

        totalEstimate = depthFirstSerach(mildStone, discoverd);

        return totalEstimate;
    }

    public int findMinDuration(final String task) {
        //String에 대해서 DST탐색을 해서 같은 깊이에 대해서 가장 큰 Estimate만 구한다
        //DST + max



        return -1;
    }

    public int findMaxBonusCount(final String task) {
        return -1;
    }

    private static void topologicalSortRecursive(Task task, HashSet<Task> discoverd, LinkedList<Task> linkedList) {
        discoverd.add(task);

        for (Task preTask : task.getPredecessors()) {
            if (discoverd.contains(preTask)) {
                continue;
            }

            topologicalSortRecursive(preTask, discoverd, linkedList);
        }

        linkedList.addFirst(task);
    }

    private static void topologicalSortRecursive(Task task, HashSet<Task> discoverd, ArrayList<Task> arrayList, HashSet<String> isCircle, boolean isRecursive) {
        discoverd.add(task);

        for (Task preTask : task.getPredecessors()) {
            if (isRecursive) {
                isCircle.add(task.getTitle());
            }

            if (discoverd.contains(preTask)) {
                continue;
            }

            isCircle.add(task.getTitle());
            topologicalSortRecursive(preTask, discoverd, arrayList, isCircle, true);
        }

        arrayList.add(task);
    }

    private int depthFirstSerach(Task task, HashMap<String, Task> discoverd) {
        int result = task.getEstimate();
        discoverd.put(task.getTitle(), task);

        for (Task preTask : task.getPredecessors()) {
            if (isCircle.contains(preTask) || discoverd.containsKey(preTask.getTitle())) {
                continue;
            }

            result += depthFirstSerach(preTask, discoverd);
        }

        return result;
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

}