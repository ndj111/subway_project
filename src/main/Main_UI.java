package main;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import model.AddCartDTO;
import model.MenuSelectDTO;


// 최종 수정 : 현진

public class Main_UI extends JFrame {
    private static final long serialVersionUID = 1L;
    

    // 로딩 시간 (1초 = 1000)
    static int loadingTime = 5000;

    // 로딩 이미지
    static JButton mainLoading;


    // GUI 창 설정
    String windowName = "2조 :: Java Project SUBWAY";
    int winXpos = 100;
    int winYpos = 100;
    int winWidth = 1295; // 창 가로 크기
    int winHeight = 800; // 창 세로 크기
    int winScrollSpeed = 15; //스크롤 속도


    // 회원 설정
    int joinPoint = 1000;          // 회원가입시 기본 적립금
    double buyPointPercent = 0.05; // 주문시 적립금 적립률



    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ


    // DB용 변수
    Connection con = null;

    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = null;

    PreparedStatement pstmtsub = null;
	ResultSet rssub = null;
	String sqlsub = null;

    

	// 회원 변수
	String LoginType = null;
    String User_id = null;
    String User_name = "비회원";
    int User_point = 0;



    // 주문용 회원 변수
    String ONO = null;
    String ODIVISION = null;
    String OPAY;
    String OID;
    String ONAME;
    int OPRICE = 0;
    int OSAVED = 0;



    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ



    // 메뉴선택 DTO
    MenuSelectDTO menuSelDto = new MenuSelectDTO();


    // 메뉴 선택 목록 표시를 위한 카운트 변수
    int orderMenuCount1;
    int orderMenuRows1;
    int orderMenuCount2;
    int orderMenuRows2;
    int orderMenuCount3;
    int orderMenuRows3;
    int orderMenuCount4;
    int orderMenuRows4;
    int orderMenuCount5;
    int orderMenuRows5;
    int orderMenuCount6;
    int orderMenuRows6;


    // 토글 메뉴를 위한 임시 선택 저장 배열
    static List<String> ResultSelName = new ArrayList<String>();
    static List<String> ResultSelPrice = new ArrayList<String>();


    // 장바구니 상품 저장 배열
    static List<AddCartDTO> orderCartMenu = new ArrayList<AddCartDTO>();




    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    // 화면 배치 컴포넌트
    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    // 메인페이지
    JButton mainBtn1 = new JButton();
    JButton mainBtn2 = new JButton();
    JButton mainBtn3 = new JButton();
    JButton mainBtn4 = new JButton();

    JLabel orderMenuMemInfo1 = new JLabel();
    JLabel orderMenuMemBg1 = new JLabel();
    JLabel orderMenuMemInfo2 = new JLabel();
    JLabel orderMenuMemBg2 = new JLabel();
    JLabel orderMenuMemInfo3 = new JLabel();
    JLabel orderMenuMemBg3 = new JLabel();
    JLabel orderMenuMemInfo4 = new JLabel();
    JLabel orderMenuMemBg4 = new JLabel();
    JLabel orderMenuMemInfo5 = new JLabel();
    JLabel orderMenuMemBg5 = new JLabel();
    JLabel orderMenuMemInfo6 = new JLabel();
    JLabel orderMenuMemBg6 = new JLabel();
    JLabel orderMenuMemInfoCart = new JLabel();
    JLabel orderMenuMemBgCart = new JLabel();

    // 1 메뉴선택
    JScrollPane omlScroll1 = new JScrollPane();
    JPanel orderMenuList1 = new JPanel();
    JLabel orderMenu1Text1 = new JLabel();
    JLabel orderMenu1Text2 = new JLabel();
    JLabel orderMenu1Text3 = new JLabel();
    String orderMenu1Text3Cont = "<html>샌드위치의 기본이 될<br/>메뉴를 골라주세요.</html>";
    JButton orderMenu1Btn1 = new JButton();
    JButton orderMenu1Btn2 = new JButton();

    // 2 빵 선택
    JScrollPane omlScroll2 = new JScrollPane();
    JPanel orderMenuList2 = new JPanel();
    JLabel orderMenu2Text1 = new JLabel();
    JLabel orderMenu2Text2 = new JLabel();
    JLabel orderMenu2Text3 = new JLabel();
    String orderMenu2Text3Cont = "<html>매장에서 구운 6가지 종류 중<br/>고객님이 원하는 빵으로<br/>추가비용 없이 선택 가능합니다.</html>";
    JButton orderMenu2Btn1 = new JButton();
    JButton orderMenu2Btn2 = new JButton();
    JButton orderMenu2Btn3 = new JButton();

    // 3 추가 토핑
    JScrollPane omlScroll3 = new JScrollPane();
    JPanel orderMenuList3 = new JPanel();
    JLabel orderMenu3Text1 = new JLabel();
    JLabel orderMenu3Text2 = new JLabel();
    JLabel orderMenu3Text3 = new JLabel();
    String orderMenu3Text3Cont = "<html>나만의 메뉴를<br/>더욱 풍성하게 즐기세요.</html>";
    JButton orderMenu3Btn1 = new JButton();
    JButton orderMenu3Btn2 = new JButton();
    JButton orderMenu3Btn3 = new JButton();

    // 4 야채 선택
    JScrollPane omlScroll4 = new JScrollPane();
    JPanel orderMenuList4 = new JPanel();
    JLabel orderMenu4Text1 = new JLabel();
    JLabel orderMenu4Text2 = new JLabel();
    JLabel orderMenu4Text3 = new JLabel();
    String orderMenu4Text3Cont = "<html>나만의 스타일을 완성하는 단계!<br/>원하지 않는 야채는 빼고<br/>좋아하는 야채는 더하세요.</html>";
    JButton orderMenu4Btn1 = new JButton();
    JButton orderMenu4Btn2 = new JButton();
    JButton orderMenu4Btn3 = new JButton();

    // 5 소스 선택
    JScrollPane omlScroll5 = new JScrollPane();
    JPanel orderMenuList5 = new JPanel();
    JLabel orderMenu5Text1 = new JLabel();
    JLabel orderMenu5Text2 = new JLabel();
    JLabel orderMenu5Text3 = new JLabel();
    String orderMenu5Text3Cont = "<html>나만의 스타일을 완성하는 단계!<br/>오늘의 기분에 맞는<br/>소스를 선택해주세요.</html>";
    JButton orderMenu5Btn1 = new JButton();
    JButton orderMenu5Btn2 = new JButton();
    JButton orderMenu5Btn3 = new JButton();

    // 6 세트 선택
    JScrollPane omlScroll6 = new JScrollPane();
    JPanel orderMenuList6 = new JPanel();
    JLabel orderMenu6Text1 = new JLabel();
    JLabel orderMenu6Text2 = new JLabel();
    JLabel orderMenu6Text3 = new JLabel();
    String orderMenu6Text3Cont = "<html>세트로 구매하시면 할인혜택을<br/>받으실 수 있습니다. 세트로<br/>더욱 든든하고 알차게 즐기세요.</html>";
    JButton orderMenu6Btn1 = new JButton();
    JButton orderMenu6Btn2 = new JButton();
    JButton orderMenu6Btn3 = new JButton();

    // 주문 확인
    DefaultTableModel orderCartListModel;
    JTable orderCartListTable;
    JScrollPane orderCartListScroll = new JScrollPane();
    JLabel orderCartText1 = new JLabel();
    JLabel orderCartText2 = new JLabel();
    JLabel orderCartText3 = new JLabel();
    JLabel orderCartText4 = new JLabel();
    JLabel orderCartText5 = new JLabel();
    JLabel orderCartText6 = new JLabel();
    JTextField orderCartInput = new JTextField();
    JRadioButton orderCartRadio1_1 = new JRadioButton();
    JRadioButton orderCartRadio1_2 = new JRadioButton();
    JRadioButton orderCartRadio2_1 = new JRadioButton();
    JRadioButton orderCartRadio2_2 = new JRadioButton();
    JRadioButton orderCartRadio2_3 = new JRadioButton();
    JRadioButton orderCartRadio2_4 = new JRadioButton();
    JRadioButton orderCartRadio2_5 = new JRadioButton();
    JButton orderCartBtn1 = new JButton();
    JButton orderCartBtn2 = new JButton();
    JButton orderCartBtn3 = new JButton();
    JButton orderCartBtn4 = new JButton();
    JButton orderCartBtn5 = new JButton();

    // 주문 완료
    JButton orderDoneBtn = new JButton();


	// 회원 로그인
	JLabel loginLbl1 = new JLabel();
	JLabel loginLbl2 = new JLabel();
	JLabel loginLbl3 = new JLabel();
	JLabel loginLbl4 = new JLabel();
	JLabel loginLbl5 = new JLabel();
	JTextField loginJtf1 = new JTextField();
	JTextField loginJtf2 = new JTextField();
	JButton loginBtn1 = new JButton();
	JButton loginBtn2 = new JButton();

    // 회원 가입
	JLabel joinLbl1 = new JLabel();
	JLabel joinLbl2 = new JLabel();
	JLabel joinLbl3 = new JLabel();
	JLabel joinLbl4 = new JLabel();
	JLabel joinLbl5 = new JLabel();
	JLabel joinLbl6 = new JLabel();
	JLabel joinLbl7 = new JLabel();
	JLabel joinLbl8 = new JLabel();
	JTextField joinJtf1 = new JTextField();
	JTextField joinJtf2 = new JTextField();
	JTextField joinJtf3 = new JTextField();
	JTextField joinJtf4 = new JTextField();
	JTextField joinJtf5 = new JTextField();
	JButton joinBtn1 = new JButton();
	JButton joinBtn2 = new JButton();


	// 관리자 - 주문관리
	JButton subOrderBtn1 = new JButton();
	JButton subOrderBtn2 = new JButton();
	JButton subOrderBtn3 = new JButton();
	JButton subOrderBtn4 = new JButton();
	JButton subOrderBtn5 = new JButton();
	JButton subOrderBtn6 = new JButton();
	JButton subOrderBtn7 = new JButton();
	JButton subOrderBtn8 = new JButton();
	DefaultTableModel subOrderModel;
	JTable subOrderTable;
	JLabel subOrderlblNewLabel = new JLabel();
	JLabel subOrderSelectedDate = new JLabel();
	JTextPane subOrderTextPane = new JTextPane();
	JTextPane subOrderTextPaneDate = new JTextPane();
	JTextPane subOrderCountTextPane = new JTextPane();

	String subOrderContent;
	int subOrderSelectPrice, subOrderSearchPrice, subOrderSelectSaved, subOrderSearchSaved;

    // JDateChooser(캘린더)
	JDateChooser dateChooserOrder = new JDateChooser();
	JTextFieldDateEditor dateEditorOrder = (JTextFieldDateEditor)dateChooserOrder.getComponent(1);    
    SimpleDateFormat dateFormatOrder = new SimpleDateFormat("yy/MM/dd");



	// 관리자 - 회원관리
	JLabel subUserLbl1 = new JLabel();
	JLabel subUserLbl2 = new JLabel();
	JLabel subUserLbl3 = new JLabel();
	JLabel subUserLbl4 = new JLabel();
	JLabel subUserLbl5 = new JLabel();
	JLabel subUserLbl6 = new JLabel();
	JTextField subUserJtf1 = new JTextField();
	JTextField subUserJtf2 = new JTextField();
	JTextField subUserJtf3 = new JTextField();
	JTextField subUserJtf4 = new JTextField();
	JTextField subUserJtf5 = new JTextField();
	JComboBox<String> userComboBox;
    DefaultTableModel userModel;
    JTable userTable;
    JScrollPane UserScrollPane = new JScrollPane();
    JButton subUserBtn1 = new JButton();
    JButton subUserBtn2 = new JButton();
    JButton subUserBtn3 = new JButton();
    JButton subUserBtn4 = new JButton();
    JButton subUserBtn5 = new JButton();
    JButton subUserBtn6 = new JButton();
    JButton subUserBtn7 = new JButton();
    JButton subUserBtn8 = new JButton();
    JButton subUserBtn9 = new JButton();
    JButton subUserBtn10 = new JButton();



    // 관리자 - 메뉴관리
    DefaultTableModel menuModifymodel;
    JTable menuModifytable;
    JButton menuModifybtnNewButton1 = new JButton();
    JButton menuModifybtnNewButton2 = new JButton();
    JButton menuModifybtnNewButton3 = new JButton();
    JButton menuModifybtnNewButton4 = new JButton();
    JButton menuModifybtnNewButton5 = new JButton();
    JButton menuModifybtnNewButton6 = new JButton();
    JButton menuModifybtnNewButton7 = new JButton();
    JButton menuModifybtnNewButton8 = new JButton();
    JButton menuModifybtnNewButton9 = new JButton();
    JButton menuModifybtnNewButton10 = new JButton();

    JTextField menuModifytextField1 = new JTextField();
    JTextField menuModifytextField2 = new JTextField();
    JTextField menuModifytextField3 = new JTextField();
    JComboBox<String> menuModifyjcb1;

    String menuModifycategory = "";
    String menuModifyupdateUNO;
    String menuModifystr = "";
    String menuModifyintStr = "";
    String menuModifycheck= "";
    String menuModifytext1 = "";
    String menuModifytext2 = "";
    String menuModifytext3 = "";
    String subUserCheck = "";



    // 관리자 - 판매통계
    DefaultTableModel statisticsModel, statisticsModel1;
    JTable statisticsTable;
    JScrollPane statisticsScrollPane = new JScrollPane();
    JLabel statisticsSelectedDate = new JLabel();
    JButton statisticsBtn0 = new JButton();
    JButton statisticsBtn1 = new JButton();
    JButton statisticsBtn2 = new JButton();
    JButton statisticsBtn3 = new JButton();
    JButton statisticsBtn4 = new JButton();
    JButton statisticsBtn5 = new JButton();
    JButton statisticsBtn6 = new JButton();
    JButton statisticsBtn7 = new JButton();
    JButton statisticsBtn8 = new JButton();
    JButton statisticsBtn9 = new JButton();
    DecimalFormat decimalFormat = new DecimalFormat("#,###");

    // JDateChooser(캘린더)
	JDateChooser dateChooser = new JDateChooser();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd");  	
	LocalDateTime today = LocalDateTime.now();  
	JTextFieldDateEditor dateEditor = (JTextFieldDateEditor)dateChooser.getComponent(1);    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd");




    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    // 화면 생성
    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    private JFrame MainPage = new JFrame();   // 메인페이지
    private JFrame Order1 = new JFrame();     // 1 메뉴 선택
    private JFrame Order2 = new JFrame();     // 2 빵 선택
    private JFrame Order3 = new JFrame();     // 3 추가 토핑
    private JFrame Order4 = new JFrame();     // 4 야채 선택
    private JFrame Order5 = new JFrame();     // 5 소스 선택
    private JFrame Order6 = new JFrame();     // 6 세트 선택
    private JFrame OrderCart = new JFrame();  // 주문 확인
    private JFrame OrderDone = new JFrame();  // 주문 완료
    private JFrame Login = new JFrame();	  // 로그인
    private JFrame Join = new JFrame();		  // 회원가입
    private JFrame SubUser = new JFrame(); // 관리자 - 회원관리
    private JFrame SubOrder = new JFrame(); // 관리자 - 주문관리
    private JFrame MenuModify = new JFrame(); // 관리자 - 메뉴관리
    private JFrame Statistics = new JFrame(); // 관리자 - 판매통계





