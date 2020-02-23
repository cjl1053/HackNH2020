from pymongo import MongoClient

mydb = MongoClient()

db = mydb["users"]

driverTable = db.drivers
passengerTable = db.passengers

routeTable = db.routes


def add_driver(driver_post):
    return driverTable.insert_one(driver_post).inserted_id


def add_passenger(passenger_post):
    return passengerTable.insert_one(passenger_post).inserted_id


def add_route(driver, passengers):
    for i, passenger in enumerate(passengers):
        routeTable.insert_one({"driver": driver.name, "passenger": passenger.name, "order": i+1})


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


def get_passenger_assignment(name):
    for route in routeTable.find():
        if name in route[1]:
            driver = get_driver_info(route[0])
            return driver['name'], driver['leaveTime']


def get_driver_route(name):
    passengers = []
    # Grab every passenger assigned to this driver
    for route in routeTable.find():
        if route['driver'] == name:
            passenger = get_passenger_info(route['passenger'])
            passengers.append((passenger['name'], passenger['longitude'], passenger['latitude'], passenger['order']))

    # return a sorted list by order, so the route is in the correct order
    return sorted(passengers, key=lambda p: p[3])


def clearDB():
    driverTable.drop()
    passengerTable.drop()

if __name__ == '__main__':
    print(get_driver_route("Connor"))