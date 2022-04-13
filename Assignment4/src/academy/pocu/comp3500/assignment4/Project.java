/*
package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

** 과제 4의 의의를 프로젝트 관리 관점으로 생각해보았다
1. findTotalManMonths : 전체 걸리는 시간을 manMonth 기준으로 구해준다
2. findMinDuration : 1명을 외주를 주고 1명은 내주로 진행한다고 할 때 걸리는 최소시간
3. bounus
    : 내수 팀을 최소한으로 유지해야하는 규모
    : 또한 일감을 일정한 기간 제한(여기서는 1달)에 끝날 때 내수 팀에서의 가장 효율적인 규모 + 남는 인원은 외주로
    : 또한 해당 일에 사람을 투입할수록 생산성이 늘어나지 않을 때 내수 팀에서의 가장 효율적인 규모 + 남는 인원은 외주로
4. 들어갈 수 있는 인원이 제한되지 않는 경우에는 4명 투입 시 걸리는 시간 : findTotalManMont /  팀 규모
5. 들어갈 수 있는 인원이 제한되는 경우 : bonus 이용해서 내수 최적 규모를 구해서 유지하고 + 남은 인원은 외주로 줌
    : 일정시간 제한이 있는 경우로 너무 많이 투입하면 빨리 끝나고, 너무 적게 투입하면 늦게 끝나니까
    : 또, 해당 일에 사람을 투입할수록 생산성이 늘어나지 않을 때

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


    //task까지 끝내려면 총 몇 개월이 걸리는 지
    //String에 대해서 DST탐색을 해서 모든 Estimate를 더 한다
    public int findTotalManMonths(final String task) {
        Task mildStone = inOrderTasks.get(task);
        HashMap<String, Task> discoverd = new HashMap<>();

        return depthFirstSerach(mildStone, discoverd);
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

    // 여기에 안현준님 코드
    // 다른 구현으로 BST탐색을 해도 됌 또한 Task에서 뒤로 탐색하기 때문에 Circle에 걸릴 일이 없음으로
    // SCC를 생각해서 구현해주지 않아도 된다



    //task까지의 일감에 각 1명씩 투입했을 떄 최소 몇 달이 걸리는 지
    //String에 대해서 DST탐색을 해서 같은 깊이에 대해서 가장 큰 Estimate만 구한다
    public int findMinDuration(final String task) {
        Task mildStone = inOrderTasks.get(task);
        HashMap<String, Task> discoverd = new HashMap<>();

        return findMaxDurationBranch(mildStone, discoverd);
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


    //어떤 마일드 스톤을 이루기 위해서 지속적으로 팀에서 유지해야 하는 인원을 알 수 있다
    //각 일감을 한 달에 마치지만 다른 브런치에 있는 일감은 동시에 진행 가능
    //브런치의 최소 값을 가져온다 && 브런치가 양쪽으로 갈라지는 경우에는 최소 값을 분할해야 함
    //task로부터 뒤를 순환하다가 방문한 적이 있는 경우가 브런치가 갈라지는 경우이고 다른 갈라진 쪽 브런치만큼 빼준다
    //내 코드 로직인 깊이 우선 + 중복되는 경우 처리로는 테스트 전부 통과 못 함 최대 유량을 이용해야 풀 수 있음
    public int findMaxBonusCount(final String task) {
        Task mildStone = inOrderTasks.get(task);
        HashMap<String, Integer> discoverd = new HashMap<>();

        return findMaxBonusCountRecursive(mildStone, discoverd);
    }

    private int findMaxBonusCountRecursive(Task task, HashMap<String, Integer> discoverd) {
        int result = task.getEstimate();
        boolean isZeroBonus = false;

        int branchBonusCountTotal = 0;
        for (Task preTask : task.getPredecessors()) {
            if (isCircle.containsKey(preTask.getTitle())) {
                continue;
            }

            if (discoverd.containsKey(preTask.getTitle())) {
                int branchMin = discoverd.get(preTask.getTitle());
                if (branchMin == Integer.MIN_VALUE) {
                    isZeroBonus = true;
                    continue;
                }

                branchBonusCountTotal += branchMin;
                continue;
            }

            int ret = findMaxBonusCountRecursive(preTask, discoverd);
            branchBonusCountTotal += ret;

            int preTaskBonusCount = ret - task.getEstimate();
            preTaskBonusCount = preTaskBonusCount < 0 ? Integer.MIN_VALUE : preTaskBonusCount;
            discoverd.put(preTask.getTitle(), preTaskBonusCount);
        }

        if (branchBonusCountTotal != 0) {
            result = Math.min(branchBonusCountTotal, result);
        } else if (isZeroBonus) {
            result = 0;
        }

        discoverd.put(task.getTitle(), result);

        return result;
    }
}


 */



