package com.brandwatch.interviews.topic.output;

import com.brandwatch.interviews.topic.tokenizers.TokenResults;

/**
 * Created by ed on 08/11/2016.
 */
public interface OutputProvider {

    void Output(TokenResults tokenResults);
}
