package cn.gmwenterprise.website.util;

import org.thymeleaf.util.StringUtils;

import java.util.regex.Pattern;

public final class ParamValidator {

    private static final int MAX_NICKNAME_LENGTH = 18;

    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MIN_USERNAME_LENGTH = 8;

    private static final int MAX_PASSWORD_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 6;

    public static void validNickname(String nickname) throws ParamValidException {
        if (StringUtils.isEmpty(nickname)) {
            throw new ParamValidException("昵称不能为空");
        }
        if (nickname.trim().length() < nickname.length()) {
            throw new ParamValidException("昵称前后不能有空白符");
        }
        if (getByteLength(nickname) > MAX_NICKNAME_LENGTH) {
            throw new ParamValidException("昵称长度超出限制，应控制在18个字符以下，中文算2个字符");
        }
    }

    /**
     * 计算字符串的长度，占用超过1个字节的字符都将记为2长度
     *
     * @param str 待计算字符串
     * @return 长度
     */
    private static int getByteLength(String str) {
        int valueLen = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.substring(i, i + 1).getBytes().length > 1) {
                // 字节数超过1的都记为长度2
                valueLen += 2;
            } else {
                valueLen++;
            }
        }
        return valueLen;
    }

    public static void validUsername(String username) throws ParamValidException {
        if (StringUtils.isEmpty(username)) {
            throw new ParamValidException("用户名不能为空");
        }
        String regExp = "^[a-zA-Z0-9]{" + MIN_USERNAME_LENGTH + "," + MAX_USERNAME_LENGTH + "}$";
        if (!Pattern.matches(regExp, username)) {
            throw new ParamValidException("用户名应为长度在8-20以内的数字、字母组合");
        }
    }

    public static void validPassword(String password) throws ParamValidException {
        if (StringUtils.isEmpty(password)) {
            throw new ParamValidException("密码不能为空");
        }
        String regExp = "^[a-zA-Z0-9]{" + MIN_PASSWORD_LENGTH + "," + MAX_PASSWORD_LENGTH + "}$";
        if (!Pattern.matches(regExp, password)) {
            throw new ParamValidException("密码应为长度在8-20以内的数字、字母组合");
        }
    }
}
