import cv2
import numpy as np
import sys
import json

def analyze_skin(image_path):
    # 1. Load the image
    img = cv2.imread(image_path)
    if img is None:
        return {"error": f"Could not read image at {image_path}"}

    # 2. Convert from BGR (OpenCV default) to YCrCb color space
    ycrcb_img = cv2.cvtColor(img, cv2.COLOR_BGR2YCrCb)

    # 3. Define typical human skin color ranges in YCrCb
    lower_skin = np.array([0, 135, 85], dtype=np.uint8)
    upper_skin = np.array([255, 180, 135], dtype=np.uint8)

    # 4. Create a binary mask (isolates skin pixels as white, background as black)
    skin_mask = cv2.inRange(ycrcb_img, lower_skin, upper_skin)

    # 5. Extract only the skin pixels from the original image
    skin_pixels = cv2.bitwise_and(img, img, mask=skin_mask)

    # 6. Check if any skin was actually detected
    flat_mask = skin_mask.flatten()
    detected_skin_count = np.count_nonzero(flat_mask)

    if detected_skin_count == 0:
        return {"undertone": "Neutral", "matchingColors": ["Navy Blue", "Black", "White"]}

    # 7. Calculate the median color of the detected skin region (BGR format)
    # Using median prevents random outliers like lips or teeth from skewing the results
    bgr_elements = img[skin_mask > 0]
    median_b = int(np.median(bgr_elements[:, 0]))
    median_g = int(np.median(bgr_elements[:, 1]))
    median_r = int(np.median(bgr_elements[:, 2]))

    # 8. Determine Undertone via Color Theory Rules
    # Standard rule: High Cr/Cb ratio indicates warmer tones, lower indicates cooler tones
    # Alternatively, comparing Red vs Blue intensity acts as an excellent baseline
    if median_r > (median_b + 30):
        undertone = "Warm"
        matching_colors = ["Olive Green", "Navy Blue", "Beige", "Burgundy"]
    elif median_b > (median_r - 10):
        undertone = "Cool"
        matching_colors = ["Lavender", "Charcoal", "Emerald Green", "White"]
    else:
        undertone = "Neutral"
        matching_colors = ["Dusty Pink", "Jade", "Navy Blue", "Classic Gray"]

    # 9. Return the clean structured package matching our Java DTO expects
    return {
        "undertone": undertone,
        "matchingColors": matching_colors
    }

if __name__ == "__main__":
    # Expect the image path to be passed as a terminal argument
    if len(sys.argv) < 2:
        print(json.dumps({"error": "No image path provided."}))
        sys.exit(1)

    target_image = sys.argv[1]
    result = analyze_skin(target_image)

    # Print out ONLY clean JSON so Java can capture it easily
    print(json.dumps(result))