import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Vector;

public class WordTextFile {
    private Scanner scanner;
    private Vector<String> wordVector = new Vector<String>();

    public WordTextFile() {
        try {
            scanner = new Scanner(new FileReader("words.txt"));
            while(scanner.hasNext()) {
                String word = scanner.nextLine();
                wordVector.add(word);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Vector getV() {
        return wordVector;
    }
    public String getWord() { // 랜덤 단어 읽어오기
        int index = (int)(Math.random()*wordVector.size());
        return wordVector.get(index);
    }
}

