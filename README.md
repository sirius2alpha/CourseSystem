# CourseSystem

## 需求分析



## 概要设计

### 系统架构

![系统架构图](https://raw.githubusercontent.com/sirius2alpha/Typora-pics/master/2023/11/upgit_20231103_1699008162.png)

整个教务系统的设计采用了一系列成熟的技术，以确保系统的可靠性和可维护性。以下是对系统设计的进一步扩展：

**前端技术选型：** 在前端方面，我们选择了Vue 3作为主要的前端框架。Vue 3是一款流行的JavaScript框架，它具有简洁的语法和出色的性能，使开发者能够快速构建交互式用户界面。为了提供美观和一致的用户体验，我们采用了Element Plus UI组件库，其中包括了各种现代化的UI元素，如表格、表单、按钮等。这些UI组件不仅提供了视觉上的吸引力，还帮助了系统的一致性和用户友好性。

**通信和数据交互：** 系统的前后端通信是通过RESTful接口来实现的。这种接口设计方式具有清晰的结构和易于理解的URL路径，使前端能够方便地向后端发送HTTP请求以获取数据或执行操作。前端使用axios库来处理HTTP请求，确保与后端服务器的可靠通信。这种标准化的接口设计提高了系统的可扩展性，允许轻松地添加新功能或与其他系统进行集成。

**后端构建和数据管理：** 后端部分采用Spring Boot框架，这是一种用于快速构建Java应用程序的框架。Spring Boot提供了许多便利的功能，包括自动配置、内置的Web服务器等，简化了开发过程。为了管理数据库操作，我们使用了MyBatis-Plus，这是一个强大的持久层框架，能够帮助我们更轻松地进行数据库操作，包括数据的增删改查。数据存储在MySQL数据库中，为系统提供可靠的数据存储和检索功能。

**构建工具：** 为了管理项目依赖和构建系统，我们采用了Maven作为项目管理工具。Maven有助于确保项目的依赖库得到正确管理，而且能够构建可部署的应用程序。这提高了项目的可维护性和可扩展性，使得团队能够更加有效地协作开发。



### MVC设计模式





### 安全设计

为了增强用户密码的安全性和保护用户隐私，我们采用了一种本地密码存储和验证的方法。以下是对这一过程的更详细的扩展说明：

**本地密码保存和验证机制：** 用户密码的保存和验证过程强调了保护用户密码和减少潜在泄漏风险的重要性。在这个机制中，密码不会在明文形式传输到服务器，而是在本地进行处理和计算。

- **用户输入账号和密码：** 用户在本地应用中输入其账号和密码，这一步骤通常在用户设备上进行，保证了密码的机密性。

- **本地摘要计算：** 在用户设备上，采用MD5摘要算法对账号和密码进行计算，生成一个摘要。这个摘要是密码的加密版本，只在用户设备上生成，不会传输到服务器。这可以防止密码明文传输和在网络上的不安全存储。
- **传递用户ID和摘要到服务器：** 用户将账号（用户ID）和计算得到的密码摘要信息传递到服务器。这个传递过程是相对安全的，因为只有密码摘要信息被传输，而不是密码本身。
- **服务器验证：** 服务器接收到用户ID和密码摘要信息后，会在数据库中查找与该用户ID相关的存储的密码摘要。然后，服务器使用相同的MD5摘要算法对用户提供的摘要信息进行计算，并与数据库中的存储摘要进行比较。如果两者匹配，服务器将返回验证成功的响应，允许用户继续访问系统。

**安全性和优点：**

- **密码保护：** 用户密码在本地进行摘要计算，保护了用户密码的机密性，即使在传输过程中也不会暴露在明文形式下。
- **降低泄漏风险：** 由于密码摘要被传输，而不是明文密码，降低了潜在的密码泄漏风险，即使在网络传输中也不容易被攻击者窃取。
- **验证安全：** 服务器验证仍然是基于密码摘要的比对，允许服务器进行密码验证，同时避免了密码明文存储的风险。
- **用户友好：** 用户不需要担心密码明文在传输中的安全问题，同时可以享受相对高水平的安全性。

这种本地密码保存和验证方法在提供用户友好性和安全性之间找到了平衡，有助于降低密码泄漏风险，同时保护用户隐私和密码安全。



### 前后端接口设计

![接口概要](https://raw.githubusercontent.com/sirius2alpha/Typora-pics/master/2023/11/upgit_20231103_1698988238.png)



### 数据库概念设计

![思维导图](https://raw.githubusercontent.com/sirius2alpha/Typora-pics/master/2023/11/upgit_20231103_1698986931.png)

选课系统主要由两个部分组成：学生、教师。

**学生** 是系统的主体，可以进行选课、退课、成绩查询等操作。学生的属性包括学号、学期等。

**教师** 是系统的提供者，可以开课、查看开设课程情况、成绩登入等操作。教师的属性包括教师号、工号等。

选课系统的操作包括选课、退课、成绩查询、课程查询、成绩分析等。

具体来说，学生可以通过选课系统进行选课操作，选择自己感兴趣的课程。教师可以通过选课系统进行开课操作，开设自己擅长的课程。选课系统会根据学生的选课情况和教师的开课情况，生成选课表。学生可以通过选课表查看自己所选的课程信息。学生在完成课程学习后，可以通过选课系统进行成绩查询。选课系统会根据学生的成绩，生成成绩表。学生可以通过成绩表查看自己的成绩情况。

图片中的结构和关系可以用以下图表表示：

```
学生
  属性：学号、学期
  操作：选课、退课、成绩查询

教师
  属性：教师号、工号
  操作：开课、查看开设课程情况、成绩登入

选课系统
  操作：选课、退课、成绩查询、课程查询、成绩分析
```



![表关系](https://raw.githubusercontent.com/sirius2alpha/Typora-pics/master/2023/11/upgit_20231103_1698987548.png)

**学生表** 用于存储学生的基本信息，包括学号、姓名、院系号等。

**教师表** 用于存储教师的基本信息，包括教师号、姓名、工号等。

**开课表** 用于存储课程信息，包括课程号、开课教师、学期等。

**选课表** 用于存储学生选课信息，包括学生学号、课程号、选课学期、平时成绩、考试成绩、总评成绩等。

**教学计划表** 用于存储课程的教学计划，包括课程号、院系号等。

**学院表** 用于存储学院信息，包括院系号、院系名称等。



## 详细设计

### 数据库表设计

**colleges（学院表）**

| 字段名  | 数据类型     | 主键 | 外键 | 说明         |
| ------- | ------------ | ---- | ---- | ------------ |
| id      | int          | ✔    |      | 主键，学院ID |
| college | varchar(255) |      |      | 学院名称     |

```sql
CREATE TABLE `colleges` (
  `id` int NOT NULL,
  `college` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
```

学院表包含学院信息，每个学院都有一个独特的ID作为主键，以及学院的名称。此表用于标识和存储不同学院的基本信息。



**course_plan（课程计划表）**

| 字段名      | 数据类型     | 主键 | 外键 | 说明                               |
| ----------- | ------------ | ---- | ---- | ---------------------------------- |
| course_name | varchar(255) |      |      | 课程名称                           |
| course_id   | int          | ✔    |      | 主键，课程ID                       |
| college_id  | int          | ✔    | ✔    | 学院ID，外键参考colleges表的id字段 |

```sql
CREATE TABLE `course_plan` (
  `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `course_id` int NOT NULL,
  `college_id` int NOT NULL,
  PRIMARY KEY (`course_id`,`college_id`) USING BTREE,
  KEY `course_id` (`course_id`) USING BTREE,
  KEY `college_id` (`college_id`) USING BTREE,
  CONSTRAINT `course_plan_ibfk_1` FOREIGN KEY (`college_id`) REFERENCES `colleges` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
```

课程计划表记录了不同课程的计划信息，包括课程名称、课程ID和学院ID。主键是课程ID，可以通过外键与学院表关联，以确定每门课程所属的学院。



**current_courses（当前课程表）**

| 字段名     | 数据类型     | 主键 | 外键 | 说明                                         |
| ---------- | ------------ | ---- | ---- | -------------------------------------------- |
| no         | int          |      |      | 编号                                         |
| time       | varchar(255) | ✔    |      | 时间                                         |
| teacher_id | int          | ✔    | ✔    | 教师ID，外键参考teachers表的id字段           |
| course_id  | int          | ✔    | ✔    | 课程ID，外键参考course_plan表的course_id字段 |
| number     | int          |      |      | 人数                                         |
| capacity   | int          |      |      | 容量                                         |

```sql
CREATE TABLE `current_courses` (
  `no` int DEFAULT NULL,
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `teacher_id` int NOT NULL,
  `course_id` int NOT NULL,
  `number` int NOT NULL,
  `capacity` int NOT NULL,
  PRIMARY KEY (`time`,`teacher_id`,`course_id`) USING BTREE,
  KEY `course_id` (`course_id`) USING BTREE,
  KEY `current_courses_ibfk_2` (`teacher_id`) USING BTREE,
  KEY `term` (`time`) USING BTREE,
  KEY `no` (`no`) USING BTREE,
  CONSTRAINT `current_courses_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course_plan` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `current_courses_ibfk_2` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
```

当前课程表用于追踪当前正在进行的课程，包括课程的编号、时间、教师ID、课程ID、学生人数和课程容量。主键由时间、教师ID和课程ID组成，确保每门课程的唯一性。该表包括多个外键，与课程计划和教师表建立关联。



**selected_courses（选课表）**

| 字段名            | 数据类型 | 主键 | 外键 | 说明                                          |
| ----------------- | -------- | ---- | ---- | --------------------------------------------- |
| student_id        | int      | ✔    | ✔    | 学生ID，外键参考students表的id字段            |
| current_course_id | int      | ✔    | ✔    | 当前课程ID，外键参考current_courses表的no字段 |
| pscj              | double   |      |      | 平时成绩                                      |
| kscj              | double   |      |      | 考试成绩                                      |
| score             | double   |      |      | 总成绩                                        |

```sql
CREATE TABLE `selected_courses` (
  `student_id` int NOT NULL,
  `current_course_id` int NOT NULL,
  `pscj` double DEFAULT NULL,
  `kscj` double DEFAULT NULL,
  `score` double DEFAULT NULL,￼

退课功能
  PRIMARY KEY (`student_id`,`current_course_id`) USING BTREE,
  KEY `student_id` (`student_id`) USING BTREE,
  KEY `current_course_id` (`current_course_id`) USING BTREE,
  CONSTRAINT `selected_courses_ibfk_4` FOREIGN KEY (`current_course_id`) REFERENCES `current_courses` (`no`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `selected_courses_ibfk_5` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
```

选课表记录学生所选的课程以及相关成绩信息，包括学生ID、当前课程ID、平时成绩、考试成绩和总成绩。主键由学生ID和当前课程ID组成，确保每个学生在每门课程中的唯一性。该表包括多个外键，分别与学生表和当前课程表建立关联。



**students（学生表）**

| 字段名 | 数据类型     | 主键 | 外键 | 说明                               |
| ------ | ------------ | ---- | ---- | ---------------------------------- |
| id     | int          | ✔    |      | 主键，学生ID                       |
| name   | varchar(255) |      |      | 学生姓名                           |
| yxh    | int          |      | ✔    | 院系号，外键参考colleges表的id字段 |

```sql
CREATE TABLE `students` (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `yxh` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `yxh` (`yxh`) USING BTREE,
  CONSTRAINT `students_ibfk_1` FOREIGN KEY (`yxh`) REFERENCES `colleges` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
```

学生表存储学生的个人信息，包括学生ID、姓名和所属学院的ID。主键是学生ID，可通过外键与学院表建立关联，以标识每个学生所属的学院。



**teachers（教师表）**

| 字段名 | 数据类型     | 主键 | 外键 | 说明         |
| ------ | ------------ | ---- | ---- | ------------ |
| id     | int          | ✔    |      | 主键，教师ID |
| name   | varchar(255) |      |      | 教师姓名     |

```sql
CREATE TABLE `teachers` (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `yxh` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `yxh` (`yxh`) USING BTREE,
  CONSTRAINT `teachers_ibfk_1` FOREIGN KEY (`yxh`) REFERENCES `colleges` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
```

教师表包含教师的个人信息，包括教师ID、姓名和所属学院的ID。主键是教师ID，可通过外键与学院表建立关联，以标识每个教师所属的学院。



**user（用户表）**

| 字段名   | 数据类型     | 主键 | 外键 | 说明            |
| -------- | ------------ | ---- | ---- | --------------- |
| id       | int          | ✔    |      | 主键，用户ID    |
| password | varchar(255) |      |      | 用户密码md5摘要 |
| role_id  | varchar(255) |      |      | 用户角色ID      |

```sql
CREATE TABLE `user` (
  `id` int NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
```

用户表用于身份验证和访问控制，包括用户ID、密码摘要和角色ID。主键是用户ID，可用于标识和验证用户的身份。该表支持用户管理和权限控制。



### 表检查约束设计

1. **colleges（学院表）**:

   - 该表有一个主键约束，保证每个学院都有唯一的ID。

   - 无其他检查约束。

2. **course_plan（课程计划表）**:
   - 主键约束由课程ID和学院ID组成，确保每门课程在每个学院中的唯一性。
   - 外键约束将学院ID与学院表中的ID字段关联，以确保课程计划表中的学院ID引用了有效的学院。

3. **current_courses（当前课程表）**:
   - 主键约束由时间、教师ID和课程ID组成，确保每门课程在每个时间点、由每个教师进行的唯一性。
   - 外键约束将课程ID与课程计划表中的课程ID字段关联，以确保当前课程表中的课程ID引用了有效的课程计划。
   - 外键约束将教师ID与教师表中的ID字段关联，以确保当前课程表中的教师ID引用了有效的教师。

4. **selected_courses（选课表）**:
   - 主键约束由学生ID和当前课程ID组成，确保每个学生在每门课程中的唯一性。
   - 外键约束将学生ID与学生表中的ID字段关联，以确保选课表中的学生ID引用了有效的学生。
   - 外键约束将当前课程ID与当前课程表中的编号字段关联，以确保选课表中的当前课程ID引用了有效的当前课程。

5. **students（学生表）**:
   - 该表有一个主键约束，保证每个学生都有唯一的ID。
   - 外键约束将学院ID与学院表中的ID字段关联，以确保学生表中的学院ID引用了有效的学院。

6. **teachers（教师表）**:
   - 该表有一个主键约束，保证每个教师都有唯一的ID。
   - 外键约束将学院ID与学院表中的ID字段关联，以确保教师表中的学院ID引用了有效的学院。

7. **user（用户表）**:
   - 该表有一个主键约束，保证每个用户都有唯一的ID。
   - 无其他检查约束。

这些表检查约束的设计确保了数据的完整性和一致性，同时通过外键约束建立了表之间的关联关系，使数据库的数据具有高度的可信度。



### 前端页面设计

```
src
├── App.vue
├── assets
├── components
│   └── CourseSchedule.vue
├── main.js
├── router
│   └── index.js
└── views
    ├── IndexLogin.vue
    ├── StudentPages.vue
    ├── StudentQueryScore.vue
    └── TeacherPages.vue
```

- `main.js`：这是项目的入口文件，它负责创建 Vue 应用实例，安装插件（如 ElementPlus 和 router），并将应用挂载到 DOM。
- `App.vue`：这是项目的根组件，它通常包含全局的布局或导航。
- `assets`：这个目录通常用于存放项目的静态资源，如图片、样式文件等。
- `components`：这个目录用于存放 Vue 组件。在这个项目中，有一个 `CourseSchedule.vue` 组件，可能用于显示课程表。
- `router`：这个目录用于存放路由配置。`index.js` 文件定义了应用的路由规则。
- `views`：这个目录用于存放页面级的 Vue 组件。在这个项目中，有 `IndexLogin.vue`（登录页面）、`StudentPages.vue`（学生页面）、`StudentQueryScore.vue`（学生查询成绩页面）和 `TeacherPages.vue`（教师页面）。



#### 登录页面

登录页面输入学生或者老师的账号就能实现登录，并能够根据自动跳转到学生界面或者老师的界面。

![登录页面](https://raw.githubusercontent.com/sirius2alpha/Typora-pics/master/2023/11/upgit_20231103_1699008642.png)

#### 学生界面

学生界面有四个功能：选课、退课、成绩查询、课表查询

**选课功能**

选课功能如下，对课程号、课程名、教师号、教师姓名、上课时间进行检索，之后会返回相应开课信息。

![学生选课-1](https://raw.githubusercontent.com/sirius2alpha/Typora-pics/master/2023/11/upgit_20231103_1699009956.png)

**退课功能**

通过多选框勾选要退选的课程，点击退课按钮，就会向后端发送退课的请求，之后再重新查询已选课程信息，响应式更新页面内容。

![学生-退课](https://raw.githubusercontent.com/sirius2alpha/Typora-pics/master/2023/11/upgit_20231103_1699010298.png)

**成绩查询**

点击边栏的成绩查询功能，会触发绑定的函数fetchScores()，在选课表中的该学生进行查询，查询他的所有课程和成绩。分数那一栏没有成绩说明老师还没有进行成绩录入。

![学生-成绩查询](https://raw.githubusercontent.com/sirius2alpha/Typora-pics/master/2023/11/upgit_20231103_1699010525.png)

**课表查询**

对退课处更新后的课表，课表的数据在这个板块继续使用。根据课程的时间在课程表中进行填入。

功能实现的javascript代码如下：

```js
 this.myCourses.forEach((course) => {
      // 将课程的时间转换为字符数组
      const times = course.time.split('');
      console.log("拆分的时间", times);

      // 提取第一个字符作为周几，并映射到英文
      const day = dayMapping[times[0]];
      console.log("周", day);

      // 提取第2和4个数字作为上课的时间
      // 注意9-10,11-12的情况
      let startTime = "";
      let endTime = "";
      if (times[1] === "9") {
        startTime = timeMapping[9];
        endTime = timeMapping[10];
      }
      else if (times[2] != "-") {
        startTime = timeMapping[11];
        endTime = timeMapping[12];
      }
      else {
        startTime = timeMapping[times[1]];
        endTime = timeMapping[times[3]];
      }
      console.log("上课时间", startTime, endTime);

      // 根据开始和结束时间找到对应的表格行的索引
      const startIndex = this.tableData.findIndex((row) => row.time === startTime);
      const endIndex = this.tableData.findIndex((row) => row.time === endTime);
      console.log("开始和结束的索引", startIndex, endIndex);

      if (startIndex !== -1 && endIndex !== -1) {
        // 根据课程的日期更新表格行的对应列
        for (let i = startIndex; i <= endIndex; i++) {
          this.tableData[i][day] = course.course_name;
        }
      }
    });
```

![image-20231103193058119](https://raw.githubusercontent.com/sirius2alpha/Typora-pics/master/2023/11/upgit_20231103_1699011058.png)

#### 教师界面

教师界面左上角是老师的姓名和工号，有一个标签为“老师”。教师界面主要设置了两个功能：

**开课详情**

功能1是查看这个教师的开课详情，已选人数等信息。

功能2是对班级内的学生进行成绩的登入。

![image-20231103185104378](https://raw.githubusercontent.com/sirius2alpha/Typora-pics/master/2023/11/upgit_20231103_1699008664.png)

**成绩录入**

在点击成绩录入之后，在单选框这里选择班级，就可以对学生的平时成绩和考试成绩进行录入。后端会根据平时成绩和考试成绩对应的占比（3:7）生成总评成绩，并保存在数据库中。

![教师-成绩录入](https://raw.githubusercontent.com/sirius2alpha/Typora-pics/master/2023/11/upgit_20231103_1699013770.png)





### 后端服务设计（可贴代码+解释，展示关键部分就行，不用很详细）



## 测试用例





## 个人体会

袁浩：此次教务系统网站项目中我主要负责的是前端、接口设计、数据库设计、技术选型部分。此次和团队成员们一起先进行需求分析，在明白我们的主要诉求之后，先进行数据库的设计，规划各个表的属性和联系，降低各个表之间的耦合，合理设置外键保证数据正确。之后建立数据库，生成数据，开始针对前后端之间的交互设计RESTful规范接口，在接口设计的过程中我们也一直在不断学习，积极沟通交流，以便修改不合理之处。在设计好接口之后，我们团队开始分工进行前后端的建设。我负责的前端采用的是Vue框架，配合Element-Plus UI框架的使用，对网站页面进行绘制，使用axios库发送和处理与后端交互的数据，在浏览器中不断使用控制台调试观察，使数据正确渲染到页面上。团队开发过程中使用git作为版本管理工具，极高地提高了团队协作的效率，另外API管理和测试的平台Apifox也提供了极大的便利和快捷，也节省了很多沟通的时间。总而言之，此次教务系统开发的过程让我收获很多，不仅是在前端技术栈上的提升，也是在团队合作、整体项目构建能力上面的成长和进步。





-------------------------------------------------------------------------------



## 技术栈

Vue3 + Element Plus + axios + Springboot + MyBatis-Plus+Mysql

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