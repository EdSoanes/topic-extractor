package com.brandwatch.interviews.topic.tokenizers;

import java.util.ArrayList;


/**
 * Created by ed on 07/11/2016.
 */
public class TokenResults {
    private ArrayList<Sentence> sentences = new ArrayList<Sentence>();

    public void addSentence(Sentence sentence) {
        sentences.add(sentence);
    }

    public ArrayList<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(ArrayList<Sentence> sentences) {
        this.sentences = sentences;
    }
}