    public Main_UI() throws IOException {
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // MainPage : 메인페이지 @노동진
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // 페이지 만들기 (창 이름, 창 타이틀, 배경이미지)
        int mainBgNum = (int)(Math.random() * 3) + 1;
        mkPage(MainPage, "2조 :: Java Project SUBWAY", "/images/main/main_bg"+mainBgNum+".jpg");


        // 로딩 이미지
        mainLoading = new JButton(new ImageIcon(Main_UI.class.getResource("/images/common/loading.gif")));
        mainLoading.setBorderPainted(false);
        mainLoading.setBounds(750, 162, 400, 446);


        // 버튼 : 비회원 주문하기 (버튼컴포넌트, 기본이미지, 오버이미지, X좌표, Y좌표, 가로크기, 세로크기)
        mkBtn(mainBtn1, "/images/main/main_btn1_off.jpg", "/images/main/main_btn1_on.jpg", 750, 270, 400, 64);
        mainBtn1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pageMove("order1");
            }
        });

        // 버튼 : 회원 주문하기
        mkBtn(mainBtn2, "/images/main/main_btn2_off.jpg", "/images/main/main_btn2_on.jpg", 750, 358, 400, 64);
        mainBtn2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	LoginType = "member";
                pageMove("login");
				loginJtf1.setText("");
				loginJtf2.setText("");
            }
        });

        // 버튼 : 회원 가입하기
        mkBtn(mainBtn3, "/images/main/main_btn3_off.jpg", "/images/main/main_btn3_on.jpg", 750, 432, 400, 64);
        mainBtn3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pageMove("join");
            }
        });

        // 버튼 : 관리자화면
        mkBtn(mainBtn4, "/images/main/main_btn4_off.jpg", "/images/main/main_btn4_on.jpg", 750, 544, 400, 64);
        mainBtn4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	LoginType = "admin";
                pageMove("login");
				loginJtf1.setText("");
				loginJtf2.setText("");
            }
        });

        // 프레임에 컴포넌트 표시
        MainPage.getContentPane().add(mainLoading);
        MainPage.getContentPane().add(mainBtn1);
        MainPage.getContentPane().add(mainBtn2);
        MainPage.getContentPane().add(mainBtn3);
        MainPage.getContentPane().add(mainBtn4);


        MainPage.setVisible(true);




        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // Order1 : 주문하기 01 메뉴선택 @노동진
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // 페이지 만들기 (창 이름, 창 타이틀, 배경이미지)
        mkPage(Order1, windowName + " - Step 1. 메뉴 선택", "/images/menu_order/menu_order1_bg.jpg");


        // 텍스트 1 (라벨컴포넌트, 텍스트, 폰트두께, 폰트사이즈, 색상R, 색상G, 색상B , X좌표, Y좌표, 가로크기, 세로크기)
        mkTxt(orderMenu1Text1, "STEP 1", "bold", 18, 0, 146, 35, 971, 175, 65, 25);

        // 텍스트 2
        mkTxt(orderMenu1Text2, "메뉴 선택", "bold", 18, 0, 0, 0, 971, 204, 220, 35);

        // 텍스트 3
        mkTxt(orderMenu1Text3, orderMenu1Text3Cont, "plain", 16, 51, 51, 51, 971, 275, 255, 80);

        // 버튼 : 다음 선택
        mkBtn(orderMenu1Btn1, "/images/menu_order/menu_order_btn_next_off.jpg", "/images/menu_order/menu_order_btn_next_on.jpg", 971, 543, 220, 56);
        orderMenu1Btn1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(menuSelDto.getSel1Name() != null){
                    pageMove("order2");
                }else{
                    JOptionPane.showMessageDialog(null, "메뉴를 선택해 주세요.");
                }
            }
        });

        // 버튼 : 처음화면으로
        mkBtn(orderMenu1Btn2, "/images/menu_order/menu_order_btn_home_off.jpg", "/images/menu_order/menu_order_btn_home_on.jpg", 971, 623, 220, 56);
        orderMenu1Btn2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gotoMain();
            }
        });


        // 메뉴목록 표시
        showMenuList(1);


        // 프레임에 컴포넌트 표시
        Order1.getContentPane().add(omlScroll1);
        orderMenuList1.setLayout(new GridLayout(orderMenuRows1, 3, 0, 0));
        Order1.getContentPane().add(orderMenu1Text1);
        Order1.getContentPane().add(orderMenu1Text2);
        Order1.getContentPane().add(orderMenu1Text3);
        Order1.getContentPane().add(orderMenu1Btn1);
        Order1.getContentPane().add(orderMenu1Btn2);





        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // Order2 : 주문하기 02 빵선택 @노동진
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // 페이지 만들기 (창 이름, 창 타이틀, 배경이미지)
        mkPage(Order2, windowName + " - Step 2. 빵 선택", "/images/menu_order/menu_order2_bg.jpg");


        // 텍스트 1
        mkTxt(orderMenu2Text1, "STEP 2", "bold", 18, 0, 146, 35, 971, 175, 65, 25);

        // 텍스트 2
        mkTxt(orderMenu2Text2, "빵 선택", "bold", 18, 0, 0, 0, 971, 204, 220, 35);

        // 텍스트 3
        mkTxt(orderMenu2Text3, orderMenu2Text3Cont, "plain", 16, 51, 51, 51, 971, 275, 255, 80);

        // 버튼 : 다음 선택
        mkBtn(orderMenu2Btn1, "/images/menu_order/menu_order_btn_next_off.jpg", "/images/menu_order/menu_order_btn_next_on.jpg", 971, 479, 220, 56);
        orderMenu2Btn1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(menuSelDto.getSel2Name() != null){
                    pageMove("order3");
                }else{
                    JOptionPane.showMessageDialog(null, "빵을 선택해 주세요.");
                }
            }
        });

        // 버튼 : 이전 선택
        mkBtn(orderMenu2Btn2, "/images/menu_order/menu_order_btn_prev_off.jpg", "/images/menu_order/menu_order_btn_prev_on.jpg", 971, 543, 220, 56);
        orderMenu2Btn2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                menuSelDto.setSel2Name(null);
                menuSelDto.setSel2Price("0");
                orderMenu2Text3.setText(orderMenu2Text3Cont);

                pageMove("order1");
            }
        });

        // 버튼 : 처음화면으로
        mkBtn(orderMenu2Btn3, "/images/menu_order/menu_order_btn_home_off.jpg", "/images/menu_order/menu_order_btn_home_on.jpg", 971, 623, 220, 56);
        orderMenu2Btn3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gotoMain();
            }
        });


        // 메뉴목록 표시
        showMenuList(2);


        // 프레임에 컴포넌트 표시
        Order2.getContentPane().add(omlScroll2);
        orderMenuList2.setLayout(new GridLayout(orderMenuRows2, 3, 0, 0));
        Order2.getContentPane().add(orderMenu2Text1);
        Order2.getContentPane().add(orderMenu2Text2);
        Order2.getContentPane().add(orderMenu2Text3);
        Order2.getContentPane().add(orderMenu2Btn1);
        Order2.getContentPane().add(orderMenu2Btn2);
        Order2.getContentPane().add(orderMenu2Btn3);





        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // Order3 : 주문하기 03 추가토핑 @노동진
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // 페이지 만들기 (창 이름, 창 타이틀, 배경이미지)
        mkPage(Order3, windowName + " - Step 3. 추가토핑", "/images/menu_order/menu_order3_bg.jpg");


        // 텍스트 1
        mkTxt(orderMenu3Text1, "STEP 3", "bold", 18, 0, 146, 35, 971, 175, 65, 25);

        // 텍스트 2
        mkTxt(orderMenu3Text2, "추가토핑 선택", "bold", 18, 0, 0, 0, 971, 204, 220, 35);

        // 텍스트 3
        mkTxt(orderMenu3Text3, orderMenu3Text3Cont, "plain", 16, 51, 51, 51, 971, 275, 255, 80);

        // 버튼 : 다음 선택
        mkBtn(orderMenu3Btn1, "/images/menu_order/menu_order_btn_next_off.jpg", "/images/menu_order/menu_order_btn_next_on.jpg", 971, 479, 220, 56);
        orderMenu3Btn1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pageMove("order4");
            }
        });

        // 버튼 : 이전 선택
        mkBtn(orderMenu3Btn2, "/images/menu_order/menu_order_btn_prev_off.jpg", "/images/menu_order/menu_order_btn_prev_on.jpg", 971, 543, 220, 56);
        orderMenu3Btn2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                menuSelDto.setSel3Name(null);
                menuSelDto.setSel3Price("0");
                orderMenu3Text3.setText(orderMenu3Text3Cont);

                pageMove("order2");
            }
        });

        // 버튼 : 처음화면으로
        mkBtn(orderMenu3Btn3, "/images/menu_order/menu_order_btn_home_off.jpg", "/images/menu_order/menu_order_btn_home_on.jpg", 971, 623, 220, 56);
        orderMenu3Btn3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gotoMain();
            }
        });


        // 메뉴목록 표시
        showMenuList(3);


        // 프레임에 컴포넌트 표시
        Order3.getContentPane().add(omlScroll3);
        orderMenuList3.setLayout(new GridLayout(orderMenuRows3, 3, 0, 0));
        Order3.getContentPane().add(orderMenu3Text1);
        Order3.getContentPane().add(orderMenu3Text2);
        Order3.getContentPane().add(orderMenu3Text3);
        Order3.getContentPane().add(orderMenu3Btn1);
        Order3.getContentPane().add(orderMenu3Btn2);
        Order3.getContentPane().add(orderMenu3Btn3);





        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // Order4 : 주문하기 04 야채 선택 @노동진
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // 페이지 만들기 (창 이름, 창 타이틀, 배경이미지)
        mkPage(Order4, windowName + " - Step 4. 야채 선택", "/images/menu_order/menu_order4_bg.jpg");


        // 텍스트 1
        mkTxt(orderMenu4Text1, "STEP 4", "bold", 18, 0, 146, 35, 971, 175, 65, 25);

        // 텍스트 2
        mkTxt(orderMenu4Text2, "야채 선택", "bold", 18, 0, 0, 0, 971, 204, 220, 35);

        // 텍스트 3
        mkTxt(orderMenu4Text3, orderMenu4Text3Cont, "plain", 16, 51, 51, 51, 971, 275, 255, 80);

        // 버튼 : 다음 선택
        mkBtn(orderMenu4Btn1, "/images/menu_order/menu_order_btn_next_off.jpg", "/images/menu_order/menu_order_btn_next_on.jpg", 971, 479, 220, 56);
        orderMenu4Btn1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pageMove("order5");
            }
        });

        // 버튼 : 이전 선택
        mkBtn(orderMenu4Btn2, "/images/menu_order/menu_order_btn_prev_off.jpg", "/images/menu_order/menu_order_btn_prev_on.jpg", 971, 543, 220, 56);
        orderMenu4Btn2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                menuSelDto.setSel4Name(null);
                menuSelDto.setSel4Price("0");
                orderMenu4Text3.setText(orderMenu4Text3Cont);

                pageMove("order3");
            }
        });

        // 버튼 : 처음화면으로
        mkBtn(orderMenu4Btn3, "/images/menu_order/menu_order_btn_home_off.jpg", "/images/menu_order/menu_order_btn_home_on.jpg", 971, 623, 220, 56);
        orderMenu4Btn3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gotoMain();
            }
        });


        // 메뉴목록 표시
        showMenuList(4);


        // 프레임에 컴포넌트 표시
        Order4.getContentPane().add(omlScroll4);
        orderMenuList4.setLayout(new GridLayout(orderMenuRows4, 3, 0, 0));
        Order4.getContentPane().add(orderMenu4Text1);
        Order4.getContentPane().add(orderMenu4Text2);
        Order4.getContentPane().add(orderMenu4Text3);
        Order4.getContentPane().add(orderMenu4Btn1);
        Order4.getContentPane().add(orderMenu4Btn2);
        Order4.getContentPane().add(orderMenu4Btn3);





        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // Order5 : 주문하기 05 소스 선택 @노동진
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // 페이지 만들기 (창 이름, 창 타이틀, 배경이미지)
        mkPage(Order5, windowName + " - Step 5. 소스 선택", "/images/menu_order/menu_order5_bg.jpg");


        // 텍스트 1
        mkTxt(orderMenu5Text1, "STEP 5", "bold", 18, 0, 146, 35, 971, 175, 65, 25);

        // 텍스트 2
        mkTxt(orderMenu5Text2, "소스 선택", "bold", 18, 0, 0, 0, 971, 204, 220, 35);

        // 텍스트 3
        mkTxt(orderMenu5Text3, orderMenu5Text3Cont, "plain", 16, 51, 51, 51, 971, 275, 255, 80);

        // 버튼 : 다음 선택
        mkBtn(orderMenu5Btn1, "/images/menu_order/menu_order_btn_next_off.jpg", "/images/menu_order/menu_order_btn_next_on.jpg", 971, 479, 220, 56);
        orderMenu5Btn1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pageMove("order6");
            }
        });

        // 버튼 : 이전 선택
        mkBtn(orderMenu5Btn2, "/images/menu_order/menu_order_btn_prev_off.jpg", "/images/menu_order/menu_order_btn_prev_on.jpg", 971, 543, 220, 56);
        orderMenu5Btn2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                menuSelDto.setSel5Name(null);
                menuSelDto.setSel5Price("0");
                orderMenu5Text3.setText(orderMenu5Text3Cont);

                pageMove("order4");
            }
        });

        // 버튼 : 처음화면으로
        mkBtn(orderMenu5Btn3, "/images/menu_order/menu_order_btn_home_off.jpg", "/images/menu_order/menu_order_btn_home_on.jpg", 971, 623, 220, 56);
        orderMenu5Btn3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gotoMain();
            }
        });


        // 메뉴목록 표시
        showMenuList(5);


        // 프레임에 컴포넌트 표시
        Order5.getContentPane().add(omlScroll5);
        orderMenuList5.setLayout(new GridLayout(orderMenuRows5, 3, 0, 0));
        Order5.getContentPane().add(orderMenu5Text1);
        Order5.getContentPane().add(orderMenu5Text2);
        Order5.getContentPane().add(orderMenu5Text3);
        Order5.getContentPane().add(orderMenu5Btn1);
        Order5.getContentPane().add(orderMenu5Btn2);
        Order5.getContentPane().add(orderMenu5Btn3);





        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // Order6 : 주문하기 06 세트 선택 @노동진
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // 페이지 만들기 (창 이름, 창 타이틀, 배경이미지)
        mkPage(Order6, windowName + " - Step 6. 세트 선택", "/images/menu_order/menu_order6_bg.jpg");


        // 텍스트 1
        mkTxt(orderMenu6Text1, "STEP 6", "bold", 18, 0, 146, 35, 971, 175, 65, 25);

        // 텍스트 2
        mkTxt(orderMenu6Text2, "세트 선택", "bold", 18, 0, 0, 0, 971, 204, 220, 35);

        // 텍스트 3
        mkTxt(orderMenu6Text3, orderMenu6Text3Cont, "plain", 16, 51, 51, 51, 971, 275, 255, 80);

        // 버튼 : 주문확인하기
        mkBtn(orderMenu6Btn1, "/images/menu_order/menu_order_btn_cart_off.jpg", "/images/menu_order/menu_order_btn_cart_on.jpg", 971, 479, 220, 56);
        orderMenu6Btn1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(menuSelDto.getSel6Name() != null){
                    orderAddCart();
                    pageMove("cart");
                }else{
                    JOptionPane.showMessageDialog(null, "세트 옵션을 선택해 주세요.");
                }
            }
        });

        // 버튼 : 이전 선택
        mkBtn(orderMenu6Btn2, "/images/menu_order/menu_order_btn_prev_off.jpg", "/images/menu_order/menu_order_btn_prev_on.jpg", 971, 543, 220, 56);
        orderMenu6Btn2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                menuSelDto.setSel6Name(null);
                menuSelDto.setSel6Price("0");
                orderMenu6Text3.setText(orderMenu6Text3Cont);

                pageMove("order5");
            }
        });

        // 버튼 : 처음화면으로
        mkBtn(orderMenu6Btn3, "/images/menu_order/menu_order_btn_home_off.jpg", "/images/menu_order/menu_order_btn_home_on.jpg", 971, 623, 220, 56);
        orderMenu6Btn3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gotoMain();
            }
        });


        // 선택안함 버튼 추가
        Image changeImg6_none = new ImageIcon(Main_UI.class.getResource("/images/common/menu_select_none.jpg")).getImage().getScaledInstance(228, 160, Image.SCALE_SMOOTH);
        JButton bt6_none = new JButton("<html><center><p>선택안함</p><p color=\"green\">0원</p></center></html>", new ImageIcon(changeImg6_none));
        bt6_none.setVerticalTextPosition(JButton.BOTTOM);
        bt6_none.setHorizontalTextPosition(JButton.CENTER);
        bt6_none.setBorderPainted(false);
        bt6_none.setPreferredSize(new Dimension(228, 275));
        bt6_none.setForeground(new Color(51, 51, 51));
        bt6_none.setBackground(new Color(255, 255, 255));
        bt6_none.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        orderMenuList6.add(bt6_none);

        bt6_none.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                menuSelDto.setSel6Name("선택안함");
                menuSelDto.setSel6Price("0");
                orderMenu6Text3.setText("<html>선택안함<br>(0원)</html>");
            }
        });

        // 메뉴목록 표시
        showMenuList(6);


        // 프레임에 컴포넌트 표시
        Order6.getContentPane().add(omlScroll6);
        orderMenuList6.setLayout(new GridLayout((orderMenuRows6), 3, 0, 0));
        Order6.getContentPane().add(orderMenu6Text1);
        Order6.getContentPane().add(orderMenu6Text2);
        Order6.getContentPane().add(orderMenu6Text3);
        Order6.getContentPane().add(orderMenu6Btn1);
        Order6.getContentPane().add(orderMenu6Btn2);
        Order6.getContentPane().add(orderMenu6Btn3);





        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // OrderCart : 주문 확인 @노동진
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // 페이지 만들기 (창 이름, 창 타이틀, 배경이미지)
        mkPage(OrderCart, windowName + " - 주문확인", "/images/menu_cart/menu_cart_bg.jpg");


        // 텍스트 1
        mkTxt(orderCartText1, "총 주문금액", "bold", 18, 0, 146, 35, 971, 175, 220, 25);

        // 텍스트 2
        mkTxt(orderCartText2, "총 0원", "bold", 28, 0, 0, 0, 971, 204, 220, 35);

        // 텍스트 3
        mkTxt(orderCartText3, "식사위치 : ", "bold", 16, 51, 51, 51, 971, 275, 220, 30);

        // 텍스트 4
        mkTxt(orderCartText4, "결제방법 : ", "bold", 16, 51, 51, 51, 971, 348, 220, 30);


        // 라디오버튼 1 : 식사장소
        orderCartRadio1_1 = new JRadioButton("매장식사", true);
        orderCartRadio1_1.setBounds(971, 297, 90, 30);
        orderCartRadio1_1.setBackground(Color.WHITE);
        orderCartRadio1_1.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

        orderCartRadio1_2 = new JRadioButton("포장");
        orderCartRadio1_2.setBounds(1070, 297, 60, 30);
        orderCartRadio1_2.setBackground(Color.WHITE);
        orderCartRadio1_2.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

        ButtonGroup orderCartRgroup1 = new ButtonGroup();
        orderCartRgroup1.add(orderCartRadio1_1);
        orderCartRgroup1.add(orderCartRadio1_2);


        // 라디오버튼 2 : 결제방법
        orderCartRadio2_1 = new JRadioButton("현금", true);
        orderCartRadio2_1.setBounds(971, 370, 60, 30);
        orderCartRadio2_1.setBackground(Color.WHITE);
        orderCartRadio2_1.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

        orderCartRadio2_2 = new JRadioButton("카드");
        orderCartRadio2_2.setBounds(1040, 370, 60, 30);
        orderCartRadio2_2.setBackground(Color.WHITE);
        orderCartRadio2_2.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

        orderCartRadio2_3 = new JRadioButton("삼성페이");
        orderCartRadio2_3.setBounds(1110, 370, 90, 30);
        orderCartRadio2_3.setBackground(Color.WHITE);
        orderCartRadio2_3.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

        orderCartRadio2_4 = new JRadioButton("네이버페이");
        orderCartRadio2_4.setBounds(971, 400, 105, 30);
        orderCartRadio2_4.setBackground(Color.WHITE);
        orderCartRadio2_4.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

        orderCartRadio2_5 = new JRadioButton("카카오페이");
        orderCartRadio2_5.setBounds(1080, 400, 105, 30);
        orderCartRadio2_5.setBackground(Color.WHITE);
        orderCartRadio2_5.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

        ButtonGroup orderCartRgroup2 = new ButtonGroup();
        orderCartRgroup2.add(orderCartRadio2_1);
        orderCartRgroup2.add(orderCartRadio2_2);
        orderCartRgroup2.add(orderCartRadio2_3);
        orderCartRgroup2.add(orderCartRadio2_4);
        orderCartRgroup2.add(orderCartRadio2_5);


        // 버튼 : 메뉴 전체 삭제
        mkBtn(orderCartBtn1, "/images/menu_cart/menu_cart_btn1_off.jpg", "/images/menu_cart/menu_cart_btn1_on.jpg", 91, 641, 138, 38);
        orderCartBtn1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "선택하신 메뉴들을 전부 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
                if(result == 0){
                    cartReset();
                    pageMove("main");
                }else{
                    return;
                }
            }
        });

        // 버튼 : 선택 메뉴 삭제
        mkBtn(orderCartBtn2, "/images/menu_cart/menu_cart_btn2_off.jpg", "/images/menu_cart/menu_cart_btn2_on.jpg", 240, 641, 141, 38);
        orderCartBtn2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 표시되는 목록 테이블에서 삭제
                int orderCartTableRow = orderCartListTable.getSelectedRow();
                orderCartListModel.removeRow(orderCartTableRow);

                // 배열에서 삭제
                orderCartMenu.remove(orderCartTableRow);

                // 장바구니 새로고침
                refreshCart();
            }
        });

        // 버튼 : 메뉴 추가하기
        mkBtn(orderCartBtn3, "/images/menu_cart/menu_cart_btn3_off.jpg", "/images/menu_cart/menu_cart_btn3_on.jpg", 705, 641, 134, 38);
        orderCartBtn3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectReset();

                pageMove("order1");
            }
        });

        // 버튼 : 주문&결제완료
        mkBtn(orderCartBtn4, "/images/menu_cart/menu_cart_btn_pay_off.jpg", "/images/menu_cart/menu_cart_btn_pay_on.jpg", 971, 543, 220, 56);
        orderCartBtn4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setOrderDone();
            }
        });

        // 버튼 : 처음화면으로
        mkBtn(orderCartBtn5, "/images/menu_order/menu_order_btn_home_off.jpg", "/images/menu_order/menu_order_btn_home_on.jpg", 971, 623, 220, 56);
        orderCartBtn5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gotoMain();
            }
        });


        // 장바구니 목록 테이블
        String[] orderCartListHeader = {"No", "메뉴", "선택 옵션", "세트", "합계금액"};
        orderCartListModel = new DefaultTableModel(orderCartListHeader, 0) {
            private static final long serialVersionUID = 1L;

            // Jtable 내용 편집 안되게
            public boolean isCellEditable(int i, int c) {
                return false;
            }
        };
        orderCartListTable = new JTable(orderCartListModel);
        orderCartListTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        orderCartListTable.getColumnModel().getColumn(1).setPreferredWidth(170);
        orderCartListTable.getColumnModel().getColumn(2).setPreferredWidth(295);
        orderCartListTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        orderCartListTable.getColumnModel().getColumn(4).setPreferredWidth(90);
        orderCartListTable.setBackground(Color.WHITE);
        orderCartListTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        orderCartListTable.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
        orderCartListTable.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
        statisticsModelCentered(orderCartListTable);

        JScrollPane orderCartListScroll = new JScrollPane(orderCartListTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        orderCartListScroll.setBorder(null);
        orderCartListScroll.getVerticalScrollBar().setUnitIncrement(winScrollSpeed);
        orderCartListScroll.setBounds(91, 171, 748, 450);




        // 프레임에 컴포넌트 표시
        OrderCart.getContentPane().add(orderCartListScroll);
        OrderCart.getContentPane().add(orderCartText1);
        OrderCart.getContentPane().add(orderCartText2);
        OrderCart.getContentPane().add(orderCartText3);
        OrderCart.getContentPane().add(orderCartText4);
        OrderCart.getContentPane().add(orderCartRadio1_1);
        OrderCart.getContentPane().add(orderCartRadio1_2);
        OrderCart.getContentPane().add(orderCartRadio2_1);
        OrderCart.getContentPane().add(orderCartRadio2_2);
        OrderCart.getContentPane().add(orderCartRadio2_3);
        OrderCart.getContentPane().add(orderCartRadio2_4);
        OrderCart.getContentPane().add(orderCartRadio2_5);
        OrderCart.getContentPane().add(orderCartBtn1);
        OrderCart.getContentPane().add(orderCartBtn2);
        OrderCart.getContentPane().add(orderCartBtn3);
        OrderCart.getContentPane().add(orderCartBtn4);
        OrderCart.getContentPane().add(orderCartBtn5);







        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // OrderDone : 주문 완료 @노동진
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // 페이지 만들기 (JFrame 이름, 창 타이틀, 배경이미지)
        mkPage(OrderDone, windowName + " - 주문완료", "/images/menu_done/menu_done_bg.jpg");


        // 버튼 : 처음화면으로
        mkBtn(orderDoneBtn, "/images/menu_done/menu_done_btn_off.jpg", "/images/menu_done/menu_done_btn_on.jpg", 515, 635, 250, 64);
        orderDoneBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pageMove("main");
            }
        });


        // 프레임에 컴포넌트 표시
        OrderDone.getContentPane().add(orderDoneBtn);






        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // Login : 회원 로그인 @전영주
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // 페이지 만들기 (JFrame 이름, 창 타이틀, 배경이미지)
        mkPage(Login, windowName + " - 회원 로그인", "/images/member_login/login_bg.jpg");


        mkTxt(loginLbl1, "아이디", "bold", 15, 51, 51, 51, 450, 320, 100, 45);
        mkTxt(loginLbl2, "비밀번호", "bold", 15, 51, 51, 51, 450, 370, 100, 45);
        mkTxt(loginLbl3, "LOGIN", "bold", 45, 51, 51, 51, 565, 151, 170, 60);
        mkTxt(loginLbl4, "써브웨이 회원으로 로그인하시면 제공하는", "plain", 16, 100, 100, 100, 500, 221, 400, 26);
        mkTxt(loginLbl5, "다양한 서비스를 이용할 수 있습니다.", "plain", 16, 100, 100, 100, 520, 243, 400, 26);

        mkInp(loginJtf1, "", 100, "plain", 16, "left", 570, 310, 260, 45);
        loginJtf2 = new JPasswordField();
        ((JPasswordField) loginJtf2).setEchoChar('*');
        loginJtf2.setBounds(570, 360, 260, 45);
        loginJtf2.setBackground(Color.WHITE);
        
        // 아이디텍스트필드에서 엔터키로 로그인
        loginJtf1.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				 if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					 login(LoginType);
				 }
			}

			@Override
			public void keyReleased(KeyEvent e) {}
        });
        
        
        
        // 비밀번호텍스트필드에서 엔터키로 로그인
        loginJtf2.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				 if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					 login(LoginType);
				 }
			}

			@Override
			public void keyReleased(KeyEvent e) {}
				
		});

		// 로그인 버튼
        mkBtn(loginBtn1, "/images/member_login/login_btn1_off.jpg", "/images/member_login/login_btn1_on.jpg", 450, 438, 390, 72);
		loginBtn1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				login(LoginType);
			}
		});
		
		

		// 처음화면으로 버튼
		mkBtn(loginBtn2, "/images/member_login/login_btn2_off.jpg", "/images/member_login/login_btn2_on.jpg", 450, 527, 390, 72);
		loginBtn2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				pageMove("main");
			}
		});


		Login.getContentPane().add(loginBtn1);
		Login.getContentPane().add(loginBtn2);
		Login.getContentPane().add(loginLbl1);
		Login.getContentPane().add(loginJtf1);
		Login.getContentPane().add(loginLbl2);
		Login.getContentPane().add(loginJtf2);
		Login.getContentPane().add(loginLbl3);
		Login.getContentPane().add(loginLbl4);
		Login.getContentPane().add(loginLbl5);







        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // Join : 회원 가입 @전영주
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // 페이지 만들기 (JFrame 이름, 창 타이틀, 배경이미지)
        mkPage(Join, windowName + " - 회원가입 화면", "/images/member_join/join_bg.jpg");

        // 회원가입 버튼
        mkBtn(joinBtn1, "/images/member_join/join_btn1_off.jpg", "/images/member_join/join_btn1_on.jpg", 450, 505, 390, 72);
		joinBtn1.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
				joinInsert();
			}
		});

		// 처음화면으로 버튼
		mkBtn(joinBtn2, "/images/member_join/join_btn2_off.jpg", "/images/member_join/join_btn2_on.jpg", 450, 588, 390, 72);
		joinBtn2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pageMove("main");
			}
		});


		mkTxt(joinLbl1, "아이디", "bold", 15, 51, 51, 51, 450, 236, 100, 45);
        mkTxt(joinLbl2, "비밀번호", "bold", 15, 51, 51, 51, 450, 286, 100, 45);
        mkTxt(joinLbl3, "비밀번호 확인", "bold", 15, 51, 51, 51, 450, 336, 100, 45);
        mkTxt(joinLbl4, "이름", "bold", 15, 51, 51, 51, 450, 386, 100, 45);
        mkTxt(joinLbl5, "연락처", "bold", 15, 51, 51, 51, 450, 436, 100, 45);
        mkTxt(joinLbl6, "JOIN", "bold", 45, 0, 0, 0, 595, 83, 130, 60);
        mkTxt(joinLbl7, "써브웨이 회원으로 회원가입하시면 제공하는", "plain", 16, 100, 100, 100, 500, 153, 400, 30);
        mkTxt(joinLbl8, "다양한 서비스를 이용할 수 있습니다.", "plain", 16, 100, 100, 100, 520, 175, 400, 30);

        mkInp(joinJtf1, "", 100, "plain", 15, "left", 570, 226, 260, 45);
        mkInp(joinJtf4, "", 100, "plain", 15, "left", 570, 376, 260, 45);
        mkInp(joinJtf5, "", 100, "plain", 15, "left", 570, 426, 260, 45);

        joinJtf2 = new JPasswordField();
        ((JPasswordField) joinJtf2).setEchoChar('*');
        joinJtf2.setBounds(570, 276, 260, 45);
        joinJtf2.setBackground(Color.WHITE);

        joinJtf3 = new JPasswordField();
        ((JPasswordField) joinJtf3).setEchoChar('*');
        joinJtf3.setBounds(570, 326, 260, 45);
        joinJtf3.setBackground(Color.WHITE);


        Join.getContentPane().add(joinBtn1);
		Join.getContentPane().add(joinBtn2);
		Join.getContentPane().add(joinLbl1);
		Join.getContentPane().add(joinJtf1);
		Join.getContentPane().add(joinLbl2);
		Join.getContentPane().add(joinJtf2);
		Join.getContentPane().add(joinLbl3);
		Join.getContentPane().add(joinJtf3);
		Join.getContentPane().add(joinLbl4);
		Join.getContentPane().add(joinJtf4);
		Join.getContentPane().add(joinLbl5);
		Join.getContentPane().add(joinJtf5);	
		Join.getContentPane().add(joinLbl6);
		Join.getContentPane().add(joinLbl7);
		Join.getContentPane().add(joinLbl8);







        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // SubOrder : 관리자페이지 - 주문관리 @공현진
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // 페이지 만들기 (JFrame 이름, 창 타이틀, 배경이미지)
        mkPage(SubOrder, windowName + " 관리자화면 - 주문관리", "/images/admin_order/admin_order_bg.jpg");


        // 날짜 별 조회 버튼
        mkBtn(subOrderBtn5, "/images/admin_order/admin_order_btn1_off.jpg", "/images/admin_order/admin_order_btn1_on.jpg", 274, 155, 122, 38);

        // 전체 조회 버튼
        mkBtn(subOrderBtn6, "/images/admin_order/admin_order_btn2_off.jpg", "/images/admin_order/admin_order_btn2_on.jpg", 408, 155, 107, 38);

        // 선택 주문 삭제 버튼
        mkBtn(subOrderBtn7, "/images/admin_order/admin_order_btn3_off.jpg", "/images/admin_order/admin_order_btn3_on.jpg", 1067, 155, 138, 38);


        // 주문 관리 버튼
        mkBtn(subOrderBtn1, "/images/admin_nav/admin_nav_btn1_on.jpg", "/images/admin_nav/admin_nav_btn1_on.jpg", 899, 20, 157, 38);
        subOrderBtn1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pageMove("suborder");
				
				String odate = todaySet(subOrderModel, dateChooserOrder); // 자동 선택 값 오늘 날짜로 변경
				connect();
				subOrderSelect(odate, "order");
				subOrderSum(odate);
				subOrderCount(odate);
				autoAdjustColumnWidth(subOrderTable);
				autoAdjustRowHeights(subOrderTable);
			}
		});

        // 메뉴 관리 버튼
        mkBtn(subOrderBtn2, "/images/admin_nav/admin_nav_btn2_off.jpg", "/images/admin_nav/admin_nav_btn2_on.jpg", 1062, 20, 157, 38);
        subOrderBtn2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	pageMove("submenu");

            	connect(); 
            	menuModifyselect();
            	conClose(rs, pstmt);
			}
		});

        // 회원 관리 버튼
        mkBtn(subOrderBtn3, "/images/admin_nav/admin_nav_btn3_off.jpg", "/images/admin_nav/admin_nav_btn3_on.jpg", 899, 63, 157, 38);
        subOrderBtn3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				pageMove("subuser");

				connect();
				userSelect();
			}
		});

        // 판매 통계 버튼
        mkBtn(subOrderBtn4, "/images/admin_nav/admin_nav_btn4_off.jpg", "/images/admin_nav/admin_nav_btn4_on.jpg", 1062, 63, 157, 38);
        subOrderBtn4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				pageMove("statistics");
				
				String odate = todaySet(statisticsModel1, dateChooser); // 자동 선택 값 오늘 날짜로 변경
				connect();
				statisticsTable.setModel(statisticsModel1);
				statisticsTable.getModel();
				subOrderSelect(odate, "statistics");
				statisticsModelCentered(statisticsTable);
				autoAdjustColumnWidth(statisticsTable);
				autoAdjustRowHeights(statisticsTable);
				
			}
		});

        // X 버튼
        mkBtn(subOrderBtn8, "/images/admin_nav/admin_nav_close_off.jpg", "/images/admin_nav/admin_nav_close_on.jpg", 12, 10, 24, 24);
        subOrderBtn8.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				pageMove("main");
			}
		});


    	dateEditorOrder.setHorizontalAlignment(JTextField.CENTER);
    	dateChooserOrder.setDateFormatString("yy/MM/dd");
    	dateChooserOrder.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
    	dateChooserOrder.setSize(new Dimension(105, 0));

    	dateChooserOrder.setBounds(80, 155, 167, 30);
        dateChooserOrder.getJCalendar().setPreferredSize(new Dimension(250, 250));
        dateChooserOrder.getCalendarButton().setBackground(Color.white);

        SubOrder.add(dateChooserOrder);


       	// 헤더 만들기
   		String[] subOrderheader = {"주문번호", "식사구분", "주문자", "주문 내용", "결제방법", "결제금액", "사용적립금", "주문일자"};

        // DefaultTableModel : 테이블을 만들고 난 후 데이터를 넣고 추가, 수정, 삭제 시에도 변경이 가능한 컴포넌트.
   		subOrderModel = new DefaultTableModel(subOrderheader, 0){
   			// Jtable 내용 편집 안되게
   			private static final long serialVersionUID = 1L;
   			public boolean isCellEditable(int i, int c) {
   				return false;
   			}
   		};
   		
		// JTable : 일단은 테이블을 만들고 난 후 데이터를 넣으면 한 번 만든 테이블의 데이터는 변경이 불가능한 컴포넌트. 추가, 수정, 삭제가 불가능한 컴포넌트.
		// 컬럼 사이즈 조절
		subOrderTable = new JTable(subOrderModel);
		statisticsModelCentered(subOrderTable);
		subOrderTable.setFont(new Font("맑은 고딕", Font.PLAIN, 13));	
		subOrderTable.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
		subOrderTable.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가

		// 스크롤
		JScrollPane subOrderjsp = new JScrollPane(
				subOrderTable,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		subOrderjsp.setBounds(20, 10, 1128, 459);
	
		// 총 판매 금액 문구
		mkTxt(subOrderlblNewLabel, "총 판매 금액 : ", "bold", 17, 0, 113, 255, 586, 155, 122, 38);
		subOrderlblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		SubOrder.getContentPane().add(subOrderlblNewLabel);
		
		// 금액 계산 창
		subOrderTextPane.setForeground(new Color(0, 113, 255));
		subOrderTextPane.setFont(new Font("맑은 고딕 Semilight", Font.BOLD, 17));
		subOrderTextPane.setBounds(720, 153, 122, 25);
		SubOrder.getContentPane().add(subOrderTextPane);
		
		// 몇 건인지 보여주는 textpane
		subOrderCountTextPane.setForeground(new Color(0, 113, 255));
		subOrderCountTextPane.setFont(new Font("맑은 고딕 Semilight", Font.BOLD, 15));
		subOrderCountTextPane.setBounds(850, 154, 100, 25);
		SubOrder.getContentPane().add(subOrderCountTextPane);


		// 프레임에 버튼 넣기
		SubOrder.getContentPane().add(subOrderBtn1);
		SubOrder.getContentPane().add(subOrderBtn2);
		SubOrder.getContentPane().add(subOrderBtn3);
		SubOrder.getContentPane().add(subOrderBtn4);
		SubOrder.getContentPane().add(subOrderBtn5);
		SubOrder.getContentPane().add(subOrderBtn6);
		SubOrder.getContentPane().add(subOrderBtn7);
		SubOrder.getContentPane().add(subOrderBtn8);
		SubOrder.getContentPane().add(subOrderSelectedDate);


		// 파넬에 jsp 올리기.
		JPanel subOrderpanel = new JPanel();
		subOrderpanel.setBackground(new Color(255, 255, 255));
		subOrderpanel.setLayout(null);
		subOrderpanel.add(subOrderjsp);
		subOrderpanel.setBounds(57, 220, 1153, 479);
		SubOrder.getContentPane().add(subOrderpanel);

		// 안내 문구 JLabel
		JLabel notSaved = new JLabel("※ 적립금 제외 총 매출 금액");
		notSaved.setFont(new Font("맑은 고딕 Semilight", Font.PLAIN, 12));
		notSaved.setBounds(720, 182, 155, 15);
		SubOrder.getContentPane().add(notSaved);


        mkTxt(subOrderSelectedDate, "※ 조회할 날짜를 선택하세요", "plain", 12, 0, 0, 0, 80, 190, 190, 15);
        subOrderSelectedDate.setHorizontalAlignment(SwingConstants.LEFT);

		// 날짜 별 조회 (검색) + 선택된 날 판매 금액 + 선택된 날 주문 수량 이벤트 처리 
	    subOrderBtn5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

				// 내용 초기화
				subOrderModel.setRowCount(0);
				
				// 연결 메소드
				connect();
				

				if(dateChooserOrder.getDate() == null) {
					JOptionPane.showMessageDialog(null, "조회할 날짜를 선택해주세요.");
					return;
				}
					
				String odate = dateFormat.format(dateChooserOrder.getDate());		
	  
				// 날짜 별 조회 메서드
				subOrderSelect(odate, "order");
		
				// 선택된 날 판매 금액
				subOrderSum(odate);
		
				// 선택된 날 주문 수량
				subOrderCount(odate);

				autoAdjustColumnWidth(subOrderTable);
				autoAdjustRowHeights(subOrderTable);
			}
		});
	


	    // 전체 조회 버튼 클릭 시 전체 조회 + 총 판매 금액 + 총 주문 수량
	    subOrderBtn6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	dateChooserOrder.setCalendar(null);
				// 연결 메서드 호출
				connect();
				// 전체 조회하는 메서드 호출 
				subOrderSelect("order");

				// 총 판매 금액, 주문 수량 조회하는 메서드 호출
				subOrderSum();
				subOrderCount();

				autoAdjustColumnWidth(subOrderTable);
				autoAdjustRowHeights(subOrderTable);
			}
		});

	

	    // 삭제(btn3) 버튼 클릭 시 JTable의 특정 행을 EMP 테이블에서 삭제하는 작업을 진행.
	    subOrderBtn7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	
            	if(subOrderTable.getSelectedRow() == -1) {
        			JOptionPane.showMessageDialog(null, "삭제 할 주문을 선택 후 클릭해 주세요.");
        			return;
        		}
            	
            	int result = JOptionPane.showConfirmDialog(null, "정말로 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);

				// 만약 창 닫기를 클릭하거나 취소 버튼을 클릭하지 않았다면
				if(result == JOptionPane.CLOSED_OPTION) {
					JOptionPane.showMessageDialog(null, "창 닫기를 클릭하셨습니다.");
				} else if (result == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(null, "취소 버튼을 클릭하셨습니다.");
				} else {

					// 연결 메서드 실행
					connect();

					// 테이블의 특정 행을 클릭했을 때 해당 테이블의
					// 행의 값을 가져오는 메서드
					int row = subOrderTable.getSelectedRow();

					// 해당 행의 값을 가져올 때 해당 행의 0번째
					// 열의 값(번호)을 가져오는 메서드.
					String no = (String) (subOrderModel.getValueAt(row, 0));

					// 데이터 베이스에서 특정 행을 삭제시키는 메서드 호출.
					subOrderDeleteo(no);

					// 실제로 테이블 상의 클릭한 한 행을 삭제
					subOrderModel.removeRow(row);


					// 삭제 후 재조회 메서드
					subOrderSelect("order");

					// 총 매출 금액 조회 메서드
					subOrderSum();

					// 총 판매 수량 조회 메서드
					subOrderCount();

					autoAdjustColumnWidth(subOrderTable);
					autoAdjustRowHeights(subOrderTable);
				}
			}
		});	






        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // SubUser : 관리자페이지 - 회원관리 @전영주
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // 페이지 만들기 (JFrame 이름, 창 타이틀, 배경이미지)
        mkPage(SubUser, windowName + "관리자화면 - 회원관리", "/images/admin_member/admin_member_bg.jpg");


        // 선택회원삭제
		mkBtn(subUserBtn6, "/images/admin_member/admin_member_btn1_off.jpg", "/images/admin_member/admin_member_btn1_on.jpg", 64, 653, 138, 38);

		// 선택회원수정
		mkBtn(subUserBtn7, "/images/admin_member/admin_member_btn2_off.jpg", "/images/admin_member/admin_member_btn2_on.jpg", 214, 653, 139, 38);

		// 새로운 회원추가
		mkBtn(subUserBtn8,"/images/admin_member/admin_member_btn3_off.jpg", "/images/admin_member/admin_member_btn3_on.jpg", 676, 653, 153, 38);

		// 작성완료
		mkBtn(subUserBtn9, "/images/admin_member/admin_member_btn4_off.jpg", "/images/admin_member/admin_member_btn4_on.jpg", 951, 569, 260, 56);

		// 입력초기화
		mkBtn(subUserBtn10, "/images/admin_member/admin_member_btn5_off.jpg", "/images/admin_member/admin_member_btn5_on.jpg", 951, 635, 260, 56);


        // 우측 상단 버튼
        // 주문관리 899, 20, 157, 38
        mkBtn(subUserBtn1, "/images/admin_nav/admin_nav_btn1_off.jpg", "/images/admin_nav/admin_nav_btn1_on.jpg", 899, 20, 157, 38);
        subUserBtn1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                pageMove("suborder");
                				
				String odate = todaySet(subOrderModel, dateChooserOrder); // 자동 선택 값 오늘 날짜로 변경
				connect();
				subOrderSelect(odate, "order");
				subOrderSum(odate);
				subOrderCount(odate);
				autoAdjustColumnWidth(subOrderTable);
				autoAdjustRowHeights(subOrderTable);
				
			}
		});

        // 메뉴관리 1062, 20, 157, 38
        mkBtn(subUserBtn2, "/images/admin_nav/admin_nav_btn2_off.jpg", "/images/admin_nav/admin_nav_btn2_on.jpg", 1062, 20, 157, 38);
        subUserBtn2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
        		pageMove("submenu");

                connect(); 
                menuModifyselect();
                conClose(rs, pstmt);
			}
		});

        // 회원관리 899, 63, 157, 38
        mkBtn(subUserBtn3, "/images/admin_nav/admin_nav_btn3_on.jpg", "/images/admin_nav/admin_nav_btn3_on.jpg", 899, 63, 157, 38);
        subUserBtn3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				pageMove("subuser");

				connect();
				userSelect();
			}
		});

        // 판매통계 1062, 63, 157, 38
        mkBtn(subUserBtn4, "/images/admin_nav/admin_nav_btn4_off.jpg", "/images/admin_nav/admin_nav_btn4_on.jpg", 1062, 63, 157, 38);
        subUserBtn4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				pageMove("statistics");
				
				String odate = todaySet(statisticsModel1, dateChooser); // 자동 선택 값 오늘 날짜로 변경
				connect();
				statisticsTable.setModel(statisticsModel1);
				statisticsTable.getModel();
				subOrderSelect(odate, "statistics");
				statisticsModelCentered(statisticsTable);
				autoAdjustColumnWidth(statisticsTable);
				autoAdjustRowHeights(statisticsTable);
				
			}
		});

        // X 버튼 12, 10, 24, 24
        mkBtn(subUserBtn5, "/images/admin_nav/admin_nav_close_off.jpg", "/images/admin_nav/admin_nav_close_on.jpg", 12, 10, 24, 24);
        subUserBtn5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	pageMove("main");
			}
		});



		mkTxt(subUserLbl1, "회원구분", "plain", 15, 51, 51, 51, 960, 248, 86, 31);
		mkTxt(subUserLbl2, "회원아이디", "plain", 15, 51, 51, 51, 960, 301, 86, 31);
		mkTxt(subUserLbl3, "회원비번", "plain", 15, 51, 51, 51, 960, 353, 86, 31);
		mkTxt(subUserLbl4, "회원이름", "plain", 15, 51, 51, 51, 960, 406, 86, 31);
		mkTxt(subUserLbl5, "회원연락처", "plain", 15, 51, 51, 51, 960, 459, 86, 31);
		mkTxt(subUserLbl6, "회원적립금", "plain", 15, 51, 51, 51, 960, 510, 86, 31);

		String[] memOrNonmem = { "회원", "관리자" };
		userComboBox = new JComboBox<String>(memOrNonmem);
		userComboBox.setSelectedItem("회원");
		userComboBox.setBounds(1040, 244, 160, 31);

		mkInp(subUserJtf1, "", 100, "plain", 15, "left", 1040, 295, 160, 31);
		subUserJtf2 = new JPasswordField();
        ((JPasswordField) subUserJtf2).setEchoChar('*');
        subUserJtf2.setBounds(1040, 347, 160, 31);
        subUserJtf2.setBackground(Color.WHITE);
		mkInp(subUserJtf3, "", 100, "plain", 15, "left", 1040, 400, 160, 31);
		mkInp(subUserJtf4, "", 100, "plain", 15, "left", 1040, 453, 160, 31);
		mkInp(subUserJtf5, "", 100, "plain", 15, "left", 1040, 504, 160, 31);


		SubUser.getContentPane().add(subUserBtn1);
		SubUser.getContentPane().add(subUserBtn2);
		SubUser.getContentPane().add(subUserBtn3);
		SubUser.getContentPane().add(subUserBtn4);
		SubUser.getContentPane().add(subUserBtn5);
		SubUser.getContentPane().add(subUserBtn6);
		SubUser.getContentPane().add(subUserBtn7);
		SubUser.getContentPane().add(subUserBtn8);
		SubUser.getContentPane().add(subUserBtn9);
		SubUser.getContentPane().add(subUserBtn10);
		SubUser.getContentPane().add(subUserLbl1);
		SubUser.getContentPane().add(userComboBox);
		SubUser.getContentPane().add(subUserLbl2);
		SubUser.getContentPane().add(subUserJtf1);
		SubUser.getContentPane().add(subUserLbl3);
		SubUser.getContentPane().add(subUserJtf2);
		SubUser.getContentPane().add(subUserLbl4);
		SubUser.getContentPane().add(subUserJtf3);
		SubUser.getContentPane().add(subUserLbl5);
		SubUser.getContentPane().add(subUserJtf4);
		SubUser.getContentPane().add(subUserLbl6);
		SubUser.getContentPane().add(subUserJtf5);



        String[] subUserListHeader = {"회원구분", "아이디", "이름", "전화번호", "적립금", "가입일자"};
        userModel = new DefaultTableModel(subUserListHeader, 0) {
            private static final long serialVersionUID = 1L;

            // Jtable 내용 편집 안되게
            public boolean isCellEditable(int i, int c) {
                return false;
            }
        };
        userTable = new JTable(userModel);
