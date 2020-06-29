package com.msgrecoverer.dequeue.common.exception;

import java.io.IOException;

public class DequeueException extends RuntimeException {
    public DequeueException(IOException e) {
        super(e);
    }
}
