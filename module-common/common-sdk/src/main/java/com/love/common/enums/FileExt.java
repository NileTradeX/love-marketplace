package com.love.common.enums;

public enum FileExt {
    jpg,
    gif,
    png,
    mp3,
    mp4,
    doc,
    docx,
    ppt,
    pptx,
    xls,
    xlsx,
    csv,
    rar,
    zip,
    unknown;

    public static FileExt of(String extension) {
        FileExt[] exts = values();
        for (FileExt ext : exts) {
            if (ext.name().equals(extension)) {
                return ext;
            }
        }
        return unknown;
    }
}
