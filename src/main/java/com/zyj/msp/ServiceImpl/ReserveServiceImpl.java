package com.zyj.msp.ServiceImpl;

import com.zyj.msp.Entity.Reserve;
import com.zyj.msp.Exception.ParameterNullException;
import com.zyj.msp.Mapper.ReserveMapper;
import com.zyj.msp.Service.ReserveService;
import com.zyj.msp.Utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReserveServiceImpl implements ReserveService {

    private final ReserveMapper reserveMapper;

    @Autowired
    public ReserveServiceImpl(ReserveMapper reserveMapper) {
        this.reserveMapper = reserveMapper;
    }

    @Override
    public boolean saveReserve(Reserve reserve) {
        int i = reserveMapper.saveReserve(reserve);
        return i > 0;
    }

    @Override
    public boolean deleteReserve(Integer reserve_id) {
        int i = reserveMapper.deleteReserve(reserve_id);
        return i > 0;
    }

    @Override
    public boolean update(Reserve reserve) {
        int i = reserveMapper.update(reserve);
        return i > 0;
    }

    @Override
    public Reserve getReserve(Integer reserve_id) {
        return reserveMapper.getReserve(reserve_id);
    }

    @Override
    public List<Reserve> listReservesByPhone(String phone) {
        return reserveMapper.listReservesByPhone(phone);
    }

    @Override
    public List<Reserve> listReservesByUsername(String username) {
        return reserveMapper.listReservesByUsername(username);
    }

    @Override
    public List<Reserve> listAllReserves() {
        return reserveMapper.listAllReserves();
    }

    @Override
    public List<Reserve> limitReserves(Integer pageSize, Integer index) {
        if (pageSize <= 0 || index <= 0) {
            throw new ParameterNullException("参数异常");
        }
        int offset = DataUtil.getOffset(pageSize, index);
        return reserveMapper.limitReserves(pageSize, offset);
    }


}
