package com.vortex.msp.Utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FtpUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ThreadLocal<FTPClient> client = ThreadLocal.withInitial(FTPClient::new);
    private FTPClient ftpClient;
    @Value("${ftp.host}")
    private String host;
    @Value("${ftp.port}")
    private int port;
    @Value("${ftp.username}")
    private String username;
    @Value("${ftp.password}")
    private String password;
    @Value("${ftp.rootPath}")
    private String rootPath;

    /**
     * 连接FTP
     *
     * @throws IOException 登录失败
     */
    private void connectFTP() throws IOException {
        ftpClient = client.get();
        if (!ftpClient.isConnected()) {
            ftpClient.connect(host, port);
            if (!ftpClient.login(username, password)) {
                throw new IOException("FTP登录失败，用户名或密码错误");
            }
            //region 编码设置（暂时不用）
//            String LOCAL_CHARSET = "ISO_8859_1";
//            int state = ftpClient.sendCommand("OPTS UTF-8", "ON");
//            logger.info("是否支持UTF8编码：{}", state);
//            if (FTPReply.isPositiveCompletion(state)) {
//                logger.info("服务器支持UTF8编码，无需转码");
//                LOCAL_CHARSET = "UTF-8";
//            }
//            ftpClient.setControlEncoding(LOCAL_CHARSET);
            //endregion
            ftpClient.enterLocalPassiveMode(); // 使用被动模式以避免防火墙问题
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // 设置为二进制传输模式
        }
    }

    /**
     * 下载文件
     *
     * @param serverFilePath FTP服务器文件路径
     * @param localFilePath  本地文件路径
     * @return 是否下载成功 成功-true 失败-false
     */
    public boolean downloadFile(String serverFilePath, String localFilePath) {
        String remoteFilePath = rootPath + serverFilePath;
        logger.info("准备下载文件，远程路径==>{}，本地路径==>{}", remoteFilePath, localFilePath);
        try {
            connectFTP();
            long fileSize = Long.parseLong(ftpClient.getSize(remoteFilePath));
            logger.info("文件大小==>{}", fileSize);
            InputStream inputStream = ftpClient.retrieveFileStream(remoteFilePath);
            try (FileOutputStream outputStream = new FileOutputStream(localFilePath)) {
                byte[] bytesArray = new byte[10 * 1024 * 1024];
                long totalBytesRead = 0L;
                int bytesRead;
                while ((bytesRead = inputStream.read(bytesArray)) != -1) {
                    outputStream.write(bytesArray, 0, bytesRead);
                    totalBytesRead += bytesRead;
                    // 显示下载进度
                    if (fileSize > 0) {
                        double percentComplete = (double) totalBytesRead / fileSize * 100;
                        logger.debug(String.format("下载进度: %.2f%%", percentComplete));
                    }
                }
                inputStream.close();
            }
            // 检查传输是否成功完成
            boolean success = ftpClient.completePendingCommand();
            if (success) {
                logger.info("FTP文件下载成功");
            } else {
                logger.error("FTP文件下载失败，远程路径==>{}，本地路径==>{}", remoteFilePath, localFilePath);
            }
            return success;
        } catch (IOException e) {
            logger.error("文件下载失败: {}", e.getMessage());
            return false;
        } finally {
            disconnectFTP();
        }
    }

    public long getFileLength(String serverFilePath) {
        String remoteFilePath = rootPath + serverFilePath;
        try {
            connectFTP();
            long fileSize = Long.parseLong(ftpClient.getSize(remoteFilePath));
            logger.info("文件大小==>{}", fileSize);
            return fileSize;
        } catch (Exception ex) {
            logger.error("文件下载失败: {}", ex.getMessage());
            return -1;
        } finally {
            disconnectFTP();
        }
    }

    public void downloadFile(String serverFilePath, OutputStream outputStream) {
        String remoteFilePath = rootPath + serverFilePath;
        logger.info("准备下载文件，远程路径==>{}", remoteFilePath);
        try {
            connectFTP();
            long fileSize = Long.parseLong(ftpClient.getSize(remoteFilePath));
            logger.info("文件大小==>{}", fileSize);
            boolean success = ftpClient.retrieveFile(remoteFilePath, outputStream);
            if (!success) {
                throw new RuntimeException("从 FTP 下载文件失败: " + remoteFilePath);
            }
        } catch (Exception ex) {
            logger.error("文件下载失败: {}", ex.getMessage());
        } finally {
            disconnectFTP();
        }
    }

    /**
     * 上传文件
     *
     * @param serverFilePath FTP服务器文件路径
     * @param localFilePath  本地文件路径
     * @return 是否上传成功 成功-true 失败-false
     */
    public boolean uploadFile(String serverFilePath, String localFilePath) {
        String remoteFilePath = rootPath + serverFilePath;
        logger.info("准备上传文件，远程路径==>{}，本地路径==>{}", remoteFilePath, localFilePath);
        try {
            connectFTP();
            createRemoteDirectories(remoteFilePath);
            try (FileInputStream fileInputStream = new FileInputStream(localFilePath)) {
                boolean success = ftpClient.storeFile(remoteFilePath, fileInputStream);
                if (success) {
                    logger.info("FTP文件上传成功");
                } else {
                    logger.error("FTP文件上传失败，远程路径==>{}，本地路径==>{}", remoteFilePath, localFilePath);
                }
                return success;
            }
        } catch (IOException e) {
            logger.error("文件上传失败: {}", e.getMessage());
            return false;
        } finally {
            disconnectFTP();
        }
    }

    public boolean uploadFile(String serverFilePath, InputStream inputStream) {
        String remoteFilePath = rootPath + serverFilePath;
        logger.info("准备上传文件，远程路径==>{}", remoteFilePath);
        try {
            connectFTP();
            createRemoteDirectories(remoteFilePath);
            boolean success = ftpClient.storeFile(remoteFilePath, inputStream);
            if (success) {
                logger.info("FTP文件上传成功");
            } else {
                logger.error("FTP文件上传失败，远程路径==>{}", remoteFilePath);
            }
            return success;
        } catch (IOException e) {
            logger.error("文件上传失败: {}", e.getMessage());
            return false;
        } finally {
            disconnectFTP();
        }
    }

    /**
     * 断开FTP连接
     */
    private void disconnectFTP() {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                logger.error("FTP断开连接失败: {}", e.getMessage());
            }
        }
    }

    /**
     * 创建远程目录
     *
     * @param remoteFilePath 远程目录路径
     * @throws IOException 无法创建远程目录
     * @throws IOException 无法改变工作目录
     */
    private void createRemoteDirectories(String remoteFilePath) throws IOException {
        String[] directories = remoteFilePath.split("/");
        for (int i = 1; i < directories.length - 1; i++) { // 排除空字符串和文件名
            String dir = directories[i];
            if (!dir.isEmpty() && !ftpClient.changeWorkingDirectory(dir)) {
                if (!ftpClient.makeDirectory(dir)) {
                    throw new IOException("无法创建远程目录: " + dir);
                }
                if (!ftpClient.changeWorkingDirectory(dir)) {
                    throw new IOException("无法改变工作目录至: " + dir);
                }
            }
        }
    }

    public InputStream downloadFile(String serverFilePath) throws IOException {
        InputStream inputStream = ftpClient.retrieveFileStream(serverFilePath);
        return inputStream;
    }

}
