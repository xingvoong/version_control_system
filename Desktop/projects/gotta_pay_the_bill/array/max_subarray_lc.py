'''
1. easy
2. problem statement
Given an integer array nums,
find the contiguous subarray (containing at least one number)
which has the largest sum and return its sum.

3. solution
Greedy appoach.
Let the first element of the array be max sum and current sum
+ iterate over the array, starting from the second element:
    current sum = max(current element, current sum + current element)
    the max sum = max(max sum, current sum)
return max sum

4. implementation

'''


def maxSubArray(nums):
    n = len(nums)
    current_sum = max_sum = nums[0]
    for i in range(1, n):
        current_sum = max(nums[i], current_sum + nums[i])
        max_sum = max(current_sum, max_sum)
    return max_sum


assert maxSubArray([-2, 1, -3, 4, -1, 2, 1, -5, 4]) == 6
assert maxSubArray([1]) == 1
assert maxSubArray([0]) == 0
assert maxSubArray([-1]) == -1
assert maxSubArray([-2147483647]) == -2147483647

'''
5. complexity analysis
time: O(N), traverse the array once
space: O(1), only need 2 new space for 2 variables current sum and max sum
'''
