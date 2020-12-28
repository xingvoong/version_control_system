'''
1. medium, tree, hashtable

2. problem statement:
Given a binary tree, return the vertical order traversal of its nodes values.

For each node at position (X, Y),
its left and right children respectively will
be at positions (X-1, Y-1) and (X+1, Y-1).

Running a vertical line from X = -infinity to X = +infinity,
whenever the vertical line touches some nodes,
we report the values of the nodes in order
from top to bottom (decreasing Y coordinates).

If two nodes have the same position,
then the value of the node that is reported first is the value that is smaller.

Return an list of non-empty reports in order of X coordinate.
Every report will have a list of values of nodes.

3. solution in plain English or pseudocode:
+intuition: project the tree in a (x,y) cordinate.
    sort the nodes base on (x, y, node value)
algorithm:
    + traverse the tree to build a dictionary:
        the key is x position
        value is a tuple of (y position, node value)
    +sorting:
        sort the key/x positions of the dictionary
        + if x and y are the same:
            sort on node value, sorted(dictionary[x])
4.Implementation:
class TreeNode(object):
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right
'''


def verticalOrder(self, root, x, y, dictionary):
    if not root:
        return root
    if x in dictionary:
        dictionary[x].append((y, root.val))
    else:
        dictionary[x] = [(y, root.val)]
    self.verticalOrder(root.left, x - 1, y + 1, dictionary)
    self.verticalOrder(root.right, x + 1, y + 1, dictionary)


def verticalTraversal(self, root):

    x = 0
    y = 0
    dictionary = {}
    toReturn = []

    self.verticalOrder(root, x, y, dictionary)
    for x in sorted(dictionary.keys()):
        column = [i[1] for i in sorted(dictionary[x])]
        toReturn.append(column)
    return toReturn


'''
5.complexity analysis:
time:
verticalOrder takes O(N) time to traverse the tree
built-in sort function in python take O(NlogN)
so O(N) +  O(NlogN)
O(NlogN) in total
space: O(N), linear space to build the dictionary
'''
