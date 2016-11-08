import com.brandwatch.interviews.topic.filters.SynonymFilter;
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
public class SynonymFilter_Tests {

    @Mock(name="textProvider")
    TextProvider textProvider;

    @Mock(name="settingsProvider")
    SettingsProvider settingsProvider;

    @InjectMocks
    SynonymFilter synonymFilter;

    @Before
    public void Setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void Single_word_sentence_with_synonym() throws IOException {

        Mockito.when(settingsProvider.getSynonymPath()).thenReturn("synonyms.txt");

        //Mock your objects like other "normally" mocked objects
        Mockito.when(textProvider.readTextLines("synonyms.txt")).thenReturn(new String[] {
                "a=b"
        });

        TokenResults res = new TokenResults();
        res.addSentence(new Sentence()
        {{
            addToken(new Token() {{ setLabel("A"); }});
        }});

        synonymFilter.Filter(res);

        assertEquals(1, res.getSentences().size());
        assertEquals(1, res.getSentences().get(0).getTokens().stream().filter(x -> x.getLabel().equals("b")).count());
    }

    @Test
    public void Four_word_sentence_with_synonym() throws IOException {

        Mockito.when(settingsProvider.getSynonymPath()).thenReturn("synonyms.txt");

        //Mock your objects like other "normally" mocked objects
        Mockito.when(textProvider.readTextLines("synonyms.txt")).thenReturn(new String[] {
                "test=banana"
        });

        TokenResults res = new TokenResults();
        res.addSentence(new Sentence()
        {{
            addToken(new Token() {{ setLabel("This"); }});
            addToken(new Token() {{ setLabel("is"); }});
            addToken(new Token() {{ setLabel("a"); }});
            addToken(new Token() {{ setLabel("test"); }});
        }});

        synonymFilter.Filter(res);

        assertEquals(1, res.getSentences().size());
        assertEquals(4, res.getSentences().get(0).tokenCount());
        assertEquals(1, res.getSentences().get(0).getTokens().stream().filter(x -> x.getLabel().equals("banana")).count());
    }

    @Test
    public void Two_four_word_sentences_with_synonyms() throws IOException {

        Mockito.when(settingsProvider.getSynonymPath()).thenReturn("synonyms.txt");

        //Mock your objects like other "normally" mocked objects
        Mockito.when(textProvider.readTextLines("synonyms.txt")).thenReturn(new String[] {
                "test=banana",
                "hat=coat"
        });

        TokenResults res = new TokenResults();
        res.addSentence(new Sentence()
        {{
            addToken(new Token() {{ setLabel("This"); }});
            addToken(new Token() {{ setLabel("is"); }});
            addToken(new Token() {{ setLabel("a"); }});
            addToken(new Token() {{ setLabel("test"); }});
        }});

        res.addSentence(new Sentence()
        {{
            addToken(new Token() {{ setLabel("The"); }});
            addToken(new Token() {{ setLabel("man"); }});
            addToken(new Token() {{ setLabel("who"); }});
            addToken(new Token() {{ setLabel("thought"); }});
            addToken(new Token() {{ setLabel("his"); }});
            addToken(new Token() {{ setLabel("wife"); }});
            addToken(new Token() {{ setLabel("was"); }});
            addToken(new Token() {{ setLabel("a"); }});
            addToken(new Token() {{ setLabel("hat"); }});
        }});

        synonymFilter.Filter(res);

        assertEquals(2, res.getSentences().size());

        assertEquals(4, res.getSentences().get(0).tokenCount());
        assertEquals(1, res.getSentences().get(0).getTokens().stream().filter(x -> x.getLabel().equals("banana")).count());
        assertEquals(0, res.getSentences().get(0).getTokens().stream().filter(x -> x.getLabel().equals("test")).count());

        assertEquals(9, res.getSentences().get(1).tokenCount());
        assertEquals(1, res.getSentences().get(1).getTokens().stream().filter(x -> x.getLabel().equals("coat")).count());
        assertEquals(0, res.getSentences().get(1).getTokens().stream().filter(x -> x.getLabel().equals("hat")).count());

    }
}
