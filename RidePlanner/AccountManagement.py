import hashlib, binascii, os
from pymongo import MongoClient

mydb = MongoClient()

db = mydb["users"]

accountsTable = db.accounts


# Hashing code adopted from https://www.vitoshacademy.com/hashing-passwords-in-python/
def hash_password(password):
    """Hash a password for storing."""
    salt = hashlib.sha256(os.urandom(60)).hexdigest().encode('ascii')
    pwdhash = hashlib.pbkdf2_hmac('sha512', password.encode('utf-8'),
                                  salt, 100000)
    pwdhash = binascii.hexlify(pwdhash)
    return (salt + pwdhash).decode('ascii')


def verify_password(stored_password, provided_password):
    """Verify a stored password against one provided by user"""
    salt = stored_password[:64]
    stored_password = stored_password[64:]
    pwdhash = hashlib.pbkdf2_hmac('sha512',
                                  provided_password.encode('utf-8'),
                                  salt.encode('ascii'),
                                  100000)
    pwdhash = binascii.hexlify(pwdhash).decode('ascii')
    return pwdhash == stored_password


def add_account(username, password):
    for _ in accountsTable.find({"user": username}):
        return "false"
    accountsTable.insert_one({"user": username, "pass": hash_password(password)})
    return "true"


def verify_login(username, password):
    for account in accountsTable.find({"user": username}):
        return "true" if verify_password(account['pass'], password) else "false"
    return "false"


if __name__ == '__main__':
    print(add_account("test", "test"))
    assert(verify_login("test", "test"))
