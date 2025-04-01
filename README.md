# SpringBoot_BackEnd



demo为SpringBoot框架，利用Maven进行依赖管理，架构设计如下：

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └──demo/
│   │               ├── controller/          // 公共的一些接口，比如LoginController
│   │               ├── repository/          // 数据库仓库层，对接实体类和数据库
│   │               ├── model/               // 数据模型，将实体类和table映射
│   │               ├── dto/                 // 数据传输对象，隐藏实体类中部分数据
│   │               └── module/              // 模块，放置内聚的接口、服务异常处理  
|	|					├── chat/              
|   |                   └── user/                  //eg.
|   |                        ├── UserController/   //User的接口          
│   │                        ├── UserService/      //User的处理逻辑
|   |                        └── UserException/    //User的异常处理
│   └── resources/
│       └── application.properties          // 配置文件，配置端口、数据库等
├── test/
|
pom.xml/                                    //Maven的依赖配置文件
```

