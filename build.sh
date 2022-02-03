#!/usr/bin/env bash

add -r 6.035 scala java
# make sure to have the correct SCALA_HOME environemnt variable set!

ant "$@"
