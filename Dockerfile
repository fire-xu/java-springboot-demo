FROM java:8 
VOLUME /tmp 
ADD points-manager/target/points-manager-0.0.1-exec.jar points-manager.jar
ENTRYPOINT ["java","-jar","/points-manager.jar"]