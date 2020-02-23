from http.server import *
import json

import db
import AccountManagement
import mapsURIgen


class RideShareRequestHandler(BaseHTTPRequestHandler):
    def _set_headers(self):
        self.send_response(200)
        self.send_header('Content-type', 'application/json')
        self.end_headers()

    def do_GET(self):
        self._set_headers()
        uri_parts = self.path[1:].split('/')
        if uri_parts[0] == "drivers":
            self.wfile.write(self.get_driver_route(uri_parts[1]))
        elif uri_parts[0] == "passengers":
            self.wfile.write(self.get_passenger_assignment(uri_parts[1]))
        elif uri_parts[0] == "login":
            self.wfile.write(self.check_login(uri_parts[1], uri_parts[2]))
        elif uri_parts[0] == "register":
            self.wfile.write(self.try_register(uri_parts[1], uri_parts[2]))
        elif uri_parts[0] == "maps":
            self.wfile.write(self.get_driver_maps_uri(uri_parts[1]))


    def do_POST(self):
        length = int(self.headers['content-length'])
        if length == 0:
            body = None
        else:
            body = json.loads(self.rfile.read(length))
        uri_parts = self.path[1:].split('/')
        if uri_parts[1] == "driver":
            self.wfile.write(self.register_driver(body))
        elif uri_parts[1] == "passenger":
            self.wfile.write(self.register_passenger(body))

    @staticmethod
    def get_driver_route(name):
        return json.dumps({'passengers': db.get_driver_route(name)}).encode()

    @staticmethod
    def get_driver_maps_uri(name):
        return json.dumps(mapsURIgen.gen_map_uri(name)).encode()

    @staticmethod
    def get_passenger_assignment(name):
        driver, time = db.get_passenger_assignment(name)
        return json.dumps({"driver": driver, "time": time}).encode()

    @staticmethod
    def check_login(username, password):
        return json.dumps({"result": AccountManagement.verify_login(username, password)}).encode()

    @staticmethod
    def try_register(username, password):
        temp = {"result": AccountManagement.add_account(username, password)}
        return json.dumps(temp).encode()

    @staticmethod
    def register_driver(driver):
        print("Register new driver...")
        db.add_driver(driver)
        return json.dumps({"result": "true"}).encode()

    @staticmethod
    def register_passenger(passenger):
        print("Register new passenger...")
        db.add_passenger(passenger)
        return json.dumps({"result": "true"}).encode()


def run(server_class=HTTPServer, handler_class=RideShareRequestHandler):
    server_address = ('', 8000)
    httpd = server_class(server_address, handler_class)
    httpd.serve_forever()


if __name__ == '__main__':
    run()
