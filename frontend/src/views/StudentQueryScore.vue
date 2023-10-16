<template>
    <div>
        <form>
            <table class="course-table">
                <tr>
                    <th>课程号</th>
                    <th>课程名</th>
                    <th>教师姓名</th>
                    <th>分数</th>
                </tr>
                <tr v-for="course in myCourses" :key="course.course_id">
                    <td>{{ course.course_id }}</td>
                    <td>{{ course.course_name }}</td>
                    <td>{{ course.teacher_name }}</td>
                    <td>{{ course.score }}</td>
                </tr>
            </table>
        </form>
    </div>
</template>

<script>
export default {
    // 来自父组件的数据
    props: {
        userId: {
            type: String,
            required: true
        },
        myCourses: {
            type: Object,
            required: true
        }
    }
};
</script>