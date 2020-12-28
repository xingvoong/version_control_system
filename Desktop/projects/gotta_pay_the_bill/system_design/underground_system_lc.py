
'''
1: medium
2: problem statement:
Implement the class UndergroundSystem that supports three methods:

I. checkIn(int id, string stationName, int t)

A customer with id card equal to id, gets in the station stationName at time t.
A customer can only be checked into one place at a time.

II. checkOut(int id, string stationName, int t)

A customer with id card equal to id,
gets out from the station stationName at time t.

III. getAverageTime(string startStation, string endStation)

-Returns the average time to travel between the startStation
and the endStation.
-The average time is computed from all the previous traveling
from startStation to endStation that happened directly.
-Call to getAverageTime is always valid.

exp:
Input
["UndergroundSystem","checkIn","checkOut","getAverageTime","checkIn",
"checkOut","getAverageTime","checkIn","checkOut","getAverageTime"]
[[],[10,"Leyton",3],[10,"Paradise",8],["Leyton","Paradise"],[5,"Leyton",10],
[5,"Paradise",16],["Leyton","Paradise"],[2,"Leyton",21],[2,"Paradise",30],["Leyton","Paradise"]]

Output
[null,null,null,5.00000,null,null,5.50000,null,null,6.66667]

Explanation
UndergroundSystem undergroundSystem = new UndergroundSystem();
undergroundSystem.checkIn(10, "Leyton", 3);
undergroundSystem.checkOut(10, "Paradise", 8);
undergroundSystem.getAverageTime("Leyton", "Paradise"); // return 5.00000
undergroundSystem.checkIn(5, "Leyton", 10);
undergroundSystem.checkOut(5, "Paradise", 16);
undergroundSystem.getAverageTime("Leyton", "Paradise"); // return 5.50000
undergroundSystem.checkIn(2, "Leyton", 21);
undergroundSystem.checkOut(2, "Paradise", 30);
undergroundSystem.getAverageTime("Leyton", "Paradise"); // return 6.66667

3: solution explain in pain English
focus on getAveragetime because it is the only function that return a value
use dictionary to store the total_time and total_trips

4: runtime analysis
time:
checkIn: O(1), insert to a dict
checkOut: O(1)
getAverageTime: O(1), dividing
space: O(N^2 + I)
where N is the number of stations.
Each station has passengers enter and exit at the station.
where I is the number of passenger check in but have not checkout


5: implementation
'''
import collections


class UndergroundSystem(object):

    def __init__(self):
        self.check_in_data = {}
        self.journey_data = collections.defaultdict(lambda: [0, 0])

    def checkIn(self, id, stationName, t):
        self.check_in_data[id] = [stationName, t]

    def checkOut(self, id, stationName, t):
        startStation, startTime = self.check_in_data.pop(id)
        self.journey_data[(startStation, stationName)][0] += (t-startTime)
        self.journey_data[(startStation, stationName)][1] += 1

    def getAverageTime(self, startStation, endStation):
        total_time, total_trips = self.journey_data[(startStation, endStation)]
        return float(total_time)/float(total_trips)


# test
test_system = UndergroundSystem()


test_system.checkIn(1, 'powell', 10)
test_system.checkOut(1, 'mongonary', 11)
travel_time = test_system.getAverageTime('powell', 'mongonary')
assert travel_time == 1.0

system2 = UndergroundSystem()
system2.checkIn(45, "embarcadero", 4)
system2.checkIn(32, "montgomery", 9)
system2.checkIn(27, "embarcadero", 11)
system2.checkOut(45, "powell", 17)
system2.checkOut(27, "powell", 22)
system2.checkOut(32, "civic_center", 24)
assert system2.getAverageTime("montgomery", "civic_center") == 15

system2.checkIn(10, "embarcadero", 28)
assert system2.getAverageTime("embarcadero", "powell") == 12

system2.checkOut(10, "powell", 36)
system2.getAverageTime("embarcadero", "powell") == 12
