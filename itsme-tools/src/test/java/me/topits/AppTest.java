package me.topits;

import me.topits.utils.Markdown;

public class AppTest {

    public static void main(String[] args) {
        Markdown markdown = Markdown.builder()
                .h2("Header 1")
                .text("This is ")
                .link("mySite", "https://topits.me")
                .h2("Header 2")
                .link("-", "name1", "url1")
                .link("-", "name2", "url2")
                .author("Wang")
                .build();

        System.out.println(markdown.getContent());
    }
}
