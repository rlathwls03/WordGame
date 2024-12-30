import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class EditPanel extends JPanel {
    private FileWriter fout = null;
    private ImageIcon editBackgroundImg = new ImageIcon("tree.png");
    private Image editBackgroundImage = editBackgroundImg.getImage();
    private JTextField wordField = new JTextField(15); // 단어 저장을 위해 단어를 입력 받는 field
    private JLabel addWord = new JLabel("단어 추가");
    private WordTextFile wordVector = new WordTextFile();
    private Vector<String> v = wordVector.getV();
    private EditPanel editPanel = this;
    public EditPanel() {
        setLayout(null);
        addWord.setSize(500, 20);
        addWord.setLocation(125, 30);
        addWord.setForeground(Color.WHITE);
        addWord.setFont(new Font("Gothic", Font.BOLD, 20));
        add(addWord);

        wordField.setSize(200, 30);
        wordField.setLocation(70, 270);
        add(wordField);

        wordField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    fout = new FileWriter("C:\\김소진\\2학년 2학기\\객체지향언어2 황기태교수님\\MiniProject\\words.txt", true);
                    JTextField t = (JTextField)(e.getSource());
                    String addWord = t.getText();
                    t.setText("");
                    fout.write(addWord);
                    fout.write("\r\n", 0, 2);
                    fout.close();
                    v.add(addWord);

                    // 단어 저장 완료 메시지 출력
                    JOptionPane.showMessageDialog(editPanel, "단어가 저장되었습니다");
                }
                catch (IOException e1) {
                    // 저장 실패 시 예외 처리
                    JOptionPane.showMessageDialog(editPanel, "단어 저장에 실패했습니다.", "에러", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    public void paintComponent(Graphics g) {
        g.drawImage(editBackgroundImage, 0, 0, getWidth(), getHeight(), editPanel);
    }
}
