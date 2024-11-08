package com.vortex.msp.Controller;

import com.vortex.msp.Entity.Sche;
import com.vortex.msp.Service.ScheService;
import com.vortex.msp.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sche")
public class ScheController implements BaseController<Sche> {

    private final ScheService scheService;

    @Autowired
    public ScheController(ScheService scheService) {
        this.scheService = scheService;
    }

    @PostMapping
    public Result saveSche(@RequestBody Sche sche) {
        sche.setCreateUser("System");
        boolean isSave = scheService.saveSche(sche);
        return isSave ? Result.SUCCEED() : Result.FAILED();
    }

    @Override
    public Result save(Sche sche) {
        return null;
    }

    @Override
    public Result save(List<Sche> sches) {
        return null;
    }

    @Override
    public Result delete(Integer id) {
        return null;
    }

    @Override
    public Result delete(List<Integer> ids) {
        return null;
    }

    @Override
    public Result update(Sche sche) {
        return null;
    }

    @Override
    public Result update(List<Sche> sches) {
        return null;
    }

    @Override
    public Result get(Integer id) {
        return null;
    }

    @Override
    public Result list() {
        return null;
    }

    @Override
    public Result limit(Integer pageSize, Integer index) {
        return null;
    }

    @Override
    public Result count() {
        return null;
    }
}
