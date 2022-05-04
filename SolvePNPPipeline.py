import cv2
import numpy as np
import math
import itertools

# global variables go here:
testVar = 0
IMGWIDTH = 960
IMGHEIGHT = 720
CAMERAANGLE = 36
tvecList = []
rvecList = []
goalHeightList = []

# To change a global variable inside a function,
# re-declare it the global keyword
def incrementTestVar():
    global testVar
    global distances
    testVar = testVar + 1
    if testVar == 25:
        pass
    if testVar >= 50:
        # print(distances)
        testVar = 0


def findAngle(ycoord):
    global IMGHEIGHT
    global CAMERAANGLE
    # print(ycoord)
    normalYCoord = IMGHEIGHT-ycoord
    normalYCoord = (2*(normalYCoord/IMGHEIGHT)-1)*24.85
    return normalYCoord+CAMERAANGLE


def contourAreaFilter(contour):
    if cv2.contourArea(contour) >= 100:
        return True
    else:
        return False


def filterCurry(largest):
    def filterCurry2(contour):
        return averageFilter(contour, largest)
    return filterCurry2
    

def averageFilter(contour, largest):
    yval = 0
    for point in contour:
        yval += point[0][1]
    yval = yval/len(contour)
    if abs(yval-largest) < 50:
        return True
    return False

def distanceToTopLeft(point):
    return math.sqrt((0-point[0][0])**2 + (0-point[0][1])**2)

def distanceToTopRight(point):
    return math.sqrt((IMGWIDTH-point[0][0])**2 + (0-point[0][1])**2)

def distanceToBotLeft(point):
    return math.sqrt((0-point[0][0])**2 + (IMGHEIGHT-point[0][1])**2)

def distanceToBotRight(point):
    return math.sqrt((IMGWIDTH-point[0][0])**2 + (IMGHEIGHT-point[0][1])**2)

def targetSort(contour):
    return contour[0][0][0]


# runPipeline() is called every frame by Limelight's backend.
def runPipeline(image, llrobot):
    global distances
    found = 0.0
    goalFound = 0.0
    img_hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
    # TODO - TUNE HERE
    img_threshold = cv2.inRange(img_hsv, (53, 70, 70), (85, 255, 255))

    contours, _ = cv2.findContours(img_threshold,
                                   cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)

    largestContour = np.array([[]])
    llpython = [0, 0, 0, 0, 0, 0, 0, 0, 0]
    filteredContours =  np.array([[[]]])

    if len(contours) > 0:
        contoursList = sorted(contours, key=cv2.contourArea, reverse=True)
        largestContour = contoursList[0]
        if largestContour.shape [0]>200 :
            return filteredContours, image, llpython
        largestAverageYValue = 0
        largestAverageYValue = np.mean(largestContour[:,:,1],0)

        #for point in largestContour:
        #    largestAverageYValue += point[0][1]
        #     print(point)
        #largestAverageYValue = largestAverageYValue/len(largestContour)
        #print(largestContour.shape)

        contoursList = filter(contourAreaFilter, contoursList)
        contoursList = filter(filterCurry(largestAverageYValue), contoursList)
        filteredContours = []
        distances = []
        for contour in itertools.islice(contoursList,10):
            #topLeftContour = min(contour, key=distanceToTopLeft)
            topLeftContour = contour [np.argmin(np.sqrt((np.sum((0-contour)**2,2))),0)][0]

            topRightContour = contour [np.argmin(np.sqrt((np.add((IMGWIDTH-contour[:,:,0])**2,(0-contour[:,:,1])**2))),0)][0]
            botLeftContour = contour [np.argmin(np.sqrt((np.add((0-contour[:,:,0])**2,(IMGHEIGHT-contour[:,:,1])**2))),0)][0]
            botRightContour = contour [np.argmin(np.sqrt((np.add((IMGWIDTH-contour[:,:,0])**2,(IMGHEIGHT-contour[:,:,1])**2))),0)][0]

            #topRightContour = min(contour, key=distanceToTopRight)
            botRightContour = min(contour, key=distanceToBotRight)
            botLeftContour = min(contour, key=distanceToBotLeft)

            topLeftDistance = 75/math.tan(math.radians(findAngle(int(topLeftContour[0][1]))))
            botLeftDistance = 73/math.tan(math.radians(findAngle(int(botLeftContour[0][1]))))
            if abs(topLeftDistance - botLeftDistance) < 2:
                distances.extend([topLeftDistance,botLeftDistance])
            topRightDistance = 75/math.tan(math.radians(findAngle(int(topRightContour[0][1]))))
            botRightDistance = 73/math.tan(math.radians(findAngle(int(botRightContour[0][1]))))
            if abs(topRightDistance - botRightDistance) < 2:
                distances.extend([topRightDistance,botRightDistance])
                
            filteredContours.append(np.array([topLeftContour, botLeftContour, botRightContour, topRightContour]))
        # largestContour = max(contours, key=cv2.contourArea)
        
        filteredContours.sort(key=targetSort)
        if len(filteredContours) > 0:
            x = (filteredContours[0][0][0][0]+filteredContours[-1][-1][0][0])/2
            found = 1    
        else:
            x = 0
        if len(distances) > 0:
            d = min(distances)
            goalFound = 1
            
        else:
            d = -1
        y, w, h = 0, 0, 0
        cv2.drawContours(image, filteredContours, -1, 255, -1)

        
        llpython = [found, x, y, w, h, 9, 8, goalFound, d]

        incrementTestVar()

    # make sure to return a contour,
    # an image to stream,
    # and optionally an array of up to 8 values for the "llpython"
    # networktables array
    return filteredContours, image, llpython

