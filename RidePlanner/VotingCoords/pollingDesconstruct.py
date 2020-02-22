import string


def readFile(filename):
    contents = []
    inFile = open("pollingPlaces.txt", "r")
    number_of_lines = 0

    for line in inFile:
        contents.append(line.rstrip("\n\r"))
    inFile.close()
    return contents


def makeSets(lst):
    currentSet = []
    sets = []
    setLen = 0

    for i in range(len(lst)):
        if len(lst[i]) == 0:
            sets.append([" ".join(currentSet), setLen])
            currentSet = []
            setLen = 0
        else:
            currentSet.append(lst[i])
            setLen += 1
    return sets


def findAddress(sets):
    validAddresses = []
    for item in sets:
        usable = False
        stillUsable = False
        for letter in item[0]:
            # will only occur of letter is lowercase
            if letter not in (string.ascii_uppercase + "0123456789 "):
                usable = True
                break
        if usable:
            for letter in item[0]:
                # ruling out phone numbers
                if letter in (string.ascii_lowercase) and letter not in '@':
                    stillUsable = True
                    break

            if stillUsable:
                if len(item[0]) > 5 and item[0][-5] == "0" and item[0][-4] == "3":
                    validAddresses.append(item[0])

            stillUsable = False
            usable = False

    return validAddresses


def writeFile(name, addresses):
    inFile = open(name, "w")
    for line in addresses:
        inFile.write(line + '\n')
    inFile.close()


if __name__ == "__main__":
    contents = readFile("pollingPlaces.txt")
    sets = makeSets(contents)
    addresses = findAddress(sets)
    writeFile("addresses.txt", addresses)
