from math import sqrt
from copy import deepcopy
from sys import argv

import db


class Driver:
    def __init__(self, name, start_long, start_lat, capacity):
        self.name = name
        self.start_long = start_long
        self.start_lat = start_lat
        self.current_long = start_long
        self.current_lat = start_lat
        self.max_capacity = capacity
        self.capacity = capacity

        self.route = []

    def __repr__(self):
        return f"Driver starting at ({self.start_long}, {self.start_lat}) with capacity {self.max_capacity}"


class Passenger:
    def __init__(self, name, longitude, latitude, amount):
        self.name = name
        self.longitude = longitude
        self.latitude = latitude
        self.amount = amount

    def __repr__(self):
        return f"{self.amount} passenger(s) at ({self.longitude}, {self.latitude})"


def get_distance(driver, passenger):
    return sqrt((driver.current_long - passenger.longitude)**2 + (driver.current_lat - passenger.latitude)**2)


class Plan:
    def __init__(self, drivers, remaining_passengers):
        self.routes = []
        for driver in drivers:
            self.routes.append((driver, deepcopy(driver.route)))
        self.score = sum([p.amount for p in remaining_passengers])


class Planner:
    def __init__(self, drivers, passengers):
        self.drivers = drivers
        self.passengers = passengers
        self.best_plan = None

    def generate_plan(self, drivers, passengers):
        # Quick end cases: if we're out of drivers, we've hit a child node
        # If we're out of passengers, all of them have been assigned and we're done
        if len(drivers) == 0 or len(passengers) == 0:
            plan = Plan(self.drivers, passengers)
            if self.best_plan is None or plan.score < self.best_plan.score:
                self.best_plan = plan
            return self.best_plan, len(passengers) == 0

        cur_driver = drivers[0]
        next_passengers = sorted(passengers, key=lambda sort_passenger: get_distance(cur_driver, sort_passenger))

        next_passenger = None

        for passenger in next_passengers:
            if passenger.amount <= cur_driver.capacity:
                next_passenger = passenger
                break

        # Case for where the driver has no more valid passengers due to capacity constraints
        if next_passenger is None:
            return self.generate_plan(drivers[1:], passengers)

        # General case, recurse down and continue assignments
        cur_long, cur_lat = cur_driver.current_long, cur_driver.current_lat

        cur_driver.route.append(next_passenger)
        cur_driver.capacity -= next_passenger.amount
        passengers.remove(next_passenger)
        cur_driver.current_long = next_passenger.longitude
        cur_driver.current_lat = next_passenger.latitude

        if self.generate_plan(drivers[1:] + drivers[:1], passengers)[1]:
            return self.best_plan, True

        cur_driver.current_lat = cur_lat
        cur_driver.current_long = cur_long
        passengers.append(next_passenger)
        cur_driver.capacity += next_passenger.amount
        cur_driver.route = cur_driver.route[:-1]

        return self.best_plan, False

    def generate_plan2(self, drivers, passengers):
        # Quick end cases: if we're out of drivers, we've hit a child node
        # If we're out of passengers, all of them have been assigned and we're done
        if len(drivers) == 0 or len(passengers) == 0:
            plan = Plan(self.drivers, passengers)
            if self.best_plan is None or plan.score < self.best_plan.score:
                self.best_plan = plan
            return self.best_plan, len(passengers) == 0

        next_passenger = passengers[0]
        driver_order = sorted(drivers, key=lambda sort_driver: get_distance(sort_driver, next_passenger))

        selected_driver = None
        for d in driver_order:
            if d.capacity <= next_passenger.amount:
                selected_driver = d
                break

        if selected_driver is None:
            return self.generate_plan2(drivers, passengers[1:])

        # General case, recurse down and continue assignments
        cur_long, cur_lat = selected_driver.current_long, selected_driver.current_lat

        selected_driver.route.append(next_passenger)
        selected_driver.capacity -= next_passenger.amount
        selected_driver.current_long = next_passenger.longitude
        selected_driver.current_lat = next_passenger.latitude

        if self.generate_plan(drivers, passengers[1:])[1]:
            return self.best_plan, True

        selected_driver.current_lat = cur_lat
        selected_driver.current_long = cur_long
        selected_driver.capacity += next_passenger.amount
        selected_driver.route = selected_driver.route[:-1]

        return self.best_plan, False


def write_plan(plan):
    for route in plan.routes:
        db.add_route(route[0], route[1])


def read_drivers(driver_list):
    driver_records = [db.get_driver_info(d) for d in driver_list]
    return [Driver(record['name'], record['longitude'], record['latitude'], record['capacity']) for record in driver_records]


def passengers_by_location(location):
    ps = db.get_passengers_by_location(location)
    return [Passenger(p['name'], p['longitude'], p['latitude'], p['amount']) for p in ps]


def run():
    ds = db.get_drivers()

    for location, driver_list in ds:
        drivers = read_drivers(driver_list)
        passengers = passengers_by_location(location)
        planner = Planner(drivers, passengers)
        plan, result = planner.generate_plan2(drivers, passengers)

        print("Did route get all passengers? " + str(result))
        for route in plan.routes:
            print(route)

        if '-p' in argv:
            write_plan(plan)


if __name__ == '__main__':
    run()