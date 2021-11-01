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
    private JLabel IDLabel = new JLabel("ID :");
    private JLabel PWLabel = new JLabel("PW :");
    private JButton JoinButton = new JButton("JOIN");
    private JButton CancleButton = new JButton("Cancle");
    //-------------------------------------------------------------- GUI부분
    private int result = -5; // 1성공, 0비번실패, -1아이디실패, -2 DB오류
    //-------------------------------------------------------------- DB 부분

    private void join_result() {
        DB db = new DB();
        String ID = IDField.getText();
        String PW = PWField.getText();
        System.out.println("Input ID:"+ ID + ", Input PW: " + PW);
        result = db.join(ID,PW);

        if( result == 1){
            System.out.println("Success Join!\n");
            JOptionPane.showMessageDialog(null, "Join Success");//화면 가운데에 메시지출력
            frame.dispose();
            Login login = new Login();
        } else if(result == -1) {
            //Sresult = "아이디중복";
            System.out.println("Failed Join!\nAlready ID.\n");
        } else if(result == -3) {
            System.out.println("DB Error\n");
        }
    }

    public JoinForm() {
        frame.setTitle("Join");
        frame.setSize(294,232);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //--------------------------------------------------------------
        IDField.setBounds(70, 49, 170, 21);
        PWField.setBounds(70, 80, 170, 21);
        IDLabel.setBounds(50, 50, 35, 18);
        PWLabel.setBounds(40, 83, 57, 15);
        JoinButton.setBounds(53, 110, 185, 25);
        CancleButton.setBounds(53, 140, 185, 25);
        //--------------------------------------------------------------
        frame.add(IDField);
        frame.add(PWField);
        frame.add(IDLabel);
        frame.add(PWLabel);
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
                System.out.println("Click JoinButton");

                if(ID.isEmpty() || PW.isEmpty()) {
                    System.out.println("Please Input ID,PW\n");
                } else {
                    join_result();
                }
            }

        });

        CancleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Login loin = new Login();
                System.out.println("Click Cancle Button");
            }

        });
    }
}
