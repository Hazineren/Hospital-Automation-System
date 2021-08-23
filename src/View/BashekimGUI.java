package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Model.Bashekim;
import Model.*;

import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import Helper.*;
import javax.swing.JComboBox;

public class BashekimGUI extends JFrame {

	static Bashekim basHekim = new Bashekim();
	Clinic clinic = new Clinic();
	private JPanel w_pane;
	private JTextField fld_dAdSoyad;
	private JTextField fld_dTcNo;
	private JTextField fld_dPass;
	private JTextField fld_doktorId;
	private JTable table_doktor;
	private DefaultTableModel doctorModel = null;
	private Object[] doctorData = null;
	private JTable table_clinic;
	private JTextField fld_clinicName;
	private DefaultTableModel clinicModel = null;
	private Object[] clinicData = null;
	private JPopupMenu clinicMenu;
	private JTable table_worker;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BashekimGUI frame = new BashekimGUI(basHekim);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public BashekimGUI(Bashekim basHekim) throws SQLException {

		// Doktor Moodel
		doctorModel = new DefaultTableModel();
		Object[] colDoctorName = new Object[4];
		colDoctorName[0] = "ID";
		colDoctorName[1] = "Ad Soyad";
		colDoctorName[2] = "TC NO";
		colDoctorName[3] = "Þifre";
		doctorModel.setColumnIdentifiers(colDoctorName);
		doctorData = new Object[4];
		for (int i = 0; i < basHekim.getDoctorList().size(); i++) {
			doctorData[0] = basHekim.getDoctorList().get(i).getId();
			doctorData[1] = basHekim.getDoctorList().get(i).getName();
			doctorData[2] = basHekim.getDoctorList().get(i).getTcno();
			doctorData[3] = basHekim.getDoctorList().get(i).getPassword();
			doctorModel.addRow(doctorData);
		}

		// Clinic Model
		clinicModel = new DefaultTableModel();
		Object[] colClinic = new Object[2];
		colClinic[0] = "ID";
		colClinic[1] = "Polikinlik Adý";
		clinicModel.setColumnIdentifiers(colClinic);
		clinicData = new Object[2];
		for (int i = 0; i < clinic.getClinicList().size(); i++) {
			clinicData[0] = clinic.getClinicList().get(i).getId();
			clinicData[1] = clinic.getClinicList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
		
		// Worker Model
		DefaultTableModel workerModel = new DefaultTableModel();
		Object[] colWorker = new Object[2];
		colWorker[0]="ID";
		colWorker[1]="Ad Soyad";
		workerModel.setColumnIdentifiers(colWorker);
		Object[] workerData = new Object[2];

		setTitle("Hastane Yönetim Sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Hoþgeldiniz, Sayýn " + basHekim.getName());
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblNewLabel.setBounds(10, 10, 291, 39);
		w_pane.add(lblNewLabel);

		JButton btnNewButton = new JButton("\u00C7\u0131k\u0131\u015F Yap");
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

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 70, 726, 392);
		w_pane.add(tabbedPane);

		JPanel w_doctor = new JPanel();
		w_doctor.setBackground(Color.WHITE);
		tabbedPane.addTab("Doktor Yönetimi", null, w_doctor, null);
		w_doctor.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Ad Soyad");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(531, 10, 79, 29);
		w_doctor.add(lblNewLabel_1);

		fld_dAdSoyad = new JTextField();
		fld_dAdSoyad.setBounds(531, 39, 167, 29);
		w_doctor.add(fld_dAdSoyad);
		fld_dAdSoyad.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("T.C. No");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(531, 78, 56, 29);
		w_doctor.add(lblNewLabel_1_1);

		fld_dTcNo = new JTextField();
		fld_dTcNo.setColumns(10);
		fld_dTcNo.setBounds(531, 107, 167, 29);
		w_doctor.add(fld_dTcNo);

		JLabel lblNewLabel_1_2 = new JLabel("\u015Eifre");
		lblNewLabel_1_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_2.setBounds(531, 146, 42, 29);
		w_doctor.add(lblNewLabel_1_2);

		fld_dPass = new JTextField();
		fld_dPass.setColumns(10);
		fld_dPass.setBounds(531, 175, 167, 29);
		w_doctor.add(fld_dPass);

		JButton btn_addDoktor = new JButton("Ekle");
		btn_addDoktor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_dAdSoyad.getText().length() == 0 || fld_dTcNo.getText().length() == 0
						|| fld_dPass.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						boolean control = basHekim.addDoctor(fld_dTcNo.getText(), fld_dPass.getText(),
								fld_dAdSoyad.getText());
						if (control) {
							Helper.showMsg("success");
							fld_dTcNo.setText(null);
							fld_dPass.setText(null);
							fld_dAdSoyad.setText(null);
							updateDoctorModel();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_addDoktor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_addDoktor.setBounds(529, 214, 169, 29);
		w_doctor.add(btn_addDoktor);

		fld_doktorId = new JTextField();
		fld_doktorId.setColumns(10);
		fld_doktorId.setBounds(531, 282, 167, 29);
		w_doctor.add(fld_doktorId);

		JLabel lblNewLabel_1_3 = new JLabel("Kullan\u0131c\u0131 ID");
		lblNewLabel_1_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_3.setBounds(531, 253, 79, 29);
		w_doctor.add(lblNewLabel_1_3);

		JButton btn_doktorSil = new JButton("Sil");
		btn_doktorSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_doktorId.getText().length() == 0) {
					Helper.showMsg("Lütfen geçerli bir doktor seçiniz");
				} else {
					if (Helper.confirm("sure")) {
						int selectID = Integer.parseInt(fld_doktorId.getText());
						boolean control;
						try {
							control = basHekim.deleteDoctor(selectID);
							if (control) {
								Helper.showMsg("success");
								fld_doktorId.setText(null);
								updateDoctorModel();
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				}
			}
		});
		btn_doktorSil.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btn_doktorSil.setBounds(531, 321, 167, 29);
		w_doctor.add(btn_doktorSil);

		JScrollPane w_scrollDoktor = new JScrollPane();
		w_scrollDoktor.setBounds(10, 10, 511, 340);
		w_doctor.add(w_scrollDoktor);

		table_doktor = new JTable(doctorModel);
		w_scrollDoktor.setViewportView(table_doktor);
		table_doktor.getColumnModel().getColumn(0).setPreferredWidth(3);

		table_doktor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_doktorId.setText(table_doktor.getValueAt(table_doktor.getSelectedRow(), 0).toString());
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
		});

		table_doktor.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int selectID = Integer
							.parseInt(table_doktor.getValueAt(table_doktor.getSelectedRow(), 0).toString());
					String selectName = table_doktor.getValueAt(table_doktor.getSelectedRow(), 1).toString();
					String selectTcno = table_doktor.getValueAt(table_doktor.getSelectedRow(), 2).toString();
					String selectPass = table_doktor.getValueAt(table_doktor.getSelectedRow(), 3).toString();

					try {
						basHekim.updateDoctor(selectID, selectTcno, selectPass, selectName);
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}

			}
		});

		JPanel w_clinic = new JPanel();
		w_clinic.setBackground(Color.WHITE);
		tabbedPane.addTab("Polikinlikler", null, w_clinic, null);
		w_clinic.setLayout(null);

		JScrollPane w_scrollClinic = new JScrollPane();
		w_scrollClinic.setBounds(10, 10, 260, 345);
		w_clinic.add(w_scrollClinic);

		clinicMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("Güncelle");
		JMenuItem deleteMenu = new JMenuItem("Sil");
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);

		updateMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedClinicID = Integer
						.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
				Clinic selectClinic = clinic.getFetch(selectedClinicID);
				UpdateClinicGUI updateGUI = new UpdateClinicGUI(selectClinic);
				updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateGUI.setVisible(true);
				updateGUI.addWindowListener(new WindowAdapter() {

					@Override
					public void windowClosed(WindowEvent e) {
						try {
							updateClinicModel();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

					}
				});
			}
		});

		deleteMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Helper.confirm("sure")) {
					int selectedClinicID = Integer
							.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
					try {
						if (clinic.deleteClinic(selectedClinicID)) {
							Helper.showMsg("success");
							updateClinicModel();
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		table_clinic = new JTable(clinicModel);
		table_clinic.setComponentPopupMenu(clinicMenu);
		table_clinic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				int selectedRow = table_clinic.rowAtPoint(point);
				table_clinic.setRowSelectionInterval(selectedRow, selectedRow);
			}
		});
		table_clinic.getColumnModel().getColumn(0).setPreferredWidth(3);
		w_scrollClinic.setViewportView(table_clinic);

		JLabel lblNewLabel_1_4 = new JLabel("Polikinlik Ad\u0131");
		lblNewLabel_1_4.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_4.setBounds(280, 10, 97, 29);
		w_clinic.add(lblNewLabel_1_4);

		fld_clinicName = new JTextField();
		fld_clinicName.setColumns(10);
		fld_clinicName.setBounds(280, 39, 167, 29);
		w_clinic.add(fld_clinicName);

		JScrollPane w_scrollWorker = new JScrollPane();
		w_scrollWorker.setBounds(458, 10, 253, 345);
		w_clinic.add(w_scrollWorker);
		
		table_worker = new JTable(workerModel);
		table_worker.getColumnModel().getColumn(0).setPreferredWidth(3);
		w_scrollWorker.setViewportView(table_worker);
		
		JButton btn_addClinic = new JButton("Ekle");
		btn_addClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_clinicName.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						if (clinic.addClinic(fld_clinicName.getText())) {
							Helper.showMsg("success");
							fld_clinicName.setText(null);
							updateClinicModel();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_addClinic.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_addClinic.setBounds(280, 78, 169, 29);
		w_clinic.add(btn_addClinic);
		
		JComboBox cbSelect_Doctor = new JComboBox();
		cbSelect_Doctor.setBounds(280, 262, 167, 34);
		for(int i=0;i<basHekim.getDoctorList().size();i++) {
			cbSelect_Doctor.addItem(new Item(basHekim.getDoctorList().get(i).getId(),basHekim.getDoctorList().get(i).getName()));
		}
		cbSelect_Doctor.addActionListener(e -> {
			JComboBox c = (JComboBox)e.getSource();
			Item item = (Item)c.getSelectedItem();
			System.out.println(item.getKey()+" : "+item.getValue());
		});
		w_clinic.add(cbSelect_Doctor);
		
		JButton btn_addWorker = new JButton("Ekle");
		btn_addWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_clinic.getSelectedRow();
				if(selectedRow >= 0) {
					String selectedClinic = table_clinic.getModel().getValueAt(selectedRow, 0).toString();
					int selectedClinicID = Integer.parseInt(selectedClinic);
					Item doctorItem = (Item)cbSelect_Doctor.getSelectedItem();
					try {
						boolean control = basHekim.addWorker(doctorItem.getKey(), selectedClinicID);
						if(control) {
							Helper.showMsg("success");
							DefaultTableModel clearModel = (DefaultTableModel)table_worker.getModel();
							clearModel.setRowCount(0);
							for(int i=0;i<basHekim.getClinicDoctorList(selectedClinicID).size();i++) {
								workerData[0] = basHekim.getClinicDoctorList(selectedClinicID).get(i).getId();
								workerData[1] = basHekim.getClinicDoctorList(selectedClinicID).get(i).getName();
								workerModel.addRow(workerData);
							}
							table_worker.setModel(workerModel);
						}else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					Helper.showMsg("Lütfen Bir Poliklinik Seçiniz !");
				}
			}
		});
		btn_addWorker.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_addWorker.setBounds(278, 306, 169, 29);
		w_clinic.add(btn_addWorker);
		
		JLabel lblNewLabel_1_4_1 = new JLabel("Polikinlik Ad\u0131");
		lblNewLabel_1_4_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_4_1.setBounds(280, 140, 97, 29);
		w_clinic.add(lblNewLabel_1_4_1);
		
		JButton btn_workerSelect = new JButton("Se\u00E7");
		btn_workerSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_clinic.getSelectedRow();
				if(selectedRow >= 0) {
					String selectedClinic = table_clinic.getModel().getValueAt(selectedRow, 0).toString();
					int selectedClinicID = Integer.parseInt(selectedClinic);
					DefaultTableModel clearModel = (DefaultTableModel)table_worker.getModel();
					clearModel.setRowCount(0);
					
					try {
						for(int i=0;i<basHekim.getClinicDoctorList(selectedClinicID).size();i++) {
							workerData[0] = basHekim.getClinicDoctorList(selectedClinicID).get(i).getId();
							workerData[1] = basHekim.getClinicDoctorList(selectedClinicID).get(i).getName();
							workerModel.addRow(workerData);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					table_worker.setModel(workerModel);
				}else {
					Helper.showMsg("Lütfen Bir Poliklinik Seçiniz !");
				}
				
			}
		});
		btn_workerSelect.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_workerSelect.setBounds(280, 169, 169, 29);
		w_clinic.add(btn_workerSelect);
	}

	public void updateDoctorModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_doktor.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < basHekim.getDoctorList().size(); i++) {
			doctorData[0] = basHekim.getDoctorList().get(i).getId();
			doctorData[1] = basHekim.getDoctorList().get(i).getName();
			doctorData[2] = basHekim.getDoctorList().get(i).getTcno();
			doctorData[3] = basHekim.getDoctorList().get(i).getPassword();
			doctorModel.addRow(doctorData);
		}
	}

	public void updateClinicModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_clinic.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < clinic.getClinicList().size(); i++) {
			clinicData[0] = clinic.getClinicList().get(i).getId();
			clinicData[1] = clinic.getClinicList().get(i).getName();
			clinicModel.addRow(clinicData);
		}

	}
}
