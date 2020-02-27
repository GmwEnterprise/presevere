package cn.gmwenterprise.test;

public class UnicodeTest {

    public static void main(String[] args) throws Exception {
//        byte[] bytes = "你好".getBytes(Charset.defaultCharset());
//        for (byte b : bytes) {
//            System.out.print(Integer.toBinaryString(b & 0xff) + "  ");
//        }
//
//        System.out.println();
//
//        String path = "E:\\Desktop\\hello.txt";
//        try (var fin = new FileInputStream(new File(path))) {
//            for (byte b : fin.readAllBytes()) {
//                System.out.print(Integer.toBinaryString(b & 0xff) + "  ");
//            }
//        }

        int a = -65536;
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(a >>> 8);
    }

    private static String space(int time) {
        return " ".repeat(time);
    }
}
