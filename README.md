# SpringBootTest

个人徒手搭建的SpringBoot项目

目前简单做了一下性能压测，调用了以下三个接口

TestController中

/api/test/getCityPageList

/api/test/getCityPageListFormRedisCache

/api/test/getCityPageListFormMemoryCache

简单使用JMeter压测，没有校验登录状态，也没有其他的东西 1000线程跑

三个接口分别是直接查库，查redis数据，查内存数据，逻辑基本一致，随机分页取city数据

吞吐量分别是

2000/DB

5000/redis

17000/memory

可以看到 通过多级缓存的概念，对于接口性能的提升还是非常明显的