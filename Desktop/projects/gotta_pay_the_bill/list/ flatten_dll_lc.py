'''
1. difficulty and related topic:
medium, linklist, dfs
2. problem statement:

You are given a doubly linked list
which in addition to the next and previous pointers,
it could have a child pointer,
which may or may not point to a separate doubly linked list.
These child lists may have one or more children of their own,
and so on, to produce a multilevel data structure,
as shown in the example below.

Flatten the list so that all the nodes appear in
a single-level, doubly linked list.
You are given the head of the first level of the list.
Input: head = [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
Output: [1,2,3,7,8,11,12,9,10,4,5,6]

3. solution in plain English or pseudocode
set a variable to store the head of the list, call it start.
+traverse through the list:
    +if a node has a child:
        append the remain of the list to a stack
        set the child become next node
        set prev, next, child pointer accordingly
    +when at the end of the list:
        +check whether the stack is empty, if not:
            pop element from the stack, add to the list
            set node pointer accordingly
    return start

4. implementation
'''


def flatten(head):
    start = head
    stack = []
    while head:
        if head.child:
            if head.next:
                stack.append(head.next)
                head.child.prev = head
                head.next = head.child
                head.child = None

        if head.next is None and len(stack) != 0:
            head.next = stack.pop()
            head.next.prev = head

        head = head.next
    return start


'''
5. complexity analysis
let N be the number of node
O(N), visit the each node once
'''
