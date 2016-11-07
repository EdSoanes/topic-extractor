import com.brandwatch.interviews.topic.filters.StopListFilter;
import com.brandwatch.interviews.topic.settings.SettingsProvider;
import com.brandwatch.interviews.topic.textproviders.TextProvider;
import com.brandwatch.interviews.topic.tokenizers.Sentence;
import com.brandwatch.interviews.topic.tokenizers.Token;
import com.brandwatch.interviews.topic.tokenizers.TokenResults;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by ed on 07/11/2016.
 */
public class StopListFilter_Tests {

    @Mock(name="textProvider")
    TextProvider textProvider;

    @Mock(name="settingsProvider")
    SettingsProvider settingsProvider;

    @InjectMocks
    StopListFilter stopListFilter;

    @Before
    public void Setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void Single_word_sentence_with_word_removed() throws IOException {

        Mockito.when(settingsProvider.getStoplistPath()).thenReturn("stoplist.txt");

        //Mock your objects like other "normally" mocked objects
        Mockito.when(textProvider.readTextLines("stoplist.txt")).thenReturn(new String[] {
            "a"
        });

        TokenResults res = new TokenResults();
        res.addSentence(new Sentence()
        {{
            addToken(new Token() {{ setLabel("A"); }});
        }});

        stopListFilter.Filter(res);

        assertEquals(0, res.getSentences().size());
    }

    @Test
    public void Multi_word_sentence_with_word_a_removed() throws IOException {

        Mockito.when(settingsProvider.getStoplistPath()).thenReturn("stoplist.txt");

        //Mock your objects like other "normally" mocked objects
        Mockito.when(textProvider.readTextLines("stoplist.txt")).thenReturn(new String[] {
            "a"
        });

        TokenResults res = new TokenResults();
        res.addSentence(new Sentence()
        {{
            addToken(new Token() {{ setLabel("This"); }});
            addToken(new Token() {{ setLabel("is"); }});
            addToken(new Token() {{ setLabel("a"); }});
            addToken(new Token() {{ setLabel("cat"); }});
        }});

        stopListFilter.Filter(res);

        assertEquals(1, res.getSentences().size());
        assertEquals(3, res.getSentences().get(0).tokenCount());
        assertEquals(0, res.getSentences().stream().filter(x -> x.equals("a")).count());
    }

    @Test
    public void Two_multi_word_sentences_with_word_a_removed() throws IOException {

        Mockito.when(settingsProvider.getStoplistPath()).thenReturn("stoplist.txt");

        //Mock your objects like other "normally" mocked objects
        Mockito.when(textProvider.readTextLines("stoplist.txt")).thenReturn(new String[] {
                "a"
        });

        TokenResults res = new TokenResults();
        res.addSentence(new Sentence()
        {{
            addToken(new Token() {{ setLabel("This"); }});
            addToken(new Token() {{ setLabel("is"); }});
            addToken(new Token() {{ setLabel("a"); }});
            addToken(new Token() {{ setLabel("cat"); }});
        }});

        res.addSentence(new Sentence()
        {{
            addToken(new Token() {{ setLabel("A"); }});
            addToken(new Token() {{ setLabel("small"); }});
            addToken(new Token() {{ setLabel("banana"); }});
        }});

        stopListFilter.Filter(res);

        assertEquals(2, res.getSentences().size());
        assertEquals(3, res.getSentences().get(0).tokenCount());
        assertEquals(2, res.getSentences().get(1).tokenCount());
        assertEquals(0, res.getSentences().stream().filter(x -> x.equals("a")).count());
    }

    @Test
    public void Two_sentences_with_word_a_removed_one_sentence_removed() throws IOException {

        Mockito.when(settingsProvider.getStoplistPath()).thenReturn("stoplist.txt");

        //Mock your objects like other "normally" mocked objects
        Mockito.when(textProvider.readTextLines("stoplist.txt")).thenReturn(new String[] {
                "a"
        });

        TokenResults res = new TokenResults();
        res.addSentence(new Sentence()
        {{
            addToken(new Token() {{ setLabel("This"); }});
            addToken(new Token() {{ setLabel("is"); }});
            addToken(new Token() {{ setLabel("a"); }});
            addToken(new Token() {{ setLabel("cat"); }});
        }});

        res.addSentence(new Sentence()
        {{
            addToken(new Token() {{ setLabel("a"); }});
        }});

        stopListFilter.Filter(res);

        assertEquals(1, res.getSentences().size());
        assertEquals(3, res.getSentences().get(0).tokenCount());
        assertEquals(0, res.getSentences().stream().filter(x -> x.equals("a")).count());
    }
}
