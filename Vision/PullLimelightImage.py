import numpy as np
import cv2
import datetime

url = "http://10.28.23.11:5800/"
video = cv2.VideoCapture(url)
while(True):
    ret, frame = video.read()
    if frame is not None:
        cv2.imshow('video', frame)
        key = cv2.waitKey(1)
        if key & 0xFF == ord('q'):
            break
        if key & 0xFF == ord('k'):
            cv2.imwrite(datetime.datetime.now().strftime("frame%Y%m%d_%H%M%S_%f.jpg"),frame)

    else:
        print("No Frame!!")
video.release()
cv2.destroyAllWindows()
