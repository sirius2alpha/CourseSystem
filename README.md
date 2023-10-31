# CourseSystem
## 技术栈
Vue3 + Elemrnt Plus + axios + springboot + +mysql

## 前端
前端相关内容在文件夹frontend下

确保在电脑上已经安装node.js和npm

执行命令`npm install`从pakage.json中安装依赖；

执行命令`npm run serve`启动项目

## 后端



## 数据库
数据库SQL文件course-system.sql直接用数据库软件执行之后本地就可以得到一个数据库了，方便开发测试用
-   学生表 students
-   教师表 teachers
-   开课表 current_courses
-   选课表和成绩表 selected_courses
-   教学计划表 course_plan
-   学院表 colleges
-   登录管理表 users

### 注释
selected_courses
-   因为选课表选了之后就一定会有考试成绩，所以就把选课表和成绩表合在一起了。
-   score = ROUND(pscj * 0.4 + kscj * 0.6, 1)，46开，保留了一位小数


users
-   所有的用户来自于学生表和教师表
-   登录密码都是123456,msg字段存储的是ID+‘123456’字符串的MD5摘要
-   role值为1表示学生，2表示教师

## git使用教程
https://juejin.cn/post/7262179139897016380