import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class GamePanel extends JPanel {
    private ImageIcon background = new ImageIcon("background.jpg");
    private Image backgroundImage = background.getImage();
    private ImageIcon lifeImg = new ImageIcon("life.png");

    private JTextField wordField = new JTextField(20); // 단어 입력창
    private JLabel [] label = new JLabel[1000]; // 내려올 단어
    private JLabel [] lifeLabel = new JLabel[5]; // 생명
    private static int life = 5; // 생명 개수
    private WordTextFile wordVector = new WordTextFile();

    private GroundPanel groundPanel = null;
    private ScorePanel scorePanel = null;
    private EditPanel editPanel = null;
    private InputPanel inputPanel = null;

    private WordThread makeWord = null; // 랜덤 단어 생성 스레드
    private FallingThread [] fallingWord = new FallingThread[100]; // 단어 떨어지도록 하는 스레드
    private static int wordCount;
    private int speed;

    public GamePanel(ScorePanel scorePanel, EditPanel editPanel) {
        this.scorePanel = scorePanel;
        this.editPanel = editPanel;

        for(int i=0; i<lifeLabel.length; i++) {
            lifeLabel[i] = new JLabel(lifeImg);
        }

        initText(); // 초기화

        groundPanel = new GroundPanel();
        inputPanel = new InputPanel();
        setLayout(new BorderLayout());
        add(groundPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // 단어 입력창 리스너
        wordField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextField tf = (JTextField)(e.getSource());
                for(int i=0; i<wordCount; i++) {
                    if(label[i].getText().equals(tf.getText())) { // 단어를 맞추면
                        groundPanel.remove(label[i]); // 맞춘 단어 제거
                        fallingWord[i].interrupt();
                        groundPanel.repaint();
                        scorePanel.increase();
                        tf.setText(""); // 텍스트 창 초기화
                    }
                }
                tf.setText(""); // 틀렸을 때도 텍스트 창 초기화
            }
        });
    }
    public void initText() { // 초기화
        for(int i=0; i<label.length; i++) {
            label[i] = new JLabel(wordVector.getWord());
        }
    }
    class GroundPanel extends JPanel {
        public GroundPanel() {
            setLayout(null);
        }
        public void paintComponent(Graphics g) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), groundPanel);
        }
    }
    class InputPanel extends JPanel {
        public InputPanel() {
            setLayout(new FlowLayout());
            for(int i=0; i<lifeLabel.length; i++) {
                add(lifeLabel[i]);
            }
            add(wordField);
        }
        public void initInput() {
            for(int i=0; i<lifeLabel.length; i++) {
                lifeLabel[i].setVisible(true);
            }
            add(wordField);
        }

        //목숨이 줄어들 때 하트 변화를 보여주는 함수
        public void changeLife() {
            switch(life) {
                case 4:
                    lifeLabel[4].setVisible(false); // 마지막 하트 숨기기
                    inputPanel.repaint();
                    break;
                case 3:
                    lifeLabel[3].setVisible(false);
                    inputPanel.repaint();
                    break;
                case 2:
                    lifeLabel[2].setVisible(false);
                    inputPanel.repaint();
                    break;
                case 1:
                    lifeLabel[1].setVisible(false);
                    inputPanel.repaint();
                    break;
                case 0:
                    lifeLabel[0].setVisible(false);
                    inputPanel.repaint();
                    makeWord.interrupt();
                    for(int i=0; i<wordCount; i++) {
                        fallingWord[i].interrupt();
                    }
                    stopGame();
                    groundPanel.removeAll();
                    groundPanel.repaint();
            }
        }
    }
    public void level1Game() {
        wordCount = 0;
        speed = 10;
        makeWord = new WordThread(inputPanel, speed);
        makeWord.start();
    }
    public void level2Game() {
        wordCount = 0;
        speed = 20;
        makeWord = new WordThread(inputPanel, speed);
        makeWord.start();
    }
    public void level3Game() {
        wordCount = 0;
        speed = 30;
        makeWord = new WordThread(inputPanel, speed);
        makeWord.start();
    }
    public void stopGame() {
        makeWord.interrupt();
        for(int i=0; i < wordCount; i++) {
            fallingWord[i].interrupt();
        }
        groundPanel.removeAll();
        inputPanel.initInput();
        life = 5;
        initText();
        groundPanel.repaint();
    }
    class WordThread extends Thread {
        private int speed;
        private InputPanel inputPanel;
        public WordThread(InputPanel inputPanel, int speed) {
            this.inputPanel = inputPanel;
            this.speed = speed;
        }
        public void run() {
            while(true) {
                if(wordCount != 0) {
                    for(int i=wordCount; i<label.length; i++) {
                        try {
                            sleep(2000);
                        }
                        catch(InterruptedException e) {
                            return;
                        }
                        label[i].setSize(200, 30);
                        label[i].setLocation((int)(Math.random()*8)*100, 10);
                        label[i].setForeground(Color.BLACK);
                        label[i].setFont(new Font("Gothic", Font.BOLD, 25));
                        groundPanel.add(label[i]);
                        fallingWord[i] = new FallingThread(groundPanel, label[i], speed);
                        fallingWord[i].start();
                        wordCount++;
                        groundPanel.repaint();
                    }
                }
                for(int i=0; i<label.length; i++) {
                    try {
                        sleep(1000);
                    }
                    catch(InterruptedException e) {
                        return;
                    }
                    label[i].setSize(200, 30);
                    label[i].setLocation((int)(Math.random()*8)*100, 10);
                    label[i].setForeground(Color.BLACK);
                    label[i].setFont(new Font("Gothic", Font.BOLD, 25));
                    groundPanel.add(label[i]);
                    fallingWord[i] = new FallingThread(groundPanel, label[i], speed);
                    fallingWord[i].start();
                    wordCount++;
                    groundPanel.repaint();
                }
            }
        }
    }
    class FallingThread extends Thread {
        private GroundPanel groundPanel;
        private JLabel label;
        private int speed;
        public FallingThread(GroundPanel groundPanel, JLabel label, int speed) {
            this.groundPanel = groundPanel;
            this.label = label;
            this.speed = speed;
        }
        public void run() {
            while(true) {
                try {
                    sleep(1000);
                }
                catch(InterruptedException e) {
                    return;
                }
                int y = label.getY() + 10;
                if(y >= groundPanel.getHeight() - label.getHeight() - 20) {
                    label.setText("");
                    groundPanel.repaint();
                    life--;
                    inputPanel.changeLife();
                    scorePanel.decrease();
                    break;
                }
                label.setLocation(label.getX(), label.getY() + speed);
                groundPanel.repaint();
            }
        }
    }
}