package academy.pocu.comp3500.assignment4;

import academy.pocu.comp3500.assignment4.project.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;


public final class Project {
    private final Task[] tasks;

    public Project(final Task[] tasks) {
        this.tasks = tasks;
    }

    public int findTotalManMonths(final String task) {
        int estimateCounter = 0;
        final Queue<Task> queue = new LinkedList<>();
        final HashMap<Task, Boolean> discovered = new HashMap<>();

        for (int i = 0; i < this.tasks.length; i++) {
            if (tasks[i].getTitle().equals(task)) {
                queue.add(tasks[i]);
                discovered.put(tasks[i], true);
                break;
            }
        }

        while (!queue.isEmpty()) {
            final Task tempTask = queue.remove();
            estimateCounter += tempTask.getEstimate();

            if (!tempTask.getPredecessors().isEmpty()) {
                final int size = tempTask.getPredecessors().size();
                for (int i = 0; i < size; i++) {
                    if (!discovered.containsKey(tempTask.getPredecessors().get(i))) {
                        queue.add(tempTask.getPredecessors().get(i));
                        discovered.put(tempTask.getPredecessors().get(i), true);
                    }
                }
            }
        }
        return estimateCounter;
    }

    public int findMinDuration(final String task) {
        final HashMap<String, Integer> maxDist = new HashMap<>();
        Task targetTask = null;
        for (Task searchTask : this.tasks) {
            if (searchTask.getTitle().equals(task)) {
                targetTask = searchTask;
                break;
            }
        }
        final ArrayList<Task> taskArrayList = topologycalSort(targetTask);
        final int taskArrayListSize = taskArrayList.size();
        int startIndex = 0;
        for (int i = 0; i < taskArrayListSize; i++) {
            if (taskArrayList.get(i).getPredecessors().isEmpty()) {
                startIndex = i;
                break;
            }
        }
        maxDist.put(taskArrayList.get(startIndex).getTitle(), taskArrayList.get(startIndex).getEstimate());
        for (int i = startIndex + 1; i < taskArrayListSize; i++) {
            final Task searchTask = taskArrayList.get(i);
            maxDist.put(searchTask.getTitle(), searchTask.getEstimate());
            int index = i - 1;
            for (int j = 0; j < searchTask.getPredecessors().size(); j++) {
                while (index >= 0 && !searchTask.getPredecessors().contains(taskArrayList.get(index))) {
                    index--;
                }
                maxDist.put(searchTask.getTitle(), Math.max(maxDist.get(searchTask.getTitle()), searchTask.getEstimate() + maxDist.get(taskArrayList.get(index).getTitle())));
                index--;
            }
        }
        return maxDist.get(targetTask.getTitle());
    }

    public int findMaxBonusCount(final String task) {
        // 여러군데에서 일을 시작할 수 있기 때문에 그 시작들을 모아두기 위해서
        // 1개의 가상 시작점 아래에 시작점들을 모아둔다
        final Task firstTask = new Task("virtualFirstTask", 0);
        final Queue<Task> queue = new LinkedList<>();
        int maxResult = 0;

        final HashMap<String, HashMap<String, Integer>> capacity = new HashMap<>();
        final HashMap<String, HashMap<String, Integer>> flow = new HashMap<>();

        Task endTask = null;
        boolean foundEndTask = false;
        for (int i = this.tasks.length - 1; i >= 0; i--) {
            //도착 Task를 찾는다
            if (!foundEndTask && this.tasks[i].getTitle().equals(task)) {
                endTask = this.tasks[i];
                foundEndTask = true;
            }
            //시작하는 Task들에 대해서 유량과 용량에 등록한다
            if (this.tasks[i].getPredecessors().isEmpty()) {
                makeCapacity(capacity, firstTask, this.tasks[i], this.tasks[i].getEstimate());
                makeFlow(flow, firstTask, this.tasks[i]);
            }
        }

        final ArrayList<Task> taskArrayList = topologycalSort(endTask);

        makeCapacityAndFlowHashMap(taskArrayList, capacity, flow);

        final HashMap<Task, Task> visited = new HashMap<>();

        while (true) {
            queue.add(firstTask);
            visited.clear();

            while (!queue.isEmpty()) {
                final Task tempTask = queue.remove();
                if (!capacity.containsKey(tempTask.getTitle())) {
                    continue;
                }
                final HashMap<String, Integer> nextTaskHashMap = capacity.get(tempTask.getTitle());
                for (int i = 0; i < taskArrayList.size(); i++) {
                    if (nextTaskHashMap.containsKey(taskArrayList.get(i).getTitle())) {
                        if (nextTaskHashMap.get(taskArrayList.get(i).getTitle()) - flow.get(tempTask.getTitle()).get(taskArrayList.get(i).getTitle()) > 0
                                && !visited.containsKey(taskArrayList.get(i))) {
                            queue.add(taskArrayList.get(i));
                            visited.put(taskArrayList.get(i), tempTask);
                            if (taskArrayList.get(i).equals(endTask)) {
                                break;
                            }
                        }
                    }
                }
            }

            if (!visited.containsKey(endTask)) {
                break;
            }

            Task preTask = visited.get(endTask);
            int minFlow = capacity.get(preTask.getTitle()).get(endTask.getTitle()) - flow.get(preTask.getTitle()).get(endTask.getTitle());
            for (Task flowTask = preTask; !flowTask.equals(firstTask); flowTask = visited.get(flowTask)) {
                minFlow = Math.min(minFlow, capacity.get(visited.get(flowTask).getTitle()).get(flowTask.getTitle()) - flow.get(visited.get(flowTask).getTitle()).get(flowTask.getTitle()));
            }
            preTask = visited.get(endTask);
            flow.get(preTask.getTitle()).put(endTask.getTitle(), flow.get(preTask.getTitle()).get(endTask.getTitle()) + minFlow);
            for (Task flowTask = preTask; !flowTask.equals(firstTask); flowTask = visited.get(flowTask)) {
                flow.get(visited.get(flowTask).getTitle()).put(flowTask.getTitle(), flow.get(visited.get(flowTask).getTitle()).get(flowTask.getTitle()) + minFlow);
            }

            maxResult += minFlow;

        }
        return maxResult;
    }

