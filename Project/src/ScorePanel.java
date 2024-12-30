import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
public class ScorePanel extends JPanel {
    private FileWriter fout = null;
    private int score = 0; // 점수 초기화
    private JLabel scoreLabel = new JLabel(Integer.toString(score)); //점수판
    private HashMap<Integer, String> scoreMap = new HashMap<Integer, String>();
    private Vector<Integer> v = new Vector<Integer>();

    private JLabel ScoreLabelTitle = new JLabel("SCORE");
    private JTextField savePlayerName = new JTextField(); //플레이어 이름 저장하는 공간
    private JLabel finishLabel = new JLabel("종료 후 이름입력");
    private JLabel [] rankLabel = new JLabel[5]; //순위 표시
    private JLabel [] playerName = new JLabel[5]; // 순위별 닉네임
    private JLabel [] rankScoreLabel = new JLabel[5]; // 순위별 점수
    public ScorePanel() {
        setLayout(null);
        ScoreLabelTitle.setSize(100, 30);
        ScoreLabelTitle.setLocation(70, 15);
        ScoreLabelTitle.setFont(new Font("Gothic", Font.BOLD, 25));
        add(ScoreLabelTitle);
        scoreLabel.setSize(100, 30);
        scoreLabel.setLocation(200, 15);
        scoreLabel.setFont(new Font("Gothic", Font.BOLD, 25));
        add(scoreLabel);

        rankLabel[0] = new JLabel("1st");
        rankLabel[0].setSize(100, 30);
        rankLabel[0].setLocation(30, 70);
        rankLabel[0].setFont(new Font("Gothic", Font.BOLD, 25));
        add(rankLabel[0]);
        for(int i=1; i<rankLabel.length; i++) {
            rankLabel[i] = new JLabel((i+1) + "th");
            rankLabel[i].setSize(100, 30);
            rankLabel[i].setLocation(30, (i*50) + 70);
            rankLabel[i].setFont(new Font("Gothic", Font.BOLD, 25));
            add(rankLabel[i]);
        }
        getPlayerScore();
        compareScore();
        for(int i=0; i<playerName.length; i++) {
            playerName[i] = new JLabel(scoreMap.get(v.get(i)));
            playerName[i].setSize(100, 30);
            playerName[i].setLocation(100, (i*50) + 70);
            playerName[i].setFont(new Font("Gothic", Font.BOLD, 25));
            add(playerName[i]);
        }
        for(int i=0; i<rankLabel.length; i++) {
            rankScoreLabel[i] = new JLabel(Integer.toString(v.get(i)));
            rankScoreLabel[i].setSize(100, 20);
            rankScoreLabel[i].setLocation(220, (i*50) + 73);
            rankScoreLabel[i].setFont(new Font("Gothic", Font.BOLD, 25));
            add(rankScoreLabel[i]);
        }

        finishLabel.setSize(200, 30);
        finishLabel.setLocation(72, 320);
        finishLabel.setFont(new Font("Gothic", Font.BOLD, 20));
        add(finishLabel);
        savePlayerName.setSize(200, 30);
        savePlayerName.setLocation(50, 350);
        add(savePlayerName);

        savePlayerName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextField t = (JTextField)e.getSource();
                String name = t.getText();
                saveScore(name);
                initScore();
                t.setText("");
                getPlayerScore(); // 이름을 입력하면 다시 플레이어 정보를 읽어옴
            }
        });
    }
    public void initScore() {
        score = 0;
        scoreLabel.setText(Integer.toString(score));
    }
    public void increase() {
        score += 10;
        scoreLabel.setText(Integer.toString(score));
    }
    public void decrease() {
        score -= 10;
        scoreLabel.setText(Integer.toString(score));
    }
    public void saveScore(String playerName) {
        try {
            File scoreFile = new File("C:\\김소진\\2학년 2학기\\객체지향언어2 황기태교수님\\MiniProject\\score.txt");
            Scanner scanner = new Scanner(scoreFile);
            StringBuilder updatedContent = new StringBuilder();

            boolean nameExists = false; // 이름이 이미 존재하는지 

            while (scanner.hasNext()) {
                String name = scanner.next();
                int oldScore = scanner.nextInt();

                if (name.equals(playerName)) {
                    nameExists = true;
                    if (score > oldScore) {
                        // 현재 점수가 더 높으면 갱신
                        updatedContent.append(playerName).append(" ").append(score).append("\r\n");
                    } else {
                        // 현재 점수가 낮으면 기존 점수를 유지
                        updatedContent.append(name).append(" ").append(oldScore).append("\r\n");
                    }
                } else {
                    updatedContent.append(name).append(" ").append(oldScore).append("\r\n");
                }
            }

            scanner.close();

            if (!nameExists) {
                // 새로운 이름인 경우 추가
                updatedContent.append(playerName).append(" ").append(score).append("\r\n");
            }

            // 파일에 쓰기
            FileWriter fout = new FileWriter(scoreFile, false);
            fout.write(updatedContent.toString());
            fout.close();
        } catch (IOException e) {
            System.out.println("입출력 오류 발생");
        }
    }

    public void compareScore() { // 점수 비교 함수
        for(int i=0; i<v.size(); i++) {
            for(int j=0; j<i; j++) {
                if(v.get(i) > v.get(j)) {
                    v.add(j, v.get(i));
                    v.remove(i+1);
                }
            }
        }
    }
    public void getPlayerScore() {
        scoreMap.clear(); // 이미 있는 데이터를 중복해서 읽지 않도록 맵 초기화
        v.clear(); // 이미 있는 데이터를 중복해서 읽지 않도록 벡터를 초기화

        Scanner scanner;
        try {
            scanner = new Scanner(new FileReader(new File("C:\\김소진\\2학년 2학기\\객체지향언어2 황기태교수님\\MiniProject\\score.txt")));
            while(scanner.hasNext()) {
                String name = scanner.next();
                int score = scanner.nextInt();
                scoreMap.put(score, name);
            }
            scanner.close();
        }
        catch(IOException e){
            return;
        }
        Set<Integer> keys = scoreMap.keySet();
        Iterator<Integer> it = keys.iterator();
        while(it.hasNext()) {
            int score = it.next();
            v.add(score);
        }
    }
}