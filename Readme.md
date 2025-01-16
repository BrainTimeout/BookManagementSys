# 图书管理系统 (Library Management System) 📚

这是一个基于 Spring Boot 和 Vue.js 开发的图书管理系统。它包括了前端的 Vue.js 项目，后端的 Spring Boot 项目，以及MYSQL数据库。

## 项目结构 📂

```
BookManagementSys
├── .idea/                # IntelliJ IDEA 配置文件
├── Data/                 # 存储数据相关文件的目录
├── node_modules/         # 前端依赖的安装目录
├── springboot/           # 后端 Spring Boot 项目目录
├── sql/             	  # 存储数据库备份文件的目录
├── vue/                  # 前端 Vue 项目目录
├── package-lock.json     # npm 锁定依赖的文件
├── package.json          # npm 项目的配置文件
└── Readme.md             # 项目的 README 文件
```

- 后端的主体部分在`BookManagementSys\springboot\src\main\java\com\example\springboot`路径下

  在该目录下：

  ```
  springboot/
  └── com/
      └── example/
          ├── comon/       # 公共工具类或常量
          ├── controller/  # 控制器层，处理请求和响应
          │   ├── dto/     # 数据传输对象 (DTO)
          │   └── request/ # 请求处理相关类
          ├── demos/       # 示例代码或测试代码
          │   └── web/     # 与 Web 相关的示例代码
          ├── entity/      # 实体类，映射数据库表
          ├── exception/   # 异常处理相关的类
          ├── mapper/      # 数据库操作相关的接口或类
          ├── service/     # 服务层，包含业务逻辑
          │   └── impl/    # 服务实现类
          └── utils/       # 工具类或辅助功能
  
  ```

- 前端的主体部分在`BookManagementSys\vue\src`路径下

  在该目录下：

  ```
  src/
  ├── assets/          # 存放静态资源文件
  ├── components/      # 存放 Vue 组件
  ├── router/          # 路由配置
  ├── utils/           # 工具函数或类库
  └── views/           # 页面视图
      ├── book/        # 图书管理相关页面
      ├── borrow/      # 借阅相关页面
      ├── category/    # 分类管理页面
      ├── layout/      # 页面布局组件
      ├── login/       # 登录页面
      ├── user/        # 用户信息相关页面
      └── userManage/  # 用户管理页面
  ```

  

## 项目功能 🛠️

### 管理员界面

- 📚 图书管理
  - 增加、删除、修改、查询图书信息。
  - 管理图书分类和库存。
- 👥 用户管理
  - 管理用户注册、登录和权限控制。
  - 查看和修改用户的个人信息。
- 🔄 图书借阅与归还管理
  - 管理用户的图书借阅、归还操作。
  - 跟踪借阅日期和归还日期。
- 📊 数据统计与报表
  - 生成数据报表，如借阅统计、用户活跃度等。

### 用户界面

- 🔍 图书搜索与查看
  - 搜索并查看图书详细信息。
  - 支持按分类、作者等筛选条件进行搜索。
- 💬 评论功能
  - 用户可以对图书进行评论。
- 🧑‍🤝‍🧑 个人信息管理
  - 查看和修改个人信息，如昵称、联系方式等。
  - 修改登录密码。
- 💰 账户信息
  - 查看个人账户余额。
  - 查看当前借阅的图书及借阅状态。

## 环境要求 🌍

- JDK 
- Mybatis
- npm
- Maven
- MySQL 数据库

## 数据库设置 🗄️

### 导入数据库 🛠️

1. 在 `sql/` 目录下找到 `bookmanagementsys.sql` 文件。
2. 使用数据库管理工具（如 MySQL Workbench）导入该文件，创建名为 `BookmanagementSys` 的数据库。

### 配置数据库连接 🔑

在 项目`\springboot`目录下的`\src\main\resources\application.yml` 中，修改数据库连接信息：

```
Spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://#数据库地址和端口/bookmanagementsys?serverTimezone=GMT%2b8
    username: #数据库用户名
    password: #数据库密码
```

在启动后端项目前须确保数据库已启动，并且配置文件中的数据库地址和端口、用户名、密码等信息正确。

在该文件下还有一段代码

```
file:
  fileDir: # 静态文件路径
```

指定一个地址作为静态文件如图片、视频的存储地址。参考项目文件`\Data`目录下的文件结构，同时，该目录中保存了初始化导入后数据库中的书籍封面、用户头像。

## 后端项目设置 ⚙️

### 安装依赖 📥

在 `springboot/` 目录下，运行以下命令安装项目依赖：

```
mvn clean install
```

### 启动后端服务 🚀

在 `springboot/` 目录下，运行以下命令启动后端服务：

```
mvn spring-boot:run
```

后端服务启动后，可以在浏览器中访问 http://localhost:9090 查看后端 API 接口。

## 前端项目设置 ⚙️

### 安装依赖 📥

在 `vue/` 目录下，运行以下命令安装项目依赖：

```bash
npm install
```

### 启动前端服务 🚀

在 `vue/` 目录下，运行以下命令启动前端服务：

```
npm run serve
```

前端服务启动后，可以在浏览器中访问 http://localhost:8080 进入登录界面。

## 使用说明 📜

1. 启动 MySQL 数据库，并创建 `BookmanagementSys` 数据库。
2. 启动后端服务：使用 Maven 启动 Spring Boot 后端。
3. 启动前端服务：使用 npm 启动 Vue 前端。
4. 打开浏览器，访问前端应用，进行图书管理系统的操作。（注：先访问`http://localhost:8080`进行用户登录，数据库中存在一位账户密码均为`root`的用户，且该用户权限为管理员)

## 注意事项 ⚠️

- 请确保您已安装npm、Maven 和 MySQL。
- 如果遇到依赖问题，请确保所有依赖版本与项目配置一致。
- 前后端服务需要同时运行，才能正常使用图书管理系统。