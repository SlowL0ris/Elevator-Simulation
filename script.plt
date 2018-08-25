set size square
set xrange [0:50]
set yrange [-3:3]
set datafile separator ","

plot 'dump' using 1:2 with lines title "position" lw 4, \
'dump' using 1:3 with lines title "p" lw 4, \
'dump' using 1:4 with lines title "d" lw 4

pause 0.001
reread
