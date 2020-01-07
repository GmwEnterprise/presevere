package cn.gmwenterprise.thinkinjava.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 文件、文件夹操作工具类
 */
public final class Directory {
    public static File[] local(File dir, String regex) {
        return dir.listFiles(new FilenameFilter() {
            private Pattern pattern = Pattern.compile(regex);

            @Override
            public boolean accept(File dir, String name) {
                return pattern.matcher(new File(name).getName()).matches();
            }
        });
    }

    public static File[] local(String path, String regex) {
        return local(new File(path), regex);
    }

    public static class TreeInfo implements Iterable<File> {
        // 实现iterable接口以提供可遍历属性

        List<File> files = new ArrayList<>();
        List<File> dirs = new ArrayList<>();

        @Override
        public Iterator<File> iterator() {
            // 默认返回files的
            return files.iterator();
        }

        void addAll(TreeInfo other) {
            // 添加另一个实例的所有文件、文件夹
            files.addAll(other.files);
            dirs.addAll(other.dirs);
        }

        @Override
        public String toString() {
            // 格式化输出
            return String.format("dirs: %s\n\nfiles: %s", PPrint.pformat(dirs), PPrint.pformat(dirs));
        }
    }

    public static TreeInfo walk(String start, String regex) {
        return recurseDirs(new File(start), regex);
    }

    public static TreeInfo walk(File start, String regex) {
        return recurseDirs(start, regex);
    }

    public static TreeInfo walk(String start) {
        return recurseDirs(new File(start), ".*");
    }

    public static TreeInfo walk(File start) {
        return recurseDirs(start, ".*");
    }

    private static TreeInfo recurseDirs(File startDir, String regex) {
        TreeInfo result = new TreeInfo();
        for (File item : Objects.requireNonNull(startDir.listFiles())) {
            if (item.isDirectory()) {
                // 为文件夹则添加文件夹后递归遍历
                result.dirs.add(item);
                result.addAll(recurseDirs(item, regex));
            } else {
                // 为文件则检查是否满足正则表达式，满足则添加
                if (item.getName().matches(regex)) {
                    result.files.add(item);
                }
            }
        }
        return result;
    }

    // simple test

    public static void main(String[] args) {
        TreeInfo walk = null;
        List<TreeInfo> list = new ArrayList<>();
        if (args.length == 0) {
            walk = walk(".");
        } else {
            for (String arg : args) {
                list.add(walk(arg));
            }
        }
        System.out.println(walk == null ? list : walk);
    }
}
