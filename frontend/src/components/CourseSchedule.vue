<template>
  <div class="schedule-container">
    <div v-if="!myCourses || myCourses.length === 0" class="empty-state">
      <i class="el-icon-date empty-icon"></i>
      <p>暂无课表数据</p>
    </div>
    <template v-else>
      <div class="schedule-filters">
        <el-radio-group v-model="viewMode" size="small">
          <el-radio-button label="week">周视图</el-radio-button>
          <el-radio-button label="list">列表视图</el-radio-button>
        </el-radio-group>
      </div>

      <!-- 周视图 -->
      <div v-if="viewMode === 'week'" class="week-view">
        <div class="timetable">
          <div class="time-column">
            <div class="time-header">时间</div>
            <div v-for="slot in timeSlots" :key="slot.id" class="time-slot">
              {{ slot.label }} ({{ slot.time }})
            </div>
          </div>
          
          <div class="day-column" v-for="day in weekDays" :key="day.id">
            <div class="day-header">{{ day.label }}</div>
            <div class="course-slots">
              <div v-for="slot in timeSlots" :key="`${day.id}-${slot.id}`" class="course-slot">
                <div 
                  v-for="course in getCoursesForSlot(day.id, slot.id)" 
                  :key="course.course_id" 
                  class="course-card"
                  :style="{ backgroundColor: getCourseColor(course.course_id) }"
                >
                  <div class="course-name">{{ course.course_name }}</div>
                  <div class="course-info">{{ course.teacher_name }}</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 列表视图 -->
      <div v-else class="list-view">
        <el-table :data="myCourses" style="width: 100%" :header-cell-style="{ background: '#f5f7fa' }" border stripe>
          <el-table-column prop="course_id" label="课程号" width="100" />
          <el-table-column prop="course_name" label="课程名" min-width="150" />
          <el-table-column prop="teacher_name" label="教师姓名" width="120" />
          <el-table-column prop="time" label="上课时间" min-width="180" />
          <el-table-column label="操作" width="100" align="center">
            <template #default="scope">
              <el-button type="text" @click="showCourseDetail(scope.row)">
                详情
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </template>

    <!-- 课程详情对话框 -->
    <el-dialog title="课程详情" v-model="dialogVisible" width="500px">
      <div v-if="selectedCourse" class="course-detail">
        <div class="detail-item">
          <span class="detail-label">课程号：</span>
          <span class="detail-value">{{ selectedCourse.course_id }}</span>
        </div>
        <div class="detail-item">
          <span class="detail-label">课程名：</span>
          <span class="detail-value">{{ selectedCourse.course_name }}</span>
        </div>
        <div class="detail-item">
          <span class="detail-label">教师：</span>
          <span class="detail-value">{{ selectedCourse.teacher_name }}</span>
        </div>
        <div class="detail-item">
          <span class="detail-label">上课时间：</span>
          <span class="detail-value">{{ selectedCourse.time }}</span>
        </div>
        <div class="detail-item">
          <span class="detail-label">课程容量：</span>
          <span class="detail-value">{{ selectedCourse.capacity }}</span>
        </div>
        <div class="detail-item">
          <span class="detail-label">已选人数：</span>
          <span class="detail-value">{{ selectedCourse.selected_number }}</span>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "CourseSchedule",
  props: {
    myCourses: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      viewMode: 'week',
      dialogVisible: false,
      selectedCourse: null,
      weekDays: [
        { id: 1, label: '周一' },
        { id: 2, label: '周二' },
        { id: 3, label: '周三' },
        { id: 4, label: '周四' },
        { id: 5, label: '周五' },
        { id: 6, label: '周六' },
        { id: 7, label: '周日' },
      ],
      timeSlots: [
        { id: 1, label: '第1节', time: '8:00-8:45' },
        { id: 2, label: '第2节', time: '8:50-9:40' },
        { id: 3, label: '第3节', time: '10:00-10:45' },
        { id: 4, label: '第4节', time: '10:50-11:40' },
        { id: 5, label: '第5节', time: '14:00-14:45' },
        { id: 6, label: '第6节', time: '14:50-15:40' },
        { id: 7, label: '第7节', time: '16:00-16:45' },
        { id: 8, label: '第8节', time: '16:50-17:40' },
        { id: 9, label: '第9节', time: '19:00-19:45' },
        { id: 10, label: '第10节', time: '19:50-20:40' },
      ],
      courseColors: {}
    };
  },
  methods: {
    getCoursesForSlot(dayId, slotId) {
      console.log('myCourses:', this.myCourses);
      
      return this.myCourses.filter(course => {
        const timeStr = course.time || '';
        console.log(`课程 ${course.course_name} 时间: ${timeStr}`);
        
        // 匹配星期
        const dayChar = this.getDayChar(dayId);
        const dayPatterns = [
          new RegExp(`^${dayChar}`),
          new RegExp(`周${dayChar}`)
        ];
        
        const dayMatch = dayPatterns.some(pattern => pattern.test(timeStr));
        console.log(`- 星期匹配: ${dayMatch} (查找 ${dayChar} 在 ${timeStr})`);
        
        if (!dayMatch) return false;
        
        // 匹配节次
        const timePattern = /(\d+)-(\d+)/;
        const matches = timeStr.match(timePattern);
        console.log(`- 时间匹配: ${matches ? 'true' : 'false'}`);
        
        if (!matches) return false;
        
        // 获取课程的开始和结束节次
        const startSlot = parseInt(matches[1]);
        const endSlot = parseInt(matches[2]);
        
        // 将实际的节次ID映射到我们的时间槽ID
        // 例如，"二1-2"中的1对应timeSlots中的id=1，2对应id=2
        const slotMatch = slotId >= startSlot && slotId <= endSlot;
        console.log(`- 节次匹配: ${slotMatch} (${startSlot}-${endSlot} vs ${slotId})`);
        
        return slotMatch;
      });
    },
    
    getDayChar(dayId) {
      const chars = ['', '一', '二', '三', '四', '五', '六', '日'];
      return chars[dayId];
    },
    
    getCourseColor(courseId) {
      if (!this.courseColors[courseId]) {
        const hue = Math.floor(Math.random() * 360);
        this.courseColors[courseId] = `hsl(${hue}, 70%, 85%)`;
      }
      return this.courseColors[courseId];
    },
    
    showCourseDetail(course) {
      this.selectedCourse = course;
      this.dialogVisible = true;
    }
  }
};
</script>

