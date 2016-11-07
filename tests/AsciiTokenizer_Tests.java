import com.brandwatch.interviews.topic.tokenizers.AsciiTokenizer;
import com.brandwatch.interviews.topic.tokenizers.TokenResults;
import com.brandwatch.interviews.topic.tokenizers.Tokenizer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ed on 07/11/2016.
 */
public class AsciiTokenizer_Tests {

    @Test
    public void Single_sentence_four_words() {

        Tokenizer tokenizer = new AsciiTokenizer();
        TokenResults results = tokenizer.Tokenize("This is a test");
        assertNotNull(results);
        assertEquals(1, results.getSentences().size());
        assertEquals(4, results.getSentences().get(0).tokenCount());
    }

    @Test
    public void Single_sentence_four_words_terminating_white_space() {

        Tokenizer tokenizer = new AsciiTokenizer();
        TokenResults results = tokenizer.Tokenize("This is a test.   ");
        assertNotNull(results);
        assertEquals(1, results.getSentences().size());
        assertEquals(4, results.getSentences().get(0).tokenCount());
    }


    @Test
    public void Single_sentence_four_words_prefixed_white_space() {

        Tokenizer tokenizer = new AsciiTokenizer();
        TokenResults results = tokenizer.Tokenize("   This is a test");
        assertNotNull(results);
        assertEquals(1, results.getSentences().size());
        assertEquals(4, results.getSentences().get(0).tokenCount());
    }

    @Test
    public void Single_sentence_four_words_prefixed_numeric_chars_ignored() {

        Tokenizer tokenizer = new AsciiTokenizer();
        TokenResults results = tokenizer.Tokenize("1234This is a test");
        assertNotNull(results);
        assertEquals(1, results.getSentences().size());
        assertEquals(4, results.getSentences().get(0).tokenCount());
    }

    @Test
    public void Single_sentence_two_words_boundary_test_A_and_a() {

        Tokenizer tokenizer = new AsciiTokenizer();
        TokenResults results = tokenizer.Tokenize("A a");
        assertNotNull(results);
        assertEquals(1, results.getSentences().size());
        assertEquals(2, results.getSentences().get(0).tokenCount());
    }

    @Test
    public void Single_sentence_two_words_boundary_test_Z_and_z() {

        Tokenizer tokenizer = new AsciiTokenizer();
        TokenResults results = tokenizer.Tokenize("Z z");
        assertNotNull(results);
        assertEquals(1, results.getSentences().size());
        assertEquals(2, results.getSentences().get(0).tokenCount());
    }

    @Test
    public void Two_sentences_four_words_in_each() {

        Tokenizer tokenizer = new AsciiTokenizer();
        TokenResults results = tokenizer.Tokenize("This is a test. Get off my lawn");
        assertNotNull(results);
        assertEquals(2, results.getSentences().size());
        assertEquals(4, results.getSentences().get(0).tokenCount());
        assertEquals(4, results.getSentences().get(1).tokenCount());
    }
}
