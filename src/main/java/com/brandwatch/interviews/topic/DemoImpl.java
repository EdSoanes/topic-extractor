package com.brandwatch.interviews.topic;

import com.brandwatch.interviews.topic.filters.Filter;
import com.brandwatch.interviews.topic.settings.SettingsProvider;
import com.brandwatch.interviews.topic.textproviders.TextProvider;
import com.brandwatch.interviews.topic.tokenizers.TokenResults;
import com.brandwatch.interviews.topic.tokenizers.Tokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class DemoImpl implements Demo {

    @Autowired
    private SettingsProvider settingsProvider;

    @Autowired
    private TextProvider provider;

    @Autowired
    private Tokenizer tokenizer;

    @Autowired
    @Qualifier("stoplistfilter")
    private Filter stopListFilter;

    @Autowired
    @Qualifier("synonymfilter")
    private Filter synonymFilter;

    public void runDemo(TopicDemoConfig config) {
        try {

            //Load the text
            String inputText = provider.readText(settingsProvider.getBlogPath());

            //Tokenize
            TokenResults tokenResults = tokenizer.Tokenize(inputText);

            //Filter the results
            List<Filter> filters = Arrays.asList(stopListFilter, synonymFilter);
            for (Filter filter : filters)
                filter.Filter(tokenResults);

            //TopicResults results = extractor.extract(inputText);
            //printer.print(results);
        } catch (IOException e) {
            System.err.println("Failed");
        }
    }
}