<style scoped>
.schedule-container {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 3rem;
  color: var(--text-secondary);
}

.empty-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.schedule-filters {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 1rem;
}

.week-view {
  overflow-x: auto;
}

.timetable {
  display: flex;
  min-width: 800px;
  border: 1px solid #ebeef5;
  border-radius: var(--radius);
  overflow: hidden;
}

.time-column,
.day-column {
  flex: 1;
  display: flex;
  flex-direction: column;
  border-right: 1px solid #ebeef5;
}

.time-column {
  flex: 0 0 150px;
  background-color: #f5f7fa;
}

.day-column:last-child {
  border-right: none;
}

.time-header,
.day-header {
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  background-color: #f5f7fa;
  border-bottom: 1px solid #ebeef5;
}

.time-slot {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0.25rem;
  border-bottom: 1px solid #ebeef5;
  font-size: 0.75rem;
  text-align: center;
}

.time-slot:last-child {
  border-bottom: none;
}

.course-slots {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.course-slot {
  height: 60px;
  border-bottom: 1px solid #ebeef5;
  padding: 0.25rem;
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
  overflow: hidden;
}

.course-slot:last-child {
  border-bottom: none;
}

.course-card {
  flex: 1;
  border-radius: 4px;
  padding: 0.5rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  font-size: 0.85rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.course-name {
  font-weight: 600;
  margin-bottom: 0.25rem;
}

.course-info {
  font-size: 0.75rem;
  opacity: 0.8;
}

.course-detail {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.detail-item {
  display: flex;
}

.detail-label {
  width: 100px;
  font-weight: 600;
  color: var(--text-secondary);
}

.detail-value {
  flex: 1;
}
</style>