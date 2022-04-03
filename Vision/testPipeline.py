import cv2
import numpy as np
import math

# global variables go here:
testVar = 0
IMGWIDTH = 320
IMGHEIGHT = 240

# To change a global variable inside a function,
# re-declare it the global keyword
def incrementTestVar():
    global testVar
    testVar = testVar + 1
    if testVar == 100:
        print("test")
    if testVar >= 200:
        print("print")
        testVar = 0


def printHeight(updown, frontback, angle):
    return frontback * math.sin(angle) - updown * math.cos(angle)


def drawObjectPoints(numbers):
    objectPointArray = []
    for num in numbers:
        yRight = 27 * math.sin(math.radians((10.61 * num * 2) + 10.61))
        xRight = 27 * math.cos(math.radians((10.61 * num * 2) + 10.61))
        yLeft = 27 * math.sin(math.radians((10.61 * num * 2)))
        xLeft = 27 * math.cos(math.radians((10.61 * num * 2)))
        objectPointArray.append([[yLeft, xLeft, 0], [yLeft, xLeft, -2], [yRight, xRight, -2], [yRight, xRight, 0]])
    return np.array(objectPointArray, dtype=np.float32).reshape((-1, 3))



def drawDecorations(image):
    cv2.putText(image,
                'Limelight python script!',
                (0, 230),
                cv2.FONT_HERSHEY_SIMPLEX,
                .5, (0, 255, 0), 1, cv2.LINE_AA)


def contourFilter(contour):
    if cv2.contourArea(contour) >= 10:
        return True
    else:
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

camera_matrix = np.matrix(
    [[2.5751292067328632e+02, 0., 1.5971077914723165e+02], [0., 2.5635071715912881e+02, 1.1971433393615548e+02],
     [0., 0., 1.]])
distortion_coefficients = np.array(
    [2.9684613693070039e-01, -1.438025225474885e+00, -2.2098421479494509e-03, 03.3894563533907176e-03,
     2.53444303544806740e+00])

object_Points = [[0, 27, 0], [0, 27, -2], [4.97, 26.54, -2], [4.97, 26.54, 0]]
img_points = [[178, 124], [178, 128], [188, 128], [188, 124]]
# img_points = [[107,132], [107,135], [117,135], [117,132]]
# img_points = [[161,168], [161,171], [169,171], [169,168]]
img_points = np.array(img_points, dtype=np.float32)
object_Points = np.array(object_Points, dtype=np.float32)
print(drawObjectPoints([0, 1]))
print(object_Points)


# runPipeline() is called every frame by Limelight's backend.
def runPipeline(image, llrobot):
    img_hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
    img_threshold = cv2.inRange(img_hsv, (53, 70, 70), (85, 255, 255))

    contours, _ = cv2.findContours(img_threshold,
                                   cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)

    largestContour = np.array([[]])
    llpython = [0, 0, 0, 0, 0, 0, 0, 0]

    if len(contours) > 0:
        contoursList = sorted(contours, key=cv2.contourArea)
        contoursList = filter(contourFilter, contoursList)
        filteredContours = []
        for contour in contoursList:
            # filteredContours.append(contours)

            #epsilon = 0.01 * cv2.arcLength(contour, True)
            #approx = cv2.approxPolyDP(contour, epsilon, True)
            #filteredContours.append(approx)
            topLeftContour = min(contour, key=distanceToTopLeft)
            topRightContour = min(contour, key=distanceToTopRight)
            botRightContour = min(contour, key=distanceToBotRight)
            botLeftContour = min(contour, key=distanceToBotLeft)
            """cv2.circle(image, topLeftContour[0], 2, (255, 255, 255), -1)
            cv2.circle(image, topRightContour[0], 2, (255, 0, 0), -1)
            cv2.circle(image, botRightContour[0], 2, (0, 255, 0), -1)
            cv2.circle(image, botLeftContour[0], 2, (0, 255, 255), -1)"""
            filteredContours.append(np.array([topLeftContour, botLeftContour, botRightContour, topRightContour]))
        largestContour = max(contours, key=cv2.contourArea)
        print(filteredContours)
        filteredContours.sort(key=targetSort)
        print(filteredContours)
        # x,y,w,h = cv2.boundingRect(largestContour)
        # epsilon = 0.05*cv2.arcLength(largestContour,True)
        # approx = cv2.approxPolyDP(filteredContours,epsilon,True)
        x, y, w, h = 0, 0, 0, 0
        # print(filteredContours)
        cv2.drawContours(image, filteredContours, -1, 255, 1)
        # print(largestContour)

        for target in filteredContours:
            ret, rvec, tvec = cv2.solvePnP(drawObjectPoints([0]), np.array(target, dtype=np.float32).reshape((-1, 2)), camera_matrix, distortion_coefficients)
            print(tvec)
            print(rvec)
            print(printHeight(tvec[1], tvec[2], math.radians(47.395)))

        # print(len(approx))
        # cv2.rectangle(image,(x,y),(x+w,y+h),(0,255,255),2)
        llpython = [1, x, y, w, h, 9, 8, 7]

        # incrementTestVar()
    # drawDecorations(image)

    # make sure to return a contour,
    # an image to stream,
    # and optionally an array of up to 8 values for the "llpython"
    # networktables array
    return filteredContours, image, llpython


img = cv2.imread("sample_frames/frame20220327_131211_701262.jpg")
# img = cv2.imread("sample_frames/frame20220327_131434_647150.jpg")
# frame20220327_131508_711085
# img = cv2.imread("sample_frames/frame20220327_131508_711085.jpg")
_, img, _ = runPipeline(img, [])
cv2.imwrite("TempImg.png", img)
while (True):
    if img is not None:
        key = cv2.waitKey(1)
        if key & 0xFF == ord('q'):
            break
