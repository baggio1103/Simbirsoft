import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FunctionalityTest {

    public static String html =
            "<html>" +
            "<head>" +
            "<title>Birthday html page</title>" +
            "</head>" +
            "<body>" +
            "<p>Parsed HTML into a doc.</p>" +
            "<h1>Happy birthday to you</h1>"+
            "<h2>Happy birthday to you, Mary!</h2>"+
            "<h3>We love you very much!</h3>"+
            "</body>" +
            "</html>";

    @Test
    public void test() {
        Document document = Jsoup.parse(html);
        String text = document.text();
        List<String>words = Functionality.splitIntoWords(text);

        Functionality.convertToUpperCase(words);

        String[] strings = {"BIRTHDAY", "HTML", "PAGE", "PARSED", "INTO", "A", "DOC", "HAPPY", "TO", "YOU", "MARY", "WE", "LOVE", "VERY", "MUCH"};

        List<String> list = Arrays.asList(strings);

        for (String string : words){
            assertTrue(list.contains(string));
        }

        Map<String, Integer> wordCount = Functionality.countWords(words);

        assertEquals(15, wordCount.size());

        assertEquals(Integer.valueOf(3), wordCount.get("BIRTHDAY"));
        assertEquals(Integer.valueOf(2), wordCount.get("HTML"));
        assertEquals(Integer.valueOf(1), wordCount.get("PAGE"));
        assertEquals(Integer.valueOf(1), wordCount.get("PARSED"));
        assertEquals(Integer.valueOf(1), wordCount.get("INTO"));
        assertEquals(Integer.valueOf(1), wordCount.get("A"));
        assertEquals(Integer.valueOf(1), wordCount.get("DOC"));
        assertEquals(Integer.valueOf(2), wordCount.get("HAPPY"));
        assertEquals(Integer.valueOf(2), wordCount.get("TO"));
        assertEquals(Integer.valueOf(3), wordCount.get("YOU"));
        assertEquals(Integer.valueOf(1), wordCount.get("MARY"));
        assertEquals(Integer.valueOf(1), wordCount.get("WE"));
        assertEquals(Integer.valueOf(1), wordCount.get("LOVE"));
        assertEquals(Integer.valueOf(1), wordCount.get("VERY"));
        assertEquals(Integer.valueOf(1), wordCount.get("MUCH"));

        int count = 0;
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()){
            count += entry.getValue();
        }

        //words - list of words that we get after splitting the text into words
        //count - overall amount of words that get after summing each word occurrence
        //e.g. BIRTHDAY - 3, YOU - 2, ... -> count = 3 + 2 ...;
        assertEquals(count, words.size());

        Functionality.print(wordCount);

    }

}