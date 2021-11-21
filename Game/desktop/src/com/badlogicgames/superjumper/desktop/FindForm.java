package com.badlogicgames.superjumper.desktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FindForm extends JFrame {
    String password;
    //-------------------------------------------------------------- 참조 부분
    private JFrame frame = new JFrame();
    private JLabel IDLabel = new JLabel("User Name");
    private JLabel MailLabel = new JLabel("Email");
    private JLabel BackLabel = new JLabel("Back to Login");
    private JTextField IDField = new JTextField();
    private JTextField EmailField = new JTextField();
    private JButton FindButton = new JButton("찾기");
    //-------------------------------------------------------------- GUI부분
    private int result = -5; // 1성공, 0비번실패, -1아이디실패, -2 DB오류
    //-------------------------------------------------------------- DB 부분

    private void pwFind_result(String userID, String userEmail) {
        DB db = new DB();
        password = db.pwFind(userID, userEmail);
    }

    public FindForm() {
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setTitle("비밀번호 찾기");
        frame.setSize(439,565);
        frame.getContentPane().setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); //화면중앙배치
        //--------------------------------------------------------------
        IDLabel.setBounds(60, 245, 105, 18);
        IDLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        MailLabel.setBounds(60,308,50,18);
        MailLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        IDField.setBounds(60, 273, 298, 25);
        IDField.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        IDField.setBackground(SystemColor.text);
        EmailField.setBounds(60, 331, 298, 25);
        EmailField.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        EmailField.setBackground(SystemColor.text);
        FindButton.setForeground(SystemColor.windowText);
        FindButton.setBounds(61, 377, 297, 49);
        FindButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        FindButton.setBackground(UIManager.getColor("Button.disabledShadow"));
        BackLabel.setBackground(SystemColor.window);
        BackLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        BackLabel.setBounds(160, 473, 105, 25);
        //--------------------------------------------------------------
        frame.getContentPane().add(IDLabel);
        frame.getContentPane().add(MailLabel);
        frame.getContentPane().add(IDField);
        frame.getContentPane().add(EmailField);
        frame.getContentPane().add(FindButton);
        frame.getContentPane().add(BackLabel);
        //--------------------------------------------------------------


        //-- 비밀번호 찾기 관련 --//
        FindButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ID = IDField.getText();
                String Email = EmailField.getText();
                System.out.println("찾기 버튼 클릭!");

                if(ID.isEmpty() || Email.isEmpty()) {
                    System.out.println("아이디, 이메일 모두 입력해주세요.\n");
                    JLabel check = new JLabel("아이디, 이메일 모두 입력해주세요.");
                    check.setFont(new Font("맑은 고딕", Font.BOLD, 14));
                    JOptionPane.showMessageDialog(null, check);//화면 가운데에 메시지출력
                } else {
                    pwFind_result(ID,Email);
                    JOptionPane.showMessageDialog(null,  password );//화면 가운데에 메시지출력
                    frame.dispose();
                    Login loin = new Login();
                }
            }

        });

        BackLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                Login loin = new Login();
                System.out.println("Back to Login 라벨 클릭!");
            }
        });
    }
}
