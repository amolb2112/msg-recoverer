package com.msgrecoverer.dequeue.common;

/**
 * Various Error codes
 *
 */
public enum DequeueResponseCodes {
    BASE_BUCKET_DOES_NOT_EXIST,
    BUCKET_ALREADY_EXISTS,

    SELECTION_PARAM_PATH_NOT_MATCHED,
    SELECTION_PARAM_VALUE_NOT_MATCHED,

    UNKNOWN_ERROR;
}
