<template>
    <div>
        <el-table :data="myCourses" style="width: 100%" :header-cell-style="{ 'text-align': 'center' }" border>
            <el-table-column prop="course_id" label="课程号" align="center" />
            <el-table-column prop="course_name" label="课程名" align="center" />
            <el-table-column prop="teacher_name" label="教师姓名" align="center" />
            <el-table-column prop="score" label="分数" align="center" />
        </el-table>
    </div>
</template>

<script>
export default {
    name: "studentQueryScore",
    props: {
        myCourses: {
            type: Array,
            required: true,
        },
    },
    data() {
        return {
            quizCourses: [
                {
                    course_id: "1",
                    course_name: "123",
                    teacher_name: "123",
                    score: "1",
                },
                {
                    course_id: "2",
                    course_name: "123",
                    teacher_name: "123",
                    score: "2",
                },
                {
                    course_id: "3",
                    course_name: "123",
                    teacher_name: "123",
                    score: "3",
                },
                {
                    course_id: "4",
                    course_name: "123",
                    teacher_name: "123",
                    score: "4",
                }
            ]
        }
    }
};
</script>

<style scoped></style>