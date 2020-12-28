'''
1. medium, array, sort
2. problem statement
Given an array of intervals where intervals[i] = [starti, endi],
merge all overlapping intervals,
and return an array of the non-overlapping intervals
that cover all the intervals in the input.
Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlaps,
merge them into [1,6].
Input: intervals = [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervals [1,4] and [4,5] are considered overlapping.

3. solution in plain English
- sort the intervals on their begin times
- add the first interval to the result list

let the end time of previous result interval = x
let the begin time of the current interval = y
let the end time of the current interval = z
+ iterate through the intervals:
    for 2 consecutive intervals:
    + if x < y and x < z:
        append current interval to result list
    + elif x >= y and x < z:
        change the end time of previous interval to z
return the result list

4. implementation

'''


def merge(intervals):

    intervals.sort(key=lambda x: x[0])
    result = [intervals[0]]

    for interval in intervals[1:]:
        current_end = result[-1][1]
        if current_end < interval[0] and current_end < interval[1]:
            result.append(interval)
        elif current_end >= interval[0] and current_end < interval[1]:
            result[-1][1] = interval[1]
    return result


input1 = [[1, 3], [2, 6], [8, 10], [15, 18]]
input2 = [[1, 4], [4, 5]]
assert merge(input1) == [[1, 6], [8, 10], [15, 18]]
assert merge(input2) == [[1, 5]]

'''
5. complexity analysis:
time: O(NlogN), sorting in python
space: O(N), to result the result

'''
