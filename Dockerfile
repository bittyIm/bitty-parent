FROM alpine:latest
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.ustc.edu.cn/g' /etc/apk/repositories
RUN apk add openjdk11
ENV JAVA_HOME="/usr/lib/jvm/default-jvm/"
CMD ["java","-jar","./target/bitty-all-in-one.jar"]
