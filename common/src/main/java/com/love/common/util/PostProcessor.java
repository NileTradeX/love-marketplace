package com.love.common.util;

public interface PostProcessor<I, O> {
    void process(I src, O dst);
}
