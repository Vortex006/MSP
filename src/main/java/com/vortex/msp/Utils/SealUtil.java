package com.vortex.msp.Utils;

import cn.hutool.core.io.FileUtil;
import com.vortex.msp.Entity.Seal.Seal;
import com.vortex.msp.Entity.Seal.SealCircle;
import com.vortex.msp.Entity.Seal.SealFont;

import java.io.File;

public class SealUtil {

    private static final String HOMENAME = "智慧医疗服务平台电子专用印章";
    private static final String LOGO = "★";

    public static void CreateSeal(String path, String typeName) throws Exception {
        File file = FileUtil.file(path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        Seal.builder().size(200).borderCircle(SealCircle.builder().line(4).width(95).height(95).build())
                .mainFont(SealFont.builder().text(HOMENAME).size(22).space(22.0).margin(4).build())
                .centerFont(SealFont.builder().text(LOGO).size(80).build())
                .titleFont(SealFont.builder().text(typeName).size(16).space(8.0).margin(54).build()).build().draw(path);
    }

}


