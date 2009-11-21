#/bin/bash

xterm -hold -T EstacionCentral -e java -jar '../EstacionCentral/dist/EstacionCentral.jar' &

xterm -hold -T redGSM -e java -jar '../red_gsm/dist/red_gsm.jar' &

xterm -hold -T Sensor0 -e java -jar '../Sensor/dist/Sensor.jar' '../Configuraciones/ConfigSensor8.txt' &

xterm -hold -T TR0 -e java -jar '../TerminalRemotaFinal/dist/TerminalRemotaFinal.jar' '../Configuraciones/ConfigTr3.txt'
