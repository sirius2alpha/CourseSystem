<template>
    <div class="common-layout">
        <el-container>
            <el-header class="top-bar">
                <el-row>

                    <el-col :span="8">
                        <div style="display: flex; align-items: center; justify-content: center; height: 100%;">
                            <span class="text-xl font-bold">聪明教务系统</span>
                        </div>
                    </el-col>

                    <el-col :span="8" :offset="8">
                        <div class="flex items-center justify-end h-full">
                            <div style="display: flex; align-items: center; justify-content: center; height: 100%;">
                                <el-avatar :size="32" class="mr-4"
                                    src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
                                <span class="text-lg font-semibold mr-5" style="margin-left: 20px ; margin-right: 10px;">{{
                                    this.userName }}</span>
                                <span class="text-sm mr-4"
                                    style="color: var(--el-text-color-regular); position: relative; top: 2px;margin-right: 10px;">{{
                                        this.userId }}</span>
                                <el-tag type="success" class="ml-2">老师</el-tag>
                            </div>
                        </div>
                    </el-col>

                </el-row>
            </el-header>

            <div class="main-content">
                <!--aside class="sidebar">
                    <ul>
                       
                        <li @click="selectFunction('开课详情'); fetchCourses()">开课详情</li>
                        <li @click="selectFunction('成绩录入')">成绩录入</li>
                        <li @click="selectFunction('成绩分析')">成绩分析</li>
                        <li @click="selectFunction('开设课程')">开设课程</li>
                    </ul>
                </aside-->

                <el-aside width="200px">
                    <el-menu default-active="1" class="el-menu-vertical-demo">
                        <el-menu-item index="1" @click="selectFunction('开课详情'); fetchCourses()">开课详情</el-menu-item>
                        <el-menu-item index="2" @click="selectFunction('成绩录入')">成绩录入</el-menu-item>
                        <el-menu-item index="3" @click="selectFunction('成绩分析')">成绩分析</el-menu-item>
                        <el-menu-item index="4" @click="selectFunction('开设课程')">开设课程</el-menu-item>
                    </el-menu>
                </el-aside>

                <div class="main-content-right">

                    <div v-if="selectedFunction === '开课详情'">
                        <el-table :data="courseInfo" style="width: 100%">
                            <el-table-column prop="course_id" label="课程号" />
                            <el-table-column prop="course_name" label="课程名" />
                            <el-table-column prop="teacher_id" label="教师号" />
                            <el-table-column prop="teacher_name" label="教师姓名" />
                            <el-table-column prop="capacity" label="课程容量" />
                            <el-table-column prop="selected_number" label="已选人数" />
                            <el-table-column prop="time" label="上课时间" />
                        </el-table>
                    </div>
                    <div v-else-if="selectedFunction === '成绩录入'">
                        <!--根据开课详情中的班级信息，可以进行筛选，录入成绩-->
                        <!--根据courseInfo的课程名字做一个选择框-->
                        <div style="margin: 20px;">
                            <label for="course-select">选择课程班级：</label>
                            <el-select v-model="selectedCourse" class="m-2" placeholder="">
                                <el-option v-for="course in courseInfo" :key="course.course_id" :label="course - select"
                                    :value="course.course_name" />
                            </el-select>

                            <!--对选中的selectedCourse进行查询，返回数据给tableData-->
                            <el-table :data="tableData" stripe style="width: 100%">
                                <el-table-column prop="student_id" label="学号" width="180" />
                                <el-table-column prop="student_name" label="姓名" width="180" />
                                <el-table-column label="成绩">
                                    <template v-slot="scope">
                                        <template v-if="scope.row">
                                            <el-row>
                                                <el-col :span="4">
                                                    <label for="daily-score-input">平时成绩</label>
                                                    <el-input v-model="scope.row.daily_score" class="w-80"
                                                        placeholder="平时成绩" id="daily-score-input" />
                                                </el-col>

                                                <el-col :span="4">
                                                    <label for="exam-score-input">考试成绩</label>
                                                    <el-input v-model="scope.row.examination_score" class="w-80"
                                                        placeholder="考试成绩" id="exam-score-input" />
                                                </el-col>

                                            </el-row>
                                        </template>

                                    </template>
                                </el-table-column>
                            </el-table>
                            <div style="margin: 20px;">
                                <el-button type="primary" @click="submitScore">保存</el-button>
                            </div>
                        </div>

                    </div>

                    <div v-else-if="selectedFunction === '成绩分析'">
                    </div>

                    <div v-else-if="selectedFunction === '开设课程'">
                    </div>

                </div>
            </div>
        </el-container>
    </div>
</template>
    
<script>
import axios from "axios";
import { ElMessage } from 'element-plus'



