package com.love.common.enums;

public enum FileType {
    UNKNOWN(0, ""),
    IMAGE(1, "png,gif,jpg,bmp,tif,jpeg"),
    VIDEO(2, "mp4,avi,mov,mpg,rm,rmvb"),
    AUDIO(3, "mp3,amr,wav,aac,flac,wma,ogg,mid"),
    DOCUMENT(4, "txt,md,pdf,doc,xls,ppt"),
    COMPRESSED(5, "zip,rar,gz,tar");

    FileType(int type, String ext) {
        this.type = type;
        this.ext = ext;
    }

    private final int type;
    private final String ext;

    public int getType() {
        return type;
    }

    public String getExt() {
        return ext;
    }

    public static boolean allow(String ext) {
        ext = realExt(ext);
        FileType[] all = FileType.values();
        for (FileType type : all) {
            if (type.ext.contains(ext)) {
                return true;
            }
        }
        return false;
    }

    public static FileType type(String ext) {
        ext = realExt(ext);
        FileType[] all = FileType.values();
        for (FileType type : all) {
            if (type.ext.contains(ext)) {
                return type;
            }
        }
        return UNKNOWN;
    }

    private static String realExt(String ext) {
        if (!ext.contains(".")) {
            return ext;
        }
        int idx = ext.lastIndexOf(".");
        return ext.substring(idx + 1).toLowerCase();
    }
}
