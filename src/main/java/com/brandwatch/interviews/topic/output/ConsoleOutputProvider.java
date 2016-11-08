package com.brandwatch.interviews.topic.output;

import com.brandwatch.interviews.topic.tokenizers.Sentence;
import com.brandwatch.interviews.topic.tokenizers.Token;
import com.brandwatch.interviews.topic.tokenizers.TokenResults;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import org.springframework.stereotype.Component;

/**
 * Created by ed on 08/11/2016.
 */
@Component
public class ConsoleOutputProvider implements OutputProvider {

    public void Output(TokenResults tokenResults) {

        Multiset<Token> tokens = HashMultiset.create();

        for (Sentence sentence: tokenResults.getSentences()) {
            sentence.getTokens().forEach(x -> tokens.add(x));
        }

        for (Token token : Iterables.limit(Multisets.copyHighestCountFirst(tokens).elementSet(), 10)) {
            System.out.println(token.getLabel() + " " + tokens.count(token));
        }
    }
}
