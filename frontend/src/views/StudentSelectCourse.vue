<template>
    <!-- 包括输入课程号、教师号、教师姓名的表单 -->
    <div class="input-row">
        <div class="input-group">
            <label for="CourseId">课程号</label>
            <input type="number" id="CourseId" v-model="CourseId">
        </div>

        <div class="input-group">
            <label for="CourseName">课程名</label>
            <input type="text" id="CourseName" v-model="CourseName">
        </div>

        <div class="input-group">
            <label for="TeacherId">教师号</label>
            <input type="number" id="TeacherId" v-model="TeacherId">
        </div>

        <div class="input-group">
            <label for="TeacherName">教师姓名</label>
            <input type="text" id="TeacherName" v-model="TeacherName">
        </div>

        <div class="input-group">
            <label for="CourseTime">上课时间</label>
            <input type="text" id="CourseTime" v-model="CourseTime">
        </div>

        <!--提交按钮-->
        <input type="button" value="提交" @click="queryCourses">


    </div>

    <!--选课记录返回，用列表进行呈现-->

    <!--展示查询到的选课信息-->
    <form v-if="showForm">
        <table class="course-table">
            <tr>
                <th></th>
                <th>课程号</th>
                <th>课程名</th>
                <th>教师号</th>
                <th>教师姓名</th>
                <th>课程容量</th>
                <th>已选人数</th>
                <th>上课时间</th>
            </tr>
            <tr v-for="course in courseInfo" :key="course.course_id">
                <td><input type="checkbox" v-model="selectedCourses" :value="course.course_id"></td>
                <td>{{ course.course_id }}</td>
                <td>{{ course.course_name }}</td>
                <td>{{ course.teacher_id }}</td>
                <td>{{ course.teacher_name }}</td>
                <td>{{ course.capacity }}</td>
                <td>{{ course.selected_number }}</td>
                <td>{{ course.time }}</td>
            </tr>
        </table>
        <button @click="selectCourses">确认选课</button>
    </form>
</template>

<script>
import axios from 'axios';

export default {
    name: "StudentSelectCourse",
    data() {
        return {
            CourseId: "",
            CourseName: "",
            TeacherId: "",
            TeacherName: "",
            CourseTime: "",
            courseInfo: [],
            selectedCourses: [],
            showForm: false
        }
    },
    methods: {
        // 查询功能
        async queryCourses() {
            // 把v-model数据保存到变量中
            const course_id = this.CourseId;
            const course_name = this.CourseName;
            const teacher_id = this.TeacherId;
            const teacher_name = this.TeacherName;
            const course_time = this.CourseTime;

            // 构造请求体
            const apiUrl = `/api/courses`;
            const requestBody = {
                course_id,
                course_name,
                teacher_id,
                teacher_name,
                course_time
            };

            try {
                // 发送 GET 请求
                const response = await axios.get(apiUrl, requestBody);
                console.log("选课信息查询成功", response.data);
                // 将查询选课的结果显示到页面上

                // 用JSON.parse()方法将字符串转换为JSON对象
                const courseData = JSON.parse(response.data);
                this.courseInfo = courseData;
                this.showForm = true; // 显示表单组件

            }
            catch (error) {
                console.error("选课信息查询失败", error);
                alert("选课信息查询失败");
            }
        },

        // 选课功能
        async selectCourses() {
            try {
                // 创建一个空数组，用于存储请求体数据
                const requestBody = [];

                // 使用 forEach 方法遍历 selectedCourses 数组
                this.selectedCourses.forEach((course) => {
                    // 将每个课程信息转换为一个包含课程信息的对象，并将其添加到 requestBody 数组中
                    requestBody.push({
                        course_id: course.course_id,
                        course_name: course.course_name,
                        teacher_id: course.teacher_id,
                        teacher_name: course.teacher_name,
                        capacity: course.capacity,
                        selected_number: course.selected_number,
                        time: course.time,
                    });
                });

                const apiUrl = `/api/students/${this.userId}/courses`;
                const response = await axios.post(apiUrl, requestBody);

                const result = JSON.parse(response.data);
                if (result.success) {
                    alert("选课成功");
                    this.selectedCourses = []; // 清空已选课程
                    this.queryCourses(); // 重新查询课表
                } else {
                    alert("选课失败：" + result.message);
                }
            } catch (error) {
                console.error("选课操作失败", error);
                alert("选课操作失败");
            }
        },
    }
}
</script>