#!/bin/bash
#bash script for building and restarting security request server

current_dir=$(dirname "$0")
domain="cmpt373-1177d.cmpt.sfu.ca"

gradle build

echo Please enter your username
read username

# copy jar and template to server
scp $current_dir/build/libs/*.jar $username@$domain:/
sudo scp $current_dir/Invoice_Template.pdf $username@$domain:/

command=$(base64 -w0 script.sh)
ssh -tt $username@$domain "echo $command | base64 -d | sudo bash"
