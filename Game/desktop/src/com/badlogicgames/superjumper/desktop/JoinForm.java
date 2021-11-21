package com.badlogicgames.superjumper.desktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JoinForm extends JFrame {
    //-------------------------------------------------------------- 참조 부분
    private JFrame frame = new JFrame();
    private JTextField IDField = new JTextField();
    private JPasswordField PWField = new JPasswordField();
    private JTextField MailField = new JTextField();
    private JLabel IDLabel = new JLabel("User Name");
    private JLabel PWLabel = new JLabel("Password");
    private JLabel MailLabel = new JLabel("Email");
    private JLabel BackLabel = new JLabel("Back to Login");
    private JButton JoinButton = new JButton("회원가입");
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
            JLabel success = new JLabel("회원가입 성공");
            success.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            JOptionPane.showMessageDialog(null, success);
            frame.dispose();
            Login login = new Login();
        } else if(result == -1) {
            //Sresult = "아이디중복";
            System.out.println("회원가입 실패!\n이미 존재하는 아이디 입니다.\n");
            JLabel alreadyId = new JLabel("이미 존재하는 ID입니다.");
            alreadyId.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            JOptionPane.showMessageDialog(null, alreadyId);//화면 가운데에 메시지출력
        } else if(result == -3) {
            System.out.println("DB연동 오류\n");
            JLabel dberror = new JLabel("DB연동 오류");
            dberror.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            JOptionPane.showMessageDialog(null, dberror);//화면 가운데에 메시지출력
        }
    }

    public JoinForm() {
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setTitle("회원가입");
        frame.setSize(439,565);
        frame.getContentPane().setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); //화면중앙배치
        frame.setResizable(false);
        frame.setVisible(true);
        //--------------------------------------------------------------
        IDLabel.setBounds(52, 209, 105, 18);
        PWLabel.setBounds(52, 272, 140, 15);
        MailLabel.setBounds(52,327,50,18);
        IDLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        PWLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        MailLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        IDField.setBounds(53, 237, 317, 25);
        IDField.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        IDField.setBackground(SystemColor.text);
        PWField.setBounds(53, 292, 317, 25);
        PWField.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        PWField.setBackground(SystemColor.text);
        MailField.setBounds(53,349,317,25);
        MailField.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        MailField.setBackground(SystemColor.text);
        JoinButton.setBounds(52, 394, 318, 43);
        JoinButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        JoinButton.setBackground(UIManager.getColor("Button.disabledShadow"));
        BackLabel.setBackground(SystemColor.window);
        BackLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        BackLabel.setBounds(160, 473, 105, 25);
        //--------------------------------------------------------------
        frame.getContentPane().add(IDLabel);
        frame.getContentPane().add(PWLabel);
        frame.getContentPane().add(MailLabel);
        frame.getContentPane().add(IDField);
        frame.getContentPane().add(PWField);
        frame.getContentPane().add(MailField);
        frame.getContentPane().add(JoinButton);
        frame.getContentPane().add(BackLabel);
        //--------------------------------------------------------------

        JoinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ID = IDField.getText();
                String PW = PWField.getText();
                String Email = MailField.getText();
                System.out.println("회원가입 버튼 클릭!");

                if(ID.isEmpty() || PW.isEmpty() || Email.isEmpty()) {
                    System.out.println("아이디,비밀번호,이메일칸 모두 입력해주세요.\n");
                    JLabel checkaccount = new JLabel("아이디,비밀번호,이메일칸 모두 입력해주세요.");
                    checkaccount.setFont(new Font("맑은 고딕", Font.BOLD, 14));
                    JOptionPane.showMessageDialog(null, checkaccount);//화면 가운데에 메시지출력
                } else {
                    join_result();
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
