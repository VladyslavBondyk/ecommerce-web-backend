#!/bin/bash
cd /home/ec2-user
aws s3 cp s3://s3bucket-buldozer/MASHHH-STORE-AWS.jar .
java -jar MASHHH-STORE-AWS.jar
