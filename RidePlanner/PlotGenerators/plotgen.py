import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
import db

drivers = db.get_drivers()[0][1]
print(drivers)

plt.suptitle("Map Layout")
plt.title("Hampton Falls, NH")
plt.xlabel("Longitude")
plt.ylabel("Latitude")

plt.grid(alpha=.5)

red = mpatches.Patch(color="red", label="Drivers")
blue = mpatches.Patch(color="blue", label="Passengers")
green = mpatches.Patch(color="green", label="Polling Center")

plt.legend(handles=[red, blue, green])

for driver in drivers:
    record = db.get_driver_info(driver)
    route = db.get_driver_route(driver)
    plt.plot([record['latitude'], route[0]['latitude']], [record['longitude'], route[0]['longitude']], 'k')
    for i, passenger in enumerate(route):
        if i < len(route)-1:
            plt.plot([route[i]['latitude'], route[i+1]['latitude']], [route[i]['longitude'], route[i+1]['longitude']], 'k')
        else:
            plt.plot([route[i]['latitude'], 42.921554], [route[i]['longitude'], -70.874153], 'k')
        plt.plot(passenger['latitude'], passenger['longitude'], 'bo')
    plt.plot(record['latitude'], record['longitude'], 'ro')
plt.plot(42.921554, -70.874153, 'go')

plt.show()
