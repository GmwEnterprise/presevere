package com.github.mrag.source;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;

public class XPathTest {
    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        // 开启验证
        documentBuilderFactory.setValidating(true);
        documentBuilderFactory.setNamespaceAware(false);
        documentBuilderFactory.setIgnoringComments(true);
        documentBuilderFactory.setIgnoringElementContentWhitespace(false);
        documentBuilderFactory.setExpandEntityReferences(true);

        // 创建DocumentBuilder
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        // 设置异常处理
        documentBuilder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                System.out.println("warning: " + exception.getMessage());
                exception.printStackTrace();
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
                System.out.println("error: " + exception.getMessage());
                exception.printStackTrace();
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                System.out.println("fatalError: " + exception.getMessage());
                exception.printStackTrace();
            }
        });

        // 加载文档
        File docFile = new File("src/main/resources/inventory.xml");
        Document doc = documentBuilder.parse(docFile);

        // 创建XPathFactory
        XPathFactory xPathFactory = XPathFactory.newInstance();

        // 创建XPath对象
        XPath xPath = xPathFactory.newXPath();

        // 编译XPath表达式
        XPathExpression expr = xPath.compile("//book[author='Neal Stephenson']/title/text()");

        // 通过XPath表达式得到结果，第一个参数指定了XPath表达式进行上下文查询的上下文节点，也就是在指定节点下
        // 查找符合XPath的节点；第二个参数指定了XPath表达式的返回类型
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        System.out.println("查询作者为Neal Stephenson的图书的标题：");
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }

        System.out.println("查询1997年之后的图书的标题：");
        nodes = (NodeList) xPath.evaluate("//book[@year>1997]/title/text()",
                doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }

        System.out.println("查询1997年之后的图书的属性和标题：");
        nodes = (NodeList) xPath.evaluate("//book[@year>1997]/@*|//book[@year>1997]/title/text()",
                doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }
    }
}
