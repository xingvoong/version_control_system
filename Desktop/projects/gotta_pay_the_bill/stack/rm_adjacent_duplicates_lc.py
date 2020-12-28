'''
1. medium, stack.

2. problem statement
Given a string s, a k duplicate removal consists of choosing k adjacent and
equal letters from s and removing them causing the left and
the right side of the deleted substring to concatenate together.

We repeatedly make k duplicate removals on s until we no longer can.

Return the final string after all such duplicate removals have been made.

It is guaranteed that the answer is unique.
input: s = "deeedbbcccbdaa", k = 3
Output: "aa"
Explanation:
First delete "eee" and "ccc", get "ddbbbdaa"
Then delete "bbb", get "dddaa"
Finally delete "ddd", get "aa"

3. solution in plain English or pseudocode:
- use a stack to track the characters and their count.
- always add the first element of s with count 1 to the stack
+ iterate through the string s:
    + if s[index] equals to the top of stack:
        + if the count equals to k-1:
            pop that off the stack
        + else:
            increase the count of the character at the top of the stack
    + else:
        add to stack with count 1
- use python join to turn a list to a string for return value

4: implementation and test
'''


def removeDuplicates(s, k):
    stack = []
    if not s:
        return ''
    stack.append([s[0], 1])
    index = 1
    while index < len(s):
        if len(stack) != 0 and s[index] == stack[-1][0]:
            if stack[-1][1] == k - 1:
                stack.pop()
            else:
                stack[-1][1] += 1
        else:
            stack.append([s[index], 1])
        index += 1
    return "".join(x[0]*x[1] for x in stack)


input1 = "pbbcggttciiippooaais"
input2 = 'abcdef'
input3 = "deeedbbcccbdaa"
input4 = ''
assert removeDuplicates(input1, 2) == 'ps'
assert removeDuplicates(input2, 3) == 'abcdef'
assert removeDuplicates(input3, 3) == 'aa'
assert removeDuplicates(input4, 1) == ''

'''
5. complexity analysis:
time: O(N), iteratee through the string s
space: O(N), for the stack

'''
