import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private JButton startBtn = new JButton("start"); //시작버튼
    private JButton stopBtn = new JButton("stop"); // 중단버튼
    private JButton level1Btn = new JButton("Lv1"); // 1레벨
    private JButton level2Btn = new JButton("Lv2"); // 2레벨
    private JButton level3Btn = new JButton("Lv3"); // 3레벨
    private JButton exitBtn = new JButton("exit"); // 종료버튼

    private ScorePanel scorePanel = new ScorePanel();
    private EditPanel editPanel = new EditPanel();
    private GamePanel gamePanel = new GamePanel(scorePanel, editPanel);
    private SoundManager soundManager; // SoundManager
    public GameFrame() {
        setTitle("뽀로로 마을에 크리스마스가 왔어요");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        makeSplitPane();
        makeToolBar();
        setResizable(false);

        soundManager = new SoundManager("jingle-bells.wav"); // 음악 파일
        soundManager.play(); // 프레임이 생성될 때 음악 재생

        setVisible(true);
    }

    private void makeToolBar() {
        JToolBar tBar = new JToolBar();
        tBar.add(startBtn);
        tBar.add(stopBtn);
        tBar.add(level1Btn);
        tBar.add(level2Btn);
        tBar.add(level3Btn);
        tBar.add(exitBtn);
        getContentPane().add(tBar, BorderLayout.NORTH);

        // start 버튼을 누르면 lv1 게임 실행
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.level1Game();
            }
        });
        // stop 버튼 누르면
        stopBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.stopGame(); // 게임 멈춤
                scorePanel.initScore(); // 점수 초기화
            }
        });
        // lv1 버튼 누르면
        level1Btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.level1Game();
            } // lv1 게임 실행
        });
        // lv2 버튼 누르면
        level2Btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.level2Game();
            } // lv2 게임 실행
        });
        // lv3 버튼 누르면
        level3Btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.level3Game();
            } // lv3 게임 실행
        });
        // 나가기 버튼 누르면
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //soundManager.stop(); // 음악 멈춤
                System.exit(0);
            }
        });
    }
    private void makeSplitPane() {
        JSplitPane hPane = new JSplitPane();
        getContentPane().add(hPane, BorderLayout.CENTER);
        hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        hPane.setDividerLocation(850);
        hPane.setEnabled(false);
        hPane.setLeftComponent(gamePanel);

        JSplitPane vPane = new JSplitPane();
        vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        vPane.setDividerLocation(400);
        vPane.setEnabled(false);
        vPane.setTopComponent(scorePanel);
        vPane.setBottomComponent(editPanel);
        hPane.setRightComponent(vPane);
    }
}

