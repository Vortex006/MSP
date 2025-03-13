package com.vortex.msp.ServiceImpl;

import com.vortex.msp.Entity.Deptment;
import com.vortex.msp.Entity.Dict;
import com.vortex.msp.Entity.Doctor;
import com.vortex.msp.Entity.Schedule;
import com.vortex.msp.Entity.VO.ScheduleVo;
import com.vortex.msp.Mapper.DeptmentMapper;
import com.vortex.msp.Mapper.DictMapper;
import com.vortex.msp.Mapper.DoctorMapper;
import com.vortex.msp.Mapper.ScheduleMapper;
import com.vortex.msp.Service.ScheduleService;
import com.vortex.msp.Utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleMapper scheduleMapper;
    private final DictMapper dictMapper;
    private final DoctorMapper doctorMapper;
    private final DeptmentMapper deptmentMapper;

    @Autowired
    public ScheduleServiceImpl(ScheduleMapper scheduleMapper, DictMapper dictMapper, DoctorMapper doctorMapper,
                               DeptmentMapper deptmentMapper) {
        this.scheduleMapper = scheduleMapper;
        this.dictMapper = dictMapper;
        this.doctorMapper = doctorMapper;
        this.deptmentMapper = deptmentMapper;
    }

    @Override
    public Boolean saveSchedule(Schedule schedule) {
        Integer x = scheduleMapper.saveSchedule(schedule);
        return x > 0;
    }

    @Override
    public List<Schedule> listSchedules() {
        List<Schedule> scheduleList = scheduleMapper.listSchedules();
        return scheduleList;
    }

    @Override
    public Schedule getSchedule(Integer scheduleId) {
        Schedule schedule = scheduleMapper.getSchedule(scheduleId);
        return schedule;
    }

    @Override
    public List<Schedule> getScheduleByDate(LocalDate date) {
        List<Schedule> scheduleList = scheduleMapper.getScheduleByDate(date);
        return scheduleList;
    }

    @Override
    public List<Schedule> getScheduleByDates(List<LocalDate> dates) {
        List<Schedule> scheduleList = scheduleMapper.getScheduleByDates(dates);
        return scheduleList;
    }

    @Override
    public Integer getScheduleCount() {
        Integer count = scheduleMapper.getScheduleCount();
        return count;
    }

    @Override
    public List<Schedule> limitSchedules(Integer pageSize, Integer index) {
        int offset = DataUtil.getOffset(pageSize, index);
        List<Schedule> schedules = scheduleMapper.limitSchedules(pageSize, offset);
        return schedules;
    }

    @Override
    public ScheduleVo DoToVo(Schedule schedule) {
        if (schedule == null) {
            return null;
        }

        Optional<Deptment> deptment = Optional.ofNullable(deptmentMapper.getDeptment(schedule.getScheduleDeptmentId()));
        String deptName = deptment.isPresent() ? deptment.get().getDeptmentName() : "";

        Optional<Doctor> doctor = Optional.ofNullable(doctorMapper.getDoctor(schedule.getScheduleDoctorId()));
        String docName = doctor.isPresent() ? doctor.get().getDoctorName() : "";


        List<Dict> scheduleTypes = dictMapper.getDictByType("ScheduleType");
        Optional<Dict> first = scheduleTypes.stream().
                filter(dict -> dict.getDictKey().equals(schedule.getScheduleType()))
                .findFirst();
        String type = first.isPresent() ? first.get().getDictValue() : "";


        ScheduleVo.ScheduleVoBuilder builder = ScheduleVo.builder()
                .scheduleId(schedule.getScheduleId())
                .scheduleDeptment(deptName)
                .scheduleDoctor(docName)
                .scheduleDate(schedule.getScheduleDate())
                .scheduleStartTime(schedule.getScheduleStartTime())
                .scheduleEndTime(schedule.getScheduleEndTime())
                .scheduleType(type)
                .scheduleNum(schedule.getScheduleNum());

        return builder.build();
    }


}
