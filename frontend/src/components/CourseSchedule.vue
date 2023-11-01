<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="time" label="时间" width="180"></el-table-column>
    <el-table-column prop="monday" label="周一"></el-table-column>
    <el-table-column prop="tuesday" label="周二"></el-table-column>
    <el-table-column prop="wednesday" label="周三"></el-table-column>
    <el-table-column prop="thursday" label="周四"></el-table-column>
    <el-table-column prop="friday" label="周五"></el-table-column>
  </el-table>
</template>

<script>
export default {
  props: {
    myCourses: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      tableData: [
        { time: "8:00 - 8:45", monday: "", tuesday: "", wednesday: "", thursday: "", friday: "" },
        { time: "8:55 - 9:40", monday: "", tuesday: "", wednesday: "", thursday: "", friday: "" },
        { time: "10:00 - 10:45", monday: "", tuesday: "", wednesday: "", thursday: "", friday: "" },
        { time: "10:55 - 11:40", monday: "", tuesday: "", wednesday: "", thursday: "", friday: "" },
        { time: "13:00 - 13:45", monday: "", tuesday: "", wednesday: "", thursday: "", friday: "" },
        { time: "13:55 - 14:40", monday: "", tuesday: "", wednesday: "", thursday: "", friday: "" },
        { time: "15:00 - 15:45", monday: "", tuesday: "", wednesday: "", thursday: "", friday: "" },
        { time: "15:55 - 16:40", monday: "", tuesday: "", wednesday: "", thursday: "", friday: "" },
        { time: "18:00 - 18:45", monday: "", tuesday: "", wednesday: "", thursday: "", friday: "" },
        { time: "18:55 - 19:40", monday: "", tuesday: "", wednesday: "", thursday: "", friday: "" },
        { time: "20:00 - 20:45", monday: "", tuesday: "", wednesday: "", thursday: "", friday: "" },
        { time: "20:55 - 21:40", monday: "", tuesday: "", wednesday: "", thursday: "", friday: "" }
      ],
    };
  },
  created() {
    console.log("课程表中的数据", this.myCourses);

    // 创建映射对象
    const dayMapping = {
      '一': 'monday',
      '二': 'tuesday',
      '三': 'wednesday',
      '四': 'thursday',
      '五': 'friday'
    };

    const timeMapping = {
      "1": "8:00 - 8:45",
      "2": "8:55 - 9:40",
      "3": "10:00 - 10:45",
      "4": "10:55 - 11:40",
      "5": "13:00 - 13:45",
      "6": "13:55 - 14:40",
      "7": "15:00 - 15:45",
      "8": "15:55 - 16:40",
      "9": "18:00 - 18:45",
      "10": "18:55 - 19:40",
      "11": "20:00 - 20:45",
      "12": "20:55 - 21:40"
    };

    this.myCourses.forEach((course) => {
      // 将课程的时间转换为字符数组
      const times = course.time.split('');
      console.log("拆分的时间", times);

      // 提取第一个字符作为周几
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
  },
};
</script>