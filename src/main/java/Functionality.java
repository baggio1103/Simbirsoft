import org.jsoup.Jsoup;import org.jsoup.nodes.Document;import java.io.*;import java.sql.SQLException;import java.util.*;public class Functionality {    public static char[] chars = {' ',  ',', '.', '!', '?', '»', '«', '"', ';', ':', ']', '[', '(', ')', '\n', '\r', '\t'};    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));    public static void start() throws IOException, SQLException {        Document document = loadPage();        System.out.println("Type the name of a file to save an html page: ");        String fileName = reader.readLine();        String html = document.html();        save(html, fileName); //saving a web-page in the local memory        String text = document.text(); // document.text() extracts text from a web-page        List<String> words = splitIntoWords(text); //splitting the text into separate words        convertToUpperCase(words); // converting all the words to upperCase format        Map<String, Integer> wordCount = countWords(words);        print(wordCount); // printing output:)        System.out.println("\n--------------------------------------------------");        System.out.println("Would you like to save statistics into the Database?" +                "\nType \"Yes\" , if you'd like");        if ((reader.readLine()).equalsIgnoreCase("yes")){            Database.saveIntoDatabase(wordCount);        }    }    public static Document loadPage() throws IOException {        String urlAddress;        System.out.println("Please type only domain address without \"https://www.\" \ne.g. udacity.com"+"\nThe url address: ");        String url;        Document document = null;        while (document == null){            try {                urlAddress = reader.readLine();                url = "https://www."+urlAddress+"/";                document = Jsoup.connect(url).get();            } catch (IOException e) {                System.out.println("Wrong URL Address!\ne.g. simbirsoft.com\n Please type again:");            }        }        return document;    }    /**     * save() - saves a given html-pages into some file     * */    public static void save(String string, String path) throws IOException {        System.out.println("Type \"yes\" to save as html format, otherwise as txt format ");        if ((reader.readLine()).equalsIgnoreCase("yes")){            path += ".html";        }else {            path += ".txt";        }        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));        writer.write(string);        writer.flush();        writer.close();    }    /**     * SplitIntoWords()  accepts a text as an argument and splits the text into words     * and returns a List of Strings     * */    public static List<String> splitIntoWords(String text){        List<String> words = new ArrayList<String>();        String temp = "";        for (int i = 0; i < text.length(); i++) {            if (!contains(text.charAt(i))) {                temp += text.charAt(i);            } else if (!temp.equals("")){                words.add(temp);                temp = "";            }        }        if (!temp.equals("")) {            words.add(temp);        }        return words;    }    /**     *Contains(char) accepts a some char as an argument and checks     * whether charset contains the  given  char     * */    public static boolean contains(char ch){        for (char aChar : chars) {            if (aChar == ch) {                return true;            }        }        return false;    }    /**     * convertToUpperCase() - an additional method that turn     * all the strings in the List to UpperCase     * It facilitates word counting     * i.e. "best" and "Best" are the same words but have different encodings     * therefore to make them have the same encoding     * */    public static void convertToUpperCase(List<String> list){        String temp;        for (int i = 0; i < list.size(); i++){            temp = list.get(i);            list.set(i, temp.toUpperCase());        }    }    /**     * countWords() - accepts a List of strings as an argument and     * counts how many times each string is used in the List     * return a Map as a return variable     * */    public static Map<String, Integer> countWords(List<String> list){        Map<String, Integer> map = new HashMap<String, Integer>();        int times = 1;        String string = "";        for (int i = 0; i < list.size(); i++){            string = list.get(i);            for (int j = 0; j < list.size(); j++){                if (i != j && string.equals(list.get(j))){                    times++;                }            }            if (!map.containsKey(list.get(i))){                map.put(string, times);            }            times = 1;        }        return map;    }    /**     * Print(Map) - an additional function that accepts a map as an     * argument and prints its content     * p.s. in our case it prints each word and its occurrence time     * */    public static void print(Map<String, Integer> map){        System.out.println("Output:");        for (Map.Entry<String, Integer> entry : map.entrySet()){            System.out.println(entry.getKey() + " - " + entry.getValue());        }    }}