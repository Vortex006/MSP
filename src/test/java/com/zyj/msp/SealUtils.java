package com.zyj.msp;

import cn.hutool.core.io.FileUtil;

import java.io.File;

public class SealUtils {

/*    public static void main(String[] args) throws Exception {
        String path = GlConfig.getUploadPath() +"/公章1.png";
        System.out.println(path);
        OfficialSeal_1(path);
    }*/

    public static void OfficialSeal_1(String path, String orgName) throws Exception {
        File file = FileUtil.file(path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        Seal.builder().size(200).borderCircle(SealCircle.builder().line(4).width(95).height(95).build())
                .mainFont(SealFont.builder().text(orgName).size(22).space(22.0).margin(4).build())
                .centerFont(SealFont.builder().text("★").size(80).build())
                .titleFont(SealFont.builder().text("电子专用印章").size(16).space(8.0).margin(54).build()).build().draw(path);
    }

    public static void main(String[] args) {
        try {
            OfficialSeal_1("C:\\gl-file\\upload\\seal\\103admin.png", "xxx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void OfficialSeal_2() throws Exception {
        Seal.builder().size(300).borderCircle(SealCircle.builder().line(5).width(140).height(140).build())
                .mainFont(SealFont.builder().text("中国四大天王股份有限公司").size(35).space(35.0).margin(10).build())
                .centerFont(SealFont.builder().text("★").size(100).build())
                .titleFont(SealFont.builder().text("电子签章").size(22).space(10.0).margin(68).build()).build()
                .draw("img/公章2.png");
    }

    public static void OfficialSeal_3() throws Exception {
        Seal.builder()
                .size(300)
                .borderCircle(SealCircle.builder().line(3).width(144).height(100).build())
                .borderInnerCircle(SealCircle.builder().line(1).width(140).height(96).build())
                .mainFont(SealFont.builder().text("中国四大天王股份有限公司").size(25).space(12.0).margin(10).build())
                .centerFont(SealFont.builder().text("NO.5201314").size(20).build())
                .titleFont(SealFont.builder().text("电子合同专用章").size(20).space(9.0).margin(64).build())
                .build()
                .draw("img/公章3.png");
    }

    public static void PrivateSeal_1() throws Exception {
        Seal.builder()
                .size(300)
                .borderSquare(16)
                .mainFont(SealFont.builder().text("刘德华").size(120).build())
                .build()
                .draw("img/私章1.png");
    }


    public static void PrivateSeal_2() throws Exception {
        Seal.builder()
                .size(300)
                .borderSquare(16)
                .mainFont(SealFont.builder().text("刘德华印").size(120).build())
                .build()
                .draw("img/私章2.png");
    }
}

