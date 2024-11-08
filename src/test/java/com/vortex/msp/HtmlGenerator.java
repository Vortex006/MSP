package com.vortex.msp;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;

public class HtmlGenerator {

    /**
     * HTML 转 PDF
     *
     * @param content html内容
     * @param outPath 输出pdf路径
     * @return 是否创建成功
     */
    public static boolean html2Pdf(String content, String outPath) {
        try {
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setCharset("UTF-8");
            FontProvider fontProvider = new FontProvider();
            fontProvider.addSystemFonts();
            converterProperties.setFontProvider(fontProvider);
            PdfWriter writer = new PdfWriter(outPath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.setDefaultPageSize(PageSize.A4.rotate());
            HtmlConverter.convertToPdf(content, pdfDoc, converterProperties);
            pdfDoc.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
