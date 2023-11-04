## 技术栈

![系统架构图](https://s2.loli.net/2023/11/04/6MXEqsY5A8lTzGD.png)

## 前端

前端相关内容在文件夹frontend下

确保在电脑上已经安装node.js和npm

执行命令`npm install`从pakage.json中安装依赖；

执行命令`npm run serve`启动项目



## 后端

使用Maven进行构建项目



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



## 有所改进的地方

- 对于管理员，可以进行：对于学期的选择
- 对于教师登录成绩之后，数据库进行更新，引进角色“教务系统管理员”，需要他统一进行发布成绩；此时所有的学生才能够查看到成绩，否则显示该学期成绩还没有发布；
- 对于管理员，可以进行：给老师批准开设课程；
  教师可以新增一个功能板块，提交开课申请给管理员。
- 对于管理员，可以进行：查看各个班级教师的成绩情况等
- 对课表的样式可以继续优化



## git使用教程

https://juejin.cn/post/7262179139897016380



下面是项目报告：

-------------------------------------------------------------------------------



# CourseSystem



## 1	需求分析

为了满足现代大学教学的需求，本章节将对聪明教务系统平台的选课系统进行深入的需求分析。

### 1.1 功能模块需求概述

##### 1.1.1 网页功能需求概述

本聪明教务系统平台项目开发了电脑网页端，基于上海大学的教学计划，面向教师和学生两个不同的角色设计了不同的页面，同时提供不同的功能。当通过登入界面进入功能页面后，学生可以有选课、退课、成绩查询、课表查询几个可选操作；教师则具有查看开课详情和录入学生成绩的选项。

具体而言，为了满足上海大学学生的选课需求，针对于学生的四个选项需要实现以下目标：

(1) 选课功能：学生可以在系统内查看所有提供的课程，包括课程的名称、授课老师、课程时间等关键信息，并根据自己的兴趣和学分要求选择相应的课程。

(2) 退课功能：若学生因某种原因需要退选已经选择的课程，系统提供简单而快速的一键退课操作，并确保学生的学分不会因此受到影响。 

(3) 成绩查询功能：学生可以随时登录系统查看自己的每门课程的考试成绩，以便更好地了解自己的学习状况和进度。

(4) 课表查询功能：系统应该为学生提供一个清晰的课表显示界面，使学生能够轻松查看自己在一周内的所有课程时间。

对于教师端，为了满足上海大学教师对于教学管理的需求，针对于教师的两个选项需要实现以下目标：

(1) 查看开课详情：教师可以查看自己所授课程的详细信息，包括课程号、学生名单、已选人数、上课时间等，方便教师管理自己的班级与规划自己的时间。 

(2) 录入学生成绩：教师可以轻松地在系统中输入或修改学生的考试成绩和平时成绩，确保学生的成绩信息准确无误。

##### 1.1.2 后台管理系统模块需求概述

(1) 在后台管理端，为了位处平台运行的稳定，需要管理员对学生的账号和数据进行管理。此处采用模块化的设计思路，系统由选课退课模块和成绩录入两个模块组成。对于选课退课模块，学生是管理员，有权限进行课程的操作，而教师只能读取学生的选课情况；对于成绩录入模块，教师是管理员，负责登记学生的成绩，学生只能查询自己每门课的具体成绩。

(2) 选课退课模块：选课退课模块主要负责学生的选课和退课操作。在此模块中，学生可以自由选择自己感兴趣的课程，并可以在规定的时间内进行退课操作。此外，教师可以查看自己教授课程的学生名单，但无权进行学生的选课或退课操作。

(3) 成绩录入模块：在成绩录入模块中，教师负责对学生的课程成绩进行录入和管理。学生完成考试后，教师可以在此模块中将学生的考试成绩录入系统，学生可以在自己的账号中查询到自己的每门课程成绩。

(4) 账号管理模块：账号管理模块主要用于管理员对学生和教师的账号进行管理。管理员可以添加、删除和修改账号信息，确保平台的用户信息安全。

##### 1.1.3 后台管理系统角色功能需求概述

表1.1 系统控制器列表及其接口功能概述

| 后台管理角色 | 功能需求            |
| ------------ | ------------------- |
| 学生         | 1. 选课管理         |
|              | 2. 成绩查询         |
|              | 3. 课表查询         |
| 教师         | 4. 课程已选详情查询 |
|              | 5. 成绩管理         |

 

### 1.2 系统流程需求

在聪明教务系统中，当到了一学期的选课时间时，系统向学生开放选课权限。学生需要登录自己的账户，进入选课界面，根据自己的兴趣和所需学分来选择相应的课程。系统会实时显示课程的容量、已选人数、课程时间、教师信息等关键数据，以帮助学生做出更为明智的选择。

学生在系统中查找并选择自己想要的课程，点击“申请选课”按钮后，系统会自动检测学生是否满足选该课程的前提条件，如是否已经选过此课程，是否有冲突的时间等。若满足条件，则选课请求会被提交到系统中等待审批，学生可以在自己的账户中查看选课申请的状态。如果学生在选课后发现自己并不想上这门课或有其他原因需要退课，可以在系统中提交退课申请。课程结束后，教师需要在系统中为每一位学生录入成绩。学生可以登录系统查看自己的成绩和排名情况。

通过这一系列的流程设计，聪明教务系统希望为学生和教师提供一个高效、便捷、公正的选课环境，确保学生能够顺利完成学业。

##### 1.2.1 登录流程

聪明教务系统的登录流程主要为学生和教师区别提供了针对各自需求的服务。数据库中存储了所有教师和学生的id和密码，当学生登入时，他会进入学生页面，而老师登陆则会进入教师页面。

##### 1.2.2 选课退课流程

学生在选课页面先通过课程号、教师号等关键词查询相关的课程，再按需选课。选课结束后，学生可以查询已选课程，并一键退选不想要的课程。

##### 1.2.3 登入成绩流程

教师可以查看他所教授的，每门课程的所有学生列表，然后为他们依次登记成绩，存储到数据库中。



### 1.3 网页详细功能

##### 1.3.1 学生模块

学生是选课系统的用户主体。考虑到学生与学生之间的不同的选课需求与偏好差异，该聪明选课系统基于友好的用户交互界面，提供选课的相关功能。学生可以在这个模块查询、选课、退课，以及查看自己的课程分数。

（1）筛选课程

学生可以通过课程号、课程名、教师号、教师姓名和上课时间对该学期开设的课程进行查询。课程号是模糊课程号查询，每学期开课列表都会对课程进行重新编号，学生只需要按照输入列表中的序号，就可以查询所有该序号的课程号。课程名即为开课列表的课程名，教师姓名是开课教师的真实姓名。上课时间的格式为星期几+一节课的开始节数-结束节数，例如一1-2.

（2）选择课程

学生浏览所筛选的课程后，可以勾选需要选择的课程，选课成功后，会有“选课成功”的提示，所选择的课程将出现在课程表中。

（3）退选课程

若学生因为时间冲突、兴趣改变、容量限制等原因无法选择一门课程，需要退选，可以进入退选课程页面，勾选需要退课的课程，再一键退选，然后被退的课程就不再出现在课程表中。

（4）查看课表

聪明教务系统设计的课程表横轴是星期一到星期五，纵轴是早上8点到晚上9点40，每节课45分钟，与上海大学的课程设置相对应 。当学生勾选了一门课后，该课程就会出现在课表中的对应时间，例如篮球出现在“周二”的“8:00-8:45”和“8:55 - 9:40”。如果退选课程，该课程将不再存在在课程表中。

（5）成绩查询

聪明教务系统中的课程表有课程号、课程名、教师姓名和分数四个栏目，展示了一名学生所选的所有课程的信息和成绩。如果教师尚未登入成绩，那么对应课程的成绩一栏就是空，其他栏目信息会由数据库自动填入。

##### 1.3.2 教师模块

相对于学生而言，教师对教务系统的需求较为简单。由于教师不能干涉学生选课，所以聪明教务系统旨在为教师提供一个简单明了易于操作的界面，让教师能够查看自己的课程的学生选课情况，并为这些学生登入成绩。

（1）查看开课详情

为了帮助教师更好地管理和规划自己的教学时间，聪明教务系统为教师提供了查看课程表的功能。在此功能下，教师可以看到每门课的课程号、课程容量、已选人数等详细信息，所有该老师开出的课程都会显示在列表中。

（2）录入学生成绩

教师在系统中可以轻松地查看每一位选课学生的详细信息，包括学号、姓名等基本信息。在结束一个学期或课程阶段后，教师需要为学生登入课程成绩。系统提供一个简洁的界面，允许教师为每个学生输入分数或者成绩等级。

因为一名老师可能教授多个课程，教师可以先选择课程，系统会自动从数据库中调出选择了该课程的学生列表。然后教师即可一一为学生登入平时成绩和考试成绩的分数。登记完毕后，学生可以在学生界面的成绩查询中进行查看。

 

### 1.4 其他需求

##### 1.4.1 系统架构需求

管理系统的前后端应能分离部署，应采用 B/S 架构，系统构件能够灵活部署。

管理系统后端应提供多种 API 接口供小程序预约端和管理系统前端调用，并能够区分用户权限。

##### 1.4.2 软硬件需求

（1）对于后端软件与数据库服务器，应保证其运行在支持 Java 8 的现代 Windows、Linux 或 macOS 操作系统上，调试运行时内存最小 1G，CPU 至少为单核、大于 1.8GHz 主频、ARM 或 x86_64 架构。

（2） 管理系统前端软件在构建后应为静态文件。可以放置于任意 Web 服务器上，并可以根据实际访问需求调整 Web 服务器规格，并采用相关负载均衡和 CDN 技术提高并发和访问速度。

（3）对于管理系统客户端，用户应能使用电脑端或移动端的任意现代浏览器访问网站。电脑端推荐分辨率为至少 1920×1080。

（4）系统可以在满足软硬件需求的条件下稳定的运行，不会出现系统崩溃或数据丢失等情况。

##### 1.4.3 性能需求

由于涉及选课退课等功能，其处理能力主要考虑系统能承载的最大并发用户数，按照用户存量，系统应当能够同时承载的最大并发用户数应达到学校全部潜在学生数量×φ，其中 φ 为 0到 1 的常数，由服务器容量以及实际运行情况确定。

为了让学生能够快捷而顺畅地进行教务相关操作，系统应当较快地处理用户需求并响应管理系统中管理员和相关业务员的操作需求。在包括高峰期的任意情况下，所有常规查询、系统操作的单条响应时间应小于 1s，对于退课请求，必须在 1 分钟内响应。

##### 1.4.4 故障处理需求

系统应当能够拒绝所有不符合业务逻辑的操作请求，对于操作过程中发生错误时或遭遇断电、关机等情形时，系统应当保存用户已提交的数据，对未提交的数据不做处理，回退发生错误的操作。

##### 1.4.5 安全需求

为了增强用户密码的安全性和保护用户隐私，我们采用了一种本地密码存储和验证的方法。以下是对这一过程的更详细的扩展说明：

（1）本地密码保存和验证机制：

用户密码的保存和验证过程强调了保护用户密码和减少潜在泄漏风险的重要性。在这个机制中，密码不会在明文形式传输到服务器，而是在本地进行处理和计算。

用户输入账号和密码： 用户在本地应用中输入其账号和密码，这一步骤通常在用户设备上进行，保证了密码的机密性。

本地摘要计算： 在用户设备上，采用MD5摘要算法对账号和密码进行计算，生成一个摘要。这个摘要是密码的加密版本，只在用户设备上生成，不会传输到服务器。这可以防止密码明文传输和在网络上的不安全存储。

传递用户ID和摘要到服务器： 用户将账号（用户ID）和计算得到的密码摘要信息传递到服务器。这个传递过程是相对安全的，因为只有密码摘要信息被传输，而不是密码本身。

服务器验证： 服务器接收到用户ID和密码摘要信息后，会在数据库中查找与该用户ID相关的存储的密码摘要。然后，服务器使用相同的MD5摘要算法对用户提供的摘要信息进行计算，并与数据库中的存储摘要进行比较。如果两者匹配，服务器将返回验证成功的响应，允许用户继续访问系统。

（2）安全性和优点

密码保护： 用户密码在本地进行摘要计算，保护了用户密码的机密性，即使在传输过程中也不会暴露在明文形式下。

降低泄漏风险： 由于密码摘要被传输，而不是明文密码，降低了潜在的密码泄漏风险，即使在网络传输中也不容易被攻击者窃取。

验证安全： 服务器验证仍然是基于密码摘要的比对，允许服务器进行密码验证，同时避免了密码明文存储的风险。

用户友好： 用户不需要担心密码明文在传输中的安全问题，同时可以享受相对高水平的安全性。这种本地密码保存和验证方法在提供用户友好性和安全性之间找到了平衡，有助于降低密码泄漏风险，同时保护用户隐私和密码安全。



## 2	概要设计

### 2.1 系统架构

![系统架构图](https://s2.loli.net/2023/11/04/zreEc1khOHxR4Zy.png)

整个教务系统的设计采用了一系列成熟的技术，以确保系统的可靠性和可维护性。以下是对系统设计的进一步扩展：

**前端技术选型：** 在前端方面，我们选择了Vue 3作为主要的前端框架。Vue 3是一款流行的JavaScript框架，它具有简洁的语法和出色的性能，使开发者能够快速构建交互式用户界面。为了提供美观和一致的用户体验，我们采用了Element Plus UI组件库，其中包括了各种现代化的UI元素，如表格、表单、按钮等。这些UI组件不仅提供了视觉上的吸引力，还帮助了系统的一致性和用户友好性。

**通信和数据交互：** 系统的前后端通信是通过RESTful接口来实现的。这种接口设计方式具有清晰的结构和易于理解的URL路径，使前端能够方便地向后端发送HTTP请求以获取数据或执行操作。前端使用axios库来处理HTTP请求，确保与后端服务器的可靠通信。这种标准化的接口设计提高了系统的可扩展性，允许轻松地添加新功能或与其他系统进行集成。

**后端构建和数据管理：** 后端部分采用Spring Boot框架，这是一种用于快速构建Java应用程序的框架。Spring Boot提供了许多便利的功能，包括自动配置、内置的Web服务器等，简化了开发过程。为了管理数据库操作，我们使用了MyBatis-Plus，这是一个强大的持久层框架，能够帮助我们更轻松地进行数据库操作，包括数据的增删改查。数据存储在MySQL数据库中，为系统提供可靠的数据存储和检索功能。

**构建工具：** 为了管理项目依赖和构建系统，我们采用了Maven作为项目管理工具。Maven有助于确保项目的依赖库得到正确管理，而且能够构建可部署的应用程序。这提高了项目的可维护性和可扩展性，使得团队能够更加有效地协作开发。



### 2.2 MVC设计模式

MVC的全名是Model View Controller，是模型(Model)－视图(view)－控制器(controller)的缩写，是一种设计模式。它是用一种业务逻辑、数据与界面显示分离的方法来组织代码，将众多的业务逻辑聚集到一个部件里面，在需要改进和个性化定制界面及用户交互的同时，不需要重新编写业务逻辑，达到减少编码的时间，提高代码复用性。

使用的MVC的目的：它将这些对象、显示、控制分离以提高软件的的灵活性和复用性，MVC结构可以使程序具有对象化的特征，也更容易维护。

- 模型层（Model）：指从现实世界中抽象出来的对象模型，是应用逻辑的反应；它封装了数据和对数据的操作，是实际进行数据处理的地方（模型层与数据库才有交互）
- 视图层（View）：是应用和用户之间的接口，它负责将应用显示给用户 和 显示模型的状态。
- 控制器（Controller）:控制器负责视图和模型之间的交互，控制对用户输入的响应、响应方式和流程；它主要负责两方面的动作，一是把用户的请求分发到相应的模型，二是吧模型的改变及时地反映到视图上。

![MVC model](https://s2.loli.net/2023/11/04/jRsoLNWy1aMTvBK.png)

以我们系统中的用户User对象为例，我们需要分别编写 UserMapper，UserService，和 UserController 来管理与 User 相关的操作。其中 User 对象类充当了模型（Model）的角色，用于处理应用程序的数据和业 务逻辑。UserMapper 充当了数据访问层（Data Access Layer）的角色，负责与数据库进行交互，将数据存储 和检索操作封装在数据访问对象（Data Access Object，DAO）中。UserService 充当了业务逻辑层（Business Logic Layer）的角色，负责处理业务逻辑，将业务操作封装在服务（Service）中。最后，UserController 充 当了控制器（Controller）的角色，负责处理用户请求，并将请求发送给对应的模型进行处理。 在 MVC 架构中，模型、视图和控制器各自独立，彼此之间耦合度较低。这种分离可以使代码更加可维护和可扩展，同时也可以使我们小组中每个人专注于自己的分工领域，提高了开发效率.



### 2.3 安全设计

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



### 2.4 系统接口设计

#### 2.4.1 接口设计规范

我们的系统接口承担了前端和后端之间的数据传递任务，因此一个规范的接口设计标准是必要的。我们在设计接口时基本遵循了 RESTful 接口的设计规范。RESTful 是目前最流行的 API 规范，适用于 Web 接口规范的设计，使用五种 HTTP 方法，对应 CRUD 操作。基于 RESTful 接口的设计规范，可让接口易读，且含义清晰。此外，我们在后端设计使用了 Apifox 来自动化地生成我们的接口文档。Apifox是一款RESTful 接口的、基于 JSON 语言的文档在线自动生成与代码自动生成的工具。这些规范的接口设计和文档生成大大提高了我们小组中前后端开发人员联调的开发效率，使系统的数据规范更为明确。

#### 2.4.2 系统接口列表

去除无效和测试接口，我们系统共设计了 3 个 controller 共 17 个接口，接口为我们小组成员为完成教务选课功能独立编写，其余接口为若依框架自带，用于实现基础的系统功能。我们列出这 3 个 controller 以及它其中接口的大致功能，如表 a 所示。

表a.系统控制器及其接口功能概述

| 控制器标识符            | 控制器名称 | 接口功能概述                                 |
| ----------------------- | ---------- | -------------------------------------------- |
| Seletecourse-controller | 选课控制器 | 查询课程、选择课程、退课、查询成绩、查询课表 |
| User-controller         | 用户控制器 | 用户验证密码进行登录                         |
| Teacher-controller      | 教师控制器 | 查看开课详情、输入学生成绩                   |

#### 2.4.3 前后端接口设计

![接口概要](https://s2.loli.net/2023/11/04/XoMf3gxPTI7zw68.png)





### 2.5 数据库概念设计

![思维导图](https://s2.loli.net/2023/11/04/eBUrjA1qM3Isgvi.png)

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



![表关系](https://s2.loli.net/2023/11/04/WNJUmHIDQ97fnKZ.png)

**学生表** 用于存储学生的基本信息，包括学号、姓名、院系号等。

**教师表** 用于存储教师的基本信息，包括教师号、姓名、工号等。

**开课表** 用于存储课程信息，包括课程号、开课教师、学期等。

**选课表** 用于存储学生选课信息，包括学生学号、课程号、选课学期、平时成绩、考试成绩、总评成绩等。

**教学计划表** 用于存储课程的教学计划，包括课程号、院系号等。

**学院表** 用于存储学院信息，包括院系号、院系名称等。



## 3	详细设计

### 3.1 数据库表设计

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



### 3.2 表检查约束设计

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



### 3.3 前端页面设计

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



#### 3.3.1登录页面

登录页面输入学生或者老师的账号就能实现登录，并能够根据自动跳转到学生界面或者老师的界面。

<img src="https://s2.loli.net/2023/11/04/RM6uQDtzjgqHEZy.png" alt="登录页面" style="zoom:150%;" />

#### 3.3.2学生界面

学生界面有四个功能：选课、退课、成绩查询、课表查询

**选课功能**

选课功能如下，对课程号、课程名、教师号、教师姓名、上课时间进行检索，之后会返回相应开课信息。

![学生选课-1](https://s2.loli.net/2023/11/04/TCxcKZYnBSQvPyw.png)

**退课功能**

通过多选框勾选要退选的课程，点击退课按钮，就会向后端发送退课的请求，之后再重新查询已选课程信息，响应式更新页面内容。

![学生-退课](https://s2.loli.net/2023/11/04/DQBqE1eLtjN7iFT.png)

**成绩查询**

点击边栏的成绩查询功能，会触发绑定的函数fetchScores()，在选课表中的该学生进行查询，查询他的所有课程和成绩。分数那一栏没有成绩说明老师还没有进行成绩录入。

![学生-成绩查询](https://s2.loli.net/2023/11/04/Y8LlSnXeCH2G7d6.png)

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

![image-20231103193058119](https://s2.loli.net/2023/11/04/X2B3feQRqgyicMj.png)

#### 3.3.3 教师界面

教师界面左上角是老师的姓名和工号，有一个标签为“老师”。教师界面主要设置了两个功能：

**开课详情**

功能1是查看这个教师的开课详情，已选人数等信息。

功能2是对班级内的学生进行成绩的登入。

![image-20231103185104378](https://s2.loli.net/2023/11/04/wLYxR2u7JD6Edeh.png)

**成绩录入**

在点击成绩录入之后，在单选框这里选择班级，就可以对学生的平时成绩和考试成绩进行录入。后端会根据平时成绩和考试成绩对应的占比（3:7）生成总评成绩，并保存在数据库中。

![教师-成绩录入](https://s2.loli.net/2023/11/04/jNhOan3rluDzmf9.png)





### 3.4 后端服务设计

#### 3.4.1 Service层概述

根据 MVC 设计模式，我们后端的 Service 层扮演着连接控制器（Controller）和数据访问层（DAO）之间的重要角色。它负责处理系统的业务逻辑，实现核心功能的具体操作。Service 层的设计和实现对于保持系统的可维护性、可扩展性和代码重用性至关重要。

我们设计 Service 层的主要作用在于将业务逻辑从控制器中分离出来，实现业务操作的封装和复用。这有助于实现单一职责原则，使代码更加模块化和清晰。通过 Service 层，可以将复杂的业务流程拆分为更小的逻辑单元，易于测试和维护。在体检管理系统中，我们按照模块和功能的划分，将 Service 层划分为不同的模块。每个模块的 Service 负责实现与其相关的业务逻辑和数据操作，这种模块划分有助于我们小组的协作和开发任务的分工。

此外在设计 Service 层时，我们遵循以下原则：业务流程封装，将复杂的业务流程封装在 Service 层中，以提高代码的可复用性和可维护性。事务管理，对涉及多个数据操作的业务，我们使用事务管理来保证数据的一致性和完整性。

举例来说，学生和教师都可以进行查询，但学生查询成绩，教师查询学生列表。但在在选课的过程中，选课信息的验证、时间冲突检查、数据保存等操作都涉及到业务逻辑的统一处理，虽然 Controller 提供的接口不同，但是它们都可以调用 Service 层的同一个函数。

通过合理的划分和设计，Service 层为体检管理系统提供了清晰的业务逻辑抽象，保证了系统的可维护性和灵活性。

#### 3.4.2 核心业务逻辑概述

在本节中，我们将挑选部分核心服务中的核心业务逻辑进行介绍。

**教师查看班级学生服务**

它定义了与教师相关的API接口。以下是该控制器所实现的功能描述：

依赖注入：使用@Autowired注解注入了五个服务：selectedCoursesService、currentCoursesService、coursePlanService、teachersService和studentsService。也注入了一个名为selectedCoursesMapper的MyBatis Mapper。

它的路径为/api/teachers/{userId}/courses，通过GET请求。

根据教师ID，查询其开设的所有课程，并以JSON格式返回这些课程的详细信息，如课程名、教师名、时间等。

学生名单接口路径为/api/teachers/{userId}/courses/{selectedCourse}，通过GET请求。根据给定的教师ID和课程ID，查询选择了该课程的所有学生，并返回这些学生的信息，如学生ID、学生名等。结果以JSON格式返回。

更新分数接口路径为/api/teachers/{userId}/courses/{selectedCourse}，通过POST请求。根据提供的学生分数列表（平时成绩和考试成绩），计算每个学生的总分，并更新数据库中相应的记录。

成功后返回成功的响应。

该控制器类主要负责处理与教师相关的API请求，包括查询课程详情、查询开课列表、查看学生名单和更新学生分数，主要依赖于注入的服务和MyBatis Mapper来实现。

#### 3.4.3 教师代码举例

##### 教师选课情况查询

当有HTTP GET请求匹配到`/api/teachers/{userId}/courses`这个路径时，这个方法会被调用。{userId}是一个路径变量，它的值会被提取并传给方法参数teacherId。

接着定义了一个名为kaike的方法，接收一个路径变量teacherId，并可能抛出JsonProcessingException异常。使用currentCoursesService（一个服务对象）来查询与给定教师ID相关的CurrentCourses列表。如果查询结果为空（即该教师没有任何课程），则返回失败的结果。

然后创建一个新的列表response，用于存储转换为JSON字符串的Courses对象；循环遍历selectno列表中的每个CurrentCourses对象，并为每个对象执行以下操作：

获取当前课程的编号、课程ID和教师ID。使用coursePlanService查询与当前课程ID相关的CoursePlan对象，并获取课程名称。使用teachersService查询与当前教师ID相关的Teachers对象，并获取教师名称。使用selectedCoursesService查询与当前课程编号相关的SelectedCourses对象，并获取选课学生的数量。为Courses对象设置上述查询到的数据。使用ObjectMapper将Courses对象转换为JSON字符串，并将该字符串添加到response列表中。

最后，如果selectno的大小大于0，返回成功的结果和response列表；否则，返回失败的结果。

```java
@GetMapping("/{userId}/courses")
public Result kaike(@PathVariable("userId") Integer teacherId) throws JsonProcessingException {
    List<CurrentCourses> selectno = currentCoursesService.lambdaQuery()
            .eq(CurrentCourses::getTeacherId, teacherId).list();
    if (selectno.size() == 0)
        return Result.fail();
    List<String> response = new ArrayList<>();
    Integer no;
    int courseid, teacherid;
    Courses courses = new Courses();

    for (int i = 0; i < selectno.size(); i++) {
        no = selectno.get(i).getNo();
        courseid = selectno.get(i).getCourseId();
        courses.setCourse_id(courseid);
        List<CoursePlan> coursesname = coursePlanService.lambdaQuery()
                .eq(CoursePlan::getCourseId, courseid).list();
        courses.setCourse_name(coursesname.get(0).getCourseName());
        teacherid = selectno.get(i).getTeacherId();
        courses.setTeacher_id(teacherid);
        List<Teachers> teachersname = teachersService.lambdaQuery()
                .eq(Teachers::getId, teacherid).list();
        courses.setTeacher_name(teachersname.get(0).getName());
        courses.setCapacity(50);
        List<SelectedCourses> selectno1 = selectedCoursesService.lambdaQuery()
                .eq(SelectedCourses::getCurrentCourseId, no).list();
        courses.setSelected_number(selectno1.size());
        courses.setTime(selectno.get(i).getTime());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(courses);
        response.add(i, json);
    }
    return selectno.size() > 0 ? Result.suc(response, selectno.size()) : Result.fail();
}
```

 

##### 教师成绩录入功能

`@GetMapping("/{userId}/courses/{selectedCourse}")`指示当有HTTP GET请求匹配到`/{userId}/courses/{selectedCourse}`这个路径时，下面的方法会被调用。{userId} 和 {selectedCourse} 是两个路径变量。`@PathVariable("selectedCourse") Integer courseId)` 方法名是xueshengmingdan，它接收两个路径变量：teacherId 和 courseId。这个方法主要执行以下操作：

查询与给定的教师ID和课程ID相匹配的CurrentCourses。如果没有匹配的数据，返回失败结果。创建一个score对象，并为其设置默认属性值。查询已选择当前课程的学生，并为每个学生创建一个score对象，然后将这些对象转换为JSON字符串，添加到响应列表中。如果响应列表不为空，返回成功的结果；否则，返回失败的结果。

`@PostMapping("/{userId}/courses/{selectedCourse}")`指示当有HTTP POST请求匹配到`/{userId}/courses/{selectedCourse}`这个路径时，下面的方法会被调用。

```java
public Result updatescore(@RequestBody List<score> Score, @PathVariable("userId") Integer teacherId, @PathVariable("selectedCourse") Integer courseId) throws JsonProcessingException
```

这个方法名是updatescore，它接收一个请求体（即一个score对象列表）和两个路径变量：teacherId 和 courseId。这个方法主要执行以下操作：

查询与给定的教师ID和课程ID相匹配的CurrentCourses。遍历请求体中的每个score对象，计算每个学生的总分，并使用LambdaUpdateWrapper来更新数据库中的对应记录。最后返回成功的结果。

```java
 @GetMapping("/{userId}/courses/{selectedCourse}")
    public Result xueshengmingdan(@PathVariable("userId") Integer teacherId,
                                  @PathVariable("selectedCourse") Integer courseId) throws JsonProcessingException {
        List<CurrentCourses> selectno = currentCoursesService.lambdaQuery()
                .eq(CurrentCourses::getTeacherId, teacherId)
                .eq(CurrentCourses::getCourseId, courseId).list();
        if (selectno.size() == 0)
            return Result.fail("没有学生选这个课");
        List<String> response = new ArrayList<>();
        Integer no = selectno.get(0).getNo();
        int studentid;
        score Score = new score();
        Score.setTeacher_id(teacherId);
        Score.setCourse_id(courseId);
        Score.setDaily_score(0);
        Score.setExamination_score(0);
        List<SelectedCourses> selectno1 = selectedCoursesService.lambdaQuery()
                .eq(SelectedCourses::getCurrentCourseId, no).list();
        for (int i = 0; i < selectno.size(); i++) {
            studentid = selectno1.get(i).getStudentId();
            List<Students> stuname = studentsService.lambdaQuery()
                    .eq(Students::getId, studentid).list();
            Score.setStudent_id(studentid);
            Score.setStudent_name(stuname.get(0).getName());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(Score);
            response.add(i, json);
        }
        return selectno.size() > 0 ? Result.suc(response, selectno.size()) : Result.fail();
    }


    @PostMapping("/{userId}/courses/{selectedCourse}")
    public Result updatescore(@RequestBody List<score> Score,
                              @PathVariable("userId") Integer teacherId,
                              @PathVariable("selectedCourse") Integer courseId) throws JsonProcessingException {
        List<CurrentCourses> selectcourse = currentCoursesService.lambdaQuery()
                .eq(CurrentCourses::getCourseId, courseId)
                .eq(CurrentCourses::getTeacherId, teacherId).list();
            double pscj,kscj,score;
        for (int j = 0; j < Score.size(); j++) {
            pscj=Score.get(j).getDaily_score();
            kscj=Score.get(j).getExamination_score();
            score=pscj*0.3+kscj*0.7;
            LambdaUpdateWrapper<SelectedCourses> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(SelectedCourses::getStudentId, Score.get(j).getStudent_id())
                    .eq(SelectedCourses::getCurrentCourseId,selectcourse.get(0).getNo())
                    .set(SelectedCourses::getPscj,pscj)
                    .set(SelectedCourses::getKscj, kscj)
                    .set(SelectedCourses::getScore, score);
            selectedCoursesMapper.update(null, lambdaUpdateWrapper);
        }
        return Result.suc();
    }
}
```



## 4	测试用例

### 4.1. 后端管理系统测试

#### 4.1.1 登入界面

在登录界面，测试人员进行下述测试：

<img src="https://s2.loli.net/2023/11/04/XP4pdgRTeIh7sGc.png" alt="image-20231104144650885" style="zoom:150%;" />

 

​														**图 登录界面视图**

**学生登入**

数据库中的学生全部是23开头，例如231000谢晓明，密码默认为123456。如果输入正确，则跳转到学生界面，否则报错。下图的测试示例中，使用了过长的用户名，显示“登录失败”。

<img src="https://s2.loli.net/2023/11/04/4LFmM8IJyvTbjXV.png" alt="image-20231104144700544" style="zoom:150%;" />

​														**图 不合规用户名报错实例**

**教师登入**

教师的用户名均为1开头，和学生登入的规则一样，如果教师输入了错误的用户名或者密码，也会返回错误信息。下图的测试中，输入了错误的密码。

<img src="https://s2.loli.net/2023/11/04/wZoyXLcU32hVNJe.png" alt="image-20231104144711023" style="zoom:150%;" />

​														**图 错误密码报错实例**



#### 4.1.2. 学生界面

以231000谢晓明为例，进入学生界面后，首先停留在选课界面。

<img src="https://s2.loli.net/2023/11/04/jgVwyCMSnWlr165.png" alt="image-20231104144721283" style="zoom:150%;" />

​														**图 登录成功的学生界面视图**

**选课功能**

学生可以进行查询和选课的操作。当查询成功或者选择成功后，都会返回操作成功的提示。

​				![image-20231104144729043](https://s2.loli.net/2023/11/04/elsBEz8FyvAtdiM.png)![image-20231104144735949](https://s2.loli.net/2023/11/04/vlim6dKZWHFV7Gj.png)

​														**图 课程查询（左）和选课示例（右）**

**退课功能**

选课后，学生如果有不想继续选择的课，可以进入退课界面，勾选想退的课，并一键退课。

​				![image-20231104144758503](https://s2.loli.net/2023/11/04/fJlGbNdUrVKAvOo.png)![image-20231104144802532](https://s2.loli.net/2023/11/04/IuB1i84wsbjRYEG.png)

​														**图 退课界面（左）与退课成功提示（右）**

**课程表功能**

在选课的过程中，学生可以通过查看当前的课程表，来辅助进行排课。

<img src="https://s2.loli.net/2023/11/04/KZ8ALErWexVQINm.png" alt="image-20231104144806136" style="zoom:150%;" />

​														**图 学生课程表视图**

**成绩查询功能**

同时，学生还可以在教务系统中查看自己所选的课程的成绩。

<img src="https://s2.loli.net/2023/11/04/lM6IkiXcjYUfRgP.png" alt="image-20231104144813943" style="zoom:150%;" />

​														**图 学生成绩查询界面视图**



#### 4.1.3. 教师界面

教师号的id开头是1，通过输入教师id并登录，可以进入到教师默认的开课详情界面，同时成功进入时提示。例如，测试用例为100021贾致远老师，进入到默认界面后，可以看到他开设了两门课程，分别是1019 golang和1005离散数学，同时还显示了这两门课程的课程容量、已选人数和上课时间。

<img src="https://s2.loli.net/2023/11/04/6yW4SRVlIDxoNAq.png" alt="image-20231104144833999" style="zoom:150%;" />

​														**图 教师开课详情界面示例**

**教师开课详情功能**

教师的工作之一就是管理学生的成绩。点开成绩录入页面，可以看到贾致远老师能够选择他所开设的课程，并为选择他的课程的所有学生录入成绩。

<img src="https://s2.loli.net/2023/11/04/PUcWv4sFekYTniB.png" alt="image-20231104144847549" style="zoom:150%;" />

​														**图 教师成绩录入功能的班级选择选项视图**

**教师成绩录入功能**

此处的测试用例为1005 离散数学课程。选择课程后，页面上自动显示出选择了该门课程的学生的列表，如图，这门课只有一名学生，是231043龙安琪。贾致远老师此时可以为他等级成绩，假设龙安琪的成绩是60 - 60，登记完毕后选择保存，系统提示保存成功。

<img src="https://s2.loli.net/2023/11/04/ZobsMh1aqVXpec7.png" alt="image-20231104144855051" style="zoom:150%;" />

​														**图 教师成绩录入成功示例**

此时再返回登录界面，登录龙安琪的账号，查看他所选课程的成绩，可以看到贾致远老师的成绩已经成功录入，可以被查询。

<img src="https://s2.loli.net/2023/11/04/JOwIXjgPZeuMYs9.png" alt="image-20231104144902703" style="zoom:150%;" />

​														**图 学生成绩录入成功示例**



### 4.2. 网站应用测试

为了测试网站的功能，该部分从学生选课开始进行全部流程。登入231043龙安琪的账号，为他选择课程。其中，当课程已选时，无法选择重名课程。

<img src="https://s2.loli.net/2023/11/04/RGniC4QtD8N5rwA.png" alt="image-20231104144911051" style="zoom:150%;" />

​														**图 无法重复选择课程示例**



测试用例中，龙安琪选择了如下课程：

<img src="https://s2.loli.net/2023/11/04/nACZDdQ9BoWu4MH.png" alt="image-20231104144917698" style="zoom:150%;" />

​														**图 龙安琪选择的课表示例**

接着登入贾致远老师，为龙安琪录入成绩60 - 60。返回龙安琪的成绩查询界面，可以看到数据成功被录入，而未被录入的课程成绩显示为空。

![image-20231104144924879](https://s2.loli.net/2023/11/04/Gw5coEsFHpCni9v.png)

​														**图 未被录入的成绩显示为空示例**
