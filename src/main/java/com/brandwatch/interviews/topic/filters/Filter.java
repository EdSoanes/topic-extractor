package com.brandwatch.interviews.topic.filters;

import com.brandwatch.interviews.topic.tokenizers.TokenResults;

/**
 * Created by ed on 07/11/2016.
 */
public interface Filter {

    void Filter(TokenResults tokenResults);
}
