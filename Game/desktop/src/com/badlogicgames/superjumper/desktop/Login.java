package com.badlogicgames.superjumper.desktop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private DB db;
    //private Game game;
    //------------------------------------------------// 참고할꺼
    private JFrame frame = new JFrame();
    private JLabel IDLabel = new JLabel("User ID :");
    private JLabel PWLabel = new JLabel("PW :");
    private JTextField IDfield = new JTextField("");
    private JPasswordField PWfield = new JPasswordField("");
    private JButton LoginButton = new JButton("Login");
    private JButton JoinButton = new JButton("Join");
    private JButton FindButton = new JButton("ID/PW Find");
    //------------------------------------------------//GUI 부분
    private String ID = "";
    private String PW = "";
    private int result = -5; // 1성공, 0비번실패, -1아이디실패, -2 DB오류
    //------------------------------------------------//DB 연결할 때 사용할꺼

    /*참고 파일 이용한 부분 여기 Text는 위에 Field와 같은 역할*/
    //private int type = -1; // 0 NICK, 1 PW, -1 입력안함
    //private String nickText = "";
    //private String pwText = "";
    //private String pwView = "";
    //private String J_nickText = "";
    //private String J_pwText = "";
    //private String Sresult="";
    //private boolean join = false;
    //private boolean pwFind = false;

    public DB getUserDB() {
        return db;
    }
    /*
    public Login (Game game, BT_USER_DB userDB) {
        this.userDB = userDB;
        this.game = game;
    }
    */
    private void join_result() {
        System.out.println("Input ID:"+ ID + ", Input PW: " + PW);
        result = db.join(ID,PW);

        if(result == -1) {
            System.out.println("Already Create ID\n");//아이디 중복
            JOptionPane.showMessageDialog(null, "Already Create ID");//화면 가운데에 메시지출력
        }
    }

    private void login_result() {
        DB db = new DB();
        String ID = IDfield.getText();
        String PW = PWfield.getText();
        result = db.login(ID,PW);
        if (result == -1) {
            System.out.println("Don't Exist ID\n");//존재하지 않는 ID
            JOptionPane.showMessageDialog(null, "Don't Exist ID");//화면 가운데에 메시지출력
        }
        if (result == -2) {
            System.out.println("Please Check PW\n");//비밀번호가 틀린경우
            JOptionPane.showMessageDialog(null, "Please Check PW");//화면 가운데에 메시지출력
        }
        if (result == -3) {
            System.out.println("DB Error\n");//DB연동 오류
            JOptionPane.showMessageDialog(null, "DB Error.");//화면 가운데에 메시지출력
        }
    }

    public static void main(String[] args) {
        new Login();
    }
	/*
	public String getNickText() {
		return nickText;
	}
	public String getPwText() {
		return pwText;
	}
	public boolean isJoin() {
		return join;
	}
	public boolean isPwFind() {
		return pwFind;
	}
	*/

    public Login() {
        frame.setTitle("Infinity Stairs");
        frame.setSize(540,650);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //--------------------------------------------------------
        IDLabel.setBounds(111, 355, 140, 25);
        PWLabel.setBounds(132, 385, 140, 25);
        IDfield.setBounds(160, 355, 143, 25);
        PWfield.setBounds(160, 385, 143, 25);
        LoginButton.setBounds(310, 355, 87, 53);
        FindButton.setBounds(160, 420, 115, 27);
        JoinButton.setBounds(282, 420, 115, 27);
        //--------------------------------------------------------------
        frame.add(IDLabel);
        frame.add(PWLabel);
        frame.add(IDfield);
        frame.add(PWfield);
        frame.add(LoginButton);
        frame.add(FindButton);
        frame.add(JoinButton);
        frame.setVisible(true);
        frame.setResizable(false);	//크기조절
        //--------------------------------------------------------------

        LoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ID = IDfield.getText();
                PW = PWfield.getText();
                System.out.printf("Input ID: %s, Input PW: %s\n", ID, PW);
                System.out.println("Login Button Click");

                if(ID.isEmpty() || PW.isEmpty()) {
                    System.out.println("Please Check ID,PW\n");
                    JOptionPane.showMessageDialog(null, "Please Input ID,PW");//화면 가운데에 메시지출력
                }
                else {
                    login_result();
                    if(result == 0) {
                        System.out.println("Login Success!\n");
                        frame.dispose();
                        JOptionPane.showMessageDialog(null, "Welcome " + IDfield.getText());//화면 가운데에 메시지출력
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
                System.out.println("JoinButton Click\n");
            }

        });

        FindButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                FindForm find = new FindForm();
                System.out.println("FindButton Click\n");
            }

        });

    }
}