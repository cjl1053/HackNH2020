import urllib.parse
import db


def gen_map_uri(driver):
    record = db.get_driver_info(driver)
    route = db.get_driver_route(driver)

    base_uri = "https://www.google.com/maps/dir/?api=1"
    # base_uri = ""

    base_uri += f"&origin={record['latitude']},{record['longitude']}"
    base_uri += f"&destination={record['polling_location']}"

    if len(route) > 0:
        base_uri += "&waypoints="

    for i, passenger in enumerate(route):
        base_uri += f"{passenger['latitude']},{passenger['longitude']}"
        if i != len(route)-1:
            base_uri += '%7C'

    return base_uri
    # return r"https://www.google.com/maps/dir/?api=1&origin=Madrid,Spain&destination=Barcelona,Spain&waypoints=Zaragoza,Spain%7CHuesca,Spain&travelmode=driving&dir_action=navigate"


if __name__ == '__main__':
    print(gen_map_uri('Macauley'))
