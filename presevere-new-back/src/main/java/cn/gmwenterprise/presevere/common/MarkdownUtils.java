package cn.gmwenterprise.presevere.common;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

public final class MarkdownUtils {

    private static Parser parser;
    private static HtmlRenderer renderer;

    static {
        MutableDataSet options = new MutableDataSet();
        parser = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();
    }

    public static String render(String markdown) {
        // You can re-use parser and renderer instances
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }

    public static void main(String[] args) {
        System.out.println(render("### Hello world;**Point**"));
    }
}
