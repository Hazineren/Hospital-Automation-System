package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.Doctor;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import com.toedter.calendar.JDateChooser;

import Helper.Helper;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class DoctorGUI extends JFrame {

	private JPanel w_pane;
	private static Doctor doctor = new Doctor();
	private JTable table_workHour;
	private DefaultTableModel workHourModel;
	private Object[] workHourData = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public DoctorGUI(Doctor doctor) throws SQLException {
		
		workHourModel = new DefaultTableModel();
		Object[] colWorkHour = new Object[2];
		colWorkHour[0]="ID";
		colWorkHour[1]="Tarih";
		workHourModel.setColumnIdentifiers(colWorkHour);
		workHourData = new Object[2];
		
		for(int i=0;i<doctor.getWorkHourList(doctor.getId()).size();i++) {
			workHourData[0] = doctor.getWorkHourList(doctor.getId()).get(i).getId();
			workHourData[1] = doctor.getWorkHourList(doctor.getId()).get(i).getWorkDate();
			workHourModel.addRow(workHourData);
		}
		
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hoþgeldiniz, Sayýn "+doctor.getName());
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblNewLabel.setBounds(10, 10, 419, 39);
		w_pane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Çýkýþ Yap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnNewButton.setBounds(635, 16, 101, 26);
		w_pane.add(btnNewButton);
		
		JTabbedPane w_tabDoctor = new JTabbedPane(JTabbedPane.TOP);
		w_tabDoctor.setBounds(10, 78, 716, 375);
		w_pane.add(w_tabDoctor);
		
		JPanel w_workHour = new JPanel();
		w_workHour.setBackground(Color.WHITE);
		w_tabDoctor.addTab("Çalýþma Saatleri", null, w_workHour, null);
		w_workHour.setLayout(null);
		
		JDateChooser select_date = new JDateChooser();
		select_date.setBounds(10, 10, 140, 21);
		w_workHour.add(select_date);
		
		JComboBox select_time = new JComboBox();
		select_time.setFont(new Font("Tahoma", Font.PLAIN, 12));
		select_time.setModel(new DefaultComboBoxModel(new String[] {"10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00"}));
		select_time.setBounds(173, 10, 57, 21);
		w_workHour.add(select_time);
		
		JButton btn_addWorkHour = new JButton("Ekle");
		btn_addWorkHour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				String date ="";
				try {
					date = sdf.format(select_date.getDate());
				} catch (Exception e2) {
					// TODO: handle exception
				}
				if(date.length()==0) {
					Helper.showMsg("Lütfen Geçerli Bir Tarih Giriniz !");
				}else {
					String time = " "+ select_time.getSelectedItem().toString()+":00";
					String selectDate = date + time;
					try {
						boolean control = doctor.addWorkHour(doctor.getId(), doctor.getName(), selectDate);
						if(control) {
							Helper.showMsg("success");
							updateWorkHourModel(doctor);
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
		btn_addWorkHour.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btn_addWorkHour.setBounds(240, 10, 77, 21);
		w_workHour.add(btn_addWorkHour);
		
		JScrollPane w_scrollWorkHour = new JScrollPane();
		w_scrollWorkHour.setBounds(0, 55, 711, 293);
		w_workHour.add(w_scrollWorkHour);
		
		table_workHour = new JTable(workHourModel);
		w_scrollWorkHour.setViewportView(table_workHour);
		
		JButton btn_deleteWorkHour = new JButton("Sil");
		btn_deleteWorkHour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_workHour.getSelectedRow();
				if(selRow >= 0) {
					String selectedRow = table_workHour.getModel().getValueAt(selRow, 0).toString();
					int selectedID = Integer.parseInt(selectedRow);
					boolean control;
					try {
						control = doctor.deleteWorkHour(selectedID);
						if(control) {
							Helper.showMsg("success");
							updateWorkHourModel(doctor);
						}else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else {
					Helper.showMsg("Lütfen Bir Tarih Seçiniz !");
				}
				
			}
		});
		btn_deleteWorkHour.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btn_deleteWorkHour.setBounds(624, 10, 77, 21);
		w_workHour.add(btn_deleteWorkHour);
	}
	
	public void updateWorkHourModel(Doctor doctor) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_workHour.getModel();
		clearModel.setRowCount(0);
		for(int i=0;i<doctor.getWorkHourList(doctor.getId()).size();i++) {
			workHourData[0] = doctor.getWorkHourList(doctor.getId()).get(i).getId();
			workHourData[1] = doctor.getWorkHourList(doctor.getId()).get(i).getWorkDate();
			workHourModel.addRow(workHourData);
		}
	}
}
