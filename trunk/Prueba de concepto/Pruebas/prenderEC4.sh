#/bin/bash

./terminal4.sh &
./terminal5.sh &

xterm -hold -T EstacionCentral4 -e java -jar '../EstacionCentral/dist/EstacionCentral.jar' '../Configuraciones/ConfigEc4.txt' &


