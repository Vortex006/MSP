package com.zyj.msp.Service;

import com.zyj.msp.Entity.Reserve;

import java.util.List;

public interface ReserveService {

    /**
     * 添加预约
     *
     * @param reserve 预约信息
     * @return 是否添加预约成功
     */
    boolean saveReserve(Reserve reserve);

    /**
     * 删除预约
     *
     * @param reserve_id 预约ID
     * @return 是否删除成功
     */
    boolean deleteReserve(Integer reserve_id);

    /**
     * 修改预约信息
     *
     * @param reserve 预约信息
     * @return 是否修改成功
     */
    boolean update(Reserve reserve);

    /**
     * 根据预约号查询预约信息
     *
     * @param reserve_id 预约ID
     * @return 单个预约信息
     */
    Reserve getReserve(Integer reserve_id);

    /**
     * 根据预约人查询预约信息
     *
     * @param phone 手机号
     * @return 预约信息集合
     */
    List<Reserve> listReservesByPhone(String phone);

    /**
     * 根据预约人手机号查询预约信息
     *
     * @param username 用户名
     * @return 预约信息集合
     */
    List<Reserve> listReservesByUsername(String username);

    /**
     * 查询所有预约信息
     *
     * @return 预约信息集合
     */
    List<Reserve> listAllReserves();

    /**
     * 分页查询所有预约信息
     *
     * @param pageSize 每页显示数量
     * @param index    当前页数
     */
    List<Reserve> limitReserves(Integer pageSize, Integer index);

}
