#/bin/bash

xterm -hold -T EstacionCentral -e java -jar '../EstacionCentral/dist/EstacionCentral.jar' &

xterm -hold -T redGSM -e java -jar '../red_gsm/dist/red_gsm.jar' &

java -jar '../Sensor/dist/Sensor.jar' '../Configuraciones/ConfigSensor1.txt' &
java -jar '../Sensor/dist/Sensor.jar' '../Configuraciones/ConfigSensor2.txt' &
java -jar '../Sensor/dist/Sensor.jar' '../Configuraciones/ConfigSensor3.txt' &
java -jar '../Sensor/dist/Sensor.jar' '../Configuraciones/ConfigSensor4.txt' &

xterm -hold -T TR1 -e java -jar '../TerminalRemotaFinal/dist/TerminalRemotaFinal.jar' '../Configuraciones/ConfigTr1.txt' &
