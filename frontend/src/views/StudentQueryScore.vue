<template>
    <div>
        <el-form>
            <el-table :data="myCourses" >
                <el-table-column prop="course_id" label="课程号" width="180" />
                <el-table-column prop="course_name" label="课程名" width="180" />
                <el-table-column prop="teacher_name" label="教师姓名" width="180" />
                <el-table-column prop="score" label="分数" width="180" />
            </el-table>
        </el-form>
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
};
</script>