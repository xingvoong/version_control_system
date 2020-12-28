'''
1. medium, design, hashmap, double l.l

2. problem statement

Design a data structure that follows the constraints of
a Least Recently Used (LRU) cache.

Implement the LRUCache class:

LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
int get(int key) Return the value of the key if the key exists,
otherwise return -1.
void put(int key, int value) Update the value of the key if the key exists.
Otherwise, add the key-value pair to the cache.
If the number of keys exceeds the capacity from this operation,
evict the least recently used key.
Follow up:
Could you do get and put in O(1) time complexity?
Example:
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
Output
[null, null, null, 1, null, -1, null, -1, 3, 4]

3: solution in plain English, or pseudocode
there are 2 approaches:
Approach 1: Use an Ordered dictionary data struction in python
for detail of ordered dict data structure:
    https://www.journaldev.com/21807/python-ordereddict
the class object is a Ordered dictionary object
get: +change the order of the entry:
        - move to end
put:
+ insert in order by using move to end
+ remove items in FIFO order

Approach 2: use a hashmap and double linklist to store key and value
+ intuition:
    use hashmap: easy access elements, to achieve get in O(1) time
    + use double linklist:
        add and remove a node next to head is O(1)
        add and remove a node next to tail is O(1)
        + when a cache is use:
            we can add to head
        + to get the least recent use cache
            we can remove the last node
+ get(node):
    check whether node is in hashmap
    move the node position to after head
    return the node.value
+ put(key, value):
    + if node is not in hashmap:
        make a new node key key-value
        put the node to the hashmap
        add the node to head of linked list
        increase the size of the current cache
        +if the size is greater then capacity:
            remove the node next to tail of DLL, aka lease recent use
            remove the key from the hashmap
            decrease the size
    + else:
        update the value
        move the position of node near head

4: implementaion
'''

# approach 1
'''
from collections import OrderedDict

class LRUCache(OrderedDict):
    def  __init__ (self, capacity):
        self.capacity = capacity

    def get(self, key):
        if key not in self:
            return -1
        self.move_to_end(key)
        return self[key]

    def put(self, key, value):
        if key in self:
            self.move_to_end(key)
        self[key] =  value
        if len(self) > self.capacity:
            #FIFO
            self.popitem(last = False)
'''


# approach 2
class DLLNode():

    def __init__(self):
        self.key = 0
        self.value = 0
        self.prev = None
        self.next = None


class LRUCache():
    def add_to_head(self, node):
        node.prev = self.head
        node.next = self.head.next

        self.head.next.prev = node
        self.head.next = node

    def remove_node(self, node):
        prev = node.prev
        new_next = node.next
        prev.next = new_next
        new_next.prev = prev

    def move_to_head(self, node):
        self.remove_node(node)
        self.add_to_head(node)

    def remove_tail(self):
        # get the node so
        # we can delete it in the cache too
        node = self.tail.prev
        self.remove_node(node)
        return node

    def __init__(self, capacity):
        self.cache = {}
        self.size = 0
        self.capacity = capacity
        self.head, self.tail = DLLNode(), DLLNode()

        self.head.next = self.tail
        self.tail.prev = self.head

    def get(self, key):
        node = self.cache.get(key)
        if not node:
            return -1
        self.move_to_head(node)
        return node.value

    def put(self, key, value):
        node = self.cache.get(key)
        if not node:
            new_node = DLLNode()
            new_node.key = key
            new_node.value = value

            self.cache[key] = new_node
            self.add_to_head(new_node)
            self.size += 1

            if self.size > self.capacity:
                tail = self.remove_tail()
                del self.cache[tail.key]
                self.size -= 1
        else:
            node.value = value
            self.move_to_head(node)


lru_cache = LRUCache(2)
lru_cache.put(1, 1)
lru_cache.put(2, 2)
assert lru_cache.get(1) == 1
lru_cache.put(3, 3)
assert lru_cache.get(2) == -1
lru_cache.put(4, 4)
assert lru_cache.get(1) == -1
lru_cache.get(3) == 3
lru_cache.get(4) == 4

'''
5. complexity analysis
runtime:
get(key): O(1)
put(key, value): O(1)
space:
'''
