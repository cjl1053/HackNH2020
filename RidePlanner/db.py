from pymongo import MongoClient
import os

mydb = MongoClient()

db = mydb["users"]

driverTable = db.drivers
passengerTable = db.passengers

routeTable = db.routes


def add_driver(driver_post):
    if 'pollingLocation' in driver_post.keys():
        driver_post['polling_location'] = driver_post['pollingLocation']
        del driver_post['pollingLocation']
    return driverTable.insert_one(driver_post).inserted_id


def add_passenger(passenger_post):
    if 'pollingLocation' in passenger_post.keys():
        passenger_post['polling_location'] = passenger_post['pollingLocation']
        del passenger_post['pollingLocation']
    if 'startTime' in passenger_post.keys():
        passenger_post['start_time'] = passenger_post['startTime']
        del passenger_post['startTime']
    if 'endTime' in passenger_post.keys():
        passenger_post['end_time'] = passenger_post['endTime']
        del passenger_post['endTime']
    return passengerTable.insert_one(passenger_post).inserted_id


def add_route(driver, passengers):
    for i, passenger in enumerate(passengers):
        routeTable.insert_one(
            {"driver": driver.name, "passenger": passenger.name, "order": i + 1}
        )


def get_passenger_info(name):
    return passengerTable.find({"name": name})[0]


def get_driver_info(name):
    return driverTable.find({"name": name})[0]


def get_drivers():
    driverDestinations = []
    locationsDict = dict()

    for item in driverTable.find():

        x = item["polling_location"]
        if x not in locationsDict:
            locationsDict[x] = [item["name"]]
        else:
            locationsDict[x].append(item["name"])

    for key in locationsDict:
        driverDestinations.append((key, locationsDict[key]))

    return driverDestinations


def get_passengers():
    passengerDestinations = []
    locationsDict = dict()
    for item in passengerTable.find():
        x = item["polling_location"]
        if x not in locationsDict:
            locationsDict[x] = [item["name"]]
        else:
            locationsDict[x].append(item["name"])

    for key in locationsDict:
        passengerDestinations.append((key, locationsDict[key]))

    return passengerDestinations


def get_passengers_by_location(polling_location):
    return [p for p in passengerTable.find({"polling_location": polling_location})]


def get_passenger_assignment(name):
    for assignment in routeTable.find({"passenger": name}):
        driver_name = assignment['driver']
        return driver_name, get_driver_info(driver_name)['leave_time']


def get_driver_route(name):
    passengers = []
    # Grab every passenger assigned to this driver
    for route in routeTable.find({"driver": name}):
        passenger = get_passenger_info(route["passenger"])
        passengers.append({"name": passenger["name"], "longitude": passenger["longitude"],
                           "latitude": passenger["latitude"], "order": route["order"]})

    # return a sorted list by order, so the route is in the correct order
    return passengers


def clearDB():
    driverTable.drop()
    passengerTable.drop()
    routeTable.drop()
    db.accounts.drop()


def setupTests():
    # os.chdir(r"./RidePlanner")
    inDriversFile = open("testdrivers.csv", "r")
    inPassengerFile = open("testpassengers.csv", "r")

    # Skipping headers
    inDriversFile.readline()
    inPassengerFile.readline()

    # Drivers
    for line in inDriversFile:
        line = line.rstrip("\n\r").split(",")
        toPush = {
            "name": line[0],
            "longitude": float(line[2]),
            "latitude": float(line[1]),
            "capacity": int(line[3]),
            "leave_time": int(line[4]),
            "polling_location": line[5],
        }
        temp = driverTable.insert_one(toPush).inserted_id

    # Passangers
    for line in inPassengerFile:
        line = line.rstrip("\n\r").split(",")
        toPush = {
            "name": line[0],
            "longitude": float(line[2]),
            "latitude": float(line[1]),
            "amount": int(line[3]),
            "start_time": int(line[4]),
            "end_time": int(line[5]),
            "polling_location": line[6],
        }
        passengerTable.insert_one(toPush)

    inDriversFile.close()
    inPassengerFile.close()


if __name__ == "__main__":
    # print(get_driver_route("Macauley"))
    # print(get_passenger_assignment("Steve"))

    # Thicc Brain Joey Testing Reigon
    clearDB()
    setupTests()
