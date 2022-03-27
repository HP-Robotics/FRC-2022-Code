import cv2
import numpy as np

# global variables go here:
testVar = 0

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

def drawDecorations(image):
    cv2.putText(image, 
        'Limelight python script!', 
        (0, 230), 
        cv2.FONT_HERSHEY_SIMPLEX, 
        .5, (0, 255, 0), 1, cv2.LINE_AA)
    

camera_matrix=[2.5751292067328632e+02, 0., 1.5971077914723165e+02, 0., 2.5635071715912881e+02, 1.1971433393615548e+02, 0., 0., 1. ]
distortion_coefficients=[2.9684613693070039e-01, -1.438025225474885e+00, -2.2098421479494509e-03, 03.3894563533907176e-03, 2.53444303544806740e+00]

object_Points = [[0,0,0], [0,-2,0], [6,-2,0], [6,0,0]]
# runPipeline() is called every frame by Limelight's backend.
def runPipeline(image, llrobot):
    img_hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
    img_threshold = cv2.inRange(img_hsv, (53, 70, 70), (85, 255, 255))
   
    contours, _ = cv2.findContours(img_threshold, 
    cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)
  
    largestContour = np.array([[]])
    llpython = [0,0,0,0,0,0,0,0]

    if len(contours) > 0:
        largestContour = max(contours, key=cv2.contourArea)
        #x,y,w,h = cv2.boundingRect(largestContour)
        epsilon = 0.05*cv2.arcLength(largestContour,True)
        approx = cv2.approxPolyDP(largestContour,epsilon,True)
        x,y,w,h = cv2.boundingRect(approx)   
        cv2.drawContours(image, [approx], -1, 255, 2)
        if(len(approx)==4):
            ret, tvec, rvec = cv2.solvePNPRansac(approx, object_Points, camera_matrix, distortion_coefficients)
            
                
            
       # print(len(approx))
       # cv2.rectangle(image,(x,y),(x+w,y+h),(0,255,255),2)
        llpython = [1,x,y,w,h,9,8,7]  

    incrementTestVar()
    drawDecorations(image)
       
    # make sure to return a contour,
    # an image to stream,
    # and optionally an array of up to 8 values for the "llpython"
    # networktables array
    return largestContour, image, llpython