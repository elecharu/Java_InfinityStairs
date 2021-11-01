package com.badlogicgames.superjumper.desktop;

import java.sql.*;

public class DB {
    private Connection conn; // db에 접근해주게 하는 객체
    private PreparedStatement pstmt;
    private ResultSet rs;

    // mysql 접속
    public DB() {
        try {
            String dbURL = "jdbc:mysql://220.89.66.203:3306/InfinityStairs"; // DB주소 "아이피 주소 포트포워딩 해야함"
            String dbID = "root";   //DB ID
            String dbPassword = "root";     //DB PW
            Class.forName("com.mysql.jdbc.Driver"); // JDBC 드라이버 로딩
            conn = (Connection) DriverManager.getConnection(dbURL, dbID, dbPassword); // 자바-DB서버 연결작업 (서버URL,계정,비밀번호)
        } catch (Exception e) {}

    }

    // 로그인 시도
    public int login(String userID, String userPassword) {
        String SQL = "SELECT userPassword FROM USER WHERE userID = ?"; // userID가 일치하면 해당하는 userPassword를 조회
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(SQL); // SQL문짱을 DB로 전송
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery(); //SQL 값받기
            if (rs.next()) { //해당 아이디가 있을때 성공한다.
                if (rs.getString(1).equals(userPassword)) { //해당 아이디와 입력한 비밀번호가 일치하면
                    return 0; // 아이디성공
                } else
                    return -2; // 비번틀림
            }
            return -1; // 아이디 없을때
        } catch (Exception e) {}
        return -3; // 디비 접속오류
    }

    // 가입시도
    public int join(String userID, String userPassword) {
        String SQL = "SELECT userID FROM USER WHERE userID = ?"; //userID가 있으면 해당 userID조회
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(SQL); // SQL문 작성
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery(); //SQL 값받기
            if(!rs.next()) { //만약 해당 userID가 조회되지 않으면 진행
                SQL = "INSERT INTO USER VALUES(?,?,?)"; //user테이블에 값2개를 순서대로 추가하는 명령문
                pstmt = (PreparedStatement) conn.prepareStatement(SQL); //SQL문 작성
                pstmt.setString(1, userID);
                pstmt.setString(2, userPassword);
                pstmt.setInt(3, 0); //포인트 0점
                pstmt.executeUpdate(); //SQL문 실행
                return 1; //가입성공
            } else
                return -1; // 가입실패
        }catch(SQLException e) {}
        return -3; // DB접속오류
    }
    /*
    //암호찾기
    public String pwFind(String userID) {
        String SQL = "SELECT userPassword FROM USER WHERE userID = ?"; //해당 userID가 존재하면 userPassword 출력
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(SQL); // SQL문 작성
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery(); //SQL 값받기
        if(rs.next()) {
            return rs.getString(1); // 암호찾기성공 및 해당 userPassword 값 반환
        } else
            return "미가입 닉네임";
        }catch(SQLException e) {}
        return "DB오류"; // DB접속오류
    }
    /*

    public String[] selectRank(int stage, String id) {
        String SQL = "SELECT P1,P2,ClearTime, ROW_NUMBER() OVER (ORDER BY ClearTime ASC) RANKs FROM Ranking WHERE Stage=?;";
        String RankList[] = new String[12];
        boolean full;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(SQL);
            pstmt.setInt(1, stage);
            rs = pstmt.executeQuery(); //SQL 값받기
            for(int i=0;rs.next();i+=2) {
                if(i<10) {
                    RankList[i] = rs.getString("ClearTime");
                    RankList[i+1] = rs.getString("P1")+", "+rs.getString("P2");
                }

                if((rs.getString("P1").equals(id) || rs.getString("P2").equals(id)) && RankList[10] == null) {
                    RankList[10] = rs.getString("RANKs");
                    RankList[11] = rs.getString("ClearTime");
                }

                full = Arrays.stream(RankList).allMatch(Objects::nonNull);
                if(full)
                    break;
            }
            return RankList;
        } catch (SQLException e) {}
        return null;
    }

    //랭킹업데이트
    public int sendRank(String P1, String P2, String ClearTime, int Stage) {
        String SQL = "insert into Ranking (P1,P2,ClearTime,Stage) values(?,?,?,?)";
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(SQL);
            pstmt.setString(1, P1);
            pstmt.setString(2, P2);
            pstmt.setString(3, ClearTime);
            pstmt.setInt(4, Stage);
            pstmt.executeUpdate(); //SQL문 실행

        } catch (SQLException e) {}
        return -3;
    }

    //아이콘 설정
    public void setIcon(String userID, int userIcon) {
        String SQL = "update user set userIcon = ? where userID = ? ";
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(SQL);
            pstmt.setInt(1, userIcon);
            pstmt.setString(2, userID);
            pstmt.executeUpdate(); //SQL문 실행

        } catch (SQLException e) {}
    }

    */
    public void close() {
        try {
            if(rs!=null) rs.close();
            if(pstmt!=null) pstmt.close();
            if(conn!=null) conn.close();
        } catch (SQLException e) {
            System.exit(0);
        }
    }
}