package com.brandwatch.interviews.topic;

import com.beust.jcommander.JCommander;
import com.brandwatch.interviews.topic.ascii.AsciiTitleGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Main {

    @Autowired
    private AsciiTitleGenerator titleGenerator;

    @Autowired
    private Demo demo;

    public static void main(String[] args) {
        System.out.println("Topic Extraction Demo");
        ApplicationContext context = new AnnotationConfigApplicationContext("com.brandwatch.interviews.topic");
        TopicDemoConfig config = new TopicDemoConfig();
        new JCommander(config, args);
        Main main = context.getBean(Main.class);
        System.out.println(main.titleGenerator.buildTitle());
        main.demo.runDemo(config);
    }

}
