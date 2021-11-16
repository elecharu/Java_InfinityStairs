package com.badlogicgames.superjumper.desktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindForm extends JFrame {
    String password;
    //-------------------------------------------------------------- 참조 부분
    private JFrame frame = new JFrame();
    private JLabel IDLabel = new JLabel("ID :");
    private JLabel EmailLabel = new JLabel("Email :");
    private JTextField IDField = new JTextField();
    private JTextField EmailField = new JTextField();
    private JButton FindButton = new JButton("찾기");
    private JButton CancleButton = new JButton("취소");
    //-------------------------------------------------------------- GUI부분
    private int result = -5; // 1성공, 0비번실패, -1아이디실패, -2 DB오류
    //-------------------------------------------------------------- DB 부분

    private void pwFind_result(String userID, String userEmail) {
        DB db = new DB();
        password = db.pwFind(userID, userEmail);
    }

    public FindForm() {
        frame.setTitle("비밀번호 찾기");
        frame.setSize(294,232);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //--------------------------------------------------------------
        IDLabel.setBounds(50, 50, 35, 18);
        EmailLabel.setBounds(30, 80, 50, 18);
        IDField.setBounds(70, 49, 170, 21);
        EmailField.setBounds(70, 79, 170, 21);
        FindButton.setBounds(53, 110, 185, 25);
        CancleButton.setBounds(53, 140, 185, 25);
        //--------------------------------------------------------------
        frame.add(IDLabel);
        frame.add(EmailLabel);
        frame.add(IDField);
        frame.add(EmailField);
        frame.add(FindButton);
        frame.add(CancleButton);
        //--------------------------------------------------------------
        frame.setResizable(false);
        frame.setVisible(true);
        //--------------------------------------------------------------


        //-- 비밀번호 찾기 관련 --//
        FindButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ID = IDField.getText();
                String Email = EmailField.getText();
                System.out.println("찾기 버튼 클릭!");

                if(ID.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "아이디를 입력해주세요");//화면 가운데에 메시지출력
                    System.out.println("아이디를 입력해주세요\n");
                } else {
                    pwFind_result(ID,Email);
                    JOptionPane.showMessageDialog(null,  password );//화면 가운데에 메시지출력
                    frame.dispose();
                    Login loin = new Login();
                }
            }

        });
        //-- 취소 관련 --//
        CancleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Login loin = new Login();
                System.out.println("취소버튼 클릭!");
            }

        });
    }
}
