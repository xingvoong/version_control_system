'''
1. medium, dfs, bfs, sorting

2. problem statement

Given a binary tree,
return the vertical order traversal of its nodes' values.
(ie, from top to bottom, column by column).
If two nodes are in the same row and column,
the order should be from left to right.
(sort on y axis)
input: [3,9,8,4,0,1,7,null,null,null,2,5]
(0's right child is 2 and 1's left child is 5)
output: [[4],[9,5],[3,0,1],[8,2],[7]]

3. solution in plain English/ pseudocode
+ intuition:
    project the tree in a (x,y) coordinate:
    sort the node base on (x,y)
+alg:
    + traverse the tree to build a dictionary:
        the key is x position
        the value is the node value
    + sorting:
        sort the key x
        sort the value of y
'''


def verticalOrder(root):

    dictionary = {}
    result = []

    verticalTraversal(root, 0, 0, dictionary)
    for x in sorted(dictionary.keys()):
        column = [i[1] for i in sorted(dictionary[x], key=lambda z:z[0])]
        result.append(column)

    return result


def verticalTraversal(root, x, y, dictionary):

    if not root:
        return root
    if x in dictionary:
        dictionary[x].append((y, root.val))
    else:
        dictionary[x] = [(y, root.val)]

    verticalTraversal(root.left, x - 1, y + 1, dictionary)
    verticalTraversal(root.right, x + 1, y + 1, dictionary)


'''
5. complexity analysis
runtime:
let N be the number nodes
verticalTraversal: takes O(N) to visit every node
in line 33: sorting in python takes O(NlogN)
so: O(N) + O(NlogN)
total: O(NlogN)
space: O(N) for dictionary
'''
