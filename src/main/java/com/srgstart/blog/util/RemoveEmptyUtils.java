package com.srgstart.blog.util;

/**
 * @author srgstart
 * @Create 2021/8/19 14:53
 * @Description TODO
 */
public class RemoveEmptyUtils {
    /**
     * 移除多余空行和空格
     */
    public static String dealRedundantSpaceAndBlankLine(String content)
    {
        if (content == null || content.length() == 0)
        {
            return "";
        }
        StringBuilder strAfterRemoveCRSB = new StringBuilder();
        for (int i = 0; i < content.length(); i++)
        {
            if (content.charAt(i) != '\r') {
                strAfterRemoveCRSB.append(content.charAt(i));
            }
        }
        String strAfterRemoveCR = strAfterRemoveCRSB.toString();
        if (strAfterRemoveCR == null || strAfterRemoveCR.length() == 0)
        {
            return "";
        }
        StringBuilder resultSB = new StringBuilder();
        String[] lines = strAfterRemoveCR.split("\n");
        int blankCount = 0;
        for (String line : lines)
        {
            if (line == null)
            {
                continue;
            }
            String lineTrim = line.trim();
            if ("".equals(lineTrim))
            {
                blankCount++;
                if (blankCount <= 2) {
                    resultSB.append("\n");
                }
            } else {
                blankCount = 0;
                resultSB.append(dealSpace4OneLine(line)).append("\n");
            }
        }
        resultSB.deleteCharAt(resultSB.length() - 1);
        return resultSB.toString();
    }

    /**
     * 移除1行中的多余空格
     */
    public static String dealSpace4OneLine(String line)
    {
        if (line == null || "".equals(line)) {
            return "";
        }
        int spaceCount = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char curChar = line.charAt(i);
            if (curChar == ' ')
            {
                spaceCount++;
                if (spaceCount <= 5) {
                    sb.append(' ');
                }
            } else {
                spaceCount = 0;
                sb.append(curChar);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String str = "呵呵  测试\n\n\n\n   \n \r\n将风控打扫房  间\n\n\n\n \n \n  快递费解封时代峰峻\n\r\n 发的几点睡 ";
        String result = dealRedundantSpaceAndBlankLine(str);
        System.out.println(result);
    }
}
