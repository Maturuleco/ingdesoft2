#/bin/bash

./terminal3.sh &

xterm -hold -T EstacionCentral3 -e java -jar '../EstacionCentral/dist/EstacionCentral.jar' '../Configuraciones/ConfigEc3.txt' &


