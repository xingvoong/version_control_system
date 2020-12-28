'''
1. medium, design, heap, pq

2. problem statement:
Design a Leaderboard class, which has 3 functions:

addScore(playerId, score): Update the leaderboard by
adding score to the given player's score.
If there is no player with such id in the leaderboard,
add him to the leaderboard with the given score.
top(K): Return the score sum of the top K players.
reset(playerId): Reset the score of the player with the given id to 0
(in other words erase it from the leaderboard).
It is guaranteed that
the player was added to the leaderboard before calling this function.
Initially, the leaderboard is empty.

exp:
Input:
["Leaderboard","addScore","addScore","addScore","addScore",
"addScore","top","reset","reset","addScore","top"]
[[],[1,73],[2,56],[3,39],[4,51],[5,4],[1],[1],[2],[2,51],[3]]
Output:
[null,null,null,null,null,null,73,null,null,null,141]

3. solution in plain English/ pseudocode
- use a dictionary with key/value as playerID/list of scores
- addScore: add to dictionary
+ top(K): use a heap(or pq) in python,
with the piority key is the sum score of each player
    + heappop(heap) pop the smallest value,
        so to get the sum of largest k, I add -score to the queue
- reset: delete the key in a dictionary

4. implementation and tests
'''


class Leaderboard(object):

    def __init__(self):
        self.dictionary = {}

    def addScore(self, playerId, score):
        """
        :type playerId: int
        :type score: int
        :rtype: None
        """
        if playerId in self.dictionary:
            self.dictionary[playerId].append(score)
        else:
            self.dictionary[playerId] = [score]

    def top(self, K):
        """
        :type K: int
        :rtype: int
        """
        import heapq
        heap = []
        sum_topK = 0
        for scores in self.dictionary.values():
            heapq.heappush(heap, -sum(scores))
        while K > 0:
            sum_topK += heapq.heappop(heap)
            K -= 1
        return sum_topK * -1

    def reset(self, playerId):
        """
        :type playerId: int
        :rtype: None
        """
        del self.dictionary[playerId]


system = Leaderboard()
system.addScore(1, 73)
system.addScore(2, 56)
system.addScore(3, 39)
system.addScore(4, 51)
system.addScore(5, 4)
assert system.top(1) == 73
system.reset(1)
system.reset(2)
system.addScore(2, 51)
assert system.top(3) == 141

'''
5. complexity analysis:
time:
addScore: O(1)
top: O(N) + O(logK)
constructing a heap of N elements take O(N) time
where N is the total number of playerID
removing K element from the heap take O(logK)
reset: O(1)
'''
