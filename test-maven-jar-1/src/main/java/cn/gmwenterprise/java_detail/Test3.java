package cn.gmwenterprise.java_detail;

public class Test3 {
    public static void main(String[] args) {
        long value = 0xABCDFFFF;
        System.out.println(Long.toHexString(value));
        System.out.println(Integer.toHexString((int) value));

        // long 转 int 的过程，高32位直接舍弃

        System.out.println(Double.toHexString(2.5555d));
    }
}
