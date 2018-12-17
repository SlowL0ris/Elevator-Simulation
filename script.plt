set size square
set xrange [0:50]
set yrange [-20:20]
set datafile separator ","

plot 'dump' using 1:2 with lines title "position" lw 10, \
'dump' using 1:3 with lines title "speed" lw 10, \

pause 0.001
reread
