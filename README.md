![](LOGO.png)

# likab-framework 
[![Build Status](https://travis-ci.org/xiapshen/likab-framework.svg?branch=master)](https://travis-ci.org/xiapshen/likab-framework) [![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

一、likab框架简介

likab框架提供不同方式的系统间的跨进程通信，主要的功能包括：

- [x] [高可用RMI远程调用](https://xiapshen.github.io/likab-framework/docs/likab-rmi%E7%94%A8%E6%88%B7%E6%8C%87%E5%8D%97)
- [ ] RPC远程过程调用
- [ ] HTTP调用器
- [ ] Web Service远程调用

------
二、各层说明
> * likab-core — 核心包，提供likab各种核心功能
> * likab-loadbalance — 负载均衡层
> * likab-registry — 服务注册层
> * likab-remote —远程调用层
