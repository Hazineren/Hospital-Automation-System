package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Hasta;
import Model.User;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_nameSurname;
	private JTextField fld_tcNo;
	private JPasswordField fld_password;
	private Hasta hasta = new Hasta();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setTitle("Hastane Y\u00F6netim Sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 330);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Ad Soyad");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(10, 10, 79, 29);
		w_pane.add(lblNewLabel_1);
		
		fld_nameSurname = new JTextField();
		fld_nameSurname.setColumns(10);
		fld_nameSurname.setBounds(10, 39, 276, 29);
		w_pane.add(fld_nameSurname);
		
		JLabel lblNewLabel_1_1 = new JLabel("T.C. Numaras\u0131");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(10, 78, 167, 29);
		w_pane.add(lblNewLabel_1_1);
		
		fld_tcNo = new JTextField();
		fld_tcNo.setColumns(10);
		fld_tcNo.setBounds(10, 107, 276, 29);
		w_pane.add(fld_tcNo);
		
		JLabel lblNewLabel_1_2 = new JLabel("\u015Eifre");
		lblNewLabel_1_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_2.setBounds(10, 146, 46, 29);
		w_pane.add(lblNewLabel_1_2);
		
		fld_password = new JPasswordField();
		fld_password.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));
		fld_password.setBounds(10, 178, 276, 29);
		w_pane.add(fld_password);
		
		JButton btn_register = new JButton("KAYIT OL");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(fld_nameSurname.getText().length()==0 || fld_tcNo.getText().length()==0 || fld_password.getText().length()==0) {
					Helper.showMsg("fill");
				}else {
					try {
						boolean control = hasta.register(fld_nameSurname.getText(), fld_tcNo.getText(), fld_password.getText());
						if(control) {
							Helper.showMsg("success");
							LoginGUI login = new LoginGUI();
							login.setVisible(true);
							dispose();
						}else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		btn_register.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_register.setBounds(10, 217, 276, 35);
		w_pane.add(btn_register);
		
		JButton btn_backTo = new JButton("G\u0130R\u0130\u015E YAP");
		btn_backTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btn_backTo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_backTo.setBounds(10, 256, 276, 36);
		w_pane.add(btn_backTo);
	}
}
