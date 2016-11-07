package com.brandwatch.interviews.topic.tokenizers;

import org.springframework.stereotype.Component;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ed on 07/11/2016.
 */
@Component
public class AsciiTokenizer implements Tokenizer {

    public TokenResults Tokenize(String source) {

        TokenResults tokenResults = new TokenResults();

        if (source != null && source.length() > 0) {

            ArrayList<String> sentences = sentences(source);

            for (String sentenceText : sentences) {

                Sentence sentence = new Sentence();
                StringBuilder sb = new StringBuilder(10);
                for (char ch : sentenceText.toCharArray() ) {

                    //characters a-z
                    if ((int)ch >= 97 && (int)ch < 123) {
                        sb.append(ch);
                    }

                    //characters A-Z
                    else if ((int)ch >= 65 && (int)ch < 91) {
                        sb.append(ch);
                    }

                    else if (sb.length() > 0) {
                        AddToSentence(sentence, sb);
                        sb.setLength(0);
                    }
                }

                if (sb.length() > 0) {
                    AddToSentence(sentence, sb);
                }

                if (sentence.tokenCount() > 0) {
                    tokenResults.addSentence(sentence);
                }
            }

        }

        return tokenResults;
    }

    private void AddToSentence(Sentence sentence, StringBuilder sb) {
        Token token = new Token();
        token.setLabel(sb.toString());
        sentence.addToken(token);
    }

    private ArrayList<String> sentences(String source) {

        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);

        iterator.setText(source);
        int start = iterator.first();
        ArrayList<String> res = new ArrayList<String>();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            res.add(source.substring(start, end).trim());
        }

        return res;
    }
}
