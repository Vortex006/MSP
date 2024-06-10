package com.zyj.msp.Controller;

import com.zyj.msp.Entity.FileInfo;
import com.zyj.msp.Utils.Result;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;

@RestController
@RequestMapping("/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${File.Path}")
    private String filePath;

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        File dic = new File(filePath);
        if (!dic.exists()) {
            boolean mkdir = dic.mkdir();
            logger.info("文件夹创建结果：{}", mkdir);
        }
        File localFile = new File(filePath + "\\" + fileName);
        if (!localFile.exists()) {
            try {
                boolean newFile = localFile.createNewFile();
                OutputStream outputStream = new FileOutputStream(localFile);
                outputStream.write(file.getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Result.SUCCEED(localFile.getPath());
    }

    @GetMapping("/metadata")
    public Result getFileInfo() {
        String filePath = "E:\\视频\\录屏\\20230604_215401.mp4";
        // 根据fileName获取文件信息，包括总大小
        File file = new File(filePath);
        FileInputStream fis;
        String md5 = null;
        try {
            fis = new FileInputStream(file);
            md5 = DigestUtils.md5Hex(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileInfo fileInfo = new FileInfo(file.getName(), file.length(), getChunk(file), md5);
        return Result.SUCCEED(fileInfo);
    }

    @GetMapping("/download/{chunkIndex}")
    public Result downloadChunk(@PathVariable("chunkIndex") int chunkIndex) {
        String fileName = "E:\\视频\\录屏\\20230604_215401.mp4";
        File file = new File(fileName);
        long fileSize = file.length(); // 获取文件总大小
        long chunkSize = getChunk(file); // 假设这个方法返回正确的分块大小或文件总块数
        if (chunkIndex < 0 || chunkIndex > chunkSize) {
            return Result.FAILED("文件块索引超出范围");
        }

        int bufferSize = 10 * 1024 * 1024; // 缓冲区大小
        byte[] bytes;

        try (InputStream in = new FileInputStream(file)) {
            // 计算当前分块的开始位置
            long startPosition = (long) (chunkIndex - 1) * bufferSize;

            // 特殊处理最后一个分块，确保不超过文件实际大小
            if (chunkIndex == chunkSize) {
                long remainingBytes = fileSize - startPosition;
                bytes = new byte[Math.toIntExact(remainingBytes)];
            } else {
                bytes = new byte[bufferSize];
            }

            // 读取数据
            int bytesRead = Math.toIntExact(in.skip(startPosition)); // 跳转到分块开始位置
            if (bytesRead != startPosition) {
                throw new IOException("跳转到分块位置失败");
            }
            bytesRead = in.read(bytes);
            if (bytesRead == -1) {
                return Result.FAILED("读取文件块时发生错误");
            }
            // 如果实际读取的字节数少于缓冲区大小，截取数组以去除未填充的部分
            if (bytesRead < bytes.length) {
                bytes = Arrays.copyOf(bytes, bytesRead);
            }
        } catch (IOException e) {
            return Result.FAILED("文件读取过程中发生错误: " + e.getMessage());
        }

        return Result.SUCCEED(bytes);
    }

//    @GetMapping("/download/{chunkIndex}")
//    public Result downloadChunk(@PathVariable("chunkIndex") int chunkIndex) throws IOException {
//        String fileName = "E:\\视频\\录屏\\20230604_215401.mp4";
//        File file = new File(fileName);
//        long chunkSize = getChunk(file);
//        if (chunkIndex < 0 || chunkIndex > chunkSize) {
//            return Result.FAILED("文件块索引超出范围");
//        }
//        byte[] bytes = new byte[10 * 1024 * 1024];
//        InputStream in = new FileInputStream(file);
//        if (!(chunkIndex == chunkSize)){
//            int a = in.read(bytes, (chunkIndex - 1) * 10 * 1024 * 1024, 10 * 1024 * 1024);
//        } else {
//            long c = in.skip((long) (chunkIndex - 1) * 10 * 1024 * 1024);
//            long fileSize = file.length();
//            int dd = Math.toIntExact(fileSize % 10 * 1024 * 1024);
//            int a = in.read(bytes,0, dd);
//        }
//        return Result.SUCCEED(bytes);
//    }

    /**
     * 计算文件的块数。
     *
     * @param file 要计算的文件
     * @return 文件的块数
     */
    public int getChunk(File file) {
        long CHUNK_SIZE = 10 * 1024 * 1024; // 100 MB
        // 检查文件是否为空，避免NullPointerException
        if (ObjectUtils.isEmpty(file)) {
            throw new IllegalArgumentException("File cannot be null.");
        }
        long fileSize = file.length();
        // 预防整数溢出，使用long类型进行计算
        long chunks = fileSize / CHUNK_SIZE;

        // 处理边界情况，如果文件大小正好是CHUNK_SIZE的倍数，应确保块数加1
        long remainder = fileSize % CHUNK_SIZE;
        if (remainder != 0) {
            chunks++;
        }
        // 由于方法签名要求返回int类型，这里将long转换为int
        // 由于之前已经预防了溢出，这里转换是安全的
        return (int) chunks;
    }

    @PostMapping("/dd")
    public Result dd(@RequestBody LocalDate date) {
        logger.info("接收到的日期是：" + date);
        return Result.SUCCEED();
    }


}
