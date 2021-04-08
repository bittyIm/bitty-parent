[![Maven Package](https://github.com/bittyIm/bitty-parent/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/bittyIm/bitty-parent/actions/workflows/maven-publish.yml)
----
# Bitty

又一个聊天服务器

文档在这里 https://bittyim.github.io/bitty/#/

### standalone

- 1 broker,root 192.168.0.1

### cluster

- 3 broker 192.168.0.1 192.168.0.2 192.168.0.3
  
- 3 root 192.168.1.1 192.168.1.2 192.168.1.3

### 插件式架构
plugin 定义插件的接口
plugins 为系统内建的 插件 

用户直接引入  bitty-plugin 实现自己的自定义功能就可以了 
具体实现残酷 plugins 下面的实现
```
        <dependency>
            <groupId>com.bitty</groupId>
            <artifactId>bitty-plugin</artifactId>
            <version>{{bitty.version}}</version>
        </dependency>      
```
打包完成之后 复制到 plugins 目录下面  系统自动扫描加载

