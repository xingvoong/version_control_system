'''
1. medium, dfs, bfs, union find

2. problem statement
Given an m x n 2d grid map of '1's (land) and '0's (water),
return the number of islands.
An island is surrounded by water
and is formed by connecting adjacent lands horizontally or vertically.
You may assume all four edges of the grid are all surrounded by water.
Input: grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
Output: 1

3. Solution in plain english or pseudocode
intuition: only count the first '1' we see.
flip all the neighbors '1' to '0'
Alg:
-check to see whether the grid is valid
+to count the island:
    +loop through grid:
        when we see a one:
            increase the count for island
            trigger a dfs on that index:
                where dfs will flip all the neightbor 1 to 0

+dfs:
    if the current index is 1:
        flip index 1 to 0:
        +check the bounds for row and column:
            trigger dfs on valid bounds

4. Implementation
'''


def numIslands(grid):
    """
        :type grid: List[List[str]]
        :rtype: int
    """
    def dfs(r, c):
        if grid[r][c] == "1":
            grid[r][c] = "0"
            if r - 1 >= 0:
                dfs(r-1, c)
            if r + 1 <= len(grid) - 1:
                dfs(r+1, c)
            if c - 1 >= 0:
                dfs(r, c-1)
            if c + 1 <= len(grid[0]) - 1:
                dfs(r, c+1)

    if grid is None and len(grid) == 0:
        return 0
    island = 0
    for r in range(len(grid)):
        for c in range(len(grid[0])):
            if grid[r][c] == '1':
                island += 1
                dfs(r, c)
    return island


input1 = [
    ["1", "1", "1", "1", "0"],
    ["1", "1", "0", "1", "0"],
    ["1", "1", "0", "0", "0"],
    ["0", "0", "0", "0", "0"]
]
input2 = [
    ["1", "1", "0", "0", "0"],
    ["1", "1", "0", "0", "0"],
    ["0", "0", "1", "0", "0"],
    ["0", "0", "0", "1", "1"]
]

assert numIslands(input1) == 1
assert numIslands(input2) == 3

'''
5. runtime analysis:
time:
assume that grid contains all 1, or all islands
one trigger of DFS will flip all 1 to 0, that takes O(M+N) time
iterate through the grid take O(M*N) time
to the leading term is O(M*N)

space: O(M*N), as explain aboved.
'''
