package com.vortex.msp.Controller;

import com.vortex.msp.Utils.Result;

import java.util.List;

public interface BaseController<T> {

    /**
     * 新增数据
     *
     * @param t 数据
     * @return 结果集
     */
    Result save(T t);

    /**
     * 批量新增数据
     *
     * @param ts 数据集合
     * @return 结果集
     */
    Result save(List<T> ts);

    /**
     * 删除数据
     *
     * @param id 数据ID
     * @return 结果集
     */
    Result delete(Integer id);

    /**
     * 批量删除数据
     *
     * @param ids 数据ID集合
     * @return 结果集
     */
    Result delete(List<Integer> ids);

    /**
     * 修改数据
     *
     * @param t 数据
     * @return 结果集
     */
    Result update(T t);

    /**
     * 批量修改数据
     *
     * @param ts 数据
     * @return 结果集
     */
    Result update(List<T> ts);

    /**
     * 根据ID获取单个数据
     *
     * @param id 数据ID
     * @return 结果集
     */
    Result get(Integer id);

    /**
     * 获取所有数据
     *
     * @return 结果集
     */
    Result list();

    /**
     * 分页查询
     *
     * @param pageSize 每页条数
     * @param index    当前页码
     * @return 结果集
     */
    Result limit(Integer pageSize, Integer index);

    /**
     * 获取数据总数
     *
     * @return 结果集
     */
    Result count();

}
