package me.topits.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Wang Ziyue
 * @date 2020/5/21 14:52
 * 适用于 Ding Robot  通知
 */
public class Markdown {

    /**
     * How to use it?
     * <p>
     * Markdown markdown = Markdown.builder()
     * .h2("Header 1")
     * .text("This is ")
     * .link("mySite", "https://topits.me")
     * .h2("Header 2")
     * .link("-", "name1", "url1")
     * .link("-", "name2", "url2")
     * .author("Wang")
     * .build();
     * </p>
     */
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static MarkdownBuilderFactory builder() {
        return new MarkdownBuilderFactory();
    }

    public Markdown(MarkdownBuilderFactory builderFactory) {
        this.content = builderFactory.content.toString() + builderFactory.footer + builderFactory.author;
    }

    public static class MarkdownBuilderFactory {
        private final StringBuilder content = new StringBuilder();
        private String footer = "\r\n" + "> *Created: " +
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                "* ";
        private String author = "";

        public MarkdownBuilderFactory() {
        }

        /** 自定义前缀header */
        public MarkdownBuilderFactory header(String prefix, String s) {
            this.content.append("\r\n")
                    .append(prefix).append(" ").append(s)
                    .append("\r\n");
            return this;
        }

        public MarkdownBuilderFactory footer(String footer) {
            this.footer = "\r\n" + "> *" + footer + "*" + "\r\n";
            return this;
        }

        public MarkdownBuilderFactory author(String author) {
            this.author = "- *Author: " + author + "*";
            return this;
        }

        public MarkdownBuilderFactory h1(String s) {
            this.content.append("\r\n")
                    .append("# ").append(s)
                    .append("\r\n\r\n");
            return this;
        }

        public MarkdownBuilderFactory h2(String s) {
            this.content.append("\r\n")
                    .append("## ").append(s)
                    .append("\r\n\r\n");
            return this;
        }

        public MarkdownBuilderFactory h3(String s) {
            this.content.append("\r\n")
                    .append("### ").append(s)
                    .append("\r\n\r\n");
            return this;
        }

        public MarkdownBuilderFactory h4(String s) {
            this.content.append("\r\n")
                    .append("#### ").append(s)
                    .append("\r\n\r\n");
            return this;
        }

        public MarkdownBuilderFactory text(String s) {
            this.content.append(s);
            return this;
        }

        /** 链接 */
        public MarkdownBuilderFactory link(String name, String url) {
            this.content.append("[").append(name).append("]")
                    .append("(").append(url).append(")");
            return this;
        }

        public MarkdownBuilderFactory link(String prefix, String name, String url) {
            this.content.append(prefix).append(" ")
                    .append("[").append(name).append("]")
                    .append("(").append(url).append(")")
                    .append("\r\n");
            return this;
        }

        public MarkdownBuilderFactory newline() {
            this.content.append("\r\n");
            return this;
        }

        public MarkdownBuilderFactory newline(String s) {
            this.content.append("\r\n").append(s);
            return this;
        }

        /** 引用 */
        public MarkdownBuilderFactory quote(String s) {
            this.content.append("\r\n").append("> ").append(s).append("\r\n");
            return this;
        }

        public MarkdownBuilderFactory link(String... urls) {
            if (urls.length > 0) {
                this.content.append("\r\n");
                for (String s : urls) {
                    this.content.append("- [").append(StringUtil.getFilename(s)).append("]")
                            .append("(").append(s).append(")")
                            .append("\r\n");
                }
            }
            return this;
        }

        public MarkdownBuilderFactory link(List<String> list) {
            if (list.size() > 0) {
                this.content.append("\r\n");
                for (String s : list) {
                    this.content.append("- [").append(StringUtil.getFilename(s)).append("]")
                            .append("(").append(s).append(")")
                            .append("\r\n");
                }
            }
            return this;
        }

        public MarkdownBuilderFactory image(String url) {
            this.content.append("\r\n")
                    .append("![]")
                    .append("(").append(url).append(")")
                    .append("\r\n");
            return this;
        }

        public MarkdownBuilderFactory ol(List<String> list) {
            if (list.size() > 0) {
                this.content.append("\r\n");
                for (int i = 0; i < list.size(); i++) {
                    this.content.append(i + 1).append(" ").append(list.get(i))
                            .append("\r\n");
                }
            }
            return this;
        }

        public MarkdownBuilderFactory ul(List<String> list) {
            if (list.size() > 0) {
                this.content.append("\r\n");
                for (String s : list) {
                    this.content
                            .append("- ").append(s)
                            .append("\r\n");
                }
            }
            return this;
        }

        public MarkdownBuilderFactory ul(String... uls) {
            if (uls.length > 0) {
                this.content.append("\r\n");
                for (String s : uls) {
                    this.content.append("- ").append(s)
                            .append("\r\n");
                }
            }
            return this;
        }

        public Markdown build() {
            return new Markdown(this);
        }

    }
}
