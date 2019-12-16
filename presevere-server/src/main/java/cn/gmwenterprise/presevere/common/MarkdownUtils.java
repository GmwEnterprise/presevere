package cn.gmwenterprise.presevere.common;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MarkdownUtils {

    private static Parser parser;
    private static HtmlRenderer renderer;

    private static final String REG_EX_SCRIPT = "<script[^>]*?>[\\s\\S]*?</script>"; // 定义script的正则表达式
    private static final String REG_EX_STYLE = "<style[^>]*?>[\\s\\S]*?</style>"; // 定义style的正则表达式
    private static final String REG_EX_HTML = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String REG_EX_SPACE = "\\s*|\t|\r|\n";// 定义空格回车换行符
    private static final String REG_EX_W = "<w[^>]*?>[\\s\\S]*?</w[^>]*?>";//定义所有w标签

    private static final Pattern P_W = Pattern.compile(REG_EX_W, Pattern.CASE_INSENSITIVE);
    private static final Pattern P_SCRIPT = Pattern.compile(REG_EX_SCRIPT, Pattern.CASE_INSENSITIVE);
    private static final Pattern P_STYLE = Pattern.compile(REG_EX_STYLE, Pattern.CASE_INSENSITIVE);
    private static final Pattern P_HTML = Pattern.compile(REG_EX_HTML, Pattern.CASE_INSENSITIVE);
    private static final Pattern P_SPACE = Pattern.compile(REG_EX_SPACE, Pattern.CASE_INSENSITIVE);

    static {
        MutableDataSet options = new MutableDataSet();
        parser = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();
    }

    /**
     * 返回渲染后的html内容
     *
     * @param markdown
     * @return
     */
    public static String render(String markdown) {
        // You can re-use parser and renderer instances
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }

    /**
     * 返回去除html符号的纯文本
     *
     * @param html html内容
     * @return 纯文本
     */
    public static String plain(String html) {
        Matcher m_w = P_W.matcher(html);
        html = m_w.replaceAll(""); // 过滤script标签
        Matcher m_script = P_SCRIPT.matcher(html);
        html = m_script.replaceAll(""); // 过滤script标签
        Matcher m_style = P_STYLE.matcher(html);
        html = m_style.replaceAll(""); // 过滤style标签
        Matcher m_html = P_HTML.matcher(html);
        html = m_html.replaceAll(""); // 过滤html标签
        Matcher m_space = P_SPACE.matcher(html);
        html = m_space.replaceAll(""); // 过滤空格回车标签
        return html.replaceAll(" ", "").trim(); //过滤并返回文本字符串
    }
}
