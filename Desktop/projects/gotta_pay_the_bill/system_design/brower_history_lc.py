'''
1. medium

2. problem statement:
you have a browser of one tab
where you start on the homepage and you can visit another url,
get back in the history number of steps
or move forward in the history number of steps.

Implement the BrowserHistory class:

BrowserHistory(string homepage) Initializes the object
with the homepage of the browser.
void visit(string url) Visits url from the current page.
It clears up all the forward history.
string back(int steps) Move steps back in history.
If you can only return x steps in the history and steps > x,
you will return only x steps.
Return the current url after moving back in history
at most steps.
string forward(int steps) Move steps forward in history.
If you can only forward x steps in the history and steps > x,
you will forward only x steps.
Return the current url after forwarding in history at most steps.

Input:
["BrowserHistory","visit","visit","visit","back","back","forward","visit","forward","back","back"]
[["leetcode.com"],["google.com"],["facebook.com"],["youtube.com"],[1],[1],[1],["linkedin.com"],[2],[2],[7]]
Output:
[null,null,null,null,"facebook.com","google.com","facebook.com",null,"linkedin.com","google.com","leetcode.com"]

3. solution in plain English or pseudocode:
Use a list to store the history and a pointer
to return the current visited page.
the important method is visit because it clears all forward history.
in the example: there is a list of visisted page:
leetcode google facebook youtube
but the next visit, it will clear youtube,
add and move to a new page, linkedln.
So we need to remove the elements after current pointer
before adding new page to the list.
Then increase the current pointer after adding.

4. implementation
'''


class BrowserHistory(object):

    def __init__(self, homepage):
        """
        :type homepage: str
        """
        self.history = [homepage]
        self.curr = 0

    def visit(self, url):
        """
        :type url: str
        :rtype: None
        """
        while self.curr < len(self.history) - 1:
            self.history.pop()
        self.history.append(url)
        self.curr += 1

    def back(self, steps):

        if self.curr - steps < 0:
            self.curr = 0
        else:
            self.curr -= steps
        return self.history[self.curr]

    def forward(self, steps):
        """
        :type steps: int
        :rtype: str
        """
        if self.curr + steps <= len(self.history) - 1:
            self.curr += steps
        else:
            self.curr = len(self.history) - 1
        return self.history[self.curr]


test_system = BrowserHistory('google')
test_system.visit('facebook')
assert test_system.history == ['google', 'facebook']

system = BrowserHistory("netflix.com")
system.visit("instagram.com")
system.visit("google.com")
system.visit("youtube.com")
assert system.history == \
    ["netflix.com", "instagram.com", "google.com", "youtube.com"]
assert system.curr == 3
assert system.back(1) == "google.com"
assert system.back(1) == "instagram.com"
assert system.forward(1) == "google.com"
system.visit("bloomberg.com")
assert system.curr == 3
assert system.forward(2) == "bloomberg.com"
assert system.back(2) == "instagram.com"
assert system.back(7) == "netflix.com"

'''
5. complexity analysis
time:
visit: O(N), at most since we do a for loop with respect to length of history
where N is the total number of page visited
back: O(1), insert to the end of a list
forward: O(1), insert to the end of a list
space: O(N), a list to store the page visited
'''
