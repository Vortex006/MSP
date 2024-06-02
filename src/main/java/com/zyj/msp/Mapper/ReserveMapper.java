package com.zyj.msp.Mapper;

import com.zyj.msp.Entity.Reserve;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReserveMapper {

    //新建预约
    int saveReserve(Reserve reserve);

    //删除预约
    int deleteReserve(Integer reserve_id);

    //修改预约信息
    int update(Reserve reserve);

    //根据预约号查询预约信息
    Reserve getReserve(Integer reserve_id);

    //根据预约人查询预约信息
    List<Reserve> listReservesByPhone(String phone);

    //根据预约人手机号查询预约信息
    List<Reserve> listReservesByUsername(String username);

    //查询所有预约信息
    List<Reserve> listAllReserves();

    /**
     * 分页查询所有预约信息
     *
     * @param pageSize 每页显示的条数
     * @param offset   偏移量
     */
    List<Reserve> limitReserves(Integer pageSize, Integer offset);
}
