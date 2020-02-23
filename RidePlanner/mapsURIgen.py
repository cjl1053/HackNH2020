import urllib.parse
import db


def gen_map_uri(driver):
    record = db.get_driver_info(driver)
    route = db.get_driver_route(driver)

    base_uri = "www.google.com/maps/dir/?api=1&"

    base_uri += f"origin={record['longitude']}, {record['latitude']}&"
    base_uri += f"destination={record['polling_location']}&"

    if len(route) > 0:
        base_uri += "waypoints="

    for i, passenger in enumerate(route):
        base_uri += f"{passenger['longitude']}, {passenger['latitude']}"
        if i != len(route)-1:
            base_uri += '|'

    return base_uri


if __name__ == '__main__':
    print(gen_map_uri('Macauley'))
