#!/bin/bash
# Please run it from src/ folder under the root project directory
# javac -cp "../lib/saxon9.jar:../lib/log4j-1.2.13.jar:." com/sw_engineering_candies/example/*.java
# java -cp "../lib/saxon9.jar:../lib/log4j-1.2.13.jar:." com.sw_engineering_candies.example.ScaReportUtility

# params:
# first, the parent folder of checkstyle, findbugs, and pmd origin report xml
# second, the target folder of output result.html
for i in YHT_Doctor/build/reports branch_dev_xinxianghos/YHT_Patient/build/reports YHT_Patient/build/reports branch_dev/YHT_patientbase/build/reports branch_dev/Library_Src/build/reports branch_dev/YHT_appbase/build/reports branch_dev/YHT_lib/build/reports branch_dev/YHT_im/build/reports; do echo $i; . run.sh ../../../$i ../../../$i; done
#java -cp "../lib/saxon9.jar:../lib/log4j-1.2.13.jar:." com.sw_engineering_candies.example.ScaReportUtility $@
