package com.brandwatch.interviews.topic.textproviders;

import java.io.IOException;

/**
 * Created by ed on 07/11/2016.
 */
public interface TextProvider {
    String readText(String file) throws IOException;
    String[] readTextLines(String file) throws IOException;
}
