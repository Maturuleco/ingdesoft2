#/bin/bash

xterm -hold -T Sensor8 -e java -jar '../Sensor/dist/Sensor.jar' '../Configuraciones/ConfigSensor8.txt' &

xterm -hold -T TR4 -e java -jar '../TerminalRemotaFinal/dist/TerminalRemotaFinal.jar' '../Configuraciones/ConfigTr4.txt'
