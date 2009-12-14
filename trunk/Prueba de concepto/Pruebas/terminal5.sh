#/bin/bash

xterm -hold -T Sensor7 -e java -jar '../Sensor/dist/Sensor.jar' '../Configuraciones/ConfigSensor7.txt' &

xterm -hold -T TR5 -e java -jar '../TerminalRemotaFinal/dist/TerminalRemotaFinal.jar' '../Configuraciones/ConfigTr5.txt'
