package com.example.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.project.dto.CourseDTO;
import com.example.project.entity.*;
import com.example.project.exception.BusinessException;
import com.example.project.mapper.CourseMapper;
import com.example.project.mapper.CoursePlanMapper;
import com.example.project.mapper.CurrentCoursesMapper;
import com.example.project.mapper.SelectedCoursesMapper;
import com.example.project.service.CourseService;
import com.example.project.service.StudentService;
import com.example.project.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 课程服务实现类
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CoursePlanMapper coursePlanMapper;

    @Autowired
    private CurrentCoursesMapper currentCoursesMapper;

    @Autowired
    private SelectedCoursesMapper selectedCoursesMapper;

    @Override
    public IPage<Course> pageCourses(Page<Course> page, LambdaQueryWrapper<Course> queryWrapper) {
        return this.page(page, queryWrapper);
    }

    @Override
    public List<CourseDTO> queryCourses(Integer courseId, String courseName, Integer teacherId, String teacherName,
            String courseTime) {
        // 构建查询条件
        LambdaQueryWrapper<CurrentCourses> wrapper = new LambdaQueryWrapper<>();

        // 根据传入的参数构建查询条件
        if (courseId != null) {
            wrapper.eq(CurrentCourses::getCourseId, courseId);
        }

        if (teacherId != null) {
            wrapper.eq(CurrentCourses::getTeacherId, teacherId);
        }

        if (courseTime != null && !courseTime.isEmpty()) {
            wrapper.like(CurrentCourses::getTime, courseTime);
        }

        // 执行查询
        List<CurrentCourses> currentCoursesList = currentCoursesMapper.selectList(wrapper);

        // 转换为CourseDTO并根据课程名和教师名进行过滤
        List<CourseDTO> courseDTOList = convertToCourseDTOList(currentCoursesList);

        // 如果指定了课程名，进行过滤
        if (courseName != null && !courseName.isEmpty()) {
            courseDTOList = courseDTOList.stream()
                    .filter(dto -> dto.getCourseName() != null && dto.getCourseName().contains(courseName))
                    .collect(Collectors.toList());
        }

        // 如果指定了教师名，进行过滤
        if (teacherName != null && !teacherName.isEmpty()) {
            courseDTOList = courseDTOList.stream()
                    .filter(dto -> dto.getTeacherName() != null && dto.getTeacherName().contains(teacherName))
                    .collect(Collectors.toList());
        }

        return courseDTOList;
    }

    @Override
    public List<CourseDTO> getStudentSelectedCourses(String studentId) {
        // 查询学生已选课程
        LambdaQueryWrapper<SelectedCourses> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SelectedCourses::getStudentId, studentId);
        List<SelectedCourses> selectedCoursesList = selectedCoursesMapper.selectList(wrapper);

        if (selectedCoursesList.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取所有课程ID
        List<Integer> courseIds = selectedCoursesList.stream()
                .map(SelectedCourses::getCurrentCourseId)
                .collect(Collectors.toList());

        // 查询课程详情
        LambdaQueryWrapper<CurrentCourses> currentCoursesWrapper = new LambdaQueryWrapper<>();
        currentCoursesWrapper.in(CurrentCourses::getNo, courseIds);
        List<CurrentCourses> currentCoursesList = currentCoursesMapper.selectList(currentCoursesWrapper);

        return convertToCourseDTOList(currentCoursesList);
    }

    @Override
    public List<CourseDTO> getTeacherCourses(Integer teacherId) {
        LambdaQueryWrapper<CurrentCourses> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CurrentCourses::getTeacherId, teacherId);
        List<CurrentCourses> currentCoursesList = currentCoursesMapper.selectList(wrapper);

        return convertToCourseDTOList(currentCoursesList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean selectCourses(String studentId, List<CourseDTO> courses) {
        if (courses == null || courses.isEmpty()) {
            throw new BusinessException("选课列表不能为空");
        }

        // 获取学生已选的课程，用于检查时间冲突
        List<CourseDTO> existingCourses = getStudentSelectedCourses(studentId);

        for (CourseDTO course : courses) {
            // 检查课程信息是否完整
            if (course.getCourseId() == null) {
                throw new BusinessException("课程ID不能为空");
            }

            if (course.getTeacherId() == null) {
                throw new BusinessException("教师ID不能为空");
            }

            // 查询当前课程信息
            LambdaQueryWrapper<CurrentCourses> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CurrentCourses::getCourseId, course.getCourseId())
                    .eq(CurrentCourses::getTeacherId, course.getTeacherId());

            CurrentCourses currentCourse = currentCoursesMapper.selectOne(wrapper);
            if (currentCourse == null) {
                // 提供更明确的错误信息，即使课程名为null
                String courseName = course.getCourseName() != null ? course.getCourseName() : "未知课程";
                String errorMsg = String.format("课程不存在: ID=%d, 教师ID=%d, 课程名=%s",
                        course.getCourseId(), course.getTeacherId(), courseName);
                throw new BusinessException(errorMsg);
            }

            // 检查时间冲突
            String courseTime = currentCourse.getTime();
            if (courseTime != null && !courseTime.isEmpty()) {
                for (CourseDTO existingCourse : existingCourses) {
                    if (isTimeConflict(courseTime, existingCourse.getTime())) {
                        String newCourseName = course.getCourseName() != null ? course.getCourseName() : "未知课程";
                        String existingCourseName = existingCourse.getCourseName() != null
                                ? existingCourse.getCourseName()
                                : "未知课程";
                        throw new BusinessException(String.format(
                                "课程时间冲突: 新课程'%s'(%s) 与已选课程'%s'(%s) 时间冲突",
                                newCourseName, courseTime, existingCourseName, existingCourse.getTime()));
                    }
                }
            }

            // 检查容量
            int capacity = 50; // 默认容量
            int selectedCount = getSelectedCount(currentCourse.getNo());
            if (selectedCount >= capacity) {
                String courseName = course.getCourseName() != null ? course.getCourseName() : "未知课程";
                throw new BusinessException("课程已满: " + courseName);
            }

            // 检查是否已选
            LambdaQueryWrapper<SelectedCourses> selectedWrapper = new LambdaQueryWrapper<>();
            selectedWrapper.eq(SelectedCourses::getStudentId, studentId)
                    .eq(SelectedCourses::getCurrentCourseId, currentCourse.getNo());

            int count = selectedCoursesMapper.selectCount(selectedWrapper);
            if (count > 0) {
                String courseName = course.getCourseName() != null ? course.getCourseName() : "未知课程";
                throw new BusinessException("已选该课程: " + courseName);
            }

            // 选课
            SelectedCourses selectedCourses = new SelectedCourses();
            selectedCourses.setStudentId(studentId);
            selectedCourses.setCurrentCourseId(currentCourse.getNo());

            selectedCoursesMapper.insert(selectedCourses);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropCourses(String studentId, List<CourseDTO> courses) {
        if (courses == null || courses.isEmpty()) {
            throw new BusinessException("退课列表不能为空");
        }

        for (CourseDTO course : courses) {
            // 查询当前课程信息
            LambdaQueryWrapper<CurrentCourses> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CurrentCourses::getCourseId, course.getCourseId())
                    .eq(CurrentCourses::getTeacherId, course.getTeacherId());

            CurrentCourses currentCourse = currentCoursesMapper.selectOne(wrapper);
            if (currentCourse == null) {
                throw new BusinessException("课程不存在: " + course.getCourseName());
            }

            // 退课
            LambdaQueryWrapper<SelectedCourses> selectedWrapper = new LambdaQueryWrapper<>();
            selectedWrapper.eq(SelectedCourses::getStudentId, studentId)
                    .eq(SelectedCourses::getCurrentCourseId, currentCourse.getNo());

            selectedCoursesMapper.delete(selectedWrapper);
        }

        return true;
    }

    @Override
    public List<Map<String, Object>> getStudentScores(String studentId) {
        // 获取学生所有已选课程
        List<CourseDTO> selectedCourses = getStudentSelectedCourses(studentId);
        List<Map<String, Object>> scoreList = new ArrayList<>();

        for (CourseDTO course : selectedCourses) {
            Map<String, Object> scoreMap = new HashMap<>();
            scoreMap.put("course_id", course.getCourseId());
            scoreMap.put("course_name", course.getCourseName());
            scoreMap.put("teacher_id", course.getTeacherId());
            scoreMap.put("teacher_name", course.getTeacherName());

            // 查询成绩
            LambdaQueryWrapper<SelectedCourses> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SelectedCourses::getStudentId, studentId)
                    .eq(SelectedCourses::getCurrentCourseId, getCourseNo(course.getCourseId(), course.getTeacherId()));

            SelectedCourses selectedCourse = selectedCoursesMapper.selectOne(wrapper);
            if (selectedCourse != null) {
                scoreMap.put("daily_score", selectedCourse.getPscj());
                scoreMap.put("examination_score", selectedCourse.getKscj());
            } else {
                scoreMap.put("daily_score", 0);
                scoreMap.put("examination_score", 0);
            }

            scoreList.add(scoreMap);
        }

        return scoreList;
    }

    @Override
    public List<Map<String, Object>> getCourseStudents(Integer courseId, Integer teacherId) {
        List<Map<String, Object>> studentsList = new ArrayList<>();

        // 获取课程编号，若teacherId为空，则不使用teacherId条件
        Integer courseNo = null;

        if (teacherId != null) {
            // 首先尝试用teacherId和courseId一起查询
            LambdaQueryWrapper<CurrentCourses> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CurrentCourses::getCourseId, courseId)
                    .eq(CurrentCourses::getTeacherId, teacherId);
            CurrentCourses currentCourse = currentCoursesMapper.selectOne(wrapper);

            if (currentCourse != null) {
                courseNo = currentCourse.getNo();
            }
        }

        // 如果没有找到课程编号，只用courseId查询
        if (courseNo == null) {
            LambdaQueryWrapper<CurrentCourses> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CurrentCourses::getCourseId, courseId);
            CurrentCourses currentCourse = currentCoursesMapper.selectOne(wrapper);

            if (currentCourse != null) {
                courseNo = currentCourse.getNo();
            } else {
                throw new BusinessException("未找到该课程");
            }
        }

        // 查询选择这门课的学生
        LambdaQueryWrapper<SelectedCourses> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SelectedCourses::getCurrentCourseId, courseNo);
        List<SelectedCourses> selectedCourses = selectedCoursesMapper.selectList(wrapper);

        // 组装学生信息和成绩
        for (SelectedCourses selectedCourse : selectedCourses) {
            String studentId = selectedCourse.getStudentId();
            Students student = studentService.getById(studentId);

            if (student != null) {
                Map<String, Object> studentInfo = new HashMap<>();
                studentInfo.put("student_id", student.getId());
                studentInfo.put("student_name", student.getName());
                studentInfo.put("daily_score", selectedCourse.getPscj());
                studentInfo.put("examination_score", selectedCourse.getKscj());

                studentsList.add(studentInfo);
            }
        }

        return studentsList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStudentScore(Integer courseId, String studentId, Float dailyScore, Float examinationScore,
            Integer teacherId) {
        // 检查主要参数
        if (dailyScore == null || examinationScore == null) {
            throw new BusinessException("平时成绩或考试成绩不能为空");
        }

        // 判断必要参数是否为null
        if (courseId == null) {
            throw new BusinessException("课程ID不能为空");
        }
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new BusinessException("学生ID不能为空");
        }

        System.out.println("更新学生成绩请求 - 课程ID: " + courseId + ", 学生ID: " + studentId
                + ", 平时成绩: " + dailyScore + ", 考试成绩: " + examinationScore
                + ", 教师ID: " + teacherId);

        try {
            Integer courseNo = null;
            List<CurrentCourses> courseList = null;

            // 第一步：如果有teacherId，先尝试使用courseId和teacherId组合查询
            if (teacherId != null) {
                LambdaQueryWrapper<CurrentCourses> teacherCourseQuery = new LambdaQueryWrapper<>();
                teacherCourseQuery.eq(CurrentCourses::getCourseId, courseId)
                        .eq(CurrentCourses::getTeacherId, teacherId);

                courseList = currentCoursesMapper.selectList(teacherCourseQuery);
                System.out.println("使用教师ID查询 - 找到" + courseList.size() + "个课程");
            }

            // 第二步：如果使用teacherId查询没有结果，则只用courseId查询
            if (courseList == null || courseList.isEmpty()) {
                LambdaQueryWrapper<CurrentCourses> courseOnlyQuery = new LambdaQueryWrapper<>();
                courseOnlyQuery.eq(CurrentCourses::getCourseId, courseId);

                courseList = currentCoursesMapper.selectList(courseOnlyQuery);
                System.out.println("使用课程ID查询 - 找到" + courseList.size() + "个课程");
            }

            // 检查是否找到课程
            if (courseList.isEmpty()) {
                throw new BusinessException("未找到课程ID为 " + courseId + " 的课程");
            }

            // 课程数量过多警告
            if (courseList.size() > 1) {
                System.out.println("警告：找到多个匹配课程，将尝试所有课程直到找到学生选课记录");
            }

            // 查找学生选修的课程
            boolean found = false;

            for (CurrentCourses course : courseList) {
                // 确保courseNo不为null
                if (course.getNo() == null) {
                    System.out.println("跳过无效课程记录：课程编号为空");
                    continue;
                }

                courseNo = course.getNo();
                System.out.println("正在检查课程 - 课程编号: " + courseNo + ", 课程ID: " + course.getCourseId() + ", 教师ID: "
                        + course.getTeacherId());

                // 尝试查找学生的选课记录
                LambdaQueryWrapper<SelectedCourses> selectedWrapper = new LambdaQueryWrapper<>();
                selectedWrapper.eq(SelectedCourses::getCurrentCourseId, courseNo)
                        .eq(SelectedCourses::getStudentId, studentId);

                SelectedCourses selectedCourse = selectedCoursesMapper.selectOne(selectedWrapper);

                if (selectedCourse != null) {
                    System.out.println("找到学生选课记录 - 学生ID: " + studentId + ", 课程编号: " + courseNo);

                    // 转换成绩为Double类型，避免自动拆箱时的空指针风险
                    double dailyScoreDouble = dailyScore.doubleValue();
                    double examinationScoreDouble = examinationScore.doubleValue();

                    // 计算总成绩，按照 40% 平时成绩 + 60% 考试成绩计算
                    double totalScore = dailyScoreDouble * 0.4 + examinationScoreDouble * 0.6;

                    // 创建一个新的SelectedCourses对象，只设置要更新的字段
                    SelectedCourses courseToUpdate = new SelectedCourses();
                    courseToUpdate.setStudentId(studentId);
                    courseToUpdate.setCurrentCourseId(courseNo);
                    courseToUpdate.setPscj(dailyScoreDouble);
                    courseToUpdate.setKscj(examinationScoreDouble);
                    courseToUpdate.setScore(totalScore);

                    // 使用唯一标识更新
                    LambdaQueryWrapper<SelectedCourses> updateWrapper = new LambdaQueryWrapper<>();
                    updateWrapper.eq(SelectedCourses::getCurrentCourseId, courseNo)
                            .eq(SelectedCourses::getStudentId, studentId);

                    int updated = selectedCoursesMapper.update(courseToUpdate, updateWrapper);
                    System.out.println("更新结果 - 更新行数: " + updated);

                    if (updated > 0) {
                        found = true;
                        break;
                    }
                } else {
                    System.out.println("未找到学生选课记录 - 学生ID: " + studentId + ", 课程编号: " + courseNo);
                }
            }

            if (!found) {
                throw new BusinessException("未找到学生 " + studentId + " 选修课程 " + courseId + " 的记录");
            }

            System.out.println("学生成绩更新成功 - 学生ID: " + studentId + ", 课程ID: " + courseId);
            return true;
        } catch (BusinessException e) {
            System.err.println("业务异常: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("系统异常: " + e.getMessage());
            e.printStackTrace();
            throw new BusinessException("更新成绩失败: " + e.getMessage());
        }
    }

    // 保持原有的方法以兼容现有调用
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStudentScore(Integer courseId, String studentId, Float dailyScore, Float examinationScore) {
        // 调用新方法，teacherId参数传null
        return updateStudentScore(courseId, studentId, dailyScore, examinationScore, null);
    }

    /**
     * 将CurrentCourses列表转换为CourseDTO列表
     */
    private List<CourseDTO> convertToCourseDTOList(List<CurrentCourses> currentCoursesList) {
        List<CourseDTO> courseDTOList = new ArrayList<>();

        for (CurrentCourses currentCourse : currentCoursesList) {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setCourseId(currentCourse.getCourseId());
            courseDTO.setTeacherId(currentCourse.getTeacherId());
            courseDTO.setTime(currentCourse.getTime());

            // 获取课程名称
            LambdaQueryWrapper<CoursePlan> coursePlanWrapper = new LambdaQueryWrapper<>();
            coursePlanWrapper.eq(CoursePlan::getCourseId, currentCourse.getCourseId());
            CoursePlan coursePlan = coursePlanMapper.selectOne(coursePlanWrapper);
            if (coursePlan != null) {
                courseDTO.setCourseName(coursePlan.getCourseName());
            }

            // 获取教师名称
            Teachers teacher = teacherService.getById(currentCourse.getTeacherId());
            if (teacher != null) {
                courseDTO.setTeacherName(teacher.getName());
            }

            // 获取已选人数
            courseDTO.setCapacity(50); // 默认容量
            courseDTO.setSelectedNumber(getSelectedCount(currentCourse.getNo()));

            courseDTOList.add(courseDTO);
        }

        return courseDTOList;
    }

    /**
     * 获取课程已选人数
     */
    private int getSelectedCount(Integer courseNo) {
        LambdaQueryWrapper<SelectedCourses> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SelectedCourses::getCurrentCourseId, courseNo);
        return selectedCoursesMapper.selectCount(wrapper);
    }

    /**
     * 获取课程编号
     */
    private Integer getCourseNo(Integer courseId, Integer teacherId) {
        LambdaQueryWrapper<CurrentCourses> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CurrentCourses::getCourseId, courseId);

        // 只在teacherId不为空时添加教师ID条件
        if (teacherId != null) {
            wrapper.eq(CurrentCourses::getTeacherId, teacherId);
        }

        CurrentCourses currentCourse = currentCoursesMapper.selectOne(wrapper);
        return currentCourse != null ? currentCourse.getNo() : null;
    }

    /**
     * 判断两个课程时间是否冲突
     * 格式如: "一1-2", "三3-4"，表示星期几的第几节到第几节课
     * 
     * @param time1 第一个课程时间
     * @param time2 第二个课程时间
     * @return 是否冲突
     */
    private boolean isTimeConflict(String time1, String time2) {
        if (time1 == null || time2 == null || time1.isEmpty() || time2.isEmpty()) {
            return false; // 如果任一时间为空，认为不冲突
        }

        // 解析时间1
        String day1 = extractDay(time1);
        int[] periods1 = extractPeriods(time1);

        // 解析时间2
        String day2 = extractDay(time2);
        int[] periods2 = extractPeriods(time2);

        // 如果不是同一天，则不冲突
        if (!day1.equals(day2)) {
            return false;
        }

        // 检查时间段是否有重叠
        // periods[0]是开始节次，periods[1]是结束节次
        return !(periods1[1] < periods2[0] || periods1[0] > periods2[1]);
    }

    /**
     * 从时间字符串中提取星期几
     * 
     * @param time 时间字符串，如 "一1-2"
     * @return 星期几，如 "一"
     */
    private String extractDay(String time) {
        if (time == null || time.isEmpty()) {
            return "";
        }
        // 提取第一个字符作为星期几
        return time.substring(0, 1);
    }

    /**
     * 从时间字符串中提取课程节次范围
     * 
     * @param time 时间字符串，如 "一1-2"
     * @return 课程节次范围，[开始节次, 结束节次]
     */
    private int[] extractPeriods(String time) {
        int[] result = new int[] { 0, 0 };
        if (time == null || time.isEmpty() || time.length() < 3) {
            return result;
        }

        try {
            // 跳过星期几，提取课程节次
            String periodPart = time.substring(1);
            String[] parts = periodPart.split("-");

            if (parts.length >= 1) {
                result[0] = Integer.parseInt(parts[0]);
            }

            if (parts.length >= 2) {
                result[1] = Integer.parseInt(parts[1]);
            } else {
                result[1] = result[0]; // 如果只有一个数字，则开始和结束节次相同
            }
        } catch (NumberFormatException e) {
            // 解析失败时返回默认值
        }

        return result;
    }
}
