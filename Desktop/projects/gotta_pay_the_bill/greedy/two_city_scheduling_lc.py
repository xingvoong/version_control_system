'''
1: medium, greedy
2: problem statement

A company is planning to interview 2n people.
Given the array costs where costs[i] = [aCosti, bCosti],
the cost of flying the ith person to city a is aCosti,
and the cost of flying the ith person to city b is bCosti.

Return the minimum cost to fly every person
to a city such that exactly n people arrive in each city.

Input: costs = [[10,20],[30,200],[400,50],[30,20]]
Output: 110
Explanation:
The first person goes to city A for a cost of 10.
The second person goes to city A for a cost of 30.
The third person goes to city B for a cost of 50.
The fourth person goes to city B for a cost of 20.

The total minimum cost is 10 + 30 + 50 + 20 = 110
to have half the people interviewing in each city.

3. solution in plain English or pseudocode:

intuition: for every pair of flying costs between city A and B,
we want to see flying to which city is cheaper.
if costA - costB < 0 means that flying to city A is cheaper than city B.
So if we sort the list in ascending order,
according to the saving cost when flying to city A over city B,
we can pick the minumum option
Since each city can have n people,
the first n can go to A and the second haft go to B.

4. implementation and test cases
'''


def twoCitySchedCost(costs):

    costs.sort(key=lambda x: x[0] - x[1])

    n = len(costs) // 2
    min_cost = 0
    # first n go to A
    for i in range(n):
        min_cost += costs[i][0]
    # remain go to B
    for i in range(n, len(costs), 1):
        min_cost += costs[i][1]

    return min_cost


input1 = [[10, 20], [30, 200], [400, 50], [30, 20]]
input2 = [[259, 770], [448, 54], [926, 667],
          [184, 139], [840, 118], [577, 469]]
input3 = [[515, 563], [451, 713], [537, 709], [343, 819],
          [855, 779], [457, 60], [650, 359], [631, 42]]
assert twoCitySchedCost(input1) == 110
assert twoCitySchedCost(input2) == 1859
assert twoCitySchedCost(input3) == 3086

'''
5. complexity analysis:
time: O(N(logN)), built-in sorting in Python
space: O(1) no extra space require besides n and min_costs variable
'''
