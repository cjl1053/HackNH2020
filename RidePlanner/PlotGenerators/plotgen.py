import matplotlib.pyplot as plt
import numpy as np

import db

drivers = db.get_drivers()[0][1]
print(drivers)

for driver in drivers:
    record = db.get_driver_info(driver)
    plt.plot(record['longitude'], record['latitude'], 'ro')
    route = db.get_driver_route(driver)
    plt.plot([record['longitude'], route[0]['longitude']], [record['latitude'], route[0]['latitude']], 'k')
    for i, passenger in enumerate(route):
        plt.plot(passenger['longitude'], passenger['latitude'], 'bo')
        if i < len(route)-1:
            plt.plot([route[i]['longitude'], route[i+1]['longitude']], [route[i]['latitude'], route[i+1]['latitude']], 'k')
        else:
            plt.plot([route[i]['longitude'], 42.921554], [route[i]['latitude'], -70.874153], 'k')

plt.show()
