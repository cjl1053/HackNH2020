from http.server import *
import json

import db


class RideShareRequestHandler(BaseHTTPRequestHandler):
    def _set_headers(self):
        self.send_response(200)
        self.send_header('Content-type', 'application/json')
        self.end_headers()

    def do_GET(self):
        self._set_headers()
        uri_parts = self.path[1:].split('/')
        if uri_parts[0] == "drivers":
            self.wfile.write(get_driver_route(uri_parts[1]))
        elif uri_parts[1] == "passengers":
            self.wfile.write(self.get_passenger_assignment(uri_parts[1]))

    def do_POST(self):
        length = int(self.headers['content-length'])
        if length == 0:
            body = None
        else:
            body = json.loads(self.rfile.read(length))
        uri_parts = self.path[1:].split('/')
        if uri_parts[1] == "driver":
            self.register_driver(body)
        elif uri_parts[1] == "passenger":
            self.register_passenger(body)

    @staticmethod
    def get_driver_route(name):
        return db.get_driver_route(name)

    @staticmethod
    def get_passenger_assignment(name):
        return db.get_passenger_assignment(name)

    @staticmethod
    def register_driver(driver):
        print("Register new driver...")
        db.add_driver(driver)

    @staticmethod
    def register_passenger(passenger):
        print("Register new passenger...")
        db.add_passenger(passenger)


def run(server_class=HTTPServer, handler_class=RideShareRequestHandler):
    server_address = ('', 8000)
    httpd = server_class(server_address, handler_class)
    httpd.serve_forever()


if __name__ == '__main__':
    run()