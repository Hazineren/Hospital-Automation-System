package View;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.jdi.connect.spi.Connection;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import Helper.*;
import Model.Bashekim;
import Model.Doctor;
import Model.Hasta;

public class LoginGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_hastaTc;
	private JTextField fld_doktorTc;
	private JPasswordField fld_doktorPass;
	private DbConnection conn = new DbConnection();
	private JPasswordField fld_hastaPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setResizable(false);
		setTitle("Hastane Y\u00F6netim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lbl_Logo = new JLabel(new ImageIcon(getClass().getResource("medicine.jpg")));
		lbl_Logo.setBounds(214, 10, 63, 54);
		w_pane.add(lbl_Logo);
		
		JLabel lblNewLabel = new JLabel("Hastane Y\u00F6netim Sistemine Ho\u015Fgeldiniz");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblNewLabel.setBounds(74, 64, 343, 27);
		w_pane.add(lblNewLabel);
		
		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBounds(10, 103, 476, 259);
		w_pane.add(w_tabpane);
		
		JPanel w_hastaLogin = new JPanel();
		w_hastaLogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Hasta Giriþi", null, w_hastaLogin, null);
		w_hastaLogin.setLayout(null);
		
		JLabel lblTcNumaranz = new JLabel("T.C. Numaran\u0131z :");
		lblTcNumaranz.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblTcNumaranz.setBounds(37, 36, 138, 27);
		w_hastaLogin.add(lblTcNumaranz);
		
		JLabel lblifre = new JLabel("\u015Eifre :");
		lblifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblifre.setBounds(120, 83, 55, 27);
		w_hastaLogin.add(lblifre);
		
		fld_hastaTc = new JTextField();
		fld_hastaTc.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fld_hastaTc.setBounds(176, 38, 247, 32);
		w_hastaLogin.add(fld_hastaTc);
		fld_hastaTc.setColumns(10);
		
		JButton btn_HastaLogin = new JButton("Giriþ Yap");
		btn_HastaLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_hastaTc.getText().length()==0 || fld_hastaPassword.getText().length()==0) {
					Helper.showMsg("fill");
				}else {
					boolean key =true;
					try {
						java.sql.Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						while(rs.next()) {
							if(fld_hastaTc.getText().equals(rs.getString("tcno")) && fld_hastaPassword.getText().equals(rs.getString("password"))) {
								if(rs.getString("type").equals("hasta")) {
									Hasta hasta = new Hasta();
									hasta.setId(rs.getInt("id"));
									hasta.setPassword(rs.getString("password"));
									hasta.setTcno(rs.getString("tcno"));
									hasta.setName(rs.getString("name"));
									hasta.setType(rs.getString("type"));
									HastaGUI hastaGUI = new HastaGUI(hasta);
									hastaGUI.setVisible(true);
									dispose();
									key = false;
								}
							}
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(key) {
						Helper.showMsg("Girilen Bilgilere Sahip Bir Hasta Bulunamadý !");
					}
				}
			}
		});
		btn_HastaLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_HastaLogin.setBounds(265, 147, 148, 43);
		w_hastaLogin.add(btn_HastaLogin);
		
		JButton btn_Register = new JButton("Kay\u0131t Ol");
		btn_Register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGuý = new RegisterGUI();
				rGuý.setVisible(true);
				dispose();
			}
		});
		btn_Register.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btn_Register.setBounds(89, 147, 148, 43);
		w_hastaLogin.add(btn_Register);
		
		fld_hastaPassword = new JPasswordField();
		fld_hastaPassword.setBounds(176, 84, 247, 32);
		w_hastaLogin.add(fld_hastaPassword);
		
		JPanel w_doktorLogin = new JPanel();
		w_doktorLogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Doktor Giriþi", null, w_doktorLogin, null);
		w_doktorLogin.setLayout(null);
		
		JLabel lblTcNumaranz_1 = new JLabel("T.C. Numaran\u0131z :");
		lblTcNumaranz_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblTcNumaranz_1.setBounds(39, 36, 138, 27);
		w_doktorLogin.add(lblTcNumaranz_1);
		
		fld_doktorTc = new JTextField();
		fld_doktorTc.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fld_doktorTc.setColumns(10);
		fld_doktorTc.setBounds(178, 38, 247, 32);
		w_doktorLogin.add(fld_doktorTc);
		
		JLabel lblifre_1 = new JLabel("\u015Eifre :");
		lblifre_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblifre_1.setBounds(122, 83, 55, 27);
		w_doktorLogin.add(lblifre_1);
		
		JButton btn_DoktorLogin = new JButton("Giri\u015F Yap");
		btn_DoktorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_doktorTc.getText().length()==0 || fld_doktorPass.getText().length()==0) {
					Helper.showMsg("fill");
				}else {
					try {
						java.sql.Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						while(rs.next()) {
							if(fld_doktorTc.getText().equals(rs.getString("tcno")) && fld_doktorPass.getText().equals(rs.getString("password"))) {
								if(rs.getString("type").equals("bashekim")) {
									Bashekim bHekim = new Bashekim();
									bHekim.setId(rs.getInt("id"));
									bHekim.setPassword(rs.getString("password"));
									bHekim.setTcno(rs.getString("tcno"));
									bHekim.setName(rs.getString("name"));
									bHekim.setType(rs.getString("type"));
									BashekimGUI bGUI = new BashekimGUI(bHekim);
									bGUI.setVisible(true);
									dispose();
								}
								
								if(rs.getString("type").equals("doktor")) {
									Doctor doctor = new Doctor();
									doctor.setId(rs.getInt("id"));
									doctor.setPassword(rs.getString("password"));
									doctor.setTcno(rs.getString("tcno"));
									doctor.setName(rs.getString("name"));
									doctor.setType(rs.getString("type"));
									DoctorGUI dGUI = new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();
								}
							}
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		btn_DoktorLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btn_DoktorLogin.setBounds(39, 147, 386, 43);
		w_doktorLogin.add(btn_DoktorLogin);
		
		fld_doktorPass = new JPasswordField();
		fld_doktorPass.setBounds(178, 81, 247, 32);
		w_doktorLogin.add(fld_doktorPass);
	}
}
