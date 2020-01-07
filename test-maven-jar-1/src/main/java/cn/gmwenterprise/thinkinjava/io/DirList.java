package cn.gmwenterprise.thinkinjava.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

public class DirList {

    public static void main(String[] args) {
        File path = new File(".");
        String[] list = path.list();
        list = Optional.ofNullable(list).orElse(new String[0]);
        Arrays.stream(list)
            .sorted(String.CASE_INSENSITIVE_ORDER)
            .forEach(System.out::println);
    }
}

class DirFilter implements FilenameFilter {
    private Pattern pattern;

    public DirFilter(String regex) {
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean accept(File dir, String name) {
        return pattern.matcher(name).matches();
    }
}
