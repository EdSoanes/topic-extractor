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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ed on 07/11/2016.
 */
@Component(value="synonymfilter")
public class SynonymFilter implements Filter {

    @Autowired
    private TextProvider textProvider;

    @Autowired
    private SettingsProvider settingsProvider;

    private HashMap<String, String> synonyms;

    private synchronized void LoadSynonyms() {
        try {
            if (synonyms == null)  {

                synonyms = new HashMap<>();
                List<String> synonymValues = Arrays.asList(textProvider.readTextLines(settingsProvider.getSynonymPath()));
                for (String synonymValue : synonymValues) {

                    String[] values = synonymValue.split("=");
                    if (!synonyms.containsKey(values[0].toLowerCase())) {
                        synonyms.put(values[0].toLowerCase(), values[1].toLowerCase());
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Failed");
        }
    }

    public void Filter(TokenResults tokenResults) {

        LoadSynonyms();

        if (tokenResults != null) {
            for (Sentence sentence : tokenResults.getSentences()) {

                Iterator<Token> iterator = sentence.getTokens().iterator();
                while( iterator.hasNext()) {

                    Token nextToken = iterator.next();
                    String translated = Translate(nextToken);
                    if (translated.equals(""))
                        iterator.remove();
                    else
                        nextToken.setLabel(translated);
                }
            }
        }
    }

    private String Translate(Token token) {

        if (token != null) {

            String key = token.getLabel().toLowerCase();
            String translation = token.getLabel();
            if (synonyms.containsKey(key))
                translation = synonyms.get(key);

            return translation;
        }

        return "";
    }
}
