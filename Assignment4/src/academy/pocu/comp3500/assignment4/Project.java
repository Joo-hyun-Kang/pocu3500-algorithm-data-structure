package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.*;

public final class Project {
    LinkedList<Task> schedule;
    HashSet<String> isCircle;

    public Project(final Task[] tasks) {
        HashSet<Task> discoverd = new HashSet<>();
        LinkedList<Task> dsTList = new LinkedList<>();

        for (Task task : tasks) {
            if (discoverd.contains(task)) {
                continue;
            }

            topologicalSortRecursive(task, discoverd, dsTList);
        }

        HashMap<String, Task> transTask = transposeTask(tasks);
        LinkedList<Task> sortedList = new LinkedList<>();
        HashSet<String> overlaped = new HashSet<>();
        HashSet<String> isCircle = new HashSet<>();
        while (!dsTList.isEmpty()) {
            Task task = transTask.get(dsTList.getFirst().getTitle());
            dsTList.removeFirst();
            assert(task != null);
            if (overlaped.contains(task.getTitle())) {
                continue;
            }

            topologicalSortRecursive2(task, overlaped, isCircle, sortedList, false);
        }

        this.schedule = sortedList;
        this.isCircle = isCircle;
    }

    public int findTotalManMonths(final String task) {
        // 모든 Task에 대해서 DST를 한다

        int totalEstimate = 0;
        int i = 0;
        do {
            Task temp = schedule.get(i);
            if (isCircle.contains(temp.getTitle())) {
                continue;
            }
            
            totalEstimate += temp.getEstimate();

            if (task.equals(temp.getTitle())) {
                break;
            }
        } while (++i < schedule.size());


        return totalEstimate;
    }

    public int findMinDuration(final String task) {
        return -1;
    }

    public int findMaxBonusCount(final String task) {
        return -1;
    }

    private List<String> findSchedule(final Task[] tasks, final boolean includeMaintenance) {
        // 모든 task에 대해서 DFS를 돌리고 그 결과에 대해서 역순으로 돌린다

        return null;
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

    private static void topologicalSortRecursive2(Task task, HashSet<String> overlaped, HashSet<String> isCircle, LinkedList<Task> linkedList, boolean isRecursive) {
        overlaped.add(task.getTitle());

        for (Task preTask : task.getPredecessors()) {
            if (isRecursive) {
                isCircle.add(task.getTitle());
            }

            if (overlaped.contains(preTask.getTitle())) {
                continue;
            }

            isCircle.add(task.getTitle());
            topologicalSortRecursive2(preTask, overlaped, isCircle, linkedList, true);
        }

        linkedList.addFirst(task);
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