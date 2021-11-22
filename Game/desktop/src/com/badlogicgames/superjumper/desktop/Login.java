package com.badlogicgames.superjumper.desktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Login extends JFrame {
    //-------------------------------------------------------------------------------------------// 참고할꺼
    private JLabel Logo = new JLabel();
    private JFrame frame = new JFrame();
    private JLabel IDLabel = new JLabel("User Name");
    private JLabel PWLabel = new JLabel("Password");
    private JTextField IDfield = new JTextField("");
    private JPasswordField PWfield = new JPasswordField("");
    private JButton LoginButton = new JButton("로그인");
    private JButton JoinButton = new JButton("회원가입");
    private final JLabel PasswordFind = new JLabel("혹시 비밀번호를 잊으셨나요?");
    //-------------------------------------------------------------------------------------------//GUI 부분
    private String ID = "";
    private String PW = "";
    private String Email = "";
    private int result = -5; // 1성공, 0비번실패, -1아이디실패, -2 DB오류
    //-------------------------------------------------------------------------------------------//DB 연결할 때 사용할꺼
    private DB db;
    //------------------------------------------------------------------------------------------------------
    public DB getUserDB() {
        return db;
    }
    //------------------------------------------------------------------------------------------------------
    private void join_result() {
        System.out.println("입력한 ID:"+ ID + ", 입력한 PW: " + PW);
        result = db.join(ID,PW,Email);

        if(result == -1) {
            System.out.println("이미 존재하는 ID입니다.\n");//아이디 중복
            JLabel alreadyId = new JLabel("이미 존재하는 ID입니다.");
            alreadyId.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            JOptionPane.showMessageDialog(null, alreadyId);//화면 가운데에 메시지출력
        }
    }
    //------------------------------------------------------------------------------------------------------
    private void login_result() {
        DB db = new DB();
        String ID = IDfield.getText();
        String PW = PWfield.getText();
        result = db.login(ID,PW);
        if (result == -1) {
            System.out.println("존재하지 않는 ID입니다.\n");//존재하지 않는 ID
            JLabel checkID = new JLabel("존재하지 않는 ID입니다.");
            checkID.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            JOptionPane.showMessageDialog(null, checkID);//화면 가운데에 메시지출력
        }
        if (result == -2) {
            System.out.println("비밀번호를 확인해주세요.\n");//비밀번호가 틀린경우
            JLabel checkPW = new JLabel("비밀번호를 확인해주세요.");
            checkPW.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            JOptionPane.showMessageDialog(null, checkPW);//화면 가운데에 메시지출력
        }
        if (result == -3) {
            System.out.println("DB연동 오류\n");//DB연동 오류
            JLabel dberror = new JLabel("DB연동 오류");
            dberror.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            JOptionPane.showMessageDialog(null, dberror);//화면 가운데에 메시지출력
        }
    }
    //------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------
    public Login() {
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setTitle("무한의 계단");
        frame.setSize(540,650);
        frame.getContentPane().setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //------------------------------------------------------------------------------------------------------
        IDLabel.setBounds(113, 333, 140, 25);
        PWLabel.setBounds(113, 395, 140, 22);
        IDLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        PWLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        IDfield.setBackground(SystemColor.text);
        IDfield.setBounds(113, 360, 298, 25);
        IDfield.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        PWfield.setBackground(SystemColor.text);
        PWfield.setBounds(113, 422, 298, 25);
        PWfield.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        LoginButton.setBackground(UIManager.getColor("Button.disabledShadow"));
        LoginButton.setBounds(266, 474, 145, 45);
        LoginButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        JoinButton.setBackground(UIManager.getColor("Button.disabledShadow"));
        JoinButton.setBounds(113, 474, 145, 45);
        JoinButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        PasswordFind.setBackground(SystemColor.window);
        PasswordFind.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        PasswordFind.setBounds(169, 542, 195, 25);
        Logo.setIcon(new ImageIcon("desktop\\src\\com\\badlogicgames\\superjumper\\desktop\\MainLogo.png"));
        Logo.setBounds(55, 30, 420, 240);

        //------------------------------------------------------------------------------------------------------
        frame.getContentPane().add(IDLabel);
        frame.getContentPane().add(PWLabel);
        frame.getContentPane().add(IDfield);
        frame.getContentPane().add(PWfield);
        frame.getContentPane().add(LoginButton);
        frame.getContentPane().add(JoinButton);
        frame.getContentPane().add(PasswordFind);
        frame.getContentPane().add(Logo);
        frame.setVisible(true);
        frame.setResizable(false);	//크기조절
        frame.setLocationRelativeTo(null); //화면중앙배치
        //------------------------------------------------------------------------------------------------------
        LoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ID = IDfield.getText();
                PW = PWfield.getText();
                System.out.printf("입력한 ID: %s, 입력한 PW: %s\n", ID, PW);
                System.out.println("로그인 버튼 클릭!");

                if(ID.isEmpty() || PW.isEmpty()) {
                    System.out.println("아이디, 비밀번호칸을 입력해주세요.\n");
                    JLabel checkaccount = new JLabel("아이디, 비밀번호칸을 입력해주세요.");
                    checkaccount.setFont(new Font("맑은 고딕", Font.BOLD, 14));
                    JOptionPane.showMessageDialog(null, checkaccount);//화면 가운데에 메시지출력
                }
                else {
                    login_result();
                    if(result == 0) {
                        System.out.println("로그인 성공!\n");
                        frame.dispose();
                        JLabel user = new JLabel(IDfield.getText()+ " 님 환영합니다.");
                        user.setFont(new Font("맑은 고딕", Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, user);//화면 가운데에 메시지출력
                        DesktopLauncher desktopLauncher = new DesktopLauncher();
                    }
                }
            }
        });
        //--------------------------------------------------------------
        JoinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ID = IDfield.getText();
                PW = PWfield.getText();
                frame.dispose();
                JoinForm joinform = new JoinForm();
                System.out.println("회원가입 버튼 클릭!\n");
            }

        });

        PasswordFind.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                FindForm find = new FindForm();
                System.out.println("비밀번호 찾기 라벨 클릭!\n");
            }
        });

    }
    //------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        new Login();
    }
    //------------------------------------------------------------------------------------------------------
}