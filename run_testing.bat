@echo off
java -Xmx2G -Xms2G -jar release/mi-lang.jar run file='examples/testing.mib'
pause > nul