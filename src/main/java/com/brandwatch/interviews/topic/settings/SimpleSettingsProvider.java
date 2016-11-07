package com.brandwatch.interviews.topic.settings;

/**
 * Created by ed on 07/11/2016.
 */
public class SimpleSettingsProvider implements SettingsProvider {

    private final String blogPath = "/Users/ed/Documents/_git/topic-interview-questions/src/main/resources/blog.txt";
    private final String stoplistPath = "/Users/ed/Documents/_git/topic-interview-questions/src/main/resources/stoplist.txt";
    private final String synonymPath = "/Users/ed/Documents/_git/topic-interview-questions/src/main/resources/synonyms.txt";

    public String getBlogPath() {
        return blogPath;
    }

    public String getStoplistPath() {
        return stoplistPath;
    }

    public String getSynonymPath() {
        return synonymPath;
    }
}
