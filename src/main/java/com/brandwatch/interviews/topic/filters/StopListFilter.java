package com.brandwatch.interviews.topic.filters;

import com.brandwatch.interviews.topic.settings.SettingsProvider;
import com.brandwatch.interviews.topic.textproviders.TextProvider;
import com.brandwatch.interviews.topic.tokenizers.Sentence;
import com.brandwatch.interviews.topic.tokenizers.Token;
import com.brandwatch.interviews.topic.tokenizers.TokenResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ed on 07/11/2016.
 */
@Component(value="stoplistfilter")
public class StopListFilter implements Filter {

    @Autowired
    private TextProvider textProvider;

    @Autowired
    private SettingsProvider settingsProvider;

    private List<String> stopList;

    public StopListFilter() {

    }

    private synchronized void LoadStopList() {

        if (stopList == null) {
            try {
                stopList = Arrays.asList(textProvider.readTextLines(settingsProvider.getStoplistPath()));
            } catch (IOException e) {
                System.err.println("Failed");
            }
        }
    }

    public void Filter(TokenResults tokenResults) {

        LoadStopList();

        if (tokenResults != null) {

            Iterator<Sentence> sentenceIterator = tokenResults.getSentences().iterator();
            while (sentenceIterator.hasNext()) {

                Sentence sentence = sentenceIterator.next();
                Iterator<Token> tokenIterator = sentence.getTokens().iterator();
                while( tokenIterator.hasNext()) {

                    Token nextToken = tokenIterator.next();
                    if (this.InStoplist(nextToken)) {
                        tokenIterator.remove();
                    }
                }

                if (sentence.tokenCount() == 0)
                    sentenceIterator.remove();
            }
        }
    }

    private boolean InStoplist(Token token) {

        if (token != null) {
            String tokenLabel = token.getLabel().toLowerCase();
            return stopList.stream().filter(x -> x.equals(tokenLabel)).count() == 1;
        }

        return false;
    }
}
