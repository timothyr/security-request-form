#!/usr/bin/env bash
# DO NOT RUN ME, RUN start.sh INSTEAD ***

pid_file="security_req_server_pid.txt"

(cd /;

pid="sudo cat $pid_file"
sudo pkill pid
ls / | grep ".jar"
sudo nohup java -jar *.jar &
pid=$!
sudo echo $pid > $pid_file

)

exit
