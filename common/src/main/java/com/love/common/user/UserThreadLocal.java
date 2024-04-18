package com.love.common.user;


public class UserThreadLocal {

    private static final ThreadLocal<IUser> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static IUser get() {
        return USER_THREAD_LOCAL.get();
    }

    public static void set(IUser user) {
        USER_THREAD_LOCAL.set(user);
    }

    public static void remove() {
        USER_THREAD_LOCAL.remove();
    }
}
