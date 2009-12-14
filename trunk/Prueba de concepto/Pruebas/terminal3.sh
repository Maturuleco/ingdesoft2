#/bin/bash

xterm -hold -T Sensor5 -e java -jar '../Sensor/dist/Sensor.jar' '../Configuraciones/ConfigSensor5.txt' &
xterm -hold -T Sensor6 -e java -jar '../Sensor/dist/Sensor.jar' '../Configuraciones/ConfigSensor6.txt' &

xterm -hold -T TR3 -e java -jar '../TerminalRemotaFinal/dist/TerminalRemotaFinal.jar' '../Configuraciones/ConfigTr3.txt'
