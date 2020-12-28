'''
1. hard
2. problem statement
given two sorted arrays nums1 and nums2 of size m and n respectively,
return the median of the two sorted arrays.

Follow up: The overall run time complexity should be O(log (m+n)).

3. solution in plain English or pseudocode
    find 4 crucial elements, use this 4 elements to find the median
    use binary search, partition 2 arrays into 2 parts such that
    + the number of element in the first haft
    equal to the number in the second haft
    + all elements in the first haft are smaller
    than all the elements in the second haft.
    + check whether the merege array is odd or even:
        +if odd: get the mid element
        +if even: get the averge of the 2 middle elements

4. implementation

'''


def findMedianSortedArray(nums1, nums2):
    x = len(nums1)
    y = len(nums2)

    # first array shorter than second array
    if (x > y):
        nums1, nums2, x, y = nums2, nums1, y, x

    partition_x, partition_y = 0, 0
    maxLeft_x, maxLeft_y, minRight_x, minRight_y = 0, 0, 0, 0
    low = 0
    high = x

    # partition_x + partition_y = (x+y+1)/2
    while low <= high:
        partition_x = int((low+high)/2)
        partition_y = int((x+y+1)/2 - partition_x)

        # if no x element in the leftside
        if (partition_x == 0):
            maxLeft_x = float('-inf')
        else:
            maxLeft_x = nums1[partition_x - 1]

        # if no x element in the rightside
        if (partition_x == x):
            minRight_x = float('inf')
        else:
            minRight_x = nums1[partition_x]

        # if no y elemnt in the leftside
        if(partition_y == 0):
            maxLeft_y = float('-inf')
        else:
            maxLeft_y = nums2[partition_y-1]

        # if no y element in the rightside
        if (partition_y == y):
            minRight_y = float('inf')
        else:
            minRight_y = nums2[partition_y]

        # the partition condition to return
        if maxLeft_x <= minRight_y and maxLeft_y <= minRight_x:
            # even
            if ((x + y) % 2) == 0:
                return 0.5 * \
                    (max(maxLeft_x, maxLeft_y) + min(minRight_x, minRight_y))
            # odd
            return max(maxLeft_x, maxLeft_y)

        elif maxLeft_x > minRight_y:
            # move toward left in x
            high = partition_x - 1
        else:
            # move toward right in x
            low = partition_x + 1


assert findMedianSortedArray([1, 3], [2]) == 2
assert findMedianSortedArray([1, 2], [3, 4]) == 2.5
assert findMedianSortedArray([0, 0], [0, 0]) == 0
assert findMedianSortedArray([], [1]) == 1
assert findMedianSortedArray([2], []) == 2

'''
5. complexity analysis:
O(log(m+n))
'''
