'''
1. medium, heap, pq, sort, greedy

2. problem statement
Given an array of meeting time intervals
consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
find the minimum number of conference rooms required.

Example 1:
Input: [[0, 30],[5, 10],[15, 20]]
Output: 2
Example 2:
Input: [[7,10],[2,4]]
Output: 1

3. solution in plain English/ pseudocode:
intuition:
- I need one more room when the next interval overlap
with the current interval.
if the start of the next interval
is bigger than the end time of the earliest available room,
- I do not need more room.  Otherise, I need more room.
- I need a data structure to keep track of my rooms
and the earliest room that would be available.
Alg:
- sort the intervals bases on their start time.
+ use a priority queue to store all the meeting rooms:
    + prioritize on end time
    so I can know what room would be available next.
- always add the first end time of the first meeting
since we need at least one room
(if there is a meeting)
+ iterate throught the intervals, at each interval:
    +compare the end time of the next available room and
    the start time of the current interval:
        + if I don't need more room:
            change the end time for the next available room
        + else:
            make a new room by pushing in the new end time
return the size of priority queue.

4. implementation and tests:
'''


def minMeetingRooms(intervals):

    import heapq

    if len(intervals) == 0:
        return 0
    rooms = []
    intervals.sort(key=lambda x: x[0])

    heapq.heappush(rooms, intervals[0][1])
    for i in intervals[1:]:
        if rooms[0] <= i[0]:
            heapq.heappop(rooms)
        heapq.heappush(rooms, i[1])

    return len(rooms)


input1 = [[0, 30], [5, 10], [15, 20]]
input2 = []
input3 = [[7, 10], [2, 4]]
assert minMeetingRooms(input1) == 2
assert minMeetingRooms(input2) == 0
assert minMeetingRooms(input3) == 1

'''
5. runtime analysis:
time: O(NlogN)
let N be the total intervals.
sort in python: O(NlogN)
push in a heap: O(logN)
pop in a heap: O(logN)
for loop: O(N)
space: O(N), worst case we need N room
'''
