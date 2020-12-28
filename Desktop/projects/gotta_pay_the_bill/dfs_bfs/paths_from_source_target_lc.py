'''
1. medium, bfs, dfs, graph

2. problem statement:

Given a directed acyclic graph (DAG) of n nodes labeled from 0 to n - 1,
find all possible paths from node 0 to node n - 1,
and return them in any order.
The graph is given as follows:
graph[i] is a list of all nodes you can visit from node i
(i.e., there is a directed edge from node i to node graph[i][j]).
Input: graph = [[1,2],[3],[3],[]]
Output: [[0,1,3],[0,2,3]]
Explanation: There are two paths: 0 -> 1 -> 3 and 0 -> 2 -> 3.

3. Solution in plain English:
+ use a queue to keep track of paths:
    always start with path [0]
+ while queue is not empthy:
    + pop off a path:
        + if last node of this path is target node:
            add this path to result list
        + else:
            + explore the neighbors of this last node:
                extend a neighbor to current path
                add this new path to the queue

4. implementation:

'''


def allPathsSourceTarget(graph):

    from collections import deque
    queue = deque()
    queue.append([0])
    target = len(graph) - 1
    result = []

    while len(queue) != 0:
        current_path = queue.popleft()
        last_node = current_path[-1]
        if last_node == target:
            result.append(current_path)
        else:
            for neighbor in graph[last_node]:
                new_path = current_path + [neighbor]
                queue.append(new_path)

    return result


input1 = [[1, 2], [3], [3], []]
input2 = [[4, 3, 1], [3, 2, 4], [3], [4], []]
input3 = [[1], []]
input4 = [[1, 2, 3], [2], [3], []]
input5 = [[1, 3], [2], [3], []]

expected_o2 = [[0, 4], [0, 3, 4], [0, 1, 4], [0, 1, 3, 4], [0, 1, 2, 3, 4]]
expected_o1 = [[0, 1, 3], [0, 2, 3]]
expected_o3 = [[0, 1]]
expected_o4 = [[0, 3], [0, 2, 3], [0, 1, 2, 3]]
expected_o5 = [[0, 3], [0, 1, 2, 3]]

assert allPathsSourceTarget(input1) == expected_o1
assert allPathsSourceTarget(input2) == expected_o2
assert allPathsSourceTarget(input3) == expected_o3
assert allPathsSourceTarget(input4) == expected_o4
assert allPathsSourceTarget(input5) == expected_o5

'''
5. complexity analysis:
runtime:
let N be the number of nodes
+ add each node, there are 2 possible path:
    - current old path
    - a new path
so there are roughly 2^N of paths
on line 47, to create a new path, I need O(N) time
total: O(N2^N)
space: O(N2^N), as explained above
'''
