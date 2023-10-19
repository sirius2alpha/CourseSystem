<template>
  <div style="align-items: center;">
    <h1>课程表</h1>
    <table>
      <thead>
        <tr>
          <th>时间</th>
          <th>星期一</th>
          <th>星期二</th>
          <th>星期三</th>
          <th>星期四</th>
          <th>星期五</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="timeSlot in timeSlots" :key="timeSlot">
          <td>{{ timeSlot }}</td>
          <td v-for="day in days" :key="day">
            <span v-if="getCourse(timeSlot, day)">{{ getCourse(timeSlot, day) }}</span>
            <span v-else>&nbsp;</span> <!-- 显示空白 -->
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: "courseSchedule",
  props: {
    myCourses: {
      type: Array,
      required: true,
      
    }
  },
  data() {
    return {
      days: ["星期一", "星期二", "星期三", "星期四", "星期五"],
      timeSlots: [
        "8:00 - 8:45",
        "8:55 - 9:40",
        "10:00 - 10:45",
        "10:55 - 11:40",
        "13:00 - 13:45",
        "13:55 - 14:40",
        "15:00 - 15:45",
        "15:55 - 16:40",
        "18:00 - 18:45",
        "18:55 - 19:40",
        "20:00 - 20:45",
        "20:55 - 21:40"
      ],
     // courseSchedule: {} // 存储课程表信息
    };
  },
  methods: {
    getCourse(timeSlot, day) {
      // 使用 find 方法来遍历 myCourses 数组，并对于每个课程执行一个回调函数
      const course = this.myCourses.find(course => {
        const time = course.time;
        const courseDay = time.substring(0, 1);
        const start = parseInt(time.substring(1, 2));
        const end = parseInt(time.substring(3, 4));
        return courseDay === day && timeSlot >= start && timeSlot <= end;
      });
      if (course) {
        return `${course.course_name} (${course.teacher_name})`;
      } else {
        return "";
      }
    },
  }
};
</script>

<style scoped>
.course-table {
  text-align: center;
  /* 水平居中对齐 */
  margin: 20px auto;
  /* 垂直居中对齐 */
  max-width: 800px;
  /* 设置最大宽度 */
  border: 1px solid #ccc;
  /* 添加边框 */
  padding: 20px;
  border-radius: 5px;
  /* 圆角边框 */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  /* 阴影效果 */
}

table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  padding: 10px;
  border: 1px solid #ccc;
}

input {
  width: 100%;
  padding: 5px;
  border: none;
  border-bottom: 1px solid #ccc;
  text-align: center;
}
</style>




