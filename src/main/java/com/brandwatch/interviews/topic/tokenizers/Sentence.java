package com.brandwatch.interviews.topic.tokenizers;

import java.util.ArrayList;

/**
 * Created by ed on 07/11/2016.
 */
public class Sentence {
    private ArrayList<Token> tokens = new ArrayList<Token>();

    public void addToken(Token token) {

        tokens.add(token);
    }

    public int tokenCount() {

        return tokens.size();
    }

    public ArrayList<Token> getTokens() {

        return tokens;
    }
}