export default {
    name: "TeacherPages",
    components: {

    },

    // 来自父组件的数据
    props: {
    },

    // 在created生命周期钩子中访问路由参数
    created() {
         console.log("this.$route",this.$route);
        this.userId = this.$route.params.userId;
        this.userName = this.$route.params.userName;
         console.log("userId", this.userId);
         console.log("userName", this.userName);
    },

    // data()函数部分
    data() {
        return {
            host: "https://127.0.0.1:9000",
            selectedFunction: "开课详情", // 默认选中的功能

            selectedCourse: "", // 默认选中的课程

            // 开课详情功能中的课程信息
            courseInfo: [{
                course_id: "course_id",
                course_name: "course_name",
                teacher_id: "teacher_id",
                teacher_name: "teacher_name",
                capacity: 0,
                selected_number: 0,
                time: "time"
            }],

            // 已经选的课程
            myCourses: [{
                course_id: "course_id",
                course_name: "course_name",
                teacher_id: "teacher_id",
                teacher_name: "teacher_name",
                capacity: 0,
                selected_number: 0,
                time: "time",
                score: 0
            }],

            // 上传成绩表格需要的信息
            tableData: [{
                // 这两个信息帮助筛选班级
                course_id: "course_id",
                teacher_id: "teacher_id",

                student_id: "21122453",
                student_name: "袁浩",
                daily_score: 0,
                examination_score: 0
            }]
        };
    },

    watch: {
        selectedCourse() {
            // 调用 fetchStudents 方法，更新 tableData 数组中的数据
            this.fetchStudents();
        }
    },

    methods: {

        // 选择功能
        selectFunction(functionName) {
            this.selectedFunction = functionName;
        },

        // 查询已选课程
        async fetchCourses() {

            // 构造请求体
            const apiUrl = `${this.host}/api/teachers/${this.userId}/courses/${this.courseId}`;

            try {
                // 发送 GET 请求
                const response = await axios.get(apiUrl);
                console.log("选课信息查询成功", response.data);

                // 用JSON.parse()方法将字符串转换为JSON对象
                const courseData = JSON.parse(response.data);
                this.myCourses = courseData;
            }
            catch (error) {
                console.error("选课信息查询失败", error);
                ElMessage.error("选课信息查询失败");
            }
        },

        // 通过watch部分关联，每次完成选择后自动调用 fetchStudents 方法，并更新 tableData 数组中的数据
        // 查询该教师和他开设的所有课程的信息返回到tableData中
        async fetchStudents() {

            // 构造请求体
            const apiUrl = `${this.host}/api/teachers/${this.userId}/courses/${this.selectedCourse}`;

            // 传递教师号和课程号，在选课表中查询到该班级下所有的学生选课信息
            const requestBody = {
                teacher_id: this.userId,
                course_id: this.selectedCourse
            };

            try {
                // 发送 GET 请求
                const response = await axios.get(apiUrl, { params: requestBody });

                // 用JSON.parse()方法将字符串转换为JSON对象
                const courseData = JSON.parse(response.data);
                this.tableData = courseData;
            }
            catch (error) {
                console.error("选课信息查询失败", error);
                ElMessage.error("选课信息查询失败");
            }
        },

        // 上传成绩
        async submitScore() {

            // 构造请求体,路径参数传递教师号和课程号
            const apiUrl = `${this.host}/api/teachers/${this.userId}/courses/${this.selectedCourse}`;

            // body中传递学生学号、平时成绩、考试成绩
            const data = [{
                student_id: "string",
                daily_score: 0,
                examination_score: 0
            }];

            try {
                // 发送 POST 请求
                const response = await axios.post(apiUrl, data);

                // 返回状态码为200，表示上传成功
                if (response.status === 200) {
                    console.log("成绩上传成功", response.data);
                    ElMessage.success("成绩上传成功");
                }
                else {
                    console.error("成绩上传失败", response.data);
                    ElMessage.error("成绩上传失败");
                }
            }
            catch (error) {
                console.error("成绩上传失败", error);
                ElMessage.error("成绩上传失败");
            }
        },

        mounted() {
            this.fetchCourses();
        }
    },
};
</script>
    
<style>
.top-bar {
    background: #208fcb;
    color: #fff;
    padding: 10px 20px;
    text-align: center;
    border-radius: 10px;
}

.top-bar-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.user-name {
    font-weight: bold;
}

.main-content {
    display: flex;
    justify-content: flex-start;
    align-items: flex-start;
}

.sidebar {
    margin-top: 10px;
    width: 100px;
    background: #aee3ed;
    padding: 10px;
    border-radius: 10px;
}

ul {
    list-style-type: none;
    padding: 0;
}

li {
    cursor: pointer;
    padding: 5px;
    border-bottom: 1px solid #ccc;
}

.main-content-right {
    flex: 1;
    padding: 20px;
}

.input-row {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
}

.input-group {
    display: flex;
    flex-direction: column;
    gap: 5px;
}

label {
    font-weight: bold;
    font-size: 14px;
    color: #333;
}

input {
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 14px;
}


.course-table {
    margin-top: 20px;
    margin-bottom: 20px;
    border-collapse: collapse;
    font-family: Arial, sans-serif;
    background-color: #f2f2f2;
    width: 100%;
}

.course-table th,
.course-table td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: left;
}

.course-table th {
    background-color: #8ac9e2;
    color: white;
}
</style>
    