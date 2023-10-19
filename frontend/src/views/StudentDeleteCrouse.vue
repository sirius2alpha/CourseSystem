<template>
    <!--先把课表查出来，前面再加一个多选框，直接选择然后点击退课就行-->
    <div>
        <button @click="fetchCourses">查询课表</button>
        <form>
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
                <tr v-for="course in myCourses" :key="course.course_id">
                    <td><input type="checkbox" v-model="deletedCourses" :value="course.course_id"></td>
                    <td>{{ course.course_id }}</td>
                    <td>{{ course.course_name }}</td>
                    <td>{{ course.teacher_id }}</td>
                    <td>{{ course.teacher_name }}</td>
                    <td>{{ course.capacity }}</td>
                    <td>{{ course.selected_number }}</td>
                    <td>{{ course.time }}</td>
                </tr>
            </table>
            <button @click="dropCourses">退选所选课程</button>
        </form>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {

    },
    method: {
        // 查询已选课程
        async fetchCourses() {

            // 构造请求体
            const apiUrl = `/api/students/${this.userId}/courses`;

            try {
                // 发送 GET 请求
                const response = await axios.get(apiUrl);
                console.log("选课信息查询成功", response.data);

                // 用JSON.parse()方法将字符串转换为JSON对象
                const courseData = JSON.parse(response.data);
                this.myCourses = courseData;
                this.showForm = true; // 显示表单组件

            }
            catch (error) {
                console.error("选课信息查询失败", error);
                alert("选课信息查询失败");
            }
        },

        // 退课功能
        async dropCourses() {
            // 发送请求进行退课操作
            try {
                const requestBody = [];
                this.deletedCourses.forEach((course) => {
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
                const response = await axios.delete(apiUrl, requestBody);

                const result = JSON.parse(response.data);
                if (result.success) {
                    alert("退课成功");
                    this.deletedCourses = []; // 清空已选课程
                    this.queryCourses(); // 重新查询课表
                } else {
                    alert("退课失败：" + result.message);
                }
            } catch (error) {
                console.error("退课操作失败", error);
                alert("退课操作失败");
            }
        },
    }
}
</script>