    private void makeCapacityAndFlowHashMap(final ArrayList<Task> taskArrayList, final HashMap<String, HashMap<String, Integer>> capacity, final HashMap<String, HashMap<String, Integer>> flow) {
        final int taskArrayListSize = taskArrayList.size();
        for (int i = 0; i < taskArrayListSize; i++) {
            if (!taskArrayList.get(i).getPredecessors().isEmpty()) {
                if (taskArrayList.get(i).getPredecessors().size() >= 2) {
                    final Task virtualTask = new Task("virtualTask" + i, 100);
                    taskArrayList.add(virtualTask);
                    for (Task predecessorTask : taskArrayList.get(i).getPredecessors()) {
                        makeCapacity(capacity, predecessorTask, virtualTask, 100);
                        makeFlow(flow, predecessorTask, virtualTask);
                    }
                    makeCapacity(capacity, virtualTask, taskArrayList.get(i), taskArrayList.get(i).getEstimate());
                    makeFlow(flow, virtualTask, taskArrayList.get(i));
                } else {
                    makeCapacity(capacity, taskArrayList.get(i).getPredecessors().get(0), taskArrayList.get(i), taskArrayList.get(i).getEstimate());
                    makeFlow(flow, taskArrayList.get(i).getPredecessors().get(0), taskArrayList.get(i));
                }
            }
        }
    }

    private void makeCapacity(final HashMap<String, HashMap<String, Integer>> capacity, final Task predecessor, final Task task, final int value) {
        if (!capacity.containsKey(predecessor.getTitle())) {
            final HashMap<String, Integer> hashMap = new HashMap<>();
            hashMap.put(task.getTitle(), value);
            capacity.put(predecessor.getTitle(), hashMap);
        } else {
            capacity.get(predecessor.getTitle()).put(task.getTitle(), value);
        }
    }

    private void makeFlow(final HashMap<String, HashMap<String, Integer>> flow, final Task predecessor, final Task task) {
        if (!flow.containsKey(predecessor.getTitle())) {
            final HashMap<String, Integer> hashMap = new HashMap<>();
            hashMap.put(task.getTitle(), 0);
            flow.put(predecessor.getTitle(), hashMap);
        } else {
            flow.get(predecessor.getTitle()).put(task.getTitle(), 0);
        }
    }

    private ArrayList<Task> topologycalSort(final Task startTask) {
        final HashMap<Task, Boolean> discoveredTask = new HashMap<>();
        final ArrayList<Task> sortedList = new ArrayList<>();

        topologycalSortRecursive(startTask, discoveredTask, sortedList);

        return sortedList;
    }

    private void topologycalSortRecursive(final Task task, final HashMap<Task, Boolean> discoveredTask, final ArrayList<Task> list) {
        discoveredTask.put(task, true);
        if (!task.getPredecessors().isEmpty()) {
            final List<Task> predecessorList = task.getPredecessors();
            for (Task predecessorTask : predecessorList) {
                if (discoveredTask.containsKey(predecessorTask)) {
                    continue;
                }
                topologycalSortRecursive(predecessorTask, discoveredTask, list);
            }
        }
        list.add(task);
    }
}

