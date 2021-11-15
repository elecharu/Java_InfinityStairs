package com.badlogicgames.superjumper.desktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindForm extends JFrame {
    String password;
    //-------------------------------------------------------------- 참조 부분
    private JFrame frame = new JFrame();
    private JTextField IDField = new JTextField();
    private JLabel IDLabel = new JLabel("ID :");
    private JButton FindButton = new JButton("Find");
    private JButton CancleButton = new JButton("Cancle");
    //-------------------------------------------------------------- GUI부분
    private int result = -5; // 1성공, 0비번실패, -1아이디실패, -2 DB오류
    //-------------------------------------------------------------- DB 부분

    private void pwFind_result() {
        DB db = new DB();
        password = db.pwFind(IDField.getText());
    }

    public FindForm() {
        frame.setTitle("PassWord Finder");
        frame.setSize(294,232);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //--------------------------------------------------------------
        IDField.setBounds(70, 49, 170, 21);
        IDLabel.setBounds(50, 50, 35, 18);
        FindButton.setBounds(53, 110, 185, 25);
        CancleButton.setBounds(53, 140, 185, 25);
        //--------------------------------------------------------------
        frame.add(IDField);
        frame.add(IDLabel);
        frame.add(FindButton);
        frame.add(CancleButton);
        //--------------------------------------------------------------
        frame.setResizable(false);
        frame.setVisible(true);
        //--------------------------------------------------------------


        //-- 회원가입 관련 --//
        FindButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ID = IDField.getText();
                System.out.println("Click FindButton");

                if(ID.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Input ID");//화면 가운데에 메시지출력
                    System.out.println("Please Input ID\n");
                } else {
                    pwFind_result();
                    JOptionPane.showMessageDialog(null, "Find! PassWord is " + password);//화면 가운데에 메시지출력
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
                System.out.println("Click Cancle Button");
            }

        });
    }
}
