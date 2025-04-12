# 课程管理系统 - 后端

## 项目概述

本项目是一个基于Spring Boot和MyBatis-Plus构建的课程管理系统后端，提供课程选择、教师管理、学生管理等核心功能。

## 技术栈

- **核心框架**：Spring Boot
- **ORM框架**：MyBatis-Plus
- **数据库**：MySQL
- **构建工具**：Maven

## 项目结构

```
backend/
├── src/main/java/com/example/project/
│   ├── config/           # 配置相关类
│   ├── controller/       # 控制器层，处理HTTP请求
│   ├── service/          # 服务层，包含业务逻辑
│   │   └── impl/         # 服务接口实现
│   ├── mapper/           # 数据访问层，与数据库交互
│   ├── entity/           # 实体类，对应数据库表
│   ├── dto/              # 数据传输对象
│   ├── common/           # 通用组件
│   ├── exception/        # 异常处理
│   └── ProjectApplication.java  # 应用程序入口
├── src/main/resources/
│   ├── mapper/           # MyBatis XML映射文件
│   └── application.yml   # 应用程序配置文件
└── pom.xml               # Maven依赖管理
```

## 核心模块详解

### 配置层 (config/)

- **WebMvcConfig.java**: Web相关配置，处理跨域请求、静态资源映射等配置

### 控制器层 (controller/)

- **CourseApiController.java**: 课程相关API，处理课程查询、学生选课/退课、成绩管理等请求
- **TeacherApiController.java**: 教师相关API，处理教师课程管理、学生名单获取等请求
- **UserApiController.java**: 用户相关API，处理用户认证、信息管理等请求

### 服务层 (service/)

- **CourseService.java**: 课程服务接口，定义课程管理、选课、成绩等业务逻辑
- **UserService.java**: 用户服务接口，定义用户认证、信息管理等业务逻辑
- **TeachersService.java**: 教师服务接口，定义教师特有业务逻辑
- **StudentsService.java**: 学生服务接口，定义学生特有业务逻辑
- **impl/**: 包含上述服务接口的实现类

### 数据访问层 (mapper/)

MyBatis-Plus的Mapper接口，负责与数据库交互，每个实体类对应一个Mapper接口。

### 实体层 (entity/)

- **User.java**: 用户实体类，存储用户基本信息
- **Course.java**: 课程实体类，存储课程基本信息
- **Teachers.java**: 教师实体类，存储教师特有信息
- **Students.java**: 学生实体类，存储学生特有信息
- **SelectedCourses.java**: 已选课程实体类，存储学生选课信息
- **CurrentCourses.java**: 当前课程实体类，存储当前可选课程信息
- **CoursePlan.java**: 课程计划实体类，存储课程安排信息

### 数据传输对象 (dto/)

- **CourseDTO.java**: 课程数据传输对象，用于前后端数据交互

### 通用组件 (common/)

- **ApiResponse.java**: 统一API响应封装，规范返回格式
- **Constants.java**: 常量定义，存储系统中使用的常量
- **QueryPageParam.java**: 分页查询参数封装
- **MybatisPlusConfig.java**: MyBatis-Plus配置类

### 异常处理 (exception/)

- **BusinessException.java**: 业务异常类，用于表示业务逻辑错误
- **GlobalExceptionHandler.java**: 全局异常处理器，统一处理系统异常

## API接口

### 用户相关

- `POST /api/users/{id}/pwd`: 用户登录
- `GET /api/users/list`: 获取所有用户
- `POST /api/users/save`: 添加用户
- `POST /api/users/mod`: 修改用户
- `GET /api/users/delete`: 删除用户

### 课程相关

- `GET /api/courses`: 根据条件查询课程
- `GET /api/students/{userId}/courses`: 获取学生已选课程
- `POST /api/students/{userId}/courses`: 学生选课
- `DELETE /api/students/{userId}/courses`: 学生退课
- `GET /api/students/{userId}/courses/score`: 获取学生成绩
- `GET /api/teachers/{userId}/courses`: 获取教师课程
- `GET /api/teachers/{teacherId}/courses/{courseId}`: 获取教师课程的学生名单

### 教师相关

- `GET /api/teachers/{teacherId}/courses/{courseId}/students`: 获取教师课程的学生名单
- `PUT /api/teachers/{teacherId}/courses/{courseId}/students/{studentId}/score`: 更新学生课程成绩

## 启动指南

1. 确保已安装Java 8+和Maven
2. 配置`application.yml`中的数据库连接信息
3. 执行`mvn spring-boot:run`启动应用
4. 应用默认运行在`http://localhost:8080`

## 开发指南

### 添加新实体

1. 在`entity`包中创建实体类
2. 在`mapper`包中创建对应的Mapper接口
3. 在`resources/mapper`目录创建XML映射文件（如需自定义SQL）
4. 在`service`包中创建服务接口和实现

### 添加新API

1. 在适当的控制器中添加新的API方法
2. 使用`ApiResponse`封装返回结果
3. 确保添加适当的异常处理 