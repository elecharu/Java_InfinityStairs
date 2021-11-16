package com.badlogicgames.superjumper.desktop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinForm extends JFrame {
    private DB db;
    //-------------------------------------------------------------- 참조 부분
    private JFrame frame = new JFrame();
    private JTextField IDField = new JTextField();
    private JPasswordField PWField = new JPasswordField();
    private JTextField MailField = new JTextField();
    private JLabel IDLabel = new JLabel("ID :");
    private JLabel PWLabel = new JLabel("PW :");
    private JLabel MailLabel = new JLabel("Email :");
    private JButton JoinButton = new JButton("회원가입");
    private JButton CancleButton = new JButton("취소");
    //-------------------------------------------------------------- GUI부분
    private int result = -5; // 1성공, 0비번실패, -1아이디실패, -2 DB오류
    //-------------------------------------------------------------- DB 부분

    private void join_result() {
        DB db = new DB();
        String ID = IDField.getText();
        String PW = PWField.getText();
        String Email = MailField.getText();
        System.out.println("입력한 ID:"+ ID + ", 입력한 PW: " + PW);
        result = db.join(ID,PW,Email);

        if( result == 1){
            System.out.println("회원가입 성공!\n");
            JOptionPane.showMessageDialog(null, "회원가입 성공");
            frame.dispose();
            Login login = new Login();
        } else if(result == -1) {
            //Sresult = "아이디중복";
            System.out.println("회원가입 실패!\n이미 존재하는 아이디 입니다.\n");
            JOptionPane.showMessageDialog(null, "이미 존재하는 ID입니다.");
        } else if(result == -3) {
            System.out.println("DB연동 오류\n");
            JOptionPane.showMessageDialog(null, "DB연동 오류");//화면 가운데에 메시지출력
        }
    }

    public JoinForm() {
        frame.setTitle("회원가입");
        frame.setSize(295,250);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //--------------------------------------------------------------
        IDLabel.setBounds(50, 50, 35, 18);
        PWLabel.setBounds(40, 83, 57, 15);
        MailLabel.setBounds(30,113,50,18);
        IDField.setBounds(70, 49, 170, 21);
        PWField.setBounds(70, 80, 170, 21);
        MailField.setBounds(70,110,170,21);
        JoinButton.setBounds(53, 140, 185, 25);
        CancleButton.setBounds(53, 170, 185, 25);
        //--------------------------------------------------------------
        frame.add(IDLabel);
        frame.add(PWLabel);
        frame.add(MailLabel);
        frame.add(IDField);
        frame.add(PWField);
        frame.add(MailField);
        frame.add(JoinButton);
        frame.add(CancleButton);
        //--------------------------------------------------------------
        frame.setResizable(false);
        frame.setVisible(true);
        //--------------------------------------------------------------

        JoinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ID = IDField.getText();
                String PW = PWField.getText();
                System.out.println("회원가입 버튼 클릭!");

                if(ID.isEmpty() || PW.isEmpty()) {
                    System.out.println("ID,PW칸을 입력해주세요\n");
                    JOptionPane.showMessageDialog(null, "아이디,비밀번호를 입력해주세요.");//화면 가운데에 메시지출력
                } else {
                    join_result();
                }
            }

        });

        CancleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Login loin = new Login();
                System.out.println("취소 버튼 클릭!");
            }

        });
    }
}
