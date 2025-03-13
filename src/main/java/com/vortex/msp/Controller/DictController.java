package com.vortex.msp.Controller;

import com.vortex.msp.Entity.Dict;
import com.vortex.msp.Exception.ParameterNullException;
import com.vortex.msp.Service.DictService;
import com.vortex.msp.Utils.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dict")
@Tag(name = "字典模块")
public class DictController {

    private final DictService dictService;

    @Autowired
    public DictController(DictService dictService) {
        this.dictService = dictService;
    }

    @GetMapping("/type/{typeName}")
    public Result getDictByType(@PathVariable("typeName") String dictType) {
        if (!StringUtils.hasText(dictType)) {
            throw new ParameterNullException("参数不符合规范");
        }

        List<Dict> dictList = dictService.getDictByType(dictType);
        return Result.SUCCEED(dictList);
    }
}
