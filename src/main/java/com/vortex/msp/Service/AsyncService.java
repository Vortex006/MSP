package com.vortex.msp.Service;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public interface AsyncService {

    CompletableFuture<String> test();

    CompletableFuture<Boolean> uploadFtp(String serverFilePath, String localFilePath);

    CompletableFuture<Boolean> uploadFtp(String serverFilePath, InputStream inputStream);
}
