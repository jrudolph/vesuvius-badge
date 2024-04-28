#!/bin/bash

set -ex

echo "Designator,Val,Package,MidX,MidY,Rotation,Layer" > $1.fixed.csv
tail -n+2 $1 >> $1.fixed.pos
