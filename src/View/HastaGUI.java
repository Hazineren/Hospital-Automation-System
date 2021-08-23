package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Appointment;
import Model.Clinic;
import Model.Hasta;
import Model.WorkHour;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class HastaGUI extends JFrame {

	private JPanel w_pane;
	private static Hasta hasta = new Hasta();
	private Clinic clinic = new Clinic();
	private JTextField txtDoktorListesi;
	private JTable table_doctor;
	private DefaultTableModel doctorModel;
	private Object[] doctorData = null;
	private JTextField txtRandevuSaatleri;
	private JTable table_workHour;
	private WorkHour workHour = new WorkHour();
	private DefaultTableModel workHourModel;
	private Object[] workHourData = null;
	private int selectDoctorID = 0;
	private String selectDoctorName = null;
	private JTable table_appointment;
	private DefaultTableModel appointmentModel;
	private Object[] appointmentData = null;
	private Appointment appointment = new Appointment();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HastaGUI frame = new HastaGUI(hasta);
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
	public HastaGUI(Hasta hasta) throws SQLException {
		
		// Doctor Model
		doctorModel = new DefaultTableModel();
		Object[] colDoctor = new Object[2];
		colDoctor[0] = "ID";
		colDoctor[1] = "Ad Soyad";
		doctorModel.setColumnIdentifiers(colDoctor);
		doctorData = new Object[2];
		
		// WorkHour Model
		workHourModel = new DefaultTableModel();
		Object[] colWorkHour = new Object[2];
		colWorkHour[0] = "ID";
		colWorkHour[1] = "Tarih";
		workHourModel.setColumnIdentifiers(colWorkHour);
		workHourData = new Object[2];
		
		// Appointment Model
		appointmentModel = new DefaultTableModel();
		Object[] colAppointment = new Object[3];
		colAppointment[0] = "ID";
		colAppointment[1] = "Doktor";
		colAppointment[2] = "Tarih";
		appointmentModel.setColumnIdentifiers(colAppointment);
		appointmentData = new Object[3];
		for(int i=0;i<appointment.getHastaList(hasta.getId()).size();i++) {
			appointmentData[0] = appointment.getHastaList(hasta.getId()).get(i).getId();
			appointmentData[1] = appointment.getHastaList(hasta.getId()).get(i).getDoctorName();
			appointmentData[2] = appointment.getHastaList(hasta.getId()).get(i).getAppointmentDate();
			appointmentModel.addRow(appointmentData);
		}
		
		
		setTitle("Hastane Yönetim Sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hoþgeldiniz, Sayýn "+hasta.getName());
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblNewLabel.setBounds(10, 10, 291, 39);
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
		
		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 70, 726, 392);
		w_pane.add(w_tab);
		
		JPanel w_appointment = new JPanel();
		w_appointment.setBackground(Color.WHITE);
		w_tab.addTab("Randevu Sistemi", null, w_appointment, null);
		w_appointment.setLayout(null);
		
		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 39, 290, 316);
		w_appointment.add(w_scrollDoctor);
		
		table_doctor = new JTable(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);
		table_doctor.getColumnModel().getColumn(0).setPreferredWidth(4);
		
		txtDoktorListesi = new JTextField();
		txtDoktorListesi.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtDoktorListesi.setText("Doktor Listesi");
		txtDoktorListesi.setBounds(10, 10, 89, 22);
		w_appointment.add(txtDoktorListesi);
		txtDoktorListesi.setColumns(10);
		
		JLabel lblNewLabel_1_4 = new JLabel("Polikinlik Ad\u0131");
		lblNewLabel_1_4.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_4.setBounds(312, 10, 97, 29);
		w_appointment.add(lblNewLabel_1_4);
		
		JComboBox cb_selectClinic = new JComboBox();
		cb_selectClinic.setBounds(310, 39, 153, 29);
		cb_selectClinic.addItem("--Poliklinik Seç--");
		for(int i=0;i<clinic.getClinicList().size();i++) {
			cb_selectClinic.addItem(new Item(clinic.getClinicList().get(i).getId(),clinic.getClinicList().get(i).getName()));
		}
		cb_selectClinic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cb_selectClinic.getSelectedIndex() != 0) {
					JComboBox jBox = (JComboBox)e.getSource();
					Item item = (Item)jBox.getSelectedItem();
					DefaultTableModel clearModel = (DefaultTableModel)table_doctor.getModel();
					clearModel.setRowCount(0);
					try {
						for(int i=0;i<clinic.getClinicDoctorList(item.getKey()).size();i++) {
							doctorData[0] = clinic.getClinicDoctorList(item.getKey()).get(i).getId();
							doctorData[1] = clinic.getClinicDoctorList(item.getKey()).get(i).getName();
							doctorModel.addRow(doctorData);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					DefaultTableModel clearModel = (DefaultTableModel)table_doctor.getModel();
					clearModel.setRowCount(0);
				}
				
			}
		});
		w_appointment.add(cb_selectClinic);
		
		JLabel lblNewLabel_1_4_1 = new JLabel("Doktor Se\u00E7");
		lblNewLabel_1_4_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_4_1.setBounds(310, 89, 89, 29);
		w_appointment.add(lblNewLabel_1_4_1);
		
		JButton btn_doctorSelect = new JButton("Se\u00E7");
		btn_doctorSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_doctor.getSelectedRow();
				if(row >= 0) {
					String value = table_doctor.getModel().getValueAt(row, 0).toString();
					int id = Integer.parseInt(value);
					DefaultTableModel clearModel = (DefaultTableModel) table_workHour.getModel();
					clearModel.setRowCount(0);
					
					try {
						for(int i=0;i<workHour.getWorkHourList(id).size();i++) {
							workHourData[0] = workHour.getWorkHourList(id).get(i).getId();
							workHourData[1] = workHour.getWorkHourList(id).get(i).getWorkDate();
							workHourModel.addRow(workHourData);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					table_workHour.setModel(workHourModel);
					selectDoctorID = id;
					selectDoctorName = table_doctor.getModel().getValueAt(row,1).toString();
					//System.out.print(selectDoctorID+" - "+selectDoctorName);
				}else {
					Helper.showMsg("Lütfen Bir Doktor Seçiniz !");
				}
				
			}
		});
		btn_doctorSelect.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_doctorSelect.setBounds(310, 118, 153, 29);
		w_appointment.add(btn_doctorSelect);
		
		txtRandevuSaatleri = new JTextField();
		txtRandevuSaatleri.setText("Randevu Alabilece\u011Finiz Saatler");
		txtRandevuSaatleri.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtRandevuSaatleri.setColumns(10);
		txtRandevuSaatleri.setBounds(473, 10, 238, 22);
		w_appointment.add(txtRandevuSaatleri);
		
		JScrollPane w_scrollWorkHour = new JScrollPane();
		w_scrollWorkHour.setBounds(473, 39, 238, 316);
		w_appointment.add(w_scrollWorkHour);
		
		table_workHour = new JTable(workHourModel);
		w_scrollWorkHour.setViewportView(table_workHour);
		
		JLabel lblNewLabel_1_4_1_1 = new JLabel("Randevu");
		lblNewLabel_1_4_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_4_1_1.setBounds(310, 297, 74, 29);
		w_appointment.add(lblNewLabel_1_4_1_1);
		
		JButton btn_appointment = new JButton("Randevu Al");
		btn_appointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_workHour.getSelectedRow();
				if(selRow >=0) {
					String date = table_workHour.getModel().getValueAt(selRow,1).toString();
					try {
						boolean control = hasta.addAppointment(selectDoctorID, hasta.getId(), selectDoctorName, hasta.getName(), date);
						if(control) {
							Helper.showMsg("success");
							hasta.updateWorkHourStatus(selectDoctorID, date);
							updateWorkHourModel(selectDoctorID);
							updateAppointmentModel(hasta.getId());
						}else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					Helper.showMsg("Lütfen Geçerli Bir Tarih Giriniz !");
				}
				
			}
		});
		btn_appointment.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_appointment.setBounds(310, 326, 153, 29);
		w_appointment.add(btn_appointment);
		
		JPanel w_myAppointment = new JPanel();
		w_tab.addTab("Randevularým", null, w_myAppointment, null);
		w_myAppointment.setLayout(null);
		
		JScrollPane w_scrollAppointment = new JScrollPane();
		w_scrollAppointment.setBounds(10, 41, 701, 314);
		w_myAppointment.add(w_scrollAppointment);
		
		table_appointment = new JTable(appointmentModel);
		w_scrollAppointment.setViewportView(table_appointment);
		
		JButton btn_appointmenDelete = new JButton("Ýptal Et");
		btn_appointmenDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_appointment.getSelectedRow();
				if(selRow >= 0) {
					String selectedRow = table_appointment.getModel().getValueAt(selRow,0).toString();
					String selectedDoctorName = table_appointment.getModel().getValueAt(selRow,1).toString();
					String date = table_appointment.getModel().getValueAt(selRow,2).toString();
					//System.out.println(date);
					int selectedID = Integer.parseInt(selectedRow);
					boolean control;
					try {
						control = hasta.deleteAppointment(selectedID);
						if(control) {
							Helper.showMsg("success");
							//System.out.println(selectDoctorID);
							hasta.updateWorkHourStatusA(selectedDoctorName, date);
							updateWorkHourModel(selectDoctorID);
							updateAppointmentModel(hasta.getId());
							
						}else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else {
					Helper.showMsg("Lütfen Bir Randevu Seçiniz");
				}
			}
		});
		btn_appointmenDelete.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btn_appointmenDelete.setBounds(617, 5, 94, 26);
		w_myAppointment.add(btn_appointmenDelete);
		table_workHour.getColumnModel().getColumn(0).setPreferredWidth(5);
	}
	
	public void updateWorkHourModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel)table_workHour.getModel();
		clearModel.setRowCount(0);
		for(int i=0;i<workHour.getWorkHourList(doctor_id).size();i++) {
			workHourData[0] = workHour.getWorkHourList(doctor_id).get(i).getId();
			workHourData[1] = workHour.getWorkHourList(doctor_id).get(i).getWorkDate();
			workHourModel.addRow(workHourData);
		}
	}
	
	public void updateAppointmentModel(int hasta_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel)table_appointment.getModel();
		clearModel.setRowCount(0);
		for(int i=0;i<appointment.getHastaList(hasta_id).size();i++) {
			appointmentData[0] = appointment.getHastaList(hasta_id).get(i).getId();
			appointmentData[1] = appointment.getHastaList(hasta_id).get(i).getDoctorName();
			appointmentData[2] = appointment.getHastaList(hasta_id).get(i).getAppointmentDate();
			appointmentModel.addRow(appointmentData);
		}
	}
}
