#!/bin/bash

PATH=$PATH:~/Library/Python/2.7/bin/

aws lambda update-function-code  --function-name StatusRequest --zip-file fileb://target/bergjes2017-0.0.1-SNAPSHOT.jar
aws lambda update-function-code  --function-name ScannedLocationRequest --zip-file fileb://target/bergjes2017-0.0.1-SNAPSHOT.jar
aws lambda update-function-code  --function-name ScannedAnswerRequest --zip-file fileb://target/bergjes2017-0.0.1-SNAPSHOT.jar