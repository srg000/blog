package com.srgstart.blog.util;


import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

/**
 * 此工具类用于将Markdown文本转化成HTML文本
 */
public class MarkdownUtils {


    /**
     * markdown格式转化成Html格式
     * @param markdown
     * @return
     */
    public static String markdownToHtml(String markdown){
        Parser parser=Parser.builder().build();
        Node document=parser.parse(markdown);
        HtmlRenderer renderer=HtmlRenderer.builder().build();
        return renderer.render(document);
    }


    /**
     * 增加扩展（标题锚点，表格生成）
     * Markdown转化成HTML
     * @param markdown
     * @return
     */
    public static String markdownToHtmlExtensions(String markdown){
        //h标题生成id
        Set<Extension> headingAnchorExtensions= Collections.singleton(HeadingAnchorExtension.create());
        //转化table的HTML
        List<Extension> tableExtension= Arrays.asList(TablesExtension.create());
        Parser parser=Parser.builder().extensions(tableExtension).build();
        Node document=parser.parse(markdown);
        HtmlRenderer renderer=HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtension)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    @Override
                    public AttributeProvider create(AttributeProviderContext attributeProviderContext) {
                        return new CustomAttributeProvider();
                    }
                }).build();
        return renderer.render(document);
    }

    /**
     * 处理标签的属性
     */
    static class CustomAttributeProvider implements AttributeProvider{
        @Override
        public void setAttributes(Node node, String s, Map<String, String> map) {
            //改变a标签的target属性为_blank
            if (node instanceof Link){
                map.put("target","_blank");
            }
            if (node instanceof TableBlock){
                map.put("class","ui celled table");
            }
        }
    }


    public static void main(String[] args){
        String table ="| hello | hi | 哈哈哈 |\n"+
                "| ----- | ----- | ----- |\n"+
                "| 斯维尔多 | 士大夫 | 大武当 |\n"+
                "| dwad | 带娃 | 带娃 |\n"+"\n";
        String a="[imCoding 爱编程](http://www.zhiqian.site)";
        System.out.println(markdownToHtml(table));
    }
}
