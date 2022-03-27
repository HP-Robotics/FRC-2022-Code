import cv2
import numpy as np
import math

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

def printHeight(updown, frontback, angle):
    return frontback*math.sin(angle)-updown*math.cos(angle)
   

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
        
camera_matrix=np.matrix([[2.5751292067328632e+02, 0., 1.5971077914723165e+02], [0., 2.5635071715912881e+02, 1.1971433393615548e+02], [0., 0., 1.]])
distortion_coefficients=np.array([2.9684613693070039e-01, -1.438025225474885e+00, -2.2098421479494509e-03, 03.3894563533907176e-03, 2.53444303544806740e+00])

object_Points = [[0,27,0], [0,27,-2], [4.97,26.54,-2], [4.97,26.54,0]]
img_points = [[178,124], [178,128], [188,128], [188,124]]
#img_points = [[107,132], [107,135], [117,135], [117,132]]
#img_points = [[161,168], [161,171], [169,171], [169,168]]
img_points = np.array(img_points, dtype=np.float32)
object_Points = np.array(object_Points, dtype=np.float32)
print(object_Points.shape)
print(img_points.shape)
# runPipeline() is called every frame by Limelight's backend.
def runPipeline(image, llrobot):
    img_hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
    img_threshold = cv2.inRange(img_hsv, (53, 70, 70), (85, 255, 255))
   
    contours, _ = cv2.findContours(img_threshold, 
    cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)
  
    largestContour = np.array([[]])
    llpython = [0,0,0,0,0,0,0,0]

    if len(contours) > 0:
        contoursList = sorted(contours, key=cv2.contourArea)
        contoursList = filter(contourFilter, contoursList)
        filteredContours = []
        for contours in contoursList:
            #filteredContours.append(contours)
            epsilon = 0.01*cv2.arcLength(contours,True)
            approx = cv2.approxPolyDP(contours,epsilon,True)
            filteredContours.append(approx)
        largestContour = max(contours, key=cv2.contourArea)
        #x,y,w,h = cv2.boundingRect(largestContour)
        #epsilon = 0.05*cv2.arcLength(largestContour,True)
        #approx = cv2.approxPolyDP(filteredContours,epsilon,True)
        x,y,w,h = cv2.boundingRect(approx)   
        #print(filteredContours)
        #cv2.drawContours(image, filteredContours, -1, 255, 1)
        #print(largestContour)
        
        if(len(approx)==4):
            pass
            #print (approx)
        ret, rvec, tvec = cv2.solvePnP(object_Points, img_points, camera_matrix, distortion_coefficients)
        print(tvec)
        print(rvec)        
        print(printHeight(tvec[1], tvec[2], math.radians(47.395)))
            
       # print(len(approx))
       # cv2.rectangle(image,(x,y),(x+w,y+h),(0,255,255),2)
        llpython = [1,x,y,w,h,9,8,7]  

    #incrementTestVar()
    #drawDecorations(image)
       
    # make sure to return a contour,
    # an image to stream,
    # and optionally an array of up to 8 values for the "llpython"
    # networktables array
    return approx, image, llpython
    
#img = cv2.imread("sample_frames/frame20220327_131211_701262.jpg")
#img = cv2.imread("sample_frames/frame20220327_131434_647150.jpg")
#frame20220327_131508_711085
img = cv2.imread("sample_frames/frame20220327_131508_711085.jpg")
_, img, _ = runPipeline(img, [])
cv2.imshow("Image Preview", img)
while(True):
    if img is not None:
        key = cv2.waitKey(1)
        if key & 0xFF == ord('q'):
            break