//        userTable.getColumnModel().getColumn(0).setPreferredWidth(110);
//        userTable.getColumnModel().getColumn(1).setPreferredWidth(130);
//        userTable.getColumnModel().getColumn(2).setPreferredWidth(110);
//        userTable.getColumnModel().getColumn(3).setPreferredWidth(160);
//        userTable.getColumnModel().getColumn(4).setPreferredWidth(100);
//        userTable.getColumnModel().getColumn(5).setPreferredWidth(190);
//        userTable.setRowHeight(30);
//        userTable.setBackground(Color.WHITE);
//        userTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        statisticsModelCentered(userTable);
		autoAdjustColumnWidth(userTable);
		autoAdjustRowHeights(userTable);
		userTable.setRowHeight(30);
        userTable.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
        userTable.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
        

        JScrollPane UserScrollPane = new JScrollPane(userTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        UserScrollPane.setBorder(null);
        UserScrollPane.getVerticalScrollBar().setUnitIncrement(winScrollSpeed);
        UserScrollPane.setBounds(60, 140, 810, 490);

        SubUser.getContentPane().add(UserScrollPane);


		// 선택회원 삭제 버튼 - 선택한 회원이 삭제된다.
		subUserBtn6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
        		if(userTable.getSelectedRow() == -1) {
        			JOptionPane.showMessageDialog(null, "삭제 할 회원을 선택 후 클릭해 주세요.");
        			return;
        		}

				int result = JOptionPane.showConfirmDialog(null, "정말로 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.CLOSED_OPTION) {
					JOptionPane.showMessageDialog(null, "취소를 클릭하셨습니다.");
				}else if(result == JOptionPane.NO_OPTION){
					JOptionPane.showMessageDialog(null, "취소버튼을 클릭하셨습니다.");
				}else {
					connect();

					userDelete();

					userSelect();
				}
			}
		});
		
		// 회원 작성 완료 버튼에 3가지 기능을 추가 합니다.
		subUserBtn9.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				if(subUserCheck=="") {
					subUserCheck = "subUserAlert";
				}
				switch (subUserCheck) {
					case "subUserAlert":
						JOptionPane.showMessageDialog(null, "추가,수정버튼을 먼저 클릭해주세요!", "클릭 입력", JOptionPane.PLAIN_MESSAGE);
						break;

					//추가기능	
					case "subUserAdd" :
						connect();
						UserInsert();
						JOptionPane.showMessageDialog(null, "회원 추가 되었습니다.", "메뉴 추가 완료", JOptionPane.PLAIN_MESSAGE);
						userSelect();
						subUserClear();
						subUserCheck = "subUserAlert";
						break;

					//수정기능
					case "subUserCorrect":
						connect();
						userUpdate();
						subUserClear();
						userSelect();
						subUserCheck = "subUserAlert";
						break;
				}
			}
		});


		// 선택회원수정 버튼 - 회원타입, 비밀번호, 이름, 핸드폰번호, 적립금 수정 가능하다. 아이디는 수정할 수 없다.
		// 선택한 회원을 수정할 수 있게 텍스트 입력창에 띄운 후 수정하고 작성완료버튼(버튼9)을 누르면 수정한 데이터가 저장된다.
		subUserBtn7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				connect();
				
				if(userTable.getSelectedRow() == -1) {
        			JOptionPane.showMessageDialog(null, "수정할 회원을 선택 후 클릭해 주세요.");
        			return;
        		}
				int row = userTable.getSelectedRow();
				userComboBox.setSelectedItem(userModel.getValueAt(row, 0));

				subUserJtf1.setText(userModel.getValueAt(row, 1).toString());
				subUserJtf2.setText("");
				subUserJtf3.setText(userModel.getValueAt(row, 2).toString());
				subUserJtf4.setText(userModel.getValueAt(row, 3).toString());
				subUserJtf5.setText(userModel.getValueAt(row, 4).toString().replaceAll("[^0-9]", ""));
				subUserCheck = "subUserCorrect";
			}
		});


		// 새로운 회원추가 버튼 - 텍스트 입력란에 아이디 부터 입력할수있게 한다.
		// 새로운 회원추가 버튼(버튼8)을 누른 후 입력하고 작성완료(버튼9)버튼을 클릭하면 새로운 데이터가 추가된다.
		subUserBtn8.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				subUserClear();
				subUserJtf1.requestFocus();
				subUserCheck = "subUserAdd";
			}
		});


		// 입력초기화 버튼 - 텍스트입력란이 깨끗하게비워진다
		subUserBtn10.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				subUserClear();
			}
		});






        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // Statistics : 관리자페이지 - 판매통계 @남윤지
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        mkPage(Statistics, windowName + " 관리자화면 - 판매통계", "/images/admin_sales/admin_sales_bg.jpg");

        // 우측 하단 버튼  
        // 날짜별 조회 274, 155, 122, 38
        mkBtn(statisticsBtn0, "/images/admin_sales/admin_sales_btn1_off.jpg", "/images/admin_sales/admin_sales_btn1_on.jpg", 274, 155, 122, 38);

        // 전체 조회 408, 155, 107, 38
        mkBtn(statisticsBtn1, "/images/admin_sales/admin_sales_btn2_off.jpg", "/images/admin_sales/admin_sales_btn2_on.jpg", 408, 155, 107, 38);

        // 메뉴별 판매순위 772, 155, 150, 38
        mkBtn(statisticsBtn2, "/images/admin_sales/admin_sales_btn3_off.jpg", "/images/admin_sales/admin_sales_btn3_on.jpg", 772, 155, 150, 38);

        // 빵 판매순위 934, 155, 121, 38
        mkBtn(statisticsBtn3, "/images/admin_sales/admin_sales_btn4_off.jpg", "/images/admin_sales/admin_sales_btn4_on.jpg", 934, 155, 121, 38);

        // 세트 판매순위 1067, 155, 137, 38
        mkBtn(statisticsBtn4, "/images/admin_sales/admin_sales_btn5_off.jpg", "/images/admin_sales/admin_sales_btn5_on.jpg", 1067, 155, 137, 38);

        // 우측 상단 버튼
        // 주문관리 899, 20, 157, 38
        mkBtn(statisticsBtn5, "/images/admin_nav/admin_nav_btn1_off.jpg", "/images/admin_nav/admin_nav_btn1_on.jpg", 899, 20, 157, 38);
		statisticsBtn5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pageMove("suborder");

				String odate = todaySet(subOrderModel, dateChooserOrder); // 자동 선택 값 오늘 날짜로 변경
				connect();
				subOrderSelect(odate, "order");
				subOrderSum(odate);
				subOrderCount(odate);
				autoAdjustColumnWidth(subOrderTable);
				autoAdjustRowHeights(subOrderTable);
			}
		});

        // 메뉴관리 1062, 20, 157, 38
        mkBtn(statisticsBtn6, "/images/admin_nav/admin_nav_btn2_off.jpg", "/images/admin_nav/admin_nav_btn2_on.jpg", 1062, 20, 157, 38);
		statisticsBtn6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
        		pageMove("submenu");

                connect(); 
            	menuModifyselect();
            	conClose(rs, pstmt);
			}
		});

        // 회원관리 899, 63, 157, 38
        mkBtn(statisticsBtn7, "/images/admin_nav/admin_nav_btn3_off.jpg", "/images/admin_nav/admin_nav_btn3_on.jpg", 899, 63, 157, 38);
		statisticsBtn7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				pageMove("subuser");

				connect();
				userSelect();
				
				statisticsModelCentered(userTable);
				autoAdjustColumnWidth(userTable);
		        autoAdjustRowHeights(userTable);
			}
		});

        // 판매통계 1062, 63, 157, 38
        mkBtn(statisticsBtn8, "/images/admin_nav/admin_nav_btn4_on.jpg", "/images/admin_nav/admin_nav_btn4_on.jpg", 1062, 63, 157, 38);
		statisticsBtn8.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				pageMove("statistics");
				
				String odate = todaySet(statisticsModel1, dateChooser); // 자동 선택 값 오늘 날짜로 변경
				connect();
				statisticsTable.setModel(statisticsModel1);
				statisticsTable.getModel();
				subOrderSelect(odate, "statistics");
				statisticsModelCentered(statisticsTable);
				autoAdjustColumnWidth(statisticsTable);
				autoAdjustRowHeights(statisticsTable);
			}
		});

        // X 버튼 12, 10, 24, 24
        mkBtn(statisticsBtn9, "/images/admin_nav/admin_nav_close_off.jpg", "/images/admin_nav/admin_nav_close_on.jpg", 12, 10, 24, 24);
		statisticsBtn9.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				pageMove("main");
			}
		});


        // 프레임에 컴포넌트 표시
        Statistics.getContentPane().add(statisticsBtn0);
        Statistics.getContentPane().add(statisticsBtn1);
        Statistics.getContentPane().add(statisticsBtn2);
        Statistics.getContentPane().add(statisticsBtn3);
        Statistics.getContentPane().add(statisticsBtn4);
        Statistics.getContentPane().add(statisticsBtn5);
        Statistics.getContentPane().add(statisticsBtn6);
        Statistics.getContentPane().add(statisticsBtn7);
        Statistics.getContentPane().add(statisticsBtn8);
        Statistics.getContentPane().add(statisticsBtn9);
        Statistics.getContentPane().add(statisticsSelectedDate);


    	dateEditor.setHorizontalAlignment(JTextField.CENTER);
    	dateChooser.setDateFormatString("yy/MM/dd");
    	dateChooser.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
    	dateChooser.setSize(new Dimension(105, 0));
    	
    	// dateChooser 80, 155, 167, 30
    	dateChooser.setBounds(80, 155, 167, 30);
        dateChooser.getJCalendar().setPreferredSize(new Dimension(250, 250));
        dateChooser.getCalendarButton().setBackground(Color.white);
    	Statistics.add(dateChooser);


		// statisticsModel - 메뉴별 판매순위, 빵 판매순위, 세트 판매순위 : 	
		String[] header = {"순위", "메뉴이름", "판매가격", "총 판매수량", "총 판매금액"};
		statisticsModel = new DefaultTableModel(header, 0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int i, int c) {
		        return false;
		    }
		};

		// statisticsModel1 - 날짜별조회, 전체조회 
		String[] header1 = {"주문번호", "식사구분", "주문자", "주문내용", "결제방법", "결제금액", "사용적립금", "주문일자"};
		statisticsModel1 = new DefaultTableModel(header1, 0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int i, int c) { // 표 안 움직이게 고정
		        return false;
		    }
		};
		
		statisticsScrollPane = new JScrollPane();
		statisticsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		statisticsScrollPane.setBounds(80, 229, 1124, 459);
		Statistics.getContentPane().add(statisticsScrollPane);
		
		statisticsTable = new JTable();
		statisticsScrollPane.setViewportView(statisticsTable);
		statisticsTable.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
		statisticsTable.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가

		// statisticsSelectedDate 0, 190, 190, 15
        mkTxt(statisticsSelectedDate, "※ 조회할 날짜를 선택하세요", "plain", 12, 0, 0, 0, 80, 190, 190, 15);
        statisticsSelectedDate.setHorizontalAlignment(SwingConstants.LEFT);


		// 날짜별 조회
		statisticsBtn0.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				statisticsModel1.setRowCount(0);
				connect();
				statisticsTable.setModel(statisticsModel1);
				statisticsTable.getModel();
				
				if(dateChooser.getDate() == null) {
					JOptionPane.showMessageDialog(null, "조회할 날짜를 선택해주세요.");
					return;
				}

				String odate = dateFormat.format(dateChooser.getDate());	
				subOrderSelect(odate, "statistics"); // 테이블에 날짜 값 입력

				statisticsModelCentered(statisticsTable);
				autoAdjustColumnWidth(statisticsTable);
				autoAdjustRowHeights(statisticsTable);
            }
		});


		// 전체조회
		statisticsBtn1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				dateChooser.setCalendar(null); // dateChooser 값 초기화
				statisticsModel1.setRowCount(0);
				connect();
				statisticsTable.setModel(statisticsModel1);
				statisticsTable.getModel();
				subOrderSelect("statistics");
				statisticsModelCentered(statisticsTable);
				autoAdjustColumnWidth(statisticsTable);
				autoAdjustRowHeights(statisticsTable);
			}
		});


		// 메뉴별 판매순위
		statisticsBtn2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				dateChooser.setCalendar(null); // dateChooser 값 초기화
				statisticsModel.setRowCount(0);
				connect();	
				statisticsTable.setModel(statisticsModel);
				statisticsTable.getModel(); // model 생성

				try {
					sql = "SELECT PMENU, PMENUPRICE, COUNT(*) AS COUNT FROM PRODUCT "
							+ "GROUP BY PMENU, PMENUPRICE "
							+ "ORDER BY COUNT(*) DESC, PMENUPRICE DESC";
					pstmt = con.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					int i =1;
					while(rs.next()) {
						String pmenu = rs.getString("PMENU");
						int pmenuprice = rs.getInt("PMENUPRICE");
						int count = rs.getInt("COUNT");
						String pmenu_price = decimalFormat.format(pmenuprice) + "원";
						int totalprice = pmenuprice*count;
						String total_price = decimalFormat.format(totalprice) + "원";
						Object[] data = {i, pmenu, pmenu_price, count, total_price};
						statisticsModel.addRow(data);
						i++;
					}			
				} catch (SQLException e1) {
					e1.printStackTrace();
				} finally {
					conClose(rs, pstmt, con);
					statisticsModelCentered(statisticsTable);
					autoAdjustColumnWidth(statisticsTable);
					autoAdjustRowHeights(statisticsTable);
					statisticsTable.setRowHeight(30);

				}
			}
		});
				
		
		// 빵 판매순위
		statisticsBtn3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	dateChooser.setCalendar(null); // dateChooser 값 초기화
				statisticsModel.setRowCount(0);
				connect();	
				statisticsTable.setModel(statisticsModel);
				statisticsTable.getModel(); // model 생성
				
				try {
					sql = "SELECT PBREAD, COUNT(PBREAD) AS COUNT FROM PRODUCT GROUP BY PBREAD ORDER BY COUNT(PBREAD) DESC";
					pstmt = con.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					int i =1;
					while(rs.next()) {
						String pbread = rs.getString("PBREAD");
						int count = rs.getInt("COUNT");
						Object[] data = {i, pbread, 0+"원", count, 0+"원"};
						statisticsModel.addRow(data);
						i++;
					}

				} catch (SQLException e1) {
					e1.printStackTrace();

				} finally {
					conClose(rs, pstmt, con);
					statisticsModelCentered(statisticsTable);
					autoAdjustColumnWidth(statisticsTable);
					autoAdjustRowHeights(statisticsTable);
					statisticsTable.setRowHeight(30);

				}
			}
		});


		// 세트 판매순위
		statisticsBtn4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
        		dateChooser.setCalendar(null); // dateChooser 값 초기화
				statisticsModel.setRowCount(0);
				connect();
				statisticsTable.setModel(statisticsModel);
				statisticsTable.getModel();

				try {
					sql = "SELECT PSET, PSETPRICE, COUNT(*) AS COUNT FROM PRODUCT "
							+ "GROUP BY PSET, PSETPRICE "
							+ "ORDER BY COUNT(*) DESC, PSETPRICE DESC";
					pstmt = con.prepareStatement(sql);
					rs = pstmt.executeQuery();
	
					int i =1;
					while(rs.next()) {
						String pset = rs.getString("PSET");
						int psetprice = rs.getInt("PSETPRICE");
						int count = rs.getInt("COUNT");
						String pset_price = decimalFormat.format(psetprice) + "원";
						int totalprice = psetprice*count;
						String total_price = decimalFormat.format(totalprice) + "원";
						Object[] data = {i, pset, pset_price, count, total_price};
						statisticsModel.addRow(data);
						i++;
					}

				} catch (SQLException e1) {
					e1.printStackTrace();

				} finally {
					conClose(rs, pstmt, con);
					statisticsModelCentered(statisticsTable);
					autoAdjustColumnWidth(statisticsTable);
					autoAdjustRowHeights(statisticsTable);
					statisticsTable.setRowHeight(30);
				}
			}
		});






        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // MenuModify : 관리자페이지 - 메뉴관리 @오현록
        //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ		
    	// 페이지 생성 메서드 (JFrame 이름, 창 타이틀, 배경이미지)
        mkPage(MenuModify, "관리자메뉴 - 메뉴관리", "/images/admin_menu/admin_menu_bg.jpg");


        mkBtn(menuModifybtnNewButton6, "/images/admin_menu/admin_menu_btn1_off.jpg", "/images/admin_menu/admin_menu_btn1_on.jpg", 64, 653, 138, 38);

        mkBtn(menuModifybtnNewButton7, "/images/admin_menu/admin_menu_btn2_off.jpg", "/images/admin_menu/admin_menu_btn2_on.jpg", 214, 653, 139, 38);

        mkBtn(menuModifybtnNewButton8, "/images/admin_menu/admin_menu_btn4_off.jpg", "/images/admin_menu/admin_menu_btn4_on.jpg", 947, 569, 260, 56);

        mkBtn(menuModifybtnNewButton9, "/images/admin_menu/admin_menu_btn5_off.jpg", "/images/admin_menu/admin_menu_btn5_on.jpg", 947, 635, 260, 56);

        mkBtn(menuModifybtnNewButton10, "/images/admin_menu/admin_menu_btn3_off.jpg", "/images/admin_menu/admin_menu_btn3_on.jpg", 676, 653, 153, 38);


        mkBtn(menuModifybtnNewButton1, "/images/admin_nav/admin_nav_btn1_off.jpg", "/images/admin_nav/admin_nav_btn1_on.jpg", 899, 20, 157, 38);
        menuModifybtnNewButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pageMove("suborder");
				
				String odate = todaySet(subOrderModel, dateChooserOrder); // 자동 선택 값 오늘 날짜로 변경
				connect();
				subOrderSelect(odate, "order");
				subOrderSum(odate);
				subOrderCount(odate);
				autoAdjustColumnWidth(subOrderTable);
				autoAdjustRowHeights(subOrderTable);
			}
		});


        mkBtn(menuModifybtnNewButton2, "/images/admin_nav/admin_nav_btn2_on.jpg", "/images/admin_nav/admin_nav_btn2_on.jpg", 1062, 20, 157, 38);
        menuModifybtnNewButton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	pageMove("submenu");

                connect(); 
                menuModifyselect();
                conClose(rs, pstmt);
			}
		});


        mkBtn(menuModifybtnNewButton3, "/images/admin_nav/admin_nav_btn3_off.jpg", "/images/admin_nav/admin_nav_btn3_on.jpg", 899, 63, 157, 38);
        menuModifybtnNewButton3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				pageMove("subuser");

				connect();
				userSelect();
			}
		});


        mkBtn(menuModifybtnNewButton4, "/images/admin_nav/admin_nav_btn4_off.jpg", "/images/admin_nav/admin_nav_btn4_on.jpg", 1062, 63, 157, 38);
        menuModifybtnNewButton4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				pageMove("statistics");
				
				String odate = todaySet(statisticsModel1, dateChooser); // 오늘 날짜로 설정
				connect();
				statisticsTable.setModel(statisticsModel1);
				statisticsTable.getModel();
				subOrderSelect(odate, "statistics");
				statisticsModelCentered(statisticsTable);
				autoAdjustColumnWidth(statisticsTable);
				autoAdjustRowHeights(statisticsTable);
			}
		});


        mkBtn(menuModifybtnNewButton5, "/images/admin_nav/admin_nav_close_off.jpg", "/images/admin_nav/admin_nav_close_on.jpg", 12, 10, 24, 24);
        menuModifybtnNewButton5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	pageMove("main");
			}
		});


        MenuModify.getContentPane().add(menuModifybtnNewButton1);
        MenuModify.getContentPane().add(menuModifybtnNewButton2);
        MenuModify.getContentPane().add(menuModifybtnNewButton3);
        MenuModify.getContentPane().add(menuModifybtnNewButton4);
        MenuModify.getContentPane().add(menuModifybtnNewButton5);
        MenuModify.getContentPane().add(menuModifybtnNewButton6);
        MenuModify.getContentPane().add(menuModifybtnNewButton7);
        MenuModify.getContentPane().add(menuModifybtnNewButton8);
        MenuModify.getContentPane().add(menuModifybtnNewButton9);
        MenuModify.getContentPane().add(menuModifybtnNewButton10);


        // 입력수정 폼 컴포넌트
        //카테고리 문구
        JEditorPane menuModifyeditorPane = new JEditorPane();
        menuModifyeditorPane.setBounds(947, 247, 86, 38);
        menuModifyeditorPane.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        menuModifyeditorPane.setText("카테고리");
        MenuModify.getContentPane().add(menuModifyeditorPane);
        
        //메뉴이름 문구
        JEditorPane menuModifyeditorPane1 = new JEditorPane();
        menuModifyeditorPane1.setBounds(947, 307, 86, 38);
        menuModifyeditorPane1.setText("메뉴이름");
        menuModifyeditorPane1.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        MenuModify.getContentPane().add(menuModifyeditorPane1);

        //이미지사진 문구
        JEditorPane menuModifyeditorPane121 = new JEditorPane();
        menuModifyeditorPane121.setText("이미지 사진");
        menuModifyeditorPane121.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        menuModifyeditorPane121.setBounds(947, 427, 96, 38);
        MenuModify.getContentPane().add(menuModifyeditorPane121);

        //판매가격 문구
        JEditorPane menuModifyeditorPane12 = new JEditorPane();
        menuModifyeditorPane12.setBounds(947, 367, 86, 38);
        menuModifyeditorPane12.setText("판매가격");
        menuModifyeditorPane12.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        MenuModify.getContentPane().add(menuModifyeditorPane12);

        //메뉴이름 텍스트 입력창
        mkInp(menuModifytextField1, "", 100, "plain", 15, "left", 1055, 307, 138, 38);
        MenuModify.getContentPane().add(menuModifytextField1);

        //이미지 텍스트 입력창
        mkInp(menuModifytextField2, "", 100, "plain", 15, "left", 1055, 427, 138, 38);
        MenuModify.getContentPane().add(menuModifytextField2);

        //판매 가격 텍스트 입력창
        mkInp(menuModifytextField3, "", 100, "plain", 15, "left", 1055, 367, 138, 38);
        MenuModify.getContentPane().add(menuModifytextField3);

        //jcb1 카테고리 콤보박스 담을 패널
        JPanel menuModifypanel2 = new JPanel();
        menuModifypanel2.setBounds(1043, 237, 162, 48);
        menuModifypanel2.setBackground(Color.WHITE);
        MenuModify.getContentPane().add(menuModifypanel2);

        // 카테고리 콤보박스
        menuModifyjcb1 = new JComboBox<String>();
        menuModifyjcb1.setBounds(12, 5, 138, 38);
        menuModifyjcb1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        menuModifyjcb1.addItem("선택");

        // jcb1 카테고리 콤보박스 패널에 담기
        menuModifypanel2.setLayout(null);
        menuModifypanel2.add(menuModifyjcb1);



        // jcb1 카테고리 콤보 박스 연결 
        try {
            connect();

            // 1. 데이터베이스에 전송할 SQL문 작성
            sql = "select mcategory from menu group by mcategory";
            pstmt = con.prepareStatement(sql);
            
            // 2. 데이터베이스에 SQL문 전송 및 SQL문 실행.
            rs = pstmt.executeQuery();
            while(rs.next()) {
                switch (rs.getString("mcategory")) {
                    case "sandwich":
                        menuModifyjcb1.addItem("샌드위치");
                        break;
                    case "bread" :
                        menuModifyjcb1.addItem("빵");
                        break;
                    case "vegetable" :
                        menuModifyjcb1.addItem("야채");
                        break;
                    case "sauce" :
                        menuModifyjcb1.addItem("소스");
                        break;
                    case "set" :
                        menuModifyjcb1.addItem("세트");
                        break;
                    case "add" :
                        menuModifyjcb1.addItem("추가 토핑");
                        break;
                }
            }
            
        } catch (SQLException e1) {
            e1.printStackTrace();
        }



        String[] header3 = {"NO", "카테고리", "메뉴이름", "판매가격","이미지 사진", "등록일자"};
        menuModifymodel = new DefaultTableModel(header3, 0) {
            private static final long serialVersionUID = 1L;

            // Jtable 내용 편집 안되게
            public boolean isCellEditable(int i, int c) {
                return false;
            }
        };
        menuModifytable = new JTable(menuModifymodel);
        menuModifytable.getColumnModel().getColumn(0).setPreferredWidth(40);
        menuModifytable.getColumnModel().getColumn(1).setPreferredWidth(100);
        menuModifytable.getColumnModel().getColumn(2).setPreferredWidth(180);
        menuModifytable.getColumnModel().getColumn(3).setPreferredWidth(100);
        menuModifytable.getColumnModel().getColumn(4).setPreferredWidth(215);
        menuModifytable.getColumnModel().getColumn(5).setPreferredWidth(160);
        menuModifytable.setBackground(Color.WHITE);
        menuModifytable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        menuModifytable.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
        menuModifytable.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
        statisticsModelCentered(menuModifytable);
    	menuModifytable.setRowHeight(30);

    	JScrollPane menuModifyjsp = new JScrollPane(menuModifytable, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    	menuModifyjsp.setBorder(null);
    	menuModifyjsp.getVerticalScrollBar().setUnitIncrement(winScrollSpeed);
    	menuModifyjsp.setBounds(62, 134, 813, 499);

    	MenuModify.getContentPane().add(menuModifyjsp);


		//작성 완료에서 3가지 기능을 수행해야 하기 때문에 switch문을 이용 하여 버튼 기능을 추가한다.
		menuModifybtnNewButton8.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
        		if(menuModifycheck=="") {
        			menuModifycheck="menuAlert";
        		}

        		switch (menuModifycheck) {
        			// case menuAlert을 적용 작성완료 버튼만 눌렀을떄 사용된다.
        			case "menuAlert" :
        				JOptionPane.showMessageDialog(null, "추가,수정버튼을 먼저 클릭해주세요!", "클릭 입력", JOptionPane.PLAIN_MESSAGE);
        				break;

        			//case menuAdd 적용 메뉴 추가 기능을 한다.
        			case "menuAdd" :
        				connect();

        				menuModifyinsert();

        				JOptionPane.showMessageDialog(null, "메뉴가 추가 되었습니다.", "메뉴 추가 완료", JOptionPane.PLAIN_MESSAGE);
        				menuModifymodel.setRowCount(0);
        				menuModifyselect();
        				menuModifyclose();

        				conClose(rs, pstmt);

        				menuModifycheck = "menuAlert";
        				break;

        			//case menuCorrect 적용 메뉴 수정 기능을 한다.
        			case "menuCorrect" :
        				connect();
        				menuModifyUpdate();

        				JOptionPane.showMessageDialog(null, "메뉴가 수정 되었습니다.", "메뉴 수정 완료", JOptionPane.PLAIN_MESSAGE);
        				menuModifymodel.setRowCount(0);

        				menuModifyselect();
        				menuModifyclose();

        				conClose(rs, pstmt);
        				menuModifycheck = "menuAlert";

        				break;
        		}
            }
		});


		//선택 메뉴 삭제 버튼
		menuModifybtnNewButton6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
        		if(menuModifytable.getSelectedRow() == -1) {
        			JOptionPane.showMessageDialog(null, "메뉴를 선택하신 후 클릭해주세요.");
        			return;
        		}

        		int result = JOptionPane.showConfirmDialog(null, "정말로 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
        		if(result == JOptionPane.CLOSED_OPTION) {
        			JOptionPane.showMessageDialog(null, "취소를 클릭하셨습니다.");
        		}else if(result == JOptionPane.NO_OPTION) {
        			JOptionPane.showMessageDialog(null, "취소 버튼을 클릭하셨습니다.");
        		}else {
        			connect();

        			menuModifydelete();

        			JOptionPane.showMessageDialog(null, "메뉴가 삭제 되었습니다.", "메뉴 삭제 완료", JOptionPane.PLAIN_MESSAGE);
        			menuModifymodel.setRowCount(0);

        			menuModifyselect();
        			conClose(rs, pstmt);
        		}
            }
		});


      	//선택 메뉴 추가 버튼
		menuModifybtnNewButton10.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
        		menuModifytextField1.requestFocus();
        		//switch 문에서 사용할 변수값 menuAdd를 저장한다.
        		menuModifycheck = "menuAdd";
            }
		});


		// 선택 메뉴 수정 버튼
		menuModifybtnNewButton7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
  				int row = menuModifytable.getSelectedRow();
  				if(row<=0) {
  					JOptionPane.showMessageDialog(null, "먼저 수정 할 메뉴를 클릭해주세요.", "수정메뉴", JOptionPane.PLAIN_MESSAGE);

  				}else{
  					menuModifyupdateUNO = menuModifymodel.getValueAt(row,0).toString();

  					if(menuModifymodel.getValueAt(row,1).equals("bread")){
  						menuModifycategory = "빵";
  					}else if(menuModifymodel.getValueAt(row,1).equals("vegetable")) {
						menuModifycategory = "야채";
					}else if(menuModifymodel.getValueAt(row,1).equals("sauce")) {
						menuModifycategory = "소스";
					}else if(menuModifymodel.getValueAt(row,1).equals("add")) {
						menuModifycategory = "추가 토핑";
					}else if(menuModifymodel.getValueAt(row,1).equals("sandwich")) {
						menuModifycategory = "샌드위치";
					}else if(menuModifymodel.getValueAt(row,1).equals("set")) {
						menuModifycategory = "세트";
					}

  					//가격을 목록에 넣을때 원을 넣어서 String으로 바뀐다. 거기서 원을 빼기위한 메서드.
      				menuModifystr = menuModifymodel.getValueAt(row, 3).toString();
      				menuModifyintStr = menuModifystr.replaceAll("[^0-9]", "");

      				menuModifyjcb1.setSelectedItem(menuModifycategory);
      				menuModifytextField1.setText(menuModifymodel.getValueAt(row, 2).toString());
      				menuModifytextField2.setText(menuModifymodel.getValueAt(row, 4).toString());
      				//원을 뺸 가격 텍스트 창에 넣기
      				menuModifytextField3.setText(menuModifyintStr.toString());
      				//switch 문에서 사용할 변수값 menuCorrect를 저장한다.
      				menuModifycheck = "menuCorrect";
  				}
  			}
		});//메뉴 수정 버튼 끝



		//메뉴 입력 초기화 버튼	
		menuModifybtnNewButton9.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	// 입력 화면을 깨끗하게해주는 메소드
        		menuModifyclose();
            }
		});

    }













    ////////////////////////////////////////////////////////////////////////////////////////////
	// Login : subuser DB에 해당하는 정보가 있는지 확인, 로그인하는 메소드. 
	// 로그인 성공하면 로그인 성공! 메시지와 함께 이름, 아이디, 적립금이 각 변수에 저장된다.
    ////////////////////////////////////////////////////////////////////////////////////////////
 	void login(String inType) {
		try {
			if(loginJtf1.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.");
				loginJtf1.requestFocus();

			}else if(loginJtf2.getText().equals("")){
				JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.");
				loginJtf2.requestFocus();

			}else{
				connect();

				if(inType == "admin") {
					sql = "select utype, u_id, u_pw, uname, ureward from subuser where utype = 'admin' and u_id = ?";
				}else{
					sql = "select utype, u_id, u_pw, uname, ureward from subuser where utype = 'member' and u_id = ?";
				}
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, loginJtf1.getText());
				rs = pstmt.executeQuery();

				if (rs.next()) {

					// 비밀번호 암호화
					String chkPWD = encryptMD5(loginJtf2.getText());

					if (rs.getString("u_pw").equals(chkPWD)) {
						loginJtf1.setText("");
						loginJtf2.setText("");
						loginJtf1.requestFocus();

                        // 로그인한 아이디, 이름, 적립금을 변수에 저장
                        User_id = rs.getString("u_id");
                        User_name = rs.getString("uname");
                        User_point = rs.getInt("ureward");

						pstmt.close();


						// 로그인 타입이 관리자라면..
						if(inType == "admin") {
							pageMove("suborder");    
							String odate = todaySet(subOrderModel, dateChooserOrder); // 자동 선택 값 오늘 날짜로 변경
							connect();
							subOrderSelect(odate, "order");
							subOrderSum(odate);
							subOrderCount(odate);
							autoAdjustColumnWidth(subOrderTable);
							autoAdjustRowHeights(subOrderTable);
						}else{
							showMemInfo();
							pageMove("order1");
						}

					} else if (!rs.getString("u_pw").equals(loginJtf2.getText())) {
						JOptionPane.showMessageDialog(null, "비밀번호가 회원정보와 다릅니다.");
						pstmt.close();
						loginJtf2.requestFocus();
					}

				} else {
					JOptionPane.showMessageDialog(null, "등록된 아이디가 아닙니다.");
					pstmt.close();
					loginJtf1.requestFocus();
				}
			}

		} catch (Exception e) {
			if (e.getMessage().contains("PRIMARY")) {
				JOptionPane.showMessageDialog(null, "아이디 중복 오류");
			}
			e.printStackTrace();
		}
	}




    ////////////////////////////////////////////////////////////////////////////////////////////
	// 로그인 한 회원정보를 화면에 표시
    ////////////////////////////////////////////////////////////////////////////////////////////
	void showMemInfo() {
	    orderMenuMemBg1.setIcon(new ImageIcon(Main_UI.class.getResource("/images/common/menu_user_bg.jpg")));;
	    orderMenuMemBg1.setBounds(910, 8, 340, 110);
	    mkTxt(orderMenuMemInfo1, "<html>"+User_name+"님 안녕하세요<br>사용가능한 적립금 : "+String.format("%,d", User_point)+"원</html>", "plain", 14, 51, 51, 51, 1014, 44, 300, 70);

	    orderMenuMemBg2.setIcon(new ImageIcon(Main_UI.class.getResource("/images/common/menu_user_bg.jpg")));;
	    orderMenuMemBg2.setBounds(910, 8, 340, 110);
	    mkTxt(orderMenuMemInfo2, "<html>"+User_name+"님 안녕하세요<br>사용가능한 적립금 : "+String.format("%,d", User_point)+"원</html>", "plain", 14, 51, 51, 51, 1014, 44, 300, 70);

	    orderMenuMemBg3.setIcon(new ImageIcon(Main_UI.class.getResource("/images/common/menu_user_bg.jpg")));;
	    orderMenuMemBg3.setBounds(910, 8, 340, 110);
	    mkTxt(orderMenuMemInfo3, "<html>"+User_name+"님 안녕하세요<br>사용가능한 적립금 : "+String.format("%,d", User_point)+"원</html>", "plain", 14, 51, 51, 51, 1014, 44, 300, 70);

	    orderMenuMemBg4.setIcon(new ImageIcon(Main_UI.class.getResource("/images/common/menu_user_bg.jpg")));;
	    orderMenuMemBg4.setBounds(910, 8, 340, 110);
	    mkTxt(orderMenuMemInfo4, "<html>"+User_name+"님 안녕하세요<br>사용가능한 적립금 : "+String.format("%,d", User_point)+"원</html>", "plain", 14, 51, 51, 51, 1014, 44, 300, 70);

	    orderMenuMemBg5.setIcon(new ImageIcon(Main_UI.class.getResource("/images/common/menu_user_bg.jpg")));;
	    orderMenuMemBg5.setBounds(910, 8, 340, 110);
	    mkTxt(orderMenuMemInfo5, "<html>"+User_name+"님 안녕하세요<br>사용가능한 적립금 : "+String.format("%,d", User_point)+"원</html>", "plain", 14, 51, 51, 51, 1014, 44, 300, 70);

	    orderMenuMemBg6.setIcon(new ImageIcon(Main_UI.class.getResource("/images/common/menu_user_bg.jpg")));;
	    orderMenuMemBg6.setBounds(910, 8, 340, 110);
	    mkTxt(orderMenuMemInfo6, "<html>"+User_name+"님 안녕하세요<br>사용가능한 적립금 : "+String.format("%,d", User_point)+"원</html>", "plain", 14, 51, 51, 51, 1014, 44, 300, 70);

	    orderMenuMemBgCart.setIcon(new ImageIcon(Main_UI.class.getResource("/images/common/menu_user_bg.jpg")));;
	    orderMenuMemBgCart.setBounds(910, 8, 340, 110);
	    mkTxt(orderMenuMemInfoCart, "<html>"+User_name+"님 안녕하세요<br>사용가능한 적립금 : "+String.format("%,d", User_point)+"원</html>", "plain", 14, 51, 51, 51, 1014, 44, 300, 70);

	    Order1.getContentPane().add(orderMenuMemInfo1);
	    Order1.getContentPane().add(orderMenuMemBg1);
	    Order2.getContentPane().add(orderMenuMemInfo2);
	    Order2.getContentPane().add(orderMenuMemBg2);
	    Order3.getContentPane().add(orderMenuMemInfo3);
	    Order3.getContentPane().add(orderMenuMemBg3);
	    Order4.getContentPane().add(orderMenuMemInfo4);
	    Order4.getContentPane().add(orderMenuMemBg4);
	    Order5.getContentPane().add(orderMenuMemInfo5);
	    Order5.getContentPane().add(orderMenuMemBg5);
	    Order6.getContentPane().add(orderMenuMemInfo6);
	    Order6.getContentPane().add(orderMenuMemBg6);
	    OrderCart.getContentPane().add(orderMenuMemInfoCart);
	    OrderCart.getContentPane().add(orderMenuMemBgCart);


        // 적립금사용 부분
        mkTxt(orderCartText5, "적립금사용 : ", "bold", 16, 51, 51, 51, 971, 461, 220, 30);
        mkTxt(orderCartText6, "원", "plain", 16, 51, 51, 51, 1152, 462, 30, 30);
        mkInp(orderCartInput, "0", 10, "plain", 16, "center", 1070, 452, 80, 40);

        OrderCart.getContentPane().add(orderCartText5);
        OrderCart.getContentPane().add(orderCartText6);
        OrderCart.getContentPane().add(orderCartInput);
	}


    ////////////////////////////////////////////////////////////////////////////////////////////
	// 로그아웃 한 회원정보를 화면에서 제거
    ////////////////////////////////////////////////////////////////////////////////////////////
	void clearMemInfo() {
	    Order1.getContentPane().remove(orderMenuMemInfo1);
	    Order1.getContentPane().remove(orderMenuMemBg1);
	    Order2.getContentPane().remove(orderMenuMemInfo2);
	    Order2.getContentPane().remove(orderMenuMemBg2);
	    Order3.getContentPane().remove(orderMenuMemInfo3);
	    Order3.getContentPane().remove(orderMenuMemBg3);
	    Order4.getContentPane().remove(orderMenuMemInfo4);
	    Order4.getContentPane().remove(orderMenuMemBg4);
	    Order5.getContentPane().remove(orderMenuMemInfo5);
	    Order5.getContentPane().remove(orderMenuMemBg5);
	    Order6.getContentPane().remove(orderMenuMemInfo6);
	    Order6.getContentPane().remove(orderMenuMemBg6);
	    OrderCart.getContentPane().remove(orderMenuMemInfoCart);
	    OrderCart.getContentPane().remove(orderMenuMemBgCart);

        OrderCart.getContentPane().remove(orderCartText5);
        OrderCart.getContentPane().remove(orderCartText6);
        OrderCart.getContentPane().remove(orderCartInput);
	}




	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// 회원가입 메소드
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	void joinInsert() {
		try {
			String pwPattern = "^[A-Za-z[0-9]]{3,20}$";

			connect();

			sql = "insert into subuser (utype, u_id, u_pw, uname, uphone, ureward, udate) values('member', ?, ?, ?, ?, ?, sysdate)";
			pstmt = con.prepareStatement(sql);

			while (true) {
				if (joinJtf1.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.");
					joinJtf1.requestFocus();
					break;
				} else if (joinJtf2.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.");
					joinJtf2.requestFocus();
					break;
				} else if (Pattern.matches(pwPattern, (String) joinJtf2.getText()) == false) {
					JOptionPane.showMessageDialog(null, "비밀번호는 영문, 숫자 3~20자리로 입력해주세요");
					joinJtf2.setText("");
 					joinJtf3.setText("");
 					joinJtf2.requestFocus();
					break;	
				} else if (!joinJtf2.getText().equals(joinJtf3.getText())) {
					JOptionPane.showMessageDialog(null, "비밀번호 확인해주세요.");
					joinJtf3.requestFocus();
					break;
				} else if (Pattern.matches(pwPattern, (String) joinJtf2.getText()) == false) {
					JOptionPane.showMessageDialog(null, "비밀번호는 영문, 숫자 3~20자리로 입력해주세요");
					break;
				} else if (joinJtf3.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "비밀번호 확인을 입력해주세요.");
					break;
				} else if (joinJtf4.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "이름를 입력해주세요.");
					joinJtf4.requestFocus();
					break;
				} else if (joinJtf5.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "연락처를 입력해주세요.");
					joinJtf5.requestFocus();
					break;
				} else {
					// 비밀번호 암호화
					String setPWD = encryptMD5(joinJtf2.getText());

					pstmt.setString(1, joinJtf1.getText());
					pstmt.setString(2, setPWD);
					pstmt.setString(3, joinJtf4.getText());
					pstmt.setString(4, joinJtf5.getText());
					pstmt.setInt(5, joinPoint);
					pstmt.executeUpdate();

					JOptionPane.showMessageDialog(null, "회원가입 성공 !!!");

					joinClear();
					pstmt.close();

					pageMove("login");
					break;
				}
			}

		} catch (Exception e) {
			if (e.getMessage().contains("unique")) {
				JOptionPane.showMessageDialog(null, "이미 가입된 아이디입니다.", "아이디 중복", 1);
			}
			e.printStackTrace();
		}
	}

	
	// join 입력 화면을 깨끗하게해주는 메소드
	void joinClear() {
		joinJtf1.setText("");
		joinJtf2.setText("");
		joinJtf3.setText("");
		joinJtf4.setText("");
		joinJtf5.setText("");
	}






    ////////////////////////////////////////////////////////////////////////////////////////////
    // 메뉴선택 리셋 메서드
    ////////////////////////////////////////////////////////////////////////////////////////////
    void selectReset() {
        menuSelDto = new MenuSelectDTO();

        orderMenu1Text3.setText(orderMenu1Text3Cont);
        orderMenu2Text3.setText(orderMenu2Text3Cont);
        orderMenu3Text3.setText(orderMenu3Text3Cont);
        orderMenu4Text3.setText(orderMenu4Text3Cont);
        orderMenu5Text3.setText(orderMenu5Text3Cont);
        orderMenu6Text3.setText(orderMenu6Text3Cont);
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
    // 장바구니 리셋 (비우기)
    ////////////////////////////////////////////////////////////////////////////////////////////
    void cartReset() {
        orderCartMenu.clear();
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
    // 회원정보 리셋 메서드
    ////////////////////////////////////////////////////////////////////////////////////////////
    void memberReset() {
    	User_id = null;
    	User_name = "비회원";
    	User_point = 0;
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
    // 처음화면으로 버튼 메서드
    ////////////////////////////////////////////////////////////////////////////////////////////
    void gotoMain(){
        int result = JOptionPane.showConfirmDialog(null,"처음화면으로 돌아가면 선택한 메뉴들이 모두 초기화되며, 로그인이 해제됩니다.\n처음화면으로 돌아가시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);              
        if(result == 0){
            pageMove("main");
        }else{
            return;
        }
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
    // 화면전환 메서드
    ////////////////////////////////////////////////////////////////////////////////////////////
    void pageMove(String page) {
        MainPage.setVisible(false);
        Order1.setVisible(false);
        Order2.setVisible(false);
        Order3.setVisible(false);
        Order4.setVisible(false);
        Order5.setVisible(false);
        Order6.setVisible(false);
        OrderCart.setVisible(false);
        OrderDone.setVisible(false);
        Login.setVisible(false);
        Join.setVisible(false);
        SubUser.setVisible(false);
        SubOrder.setVisible(false);
        MenuModify.setVisible(false);
        Statistics.setVisible(false);

        if(page == "main") {
            // 메뉴선택 리셋
            selectReset();

            // 장바구니 리셋
            cartReset();

            // 회원로그인 리셋
            memberReset();
            clearMemInfo();

            MainPage.setVisible(true);
            MainPage.setLocationRelativeTo(null);

        }else if(page == "order1") {
            Order1.setVisible(true);
            Order1.setLocationRelativeTo(null);
            omlScroll1.getVerticalScrollBar().setValue(0); // 스크롤 위치 맨위로

        }else if(page == "order2") {
            Order2.setVisible(true);
            Order2.setLocationRelativeTo(null);
            omlScroll2.getVerticalScrollBar().setValue(0);

        }else if(page == "order3") {
            Order3.setVisible(true);
            Order3.setLocationRelativeTo(null);
            omlScroll3.getVerticalScrollBar().setValue(0);

        }else if(page == "order4") {
            Order4.setVisible(true);
            Order4.setLocationRelativeTo(null);
            omlScroll4.getVerticalScrollBar().setValue(0);

        }else if(page == "order5") {
            Order5.setVisible(true);
            Order5.setLocationRelativeTo(null);
            omlScroll5.getVerticalScrollBar().setValue(0);

        }else if(page == "order6") {
            Order6.setVisible(true);
            Order6.setLocationRelativeTo(null);
            omlScroll6.getVerticalScrollBar().setValue(0);

        }else if(page == "cart") {
            OrderCart.setVisible(true);
            OrderCart.setLocationRelativeTo(null);

        }else if(page == "done") {
            // 메뉴선택 리셋
            selectReset();

            // 장바구니 리셋
            cartReset();

            // 회원로그인 리셋
            memberReset();
            clearMemInfo();

            OrderDone.setVisible(true);
            OrderDone.setLocationRelativeTo(null);

        }else if(page == "login") {
        	Login.setVisible(true);
        	Login.setLocationRelativeTo(null);

        }else if(page == "join") {
        	Join.setVisible(true);
        	Join.setLocationRelativeTo(null);

        }else if(page == "subuser") {
        	SubUser.setVisible(true);
        	SubUser.setLocationRelativeTo(null);

        }else if(page == "suborder") {
        	SubOrder.setVisible(true);
        	SubOrder.setLocationRelativeTo(null);

        }else if(page == "submenu") {
        	MenuModify.setVisible(true);
        	MenuModify.setLocationRelativeTo(null);

        }else if(page == "statistics") {
        	Statistics.setVisible(true);
        	Statistics.setLocationRelativeTo(null);

        }
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
    // 여러가지 메뉴 선택시 토글 메서드
    ////////////////////////////////////////////////////////////////////////////////////////////
    public static String SelectToggleMenu(String dtoName, String dtoPrice, String thisName, String thisPrice) {
        // 기존 값이 있으면
        if(dtoName != null){
            String[] epdName = dtoName.split(", ");
            String[] epdPrice = dtoPrice.split(", ");

            for(int j=0; j<epdName.length; j++){
                ResultSelName.add(epdName[j]);
                ResultSelPrice.add(epdPrice[j]);
            }

            // 중복값 배열 인덱스 찾아서 값 삭제하기
            int tempSelIndex = ResultSelName.indexOf(thisName);
            if(tempSelIndex >= 0){
                ResultSelName = arrayValueRemove(ResultSelName, tempSelIndex);
                ResultSelPrice = arrayValueRemove(ResultSelPrice, tempSelIndex);
            }else{
                ResultSelName.add(thisName);
                ResultSelPrice.add(thisPrice);
            }

        }else{
            ResultSelName.add(thisName);
            ResultSelPrice.add(thisPrice);
        }

    
        // 선택값 정리
        String resultSaveMenu = "";
        String resultSavePrice = "";

        if(ResultSelName.size() > 0){
            for(int j=0; j<ResultSelName.size(); j++){
                String addHeader = "";
                if(j > 0){
                    addHeader = ", ";
                }else{
                    addHeader = "";
                }
    
                resultSaveMenu += addHeader+ResultSelName.get(j);
                resultSavePrice += addHeader+ResultSelPrice.get(j);
            }
        }

        // 임시 선택 저장 배열 비우기
        ResultSelName.clear();
        ResultSelPrice.clear();

        return resultSaveMenu+"/"+resultSavePrice;
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
    // 메뉴 목록 상품 표시&선택 메서드
    ////////////////////////////////////////////////////////////////////////////////////////////
    void showMenuList(int num) {

        JPanel smlPanel = null;
        JScrollPane smlScroll = null;
        String smlMenu = null;
        int smlCount;
        @SuppressWarnings("unused")
        int smlRows;

        if(num == 1) {
            smlPanel = orderMenuList1;
            smlScroll = omlScroll1 = new JScrollPane(smlPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            smlMenu = "sandwich";
            smlCount = orderMenuCount1;
            smlRows = orderMenuRows1;

        }else if(num == 2) {
            smlPanel = orderMenuList2;
            smlScroll = omlScroll2 = new JScrollPane(smlPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            smlMenu = "bread";
            smlCount = orderMenuCount2;
            smlRows = orderMenuRows2;

        }else if(num == 3) {
            smlPanel = orderMenuList3;
            smlScroll = omlScroll3 = new JScrollPane(smlPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            smlMenu = "add";
            smlCount = orderMenuCount3;
            smlRows = orderMenuRows3;

        }else if(num == 4) {
            smlPanel = orderMenuList4;
            smlScroll = omlScroll4 = new JScrollPane(smlPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            smlMenu = "vegetable";
            smlCount = orderMenuCount4;
            smlRows = orderMenuRows4;

        }else if(num == 5) {
            smlPanel = orderMenuList5;
            smlScroll = omlScroll5 = new JScrollPane(smlPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            smlMenu = "sauce";
            smlCount = orderMenuCount5;
            smlRows = orderMenuRows5;

        }else if(num == 6) {
            smlPanel = orderMenuList6;
            smlScroll = omlScroll6 = new JScrollPane(smlPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            smlMenu = "set";
            smlCount = orderMenuCount6;
            smlRows = orderMenuRows6;

        }


        // 메뉴목록 설정
        smlPanel.setBackground(Color.WHITE);
        smlScroll.setBorder(null);
        smlScroll.getVerticalScrollBar().setUnitIncrement(winScrollSpeed);
        smlScroll.setBounds(51, 131, 828, 588);

        try {
            connect();

            // 메뉴 전체 갯수 SQL
            pstmt = con.prepareStatement("select count(*) from MENU where MCATEGORY = '"+smlMenu+"'");
            rs = pstmt.executeQuery();
            rs.next();

            smlCount = rs.getInt(1); // 전체 갯수
            smlRows = (int)Math.ceil((double)smlCount / 3); // 표시될 줄 수


            // 메뉴 목록 불러오기
            pstmt = con.prepareStatement("select * from MENU where MCATEGORY = '"+smlMenu+"' order by mname asc, mno desc");
            rs = pstmt.executeQuery();

            int i = 0;
            JButton bt[] = new JButton[smlCount];
            Image changeImg[] = new Image[smlCount];

            while(rs.next()){
                try {
                    changeImg[i] = new ImageIcon(Main_UI.class.getResource("/images/menu"+rs.getString("MIMAGE"))).getImage().getScaledInstance(228, 160, Image.SCALE_SMOOTH);
                } catch(Exception e){
                    changeImg[i] = new ImageIcon(Main_UI.class.getResource("/images/common/no_img.jpg")).getImage().getScaledInstance(228, 160, Image.SCALE_SMOOTH);
                }
                bt[i] = new JButton("<html><center><p>"+rs.getString("MNAME")+"</p><p color=\"green\">"+String.format("%,d", rs.getInt("MPRICE"))+"원</p></center></html>", new ImageIcon(changeImg[i]));
                bt[i].setVerticalTextPosition(JButton.BOTTOM);
                bt[i].setHorizontalTextPosition(JButton.CENTER);
                bt[i].setBorderPainted(false);
                bt[i].setPreferredSize(new Dimension(228, 275));
                bt[i].setForeground(new Color(51, 51, 51));
                bt[i].setBackground(new Color(255, 255, 255));
                bt[i].setFont(new Font("맑은 고딕", Font.BOLD, 20));
                smlPanel.add(bt[i]);

                int btnIndex = i;
                bt[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        String tempVal = bt[btnIndex].getText();
                        String[] epdVal = tempVal.split("</p><p color=\"green\">");
                        String thisName = epdVal[0].replace("<html><center><p>", "");
                        String thisPrice = epdVal[1].replace("원</p></center></html>", "").replace(",", "");
                        

                        if(num == 1) {
                            menuSelDto.setSel1Name(thisName);
                            menuSelDto.setSel1Price(thisPrice);
                            orderMenu1Text3.setText("<html>"+thisName+"<br>("+String.format("%,d", Integer.parseInt(thisPrice))+"원)</html>");

                        }else if(num == 2) {
                            menuSelDto.setSel2Name(thisName);
                            menuSelDto.setSel2Price(thisPrice);
                            orderMenu2Text3.setText("<html>"+thisName+"<br>("+String.format("%,d", Integer.parseInt(thisPrice))+"원)</html>");

                        }else if(num == 3) {
                            String sel3Toggle = SelectToggleMenu(menuSelDto.getSel3Name(), menuSelDto.getSel3Price(), thisName, thisPrice);
                            String[] epdSel3Toggle = sel3Toggle.split("/"); 

                            if(epdSel3Toggle.length > 0){
                                menuSelDto.setSel3Name(epdSel3Toggle[0]);
                                menuSelDto.setSel3Price(epdSel3Toggle[1]);
                                orderMenu3Text3.setText("<html>"+menuSelDto.getSel3Name()+"<br>("+menuSelDto.getSel3Price()+")</html>");
                            }else{
                                menuSelDto.setSel3Name(null);
                                menuSelDto.setSel3Price(null);
                                orderMenu3Text3.setText(orderMenu3Text3Cont);
                            }

                        }else if(num == 4) {
                            String sel4Toggle = SelectToggleMenu(menuSelDto.getSel4Name(), menuSelDto.getSel4Price(), thisName, thisPrice);
                            String[] epdSel4Toggle = sel4Toggle.split("/"); 

                            if(epdSel4Toggle.length > 0){
                                menuSelDto.setSel4Name(epdSel4Toggle[0]);
                                menuSelDto.setSel4Price(epdSel4Toggle[1]);
                                orderMenu4Text3.setText("<html>"+menuSelDto.getSel4Name()+"<br>("+menuSelDto.getSel4Price()+")</html>");
                            }else{
                                menuSelDto.setSel4Name(null);
                                menuSelDto.setSel4Price(null);
                                orderMenu4Text3.setText(orderMenu4Text3Cont);
                            }

                        }else if(num == 5) {
                            String sel5Toggle = SelectToggleMenu(menuSelDto.getSel5Name(), menuSelDto.getSel5Price(), thisName, thisPrice);
                            String[] epdSel5Toggle = sel5Toggle.split("/"); 

                            if(epdSel5Toggle.length > 0){
                                menuSelDto.setSel5Name(epdSel5Toggle[0]);
                                menuSelDto.setSel5Price(epdSel5Toggle[1]);
                                orderMenu5Text3.setText("<html>"+menuSelDto.getSel5Name()+"<br>("+menuSelDto.getSel5Price()+")</html>");
                            }else{
                                menuSelDto.setSel5Name(null);
                                menuSelDto.setSel5Price(null);
                                orderMenu5Text3.setText(orderMenu5Text3Cont);
                            }

                        }else if(num == 6) {
                            menuSelDto.setSel6Name(thisName);
                            menuSelDto.setSel6Price(thisPrice);
                            orderMenu6Text3.setText("<html>"+thisName+"<br>("+String.format("%,d", Integer.parseInt(thisPrice))+"원)</html>");

                        }
                    }
                });
                i++;
            }

            // 4. 데이터베이스에 연결된 자원 종료
            conClose(rs, pstmt, con);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
    // 주문확인하기 버튼 눌렀을 때, 선택한 메뉴들 장바구니에 넣기
    ////////////////////////////////////////////////////////////////////////////////////////////
    void orderAddCart() {

        // 메뉴 선택 내용 정리
        String TempSel1Name = menuSelDto.getSel1Name();
        int TempSel1Price = Integer.parseInt(menuSelDto.getSel1Price());
        String TempSel2Name = menuSelDto.getSel2Name();
        int TempSel2Price = Integer.parseInt(menuSelDto.getSel2Price());
        String TempSel3Name = menuSelDto.getSel3Name();
        int TempSel3Price = 0;
        String TempSel4Name = menuSelDto.getSel4Name();
        int TempSel4Price = 0;
        String TempSel5Name = menuSelDto.getSel5Name();
        int TempSel5Price = 0;
        String TempSel6Name = menuSelDto.getSel6Name();
        int TempSel6Price = Integer.parseInt(menuSelDto.getSel6Price());

        if(menuSelDto.getSel3Name() == null){
            TempSel3Name = "선택안함";
        }else{
            String[] EpdTempSel3Price = menuSelDto.getSel3Price().split(", ");
            for(int i=0; i<EpdTempSel3Price.length; i++){
                if(Integer.parseInt(EpdTempSel3Price[i]) > 0){
                    TempSel3Price += Integer.parseInt(EpdTempSel3Price[i]);
                }
            }
        }

        if(menuSelDto.getSel4Name() == null){
            TempSel4Name = "선택안함";
        }else{
            String[] EpdTempSel4Price = menuSelDto.getSel4Price().split(", ");
            for(int i=0; i<EpdTempSel4Price.length; i++){
                if(Integer.parseInt(EpdTempSel4Price[i]) > 0){
                    TempSel4Price += Integer.parseInt(EpdTempSel4Price[i]);
                }
            }
        }

        if(menuSelDto.getSel5Name() == null){
            TempSel5Name = "선택안함";
        }else{
            String[] EpdTempSel5Price = menuSelDto.getSel5Price().split(", ");
            for(int i=0; i<EpdTempSel5Price.length; i++){
                if(Integer.parseInt(EpdTempSel5Price[i]) > 0){
                    TempSel3Price += Integer.parseInt(EpdTempSel5Price[i]);
                }
            }
        }


        // 총 합계 금액
        int TempSelSumprice = TempSel1Price + TempSel2Price + TempSel3Price + TempSel4Price + TempSel5Price + TempSel6Price;

        // 주문 배열에 넣기
        orderCartMenu.add(new AddCartDTO(TempSel1Name, TempSel1Price, TempSel2Name, TempSel2Price, TempSel3Name, TempSel3Price, TempSel4Name, TempSel4Price, TempSel5Name, TempSel5Price, TempSel6Name, TempSel6Price, TempSelSumprice));

        // 장바구니 새로고침
        refreshCart();
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
    // 장바구니 새로고침
    ////////////////////////////////////////////////////////////////////////////////////////////
    void refreshCart() {

        // 장바구니 합계금액
        int totalCartSumPrice = 0;

        // 테이블 리셋
        orderCartListModel.setRowCount(0);

        // 테이블에 데이터 삽입
        for(int i=0; i<orderCartMenu.size(); i++){
            AddCartDTO ACdto = orderCartMenu.get(i);
 
            int setTMno = i+1;
            String setTMmenu = "<html><center>"+ACdto.getAddSel1Name()+"<br>("+String.format("%,d", ACdto.getAddSel1Price())+"원)</center></html>";
            String setTMopt = "<html><center>빵 : "+ACdto.getAddSel2Name()+" ("+String.format("%,d", ACdto.getAddSel2Price())+"원)<br>"
                    + "추가 : "+ACdto.getAddSel3Name()+" ("+String.format("%,d", ACdto.getAddSel3Price())+"원)<br>"
                    + "야채 : "+ACdto.getAddSel4Name()+" ("+String.format("%,d", ACdto.getAddSel4Price())+"원)<br>"
                    + "소스 : "+ACdto.getAddSel5Name()+" ("+String.format("%,d", ACdto.getAddSel5Price())+"원)</center></html>";
            String setTMset = "<html><center>"+ACdto.getAddSel6Name()+"<br>("+String.format("%,d", ACdto.getAddSel6Price())+"원)</center></html>";
            String setTMprice = String.format("%,d", ACdto.getAddSumPrice())+"원";

            Object[] addMenuRow = {setTMno, setTMmenu, setTMopt, setTMset, setTMprice};
            orderCartListModel.addRow(addMenuRow);
            
            totalCartSumPrice += ACdto.getAddSumPrice();
        }
        autoAdjustRowHeights(orderCartListTable);

        // 장바구니 총 합계금액 표시
        orderCartText2.setText(String.format("%,d", totalCartSumPrice)+"원");


        // 메뉴선택 리셋
        selectReset();
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
    // 주문 넣기
    ////////////////////////////////////////////////////////////////////////////////////////////
    void setOrderDone() {
        int result = JOptionPane.showConfirmDialog(null, "선택하신 메뉴와 정보를 확인하셨나요?\n주문&결제를 진행하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);                 
        if(result == 0){
            try {
            	// 주문번호 앞(날짜) 생성
                SimpleDateFormat nowDateFormat = new SimpleDateFormat("yyMMdd");
                Calendar c1 = Calendar.getInstance();
                String sessHeader = nowDateFormat.format(c1.getTime());

                // 오늘날짜 주문건 체크 SQL
                connect();

                pstmt = con.prepareStatement("select ONO from SUBORDER where ONO like '%"+sessHeader+"%' order by ONO desc");
                rs = pstmt.executeQuery();

                String chkSess = null;
                if(rs.next()) {
                		chkSess = rs.getString(1);
                }else {
                		chkSess = null;
                }

                conClose(rs, pstmt, con);

                // 주문번호 생성
                if(chkSess != null) {
            		String[] epdSess = chkSess.split("_");
            		int tmp = Integer.parseInt(epdSess[1]) + 1;
            		String sessFooter = setLength(tmp);
            		ONO = sessHeader+"_"+sessFooter; 
            	}else{
            		ONO = sessHeader+"_000001"; 
            	}


                // 식사장소
                if(orderCartRadio1_1.isSelected()) {
                    ODIVISION = "here";
                }else{
                    ODIVISION = "takeout";
                }


                // 회원정보
                if(User_id != null) {
                	OID = User_id;
                    ONAME = User_name;
                }else{
                	OID = null;
                	ONAME = "비회원";
                }


                // 결제방법
                if(orderCartRadio2_1.isSelected()) {
                    OPAY = "cash";
                }else if(orderCartRadio2_2.isSelected()) {
                    OPAY = "card";
                }else if(orderCartRadio2_3.isSelected()) {
                    OPAY = "samsungpay";
                }else if(orderCartRadio2_4.isSelected()) {
                    OPAY = "naverpay";
                }else if(orderCartRadio2_5.isSelected()) {
                    OPAY = "kakaopay";
                }


                // 적립금사용
                if(User_id != null && orderCartInput.getText() != null) {
            		OSAVED = Integer.parseInt(orderCartInput.getText());

            		if(OSAVED > User_point) {
            			JOptionPane.showMessageDialog(null, "보유하고있는 적립금 보다 많은 금액을 사용할 수 없습니다.");
            			orderCartInput.setText("0");
            			orderCartInput.requestFocus();
            			return;
            		}

            		// 유저 적립금 수정
            		User_point = User_point - OSAVED;

            		connect();

            		pstmt = con.prepareStatement("update SUBUSER set UREWARD = ? where U_ID = ?");
        			pstmt.setInt(1, User_point);
        			pstmt.setString(2, User_id);
        			pstmt.executeUpdate();

        			pstmt.close();
                }else{
                	OSAVED = 0;
                }


                connect();

                // 주문상품 넣기
                for(int i=0; i<orderCartMenu.size(); i++){
                    AddCartDTO ACdto = orderCartMenu.get(i);

                    sql = "insert into PRODUCT values(PRODUCT_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    pstmt = con.prepareStatement(sql);

                	pstmt.setString(1, ONO); //PNO
                	pstmt.setString(2, ACdto.getAddSel1Name()); //PMENU
                	pstmt.setInt(3, ACdto.getAddSel1Price()); //PMENUPRICE
                	pstmt.setString(4, ACdto.getAddSel2Name()); //PBREAD
                	pstmt.setInt(5, ACdto.getAddSel2Price()); //PBREADPRICE
                	pstmt.setString(6, ACdto.getAddSel3Name()); //PTOPPING
                	pstmt.setInt(7, ACdto.getAddSel3Price()); //PTOPPINGPRICE
                	pstmt.setString(8, ACdto.getAddSel4Name()); //PVEGIES
                	pstmt.setInt(9, ACdto.getAddSel4Price()); //PVEGIESPRICE
                	pstmt.setString(10, ACdto.getAddSel5Name()); //PSAUCE
                	pstmt.setInt(11, ACdto.getAddSel5Price()); //PSAUCEPRICE
                	pstmt.setString(12, ACdto.getAddSel6Name()); //PSET
                	pstmt.setInt(13, ACdto.getAddSel6Price()); //PSETPRICE
                	pstmt.setInt(14, ACdto.getAddSumPrice()); //PTOTALPRICE
                	pstmt.executeUpdate();

                    OPRICE += ACdto.getAddSumPrice();
                }
                OPRICE = OPRICE - OSAVED;



                // 주문정보 테이블(SUBORDER)에 입력
                // 1 주문번호, 2 식사장소, 3 주문자이름, 4 주문자ID, 5 결제방법, 6 결제금액, 7 적립금사용, 주문일자
                sql = "insert into SUBORDER values(?, ?, ?, ?, ?, ?, ?, sysdate)";
                pstmt = con.prepareStatement(sql);
            	pstmt.setString(1, ONO);
            	pstmt.setString(2, ODIVISION);
            	pstmt.setString(3, ONAME);
            	pstmt.setString(4, OID);
            	pstmt.setString(5, OPAY);
            	pstmt.setInt(6, OPRICE);
            	pstmt.setInt(7, OSAVED);
            	pstmt.executeUpdate();


            	// 주문 적립금 적립
            	if(User_id != null) {
            		int addBuy_point = (int)(OPRICE * buyPointPercent);
            		User_point = User_point + addBuy_point;

            		pstmt = con.prepareStatement("update SUBUSER set UREWARD = ? where U_ID = ?");
            		pstmt.setInt(1, User_point);
            		pstmt.setString(2, User_id);
            		pstmt.executeUpdate();
            		pstmt.close();

            		JOptionPane.showMessageDialog(null, "주문시 적립금 "+String.format("%,d", addBuy_point)+"원이 적립되었습니다.");
            	}


        		conClose(rs, pstmt, con);


                // 페이지 이동
                pageMove("done");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            return;
        }
    }

    

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //  주문관리 - 총 매출액 계산 메서드
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    void subOrderSum() {
    	try {
    		// 1. 데이터베이스로 전송할 SQL문 작성.
    		sql = "select sum(oprice) from suborder";
    		pstmt = con.prepareStatement(sql);

    		// 2. 데이터베이스에 SQL문을 전송 및 SQL문 실행.
    		rs = pstmt.executeQuery();

    		if(rs.next()) {
    			subOrderTextPane.setText(String.format("%,d", rs.getInt(1))+ " 원");
    		}

    		// 3. 데이터베이스에 연결되어 있던 자원 종료
    		rs.close(); pstmt.close();

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //  총 수량 계산 메서드
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    void subOrderCount() {
    	try {
    		// 1. 데이터베이스로 전송할 SQL문 작성.
    		sql = "select count(ono) from suborder";
    		pstmt = con.prepareStatement(sql);

    		// 2. 데이터베이스에 SQL문을 전송 및 SQL문 실행.
    		rs = pstmt.executeQuery();

    		if(rs.next()) {
    			subOrderCountTextPane.setText("(" + rs.getInt(1)+ "건)");
    		}

    		// 3. 데이터베이스에 연결되어 있던 자원 종료
    		rs.close(); pstmt.close(); 
					
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // 주문관리 - 전체 조회 메서드
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    void subOrderSelect(String page) {
    	subOrderModel.setRowCount(0);

    	try {
			// 1. 주문 테이블(SubOrder_sub) 불러오기
			sql = "select * from suborder order by odate desc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				String ono = rs.getString("ono");
				String division = rs.getString("odivision");
				String name = rs.getString("oname");
				String id = rs.getString("oid");
				String pay = rs.getString("opay");
				String subOrderPrice = decimalFormat.format(rs.getInt("oprice"))+"원";
				String subOrderSaved = decimalFormat.format(rs.getInt("osaved"))+"원";
				String date = rs.getString("odate");


				// 만약 구분이 here 이면 방문식사, takeout이면 포장.
				if(division.equals("here")) {
					division = "방문 식사";
				} else if (division.equals("takeout")) {
					division = "포장";
				}

				// pay 한글 변환.
				switch(pay) { 
					case "cash" : pay = "현금"; break;
					case "card" : pay = "카드"; break;
					case "samsungpay" : pay = "삼성페이"; break;
					case "naverpay" : pay = "네이버페이"; break;
					case "kakaopay" : pay = "카카오페이"; break;
				}


				// id 값이 null이면 표시 안 함. 있으면 id 표시함.
				if (id != null) {
					id = "(id : " + id + ")";
				} else {
					id = " ";
				}


				subOrderContent = "<html>";

				// 2. 주문 상품 테이블(product) 불러오기
				sqlsub = "select * from product where pno = '"+ono+"'";
				pstmtsub = con.prepareStatement(sqlsub);
				rssub = pstmtsub.executeQuery();

				while(rssub.next()) {
					String menu = rssub.getString("pmenu");
					String menuPrice = decimalFormat.format(rssub.getInt("pmenuprice"));
					String bread = rssub.getString("pbread");
					String breadPrice = decimalFormat.format(rssub.getInt("pbreadprice"));
					String topping = rssub.getString("ptopping");
					String toppingPrice = decimalFormat.format(rssub.getInt("ptoppingprice"));
					String vegies = rssub.getString("pvegies");
					String vegiesPrice = decimalFormat.format(rssub.getInt("pvegiesprice"));
					String sauce = rssub.getString("psauce");
					String saucePrice = decimalFormat.format(rssub.getInt("psauceprice"));
					String set = rssub.getString("pset");
					String setPrice = decimalFormat.format(rssub.getInt("psetprice"));

					// 토핑 값이 null 이면 선택 안 함으로 표시.
					if (topping == null) {
						topping = "선택안함";
					}

					subOrderContent += (("<div style= text-align:center>" 
							+ "<br>" +  menu  +  " (" + menuPrice + "원) " + "<br>" +  
							" - " + bread +  " (" + breadPrice + "원) " + "<br>" +
							" - " + topping  + " (" + toppingPrice  +  "원) " + "<br>" + " - " + vegies +  " (" + vegiesPrice + "원) " + "<br>" +
							" - " +  sauce + " (" + saucePrice +  "원) " + "<br>" + " - " + set + "(" +  setPrice+ "원) " + "<br>"+"</div><br>"));
				}
				subOrderContent += "</html>";

				Object[] data = {ono, division, name + id, subOrderContent, pay, subOrderPrice, subOrderSaved, date};

				if(page == "statistics") {
					statisticsModel1.addRow(data);
				}else {
					subOrderModel.addRow(data);
				}

			} // while end
			
			// 3. 데이터베이스에 연결되어 있던 자원 종료
			rs.close(); pstmt.close();

		}catch (Exception e) {
			e.printStackTrace();
		}
	}



    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // 날짜 별 주문 조회 (오버로딩)
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    void subOrderSelect(String odate, String page) {
    	try {
    		// 1. 데이터베이스로 전송할 SQL문 작성.
			sql = "select * from suborder where odate like ?";

			//서브오더에서 모든 자료 날짜가 선택한 창일 경우			
			pstmt = con.prepareStatement(sql);

			// ? 자리에 들어갈 값 줌. 순서는 ? 순서대로 차례로.
			pstmt.setString(1, odate);

			// 2. 데이터베이스에 SQL문을 전송 및 SQL문 실행.
			rs = pstmt.executeQuery();


			while(rs.next()) {
				String ono = rs.getString("ono");
				String division = rs.getString("odivision");
				String name = rs.getString("oname");
				String id = rs.getString("oid");
				String pay = rs.getString("opay");
				String subOrderPrice = decimalFormat.format(rs.getInt("oprice"));
				String subOrderSaved = decimalFormat.format(rs.getInt("osaved"));
				String date = rs.getString("odate");


				// 만약 구분이 here 이면 방문식사, takeout이면 포장.
				if(division.equals("here")) {
					division = "방문 식사";
				} else if (division.equals("takeout")) {
					division = "포장";
				}

				// pay 한글 변환.
				switch(pay) { 
					case "cash" : pay = "현금"; break;
					case "card" : pay = "카드"; break;
					case "samsungpay" : pay = "삼성페이"; break;
					case "naverpay" : pay = "네이버페이"; break;
					case "kakaopay" : pay = "카카오페이"; break;
				}


				// id 값이 null이면 표시 안 함. 있으면 id 표시함.
				if (id != null) {
					id = "(id : " + id + ")";
				} else {
					id = " ";
				}


				subOrderContent = "<html>";

				// 2. 주문 상품 테이블(product) 불러오기
				sqlsub = "select * from product where pno = '"+ono+"'";
				pstmtsub = con.prepareStatement(sqlsub);
				rssub = pstmtsub.executeQuery();

				while(rssub.next()) {
					String menu = rssub.getString("pmenu");
					String menuPrice = decimalFormat.format(rssub.getInt("pmenuprice"));
					String bread = rssub.getString("pbread");
					String breadPrice = decimalFormat.format(rssub.getInt("pbreadprice"));
					String topping = rssub.getString("ptopping");
					String toppingPrice = decimalFormat.format(rssub.getInt("ptoppingprice"));
					String vegies = rssub.getString("pvegies");
					String vegiesPrice = decimalFormat.format(rssub.getInt("pvegiesprice"));
					String sauce = rssub.getString("psauce");
					String saucePrice = decimalFormat.format(rssub.getInt("psauceprice"));
					String set = rssub.getString("pset");
					String setPrice = decimalFormat.format(rssub.getInt("psetprice"));


					// 토핑 값이 null 이면 선택 안 함으로 표시.
					if (topping == null) {
						topping = "선택안함";
					} 

					
					subOrderContent += (("<div style= text-align:center>" 
							+ "<br>" +  menu  +  " (" + menuPrice + "원) " + "<br>" +  
							" - " + bread +  " (" + breadPrice + "원) " + "<br>" +
							" - " + topping  + " (" + toppingPrice  +  "원) " + "<br>" + " - " + vegies +  " (" + vegiesPrice + "원) " + "<br>" +
							" - " +  sauce + " (" + saucePrice +  "원) " + "<br>" + " - " + set + "(" +  setPrice+ "원) " + "<br>"+"</div><br>"));						
				}
				subOrderContent += "</html>";

				Object[] data = {ono, division, name + id, subOrderContent, pay, subOrderPrice + "원", subOrderSaved + "원", date};

				if(page == "statistics") {
					statisticsModel1.addRow(data);
				}else {
					subOrderModel.addRow(data);
				}

			} // while end
			// 저장된 한 개의 레코드(데이터)를 model에 추가해 준다.


			// 3. 데이터베이스에 연결되어 있던 자원 종료
			rs.close(); pstmt.close(); 


    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // 선택된 날짜의 매출 합계 구하는 메서드
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    void subOrderSum(String odate) {
    	try {
    		// 1. 데이터베이스로 전송할 SQL문 작성.
    		sql = "select sum(oprice) from suborder where odate like ?";
    		pstmt = con.prepareStatement(sql);

    		// ? 자리에 들어갈 값 줌. 순서는 ? 순서대로 차례로.
    		pstmt.setString(1, odate);

    		// 2. 데이터베이스에 SQL문을 전송 및 SQL문 실행.
    		rs = pstmt.executeQuery();

    		if(rs.next()) {
    			subOrderTextPane.setText(String.format("%,d", rs.getInt(1))+ " 원");
    		}

    		// 3. 데이터베이스에 연결되어 있던 자원 종료
    		rs.close(); pstmt.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }



	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// 선택된 날짜의 주문 수량 합계 구하는 메서드
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	void subOrderCount(String odate) {
		try {
			// 1. 데이터베이스로 전송할 SQL문 작성.
			sql = "select count(ono) from suborder where odate like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, odate);

			// 2. 데이터베이스에 SQL문을 전송 및 SQL문 실행.
			rs = pstmt.executeQuery();

			if(rs.next()) {
				subOrderCountTextPane.setText("(" + rs.getInt(1)+ "건)");
			}
	
			// 3. 데이터베이스에 연결되어 있던 자원 종료
			rs.close(); pstmt.close(); 

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// 선택된 주문 order에서 삭제 메서드
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	void subOrderDeleteo(String no) {
		try {
			sql = "delete from suborder where ono = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, no);

			int res = pstmt.executeUpdate();
			if (res > 0 ) {
				JOptionPane.showMessageDialog(null, "성공적으로 삭제되었습니다.");
			} else {
				JOptionPane.showMessageDialog(null, "삭제에 실패했습니다.");
			}
			rs.close(); 
			pstmt.close();;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// 선택된 주문 product에서 삭제 메서드
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	void subOrderDeletep(String no) {
		try {
			sql = "delete from product where pno = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.executeUpdate();

			rs.close();
			pstmt.close();;
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}






    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // 메뉴 입력창 깨끗이 하기
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    void menuModifyclose() {
        menuModifyjcb1.setSelectedItem("선택");
        menuModifytextField1.setText(null);
        menuModifytextField2.setText(null);
        menuModifytextField3.setText(null);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // DB에서 메뉴 목록 불러오는 메서드
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    void menuModifyselect() {
    	menuModifymodel.setRowCount(0);

        try {
            // 1. 데이터베이스로 전송할 SQL문 작성.
            sql = "select * from menu order by mno asc";

            // 2. 데이터베이스에 SQL문 전송 및 SQL문 실행.
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                int mno = rs.getInt("mno");
                String mcategory = rs.getString("mcategory");
                String mname = rs.getString("mname");
                String mprice = decimalFormat.format(rs.getInt("mprice"))+"원";
                String mimage = rs.getString("mimage");
                String mday = rs.getString("mday");

                // 저장된 한개의 레코드(데이터)를 model에 추가해 주면 됨.
                Object[] data = {mno, mcategory, mname, mprice,mimage, mday};
                menuModifymodel.addRow(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statisticsModelCentered(menuModifytable);
        } 
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // 메뉴 정보 수정하는 메서드
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    void menuModifyUpdate() {
        try {
            sql = "update menu set mcategory = ?, mname = ?, mimage =?, mprice = ? where mno = ?";
            pstmt = con.prepareStatement(sql);

            if(menuModifycategory.equals("빵")){
                pstmt.setString(1, "bread");
            }else if(menuModifycategory.equals("샌드위치")) {
                pstmt.setString(1, "sandwich"); 
            }else if(menuModifycategory.equals("추가 토핑")) {
                pstmt.setString(1, "add");  
            }else if(menuModifycategory.equals("야채")) {
                pstmt.setString(1, "vegetable");    
            }else if(menuModifycategory.equals("소스")) {
                pstmt.setString(1, "sauce");    
            }else if(menuModifycategory.equals("세트")) {
                pstmt.setString(1, "set");
            }

            //가격 텍스트창에서 받아온 값이 String일수 있어서 DB에 에러가 날수 있으니까 숫자만 남겨 DB에 넘긴다.
            menuModifystr = menuModifytextField3.getText();
            menuModifyintStr = menuModifystr.replaceAll("[^0-9]", "");

            pstmt.setString(2,menuModifytextField1.getText());
            pstmt.setString(3,menuModifytextField2.getText());
            pstmt.setInt(4,Integer.parseInt(menuModifyintStr));
            pstmt.setString(5, menuModifyupdateUNO);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }       



    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //메뉴 삭제 메서드
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    void menuModifydelete() {
        try {
            sql = "delete from menu where mname = ?";
            pstmt= con.prepareStatement(sql);

            int row = menuModifytable.getSelectedRow();
            pstmt.setString(1, menuModifymodel.getValueAt(row, 2).toString());
            pstmt.executeUpdate();

            // 테이블상에서 한 행 삭제
            menuModifymodel.removeRow(row);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // 메뉴 추가 메서드
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    void menuModifyinsert() {
        try {
            sql = "insert into menu values(mno.nextval, ?, ?, ?, ?, sysdate)";
            pstmt = con.prepareStatement(sql);

            if(menuModifyjcb1.getSelectedItem().toString().equals("빵")) {
                pstmt.setString(1, "bread");
            }else if(menuModifyjcb1.getSelectedItem().toString().equals("샌드위치")) {
                pstmt.setString(1, "sandwich");
            }else if(menuModifyjcb1.getSelectedItem().toString().equals("추가 토핑")) {
                pstmt.setString(1, "add");
            }else if(menuModifyjcb1.getSelectedItem().toString().equals("야채")) {
                pstmt.setString(1, "vegetable");
            }else if(menuModifyjcb1.getSelectedItem().toString().equals("소스")) {
                pstmt.setString(1, "sauce");
            }else if(menuModifyjcb1.getSelectedItem().toString().equals("세트")) {
                pstmt.setString(1, "set");
            }

            pstmt.setString(2,menuModifytextField1.getText());
            pstmt.setString(3,menuModifytextField2.getText());
            pstmt.setInt(4,Integer.parseInt(menuModifytextField3.getText()));
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }






	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// 회원관리 메소드
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	// 회원정보 입력 화면을 깨끗하게해주는 메소드
	void subUserClear() {
		userComboBox.setSelectedItem("회원");
		subUserJtf1.setText("");
		subUserJtf2.setText("");
		subUserJtf3.setText("");
		subUserJtf4.setText("");
		subUserJtf5.setText("");
	}


	// SubUser 테이블의 전체 목록을 조회하는 메소드
	void userSelect() {
		userModel.setRowCount(0);

		try {
			pstmt = con.prepareStatement("select * from subuser order by udate");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				while (rs.next()) {
					String utype = "";
					if(rs.getString("utype").equals("admin")) {
						utype = "관리자";
					}else{
						utype = "회원";
					}

					String u_id = rs.getString("u_id");
					String uname = rs.getString("uname");
					String uphone = rs.getString("uphone");
					int ureward = rs.getInt("ureward");
					String udate = rs.getString("udate");
					Object[] data = { utype, u_id, uname, uphone, String.format("%,d", ureward)+"원", udate };
					userModel.addRow(data);// 저장한 한 개의 데이터를 모델에 추가
				}
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// 회원 데이터베이스에 저장하는 메소드
	void UserInsert() {
		try {
			pstmt = con.prepareStatement("insert into subuser values(?, ?, ?, ?, ?, ?, sysdate)");

			int setUserInsertP = 0;
			try {
				setUserInsertP = Integer.parseInt(subUserJtf5.getText().toString());
			} catch (Exception e) {
				setUserInsertP = 0;
			}

			if ((userComboBox.getSelectedItem().toString()).equals("회원")) {
				pstmt.setString(1, "member");
			} else if (userComboBox.getSelectedItem().toString().equals("관리자")) {
				pstmt.setString(1, "admin");
			}
			pstmt.setString(2, subUserJtf1.getText());
			pstmt.setString(3, encryptMD5(subUserJtf2.getText())); // 비번
			pstmt.setString(4, subUserJtf3.getText());
			pstmt.setString(5, subUserJtf4.getText());
			pstmt.setInt(6, setUserInsertP);
			pstmt.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// 회원 수정하는 메소드 - 회원타입(회원/관리자), 비밀번호, 이름, 핸드폰번호, 적립금 수정 가능하다.
	// 아이디는 수정할 수 없다.
	void userUpdate() {
		try {
			pstmt = con.prepareStatement("update subuser set utype=?, u_pw=?, uname=?, uphone=?, ureward=?  where u_id = ?");

			if ((userComboBox.getSelectedItem().toString()).equals("회원")) {
				pstmt.setString(1, "member");
			} else if (userComboBox.getSelectedItem().toString().equals("관리자")) {
				pstmt.setString(1, "admin");
			}
			pstmt.setString(2, encryptMD5(subUserJtf2.getText())); // u_pw
			pstmt.setString(3, subUserJtf3.getText()); // uname
			pstmt.setString(4, subUserJtf4.getText()); // uphone
			pstmt.setInt(5, Integer.parseInt(subUserJtf5.getText())); // ureward
			pstmt.setString(6, subUserJtf1.getText()); // u_id

			// 아이디는 수정 불가능한 값이므로 선택한 열의 아이디와 텍스트필드에 입력한 아이디가 다르면
			// "아이디는 수정할 수 없습니다." 라는 메시지를 띄워주고 userSql문을 전송하지 않는다.
			int row = userTable.getSelectedRow();
			if (!userModel.getValueAt(row, 1).toString().equals(subUserJtf1.getText())) {
				JOptionPane.showMessageDialog(null, "아이디는 수정할 수 없습니다.");
				pstmt.close();
			} else { // 선택한 열의 아이디와 텍스트필드에 입력한 아이디가 같아야지 DB가 Update된다.
				pstmt.executeUpdate();
				pstmt.close();
			}
			// userPstmt.close();
		} catch (ArrayIndexOutOfBoundsException n) {
		} catch (NumberFormatException n) {
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	// 회원 삭제 메소드
	void userDelete() {
		try {
			pstmt = con.prepareStatement("delete from subuser where u_id = ?");
			int row = userTable.getSelectedRow();
			pstmt.setString(1, userModel.getValueAt(row, 1).toString());
			pstmt.executeUpdate();
			userModel.removeRow(row);
			pstmt.close();
		} catch (Exception e) {
			if (e.getMessage().contains("child")) {
				JOptionPane.showMessageDialog(null, "주문 내역이 있는 회원입니다! 삭제가 불가능합니다.");
			}
			e.printStackTrace();
		}
	}







    ////////////////////////////////////////////////////////////////////////////////////////////
    // 테이블 가운데 정렬 + 테이블 및 헤더 폰트 설정 메서드
    ////////////////////////////////////////////////////////////////////////////////////////////
    void statisticsModelCentered(JTable table) {

        // 가운데정렬
        DefaultTableCellRenderer dtchr = new DefaultTableCellRenderer();

        // dtchr을 가운데정렬 설정
        dtchr.setHorizontalAlignment(SwingConstants.CENTER);

        // 정렬할 테이블의 ColumnstatisticsModel 가져오기
        TableColumnModel tcmSchedule = table.getColumnModel();

        // 반복문 이용하여 테이블을 가운데정렬로 지정
        for (int i=0; i<tcmSchedule.getColumnCount(); i++) {
            tcmSchedule.getColumn(i).setCellRenderer(dtchr);
        }

        table.setFont(new Font("맑은 고딕", Font.PLAIN, 13)); // table 폰트 설정
        table.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 14)); // header 폰트 설정
        table.getTableHeader().setPreferredSize(new Dimension(50, 30)); // header width, height 조절
    }



    ////////////////////////////////////////////////////////////////////////////////////////////
    // 테이블 컬럼 너비 자동조절
    ////////////////////////////////////////////////////////////////////////////////////////////
    public void autoAdjustColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int columeWidth = 50; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                columeWidth = Math.max(comp.getPreferredSize().width +1 , columeWidth);
            }
            columnModel.getColumn(column).setPreferredWidth(columeWidth);
        }
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
    // 테이블 행 높이 자동조절
    ////////////////////////////////////////////////////////////////////////////////////////////
    void autoAdjustRowHeights(JTable table) {
        for (int row = 0; row < table.getRowCount(); row++) {
            int rowHeight = table.getRowHeight();
            for (int column = 0; column < table.getColumnCount(); column++) {
                Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }
            table.setRowHeight(row, rowHeight);
        }
    }





    ////////////////////////////////////////////////////////////////////////////////////////////
    // 페이지 만들기 (창 이름, 창 타이틀)
    ////////////////////////////////////////////////////////////////////////////////////////////
    private void mkPage(JFrame pName, String pTitle, String pBg) {
        pName.setTitle(pTitle);
        pName.setBounds(winXpos, winYpos, winWidth, winHeight);
        pName.setResizable(false); // 창의 크기를 변경하지 못하게
        pName.setLocationRelativeTo(null); // 창이 가운데 나오게
        pName.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pName.getContentPane().setLayout(null);

        // 배경설정
        try {
            pName.setContentPane(new ImagePanel(ImageIO.read(Main_UI.class.getResource(pBg))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////
    // 텍스트라벨 생성 (라벨컴포넌트, 텍스트, 폰트두께, 폰트사이즈, 색상R, 색상G, 색상B , X좌표, Y좌표, 가로크기, 세로크기)
    ////////////////////////////////////////////////////////////////////////////////////////////
    private void mkTxt(JLabel lb, String txt, String fWeight, int fSize, int colorR, int colorG, int colorB, int posX, int posY, int width, int height) {
        lb.setText(txt);
        if(fWeight.equalsIgnoreCase("bold")){
            lb.setFont(new Font("맑은 고딕", Font.BOLD, fSize));
        }else{
            lb.setFont(new Font("맑은 고딕", Font.PLAIN, fSize));
        }
        lb.setForeground(new Color(colorR, colorG, colorB));
        lb.setBounds(posX, posY, width, height);
        lb.setVerticalAlignment(SwingConstants.TOP);
    }
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////
    // 캘린더 pop-up 시 오늘 날짜로 세팅 (테이블모델, dateChooser)
    //////////////////////////////////////////////////////////////////////////////////////////// 
    String todaySet(DefaultTableModel model, JDateChooser dateChooser) {
    	
    	model.setRowCount(0);
    	String odate = dtf.format(today); // 자동 선택 값 오늘 날짜로 변경
    	try {
    		dateChooser.setDate(new SimpleDateFormat("yy/MM/dd").parse(odate));
    	} catch (ParseException e1) {
    		e1.printStackTrace();
    	}
    	
		return odate;
    	
    }


    ////////////////////////////////////////////////////////////////////////////////////////////
    // 버튼 생성 (버튼컴포넌트, 기본이미지, 오버이미지, X좌표, Y좌표, 가로크기, 새로크기)
    ////////////////////////////////////////////////////////////////////////////////////////////
    private void mkBtn(JButton btn, String imgOff, String imgOn, int posX, int posY, int width, int height) {
    	btn.setIcon(new ImageIcon(Main_UI.class.getResource(imgOff)));
        btn.setRolloverIcon(new ImageIcon(Main_UI.class.getResource(imgOn)));
        btn.setBorderPainted(false);
        btn.setBounds(posX, posY, width, height);
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
    // 텍스트인풋 생성 (인풋컴포넌트, 기본값입력, 입력길이, 폰트두께, 폰트사이즈, 입력정렬, X좌표, Y좌표, 가로크기, 세로크기)
    ////////////////////////////////////////////////////////////////////////////////////////////
    private void mkInp(JTextField jtf, String inTxt, int inLen, String fWeight, int fSize, String align, int posX, int posY, int width, int height) {
        jtf.setText(inTxt);
        jtf.setColumns(inLen);
        if(fWeight.equalsIgnoreCase("bold")){
            jtf.setFont(new Font("맑은 고딕", Font.BOLD, fSize));
        }else{
            jtf.setFont(new Font("맑은 고딕", Font.PLAIN, fSize));
        }
        if(align.equalsIgnoreCase("center")){
            jtf.setHorizontalAlignment(JTextField.CENTER);
        }else{
            jtf.setHorizontalAlignment(JTextField.LEFT);
        }
        jtf.setBounds(posX, posY, width, height);
        jtf.setBackground(Color.WHITE);
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
    // 프레임 배경 설정
    ////////////////////////////////////////////////////////////////////////////////////////////
    class ImagePanel extends JComponent {
        private static final long serialVersionUID = 1L;
        private Image image;
        public ImagePanel(Image image){
            this.image = image;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }





    ////////////////////////////////////////////////////////////////////////////////////////////
    // 데이터베이스를 연동하는 메서드
    ////////////////////////////////////////////////////////////////////////////////////////////
    void connect() {

        String driver = "oracle.jdbc.driver.OracleDriver";
        // String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String url = "jdbc:oracle:thin:@121.164.91.191:1521:xe";
        String user = "web";
        String password = "1234";

        try {
            // 1단계 : 오라클 드라이버 로딩
            Class.forName(driver);

            // 2단계 : 오라클 데이터베이스와 연결 시도
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

    } // connect 메서드 end




    ////////////////////////////////////////////////////////////////////////////////////////////
    // 데이터베이스 사용 자원 종료하는 메서드
    ////////////////////////////////////////////////////////////////////////////////////////////
    void conClose(ResultSet rs, PreparedStatement pstmt){

        try {
            if(rs != null){
                rs.close();
            }

            if(pstmt != null){
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    } // connClose 메서드 end




    ////////////////////////////////////////////////////////////////////////////////////////////
    // 글자 수 맞춰서 0 붙이기
    ////////////////////////////////////////////////////////////////////////////////////////////
    String setLength(int val){
        String temp = "";
        for(int i = (int)(Math.log10(val)+1); i<6; i++){
            temp += "0";
        }
        return temp+val;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////
    // 데이터베이스 사용 자원 종료하는 메서드
    ////////////////////////////////////////////////////////////////////////////////////////////
    void conClose(ResultSet rs, PreparedStatement pstmt, Connection con){

        try {
            if(rs != null){
                rs.close();
            }

            if(pstmt != null){
                pstmt.close();
            }

            if(con != null){
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    } // connClose 메서드 end




    ////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayList 배열에서 특정 인덱스 값 삭제
    ////////////////////////////////////////////////////////////////////////////////////////////
    public static List<String> arrayValueRemove(List<String> resultSelName, int index){
        if(resultSelName == null || index < 0 || index >= resultSelName.size()){
            return resultSelName;
        }

        List<String> result = new ArrayList<String>();

        for(int i=0; i<index; i++){
            result.add(resultSelName.get(i));
        }
 
        for(int i=index; i<resultSelName.size()-1; i++){
            result.add(resultSelName.get(i+1));
        }
 
        return result;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////
    // 비밀번호 암호화
    ////////////////////////////////////////////////////////////////////////////////////////////
    public static String encryptMD5(String pwd) {
        String MD5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pwd.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            MD5 = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            MD5 = null;
        }
        return MD5;
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
    // 전체 폰트 설정
    ////////////////////////////////////////////////////////////////////////////////////////////
    public static void setUIFont(FontUIResource f) {
        Enumeration<?> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource orig = (FontUIResource) value;
                Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {

        try {
            // 대기시간을 위한 로딩 처리
            Timer mloading = new Timer();
            TimerTask mloadingTask = new TimerTask() {
                @Override
                public void run() {
                    mainLoading.setVisible(false);
                }
            };
            mloading.schedule(mloadingTask, loadingTime);

            // 전체 폰트 적용 메서드 실행
            setUIFont(new FontUIResource(new Font("맑은 고딕", 0, 14)));

            // 메인 프로그램 실행
            new Main_UI();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}