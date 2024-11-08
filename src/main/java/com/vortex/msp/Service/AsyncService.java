package com.vortex.msp.Service;

import java.util.concurrent.CompletableFuture;

public interface AsyncService {

    CompletableFuture<String> test();
}
