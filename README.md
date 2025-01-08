## 实现的功能

- 学生选课/退课/查询成绩/查询课表
- 教师录入成绩/查询课表

## 技术栈

![系统架构图](https://s2.loli.net/2023/11/04/6MXEqsY5A8lTzGD.png)

## 前端

确保在电脑上已经安装node.js和npm

在`frontend/`目录下

执行命令`npm install`从pakage.json中安装依赖；

执行命令`npm run serve`启动项目

## 数据库

数据库SQL文件course-system.sql直接用数据库软件执行之后本地就可以得到一个数据库了，方便开发测试用

-   学生表 students
-   教师表 teachers
-   开课表 current_courses
-   选课表和成绩表 selected_courses
-   教学计划表 course_plan
-   学院表 colleges
-   登录管理表 users



#### 把sql文件导入到本地的mysql中

在本地安装好mysql环境之后，记得要在项目中修改mysql的登录用户名和密码（在backend/src/main/resources/application.yml中）

> 注意本地的数据库的名字应该为course-system，不然读取不到数据库
> 注意是否有权限连接数据库

登录上mysql之后，在含有course-system.sql的目录下执行

```sql
create database `course-system`;
use `course-system`;
source course-system.sql;
```


### 关于数据库表的一些注释

selected_courses

-   因为选课表选了之后就一定会有考试成绩，所以就把选课表和成绩表合在一起了。
-   score = ROUND(pscj * 0.4 + kscj * 0.6, 1)，46开，保留了一位小数


users

-   所有的用户来自于学生表和教师表
-   登录密码都是123456,msg字段存储的是ID+‘123456’字符串的MD5摘要
-   role值为1表示学生，2表示教师


## 后端

#### 版本说明

java17，java8都能跑，其他的版本没有测试

使用Maven进行构建项目，maven 3.8.7 测试正常


#### 后端启用

在包含有pom.xml的目录(`backend/`)下执行`mvn clean install`执行项目的构建；

> 本地的maven构建失败的话可以尝试`backend/mvnw`进行构建
> 
> Linux/macOS：
> ```bash
> ./mvnw clean install
> ```
> Windows：
>
> ```bash
> mvnw.cmd clean install
> ```

在生成的`backend/target/`的目录下会有一个.jar文件，直接运行这个文件就可以了`java -jar course-system.jar`


## 其他使用帮助

### git使用教程

[git使用教程](https://sirius1y.me/posts/notes/dev/%E6%8C%87%E5%8D%97%E5%9B%A2%E9%98%9Fgit%E5%8D%8F%E4%BD%9C/)

### 项目文档、报告

详见仓库[wiki页面](https://github.com/sirius2alpha/CourseSystem/wiki)
