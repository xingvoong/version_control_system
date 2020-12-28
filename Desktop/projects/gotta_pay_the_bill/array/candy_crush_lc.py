'''
1: medium
2: problem statement
This question is about implementing
a basic elimination algorithm for Candy Crush.
Given a 2D integer array board representing the grid of candy,
different positive integers board[i][j] represent different types of candies.
A value of board[i][j] = 0 represents
that the cell at position (i, j) is empty.
The given board represents the state of the game following the player's move.
Now, you need to restore the board to a stable state by crushing candies
according to the following rules:

If three or more candies of the same type are
adjacent vertically or horizontally,
"crush" them all at the same time - these positions become empty.
After crushing all candies simultaneously,
if an empty space on the board has candies on top of itself,
then these candies will drop until they hit a candy or bottom at the same time.
(No new candies will drop outside the top boundary.)

After the above steps, there may exist more candies that can be crushed.
If so, you need to repeat the above steps.
If there does not exist more candies that can be crushed
(ie. the board is stable),
then return the current board.
You need to perform the above rules until the board becomes stable,
then return the current board.

Input:
board =
[[110,5,112,113,114],[210,211,5,213,214],[310,311,3,313,314],
[410,411,412,5,414],[5,1,512,3,3],[610,4,1,613,614],
[710,1,2,713,714],[810,1,2,1,1],[1,1,2,2,2],[4,1,4,4,1014]]

Output:
[[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],
[110,0,0,0,114],[210,0,0,0,214],[310,0,0,113,314],
[410,0,0,213,414],[610,211,112,313,614],[710,311,412,613,714],
[810,411,512,713,1014]]

3: solution in plain English
there are 2 main steps
- step 1, crush: scan through the board to crush the candies as require
since rows and columns can be crush all at the same time.
I use abs see whether they match the crush require.

- step2, gravity:
move positive number down, start from the bottom
fill empty with 0

4: implementation
'''


def candyCrush(board):
    if not board:
        board

    done = True

    # crush row
    for r in range(len(board)):
        for c in range(len(board[0])-2):
            num1 = abs(board[r][c])
            num2 = abs(board[r][c+1])
            num3 = abs(board[r][c+2])
            if num1 == num2 and num2 == num3 and num1 != 0:
                board[r][c] = -num1
                board[r][c+1] = -num2
                board[r][c+2] = -num3
                done = False

    # crush column
    for c in range(len(board[0])):
        for r in range(len(board)-2):
            num1 = abs(board[r][c])
            num2 = abs(board[r+1][c])
            num3 = abs(board[r+2][c])
            if num1 == num2 and num2 == num3 and num1 != 0:
                board[r][c] = -num1
                board[r+1][c] = -num2
                board[r+2][c] = -num3
                done = False

    # gravity
    if not done:
        for c in range(len(board[0])):
            index = len(board) - 1
            # drop candies down
            for r in range(len(board)-1, -1, -1):
                if board[r][c] > 0:
                    board[index][c] = board[r][c]
                    index -= 1
            # fill empty with 0
            for r in range(index, -1, -1):
                board[r][c] = 0
    # done means there is nothing else to crush
    # the board is stable
    if done:
        return board
    else:
        return candyCrush(board)


'''
5. test
'''
input = [[110, 5, 112, 113, 114], [210, 211, 5, 213, 214],
            [310, 311, 3, 313, 314], [410, 411, 412, 5, 414],
            [5, 1, 512, 3, 3], [610, 4, 1, 613, 614],
            [710, 1, 2, 713, 714], [810, 1, 2, 1, 1],
            [1, 1, 2, 2, 2], [4, 1, 4, 4, 1014]]

expected_output = [[0, 0, 0, 0, 0], [0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0], [110, 0, 0, 0, 114],
                    [210, 0, 0, 0, 214], [310, 0, 0, 113, 314],
                    [410, 0, 0, 213, 414], [610, 211, 112, 313, 614],
                    [710, 311, 412, 613, 714], [810, 411, 512, 713, 1014]]

assert candyCrush(input) == expected_output
'''
6. complexity analysis
1: time:
O(R*C)^2, it takes R*C to scan the board once.
May only crush one row or one column at a time.
It takes (R*C)^2 to make the stable.
we only stop when the board is stable.
O(1): since we use constant space, only one board.
'''
