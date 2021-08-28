package com.srgstart.blog;

import com.srgstart.blog.util.HtmlToPlainText;
import com.srgstart.blog.util.MarkdownToHtml;
import com.srgstart.blog.util.RemoveEmptyUtils;

/**
 * @author srgstart
 * @Create 2021/8/19 14:47
 * @Description TODO
 */
public class MarkdownTest {

    public static void main(String[] args) {
        String markdown = "package com.srgstart.layui.config;\n" +
                "\n" +
                "import org.springframework.context.annotation.Bean;\n" +
                "import org.springframework.context.annotation.Configuration;\n" +
                "import org.springframework.web.cors.CorsConfiguration;\n" +
                "import org.springframework.web.cors.UrlBasedCorsConfigurationSource;\n" +
                "import org.springframework.web.filter.CorsFilter;\n" +
                "\n" +
                "import java.util.Collections;\n" +
                "\n" +
                "@Configuration\n" +
                "public class CorsConfig {\n" +
                "    @Bean\n" +
                "    public CorsFilter corsFilter() {\n" +
                "        CorsConfiguration corsConfiguration = new CorsConfiguration();\n" +
                "        //1,允许任何来源\n" +
                "        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList(\"*\"));\n" +
                "        //2,允许任何请求头\n" +
                "        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);\n" +
                "        //3,允许任何方法\n" +
                "        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);\n" +
                "        //4,允许凭证\n" +
                "        corsConfiguration.setAllowCredentials(true);\n" +
                "\n" +
                "        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();\n" +
                "        source.registerCorsConfiguration(\"/**\", corsConfiguration);\n" +
                "        return new CorsFilter(source);\n" +
                "    }\n" +
                "}";
        String htmlContent = MarkdownToHtml.convert(markdown);
        String content = HtmlToPlainText.convert(htmlContent);
        System.out.println(content);
        System.out.println("==============");
        String finalContent = RemoveEmptyUtils.dealRedundantSpaceAndBlankLine(content);
        System.out.println(finalContent);
    }
}
