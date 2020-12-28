'''
1. medium, array

2: problem statement
A transaction is possibly invalid if:
the amount exceeds $1000, or;
if it occurs within (and including) 60 minutes of another transaction
with the same name in a different city.

Each transaction string transactions[i]
consists of comma separated values representing the name,
time (in minutes), amount, and city of the transaction.

Given a list of transactions,
return a list of transactions that are possibly invalid.
You may return the answer in any order.

Input: transactions = ["alice,20,800,mtv","alice,50,100,beijing"]
Output: ["alice,20,800,mtv","alice,50,100,beijing"]
Explanation: The first transaction is invalid
because the second transaction occurs within a difference of 60 minutes,
have the same name and is in a different city.
Similarly the second one is invalid too

3. solution in plain English or pseudocode:
split the string into 4 different field
loop through the string and check the requirements for each index

4.Implementation

'''


def invalidTransactions(transactions):

    name = []
    time = []
    amount = []
    city = []
    toReturn = []

    for s in transactions:
        temp = s.split(',')
        name.append(temp[0])
        time.append(int(temp[1]))
        amount.append(int(temp[2]))
        city.append(temp[3])

    for i in range(len(amount)):
        if amount[i] > 1000:
            toReturn.append(transactions[i])

    for i in range(len(name)):
        for j in range(i+1, len(name)):
            if name[i] == name[j] and city[i] != city[j] \
                    and abs(time[i] - time[j]) <= 60:
                if transactions[i] not in toReturn:
                    toReturn.append(transactions[i])
                if transactions[j] not in toReturn:
                    toReturn.append(transactions[j])
    return toReturn


input1 = ["alice,20,800,mtv", "alice,50,100,beijing"]
expected_output1 = ["alice,20,800,mtv", "alice,50,100,beijing"]

input2 = ["alice,20,800,mtv", "alice,50,1200,mtv"]
input3 = ["alice,20,800,mtv", "bob,50,1200,mtv"]
assert invalidTransactions(input1) == expected_output1
assert invalidTransactions(input2) == ["alice,50,1200,mtv"]
assert invalidTransactions(input3) == ["bob,50,1200,mtv"]

'''
5. runtime analysis:
time: O(N^2), 1 nested for loop
space: O(N)
'''
