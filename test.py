import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BOARD)
GPIO.setup(16,GPIO.IN)

output_state = False

while True:
    input_state = GPIO.input(16)
    if input_state == False:
        print('Button Pressed')
	output_state = not output_state
	GPIO.output(18,output_state)
        time.sleep(0.2)