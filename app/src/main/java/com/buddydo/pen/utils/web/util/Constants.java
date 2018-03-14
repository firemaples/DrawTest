package com.buddydo.pen.utils.web.util;

/**
 * Created by EUNJI on 2016-09-29.
 */
public class Constants {

    public static final String IMAGE_PATTERN = "(.+?)\\.(jpg|png|gif|bmp)$";
    public static final String IMAGE_TAG_PATTERN = "<img(.*?)src=(\"|')(.+?)(gif|jpg|png|bmp)(\"|')(.*?)(/)?>(</img>)?";
    public static final String ICON_TAG_PATTERN = "<img(.*?)src=(\"|')(.+?)(gif|jpg|png|bmp)(\"|')(.*?)(/)?>(</img>)?";
    public static final String ICON_REV_TAG_PATTERN = "<img(.*?)src=(\"|')(.+?)(gif|jpg|png|bmp)(\"|')(.*?)(/)?>(</img>)?";
    public static final String ITEMPROP_IMAGE_TAG_PATTERN = "<img(.*?)src=(\"|')(.+?)(gif|jpg|png|bmp)(\"|')(.*?)(/)?>(</img>)?";
    public static final String ITEMPROP_IMAGE_REV_TAG_PATTERN = "<img(.*?)src=(\"|')(.+?)(gif|jpg|png|bmp)(\"|')(.*?)(/)?>(</img>)?";
    public static final String TITLE_PATTERN = "<title(.*?)>(.*?)</title>";
    public static final String SCRIPT_PATTERN = "<script(.*?)>(.*?)</script>";
    public static final String METATAG_PATTERN = "<meta(.*?)>";
    public static final String METATAG_CONTENT_PATTERN = "content=\"(.*?)\"";
    public static final String URL_PATTERN = "<\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]>";

    public static final String URL_ZAPPSHARE = "https://goo.gl/l6ffgm";

    public static final int THUMBNAIL_COMPRESS_QUALITY = 95;
    public static final int THUMBNAIL_COMPRESS_HIGH_QUALITY = 100;
}
