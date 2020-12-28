'''
- 1: Easy
- 2: Problem statement:

Given an array of integers nums and an integer target,
return indices of the two numbers such that they add up to target.

You may assume that each input would have exactly one solution,
and you may not use the same element twice.

You can return the answer in any order.
'''

'''
3. Solution:
make a dictionary to store value:index of the value
loop through each element x to find whether there is complement =  target -  x
+ if the complemet = target - x  not in dictionary:
    + add pair value:index to the dictionary
+ else
    +return [current position, index of complement]



4. Implementation:
'''


def twoSum(nums, target):
    dictionary = dict()
    pos = 0
    while pos < len(nums):
        complement = target - nums[pos]
        if complement not in dictionary:
            dictionary[nums[pos]] = pos
        else:
            return [pos, dictionary[complement]]

        pos += 1
    return None


assert twoSum([2, 7, 11, 15], 9) == [1, 0]

'''
5. Complexity analysis:
time: O(N), loop through the array
space: O(N), to maintain max size of the dictionary
'''
