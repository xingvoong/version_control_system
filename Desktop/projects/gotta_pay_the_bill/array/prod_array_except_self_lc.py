'''
1. medium, array
2. problem statement

Given an array nums of n integers where n > 1,
return an array output such that
output[i] is equal to the product of all the elements of nums except nums[i].
Constraint: It's guaranteed that the product of the elements of any prefix or
suffix of the array (including the whole array)
fits in a 32 bit integer.
example:
Input:  [1,2,3,4]
Output: [24,12,8,6]


3. solution in plain English or pseudocode
 key point: the product of an element except itself equal
 to the product of all the element to it left
 and the product of all element to it right



4. implementation

'''


def productExceptSelf(nums):
    n = len(nums)
    toReturn = [None]*n
    toReturn[0] = 1
    prev_right_prod = 1

    # compute products in left of each element
    for i in range(1, n, 1):
        toReturn[i] = nums[i-1] * toReturn[i-1]

    # compute products in right of each element
    for i in range(n-2, -1, -1):
        new_prod_right = nums[i+1] * prev_right_prod
        # compute the result
        toReturn[i] *= new_prod_right
        prev_right_prod = new_prod_right

    return toReturn


assert productExceptSelf([1, 2, 3, 4]) == [24, 12, 8, 6]

'''
5. complexity analysis
time: O(N), traverse through the array
space: O(1), only use one result array

'''
