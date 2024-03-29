package boundary;

import java.awt.BorderLayout;
import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.Agenzia;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PageLogin extends JFrame {

	private JPanel contentPane;
	private JTextField textField_Email;
	private JTextField textField_Password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PageLogin frame = new PageLogin();
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
	public PageLogin() {
		getContentPane().setLayout(null);
		setTitle("Pagina di Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField_Email = new JTextField();
		textField_Email.setBounds(167, 78, 139, 20);
		contentPane.add(textField_Email);
		textField_Email.setColumns(10);
		
		textField_Password = new JTextField();
		textField_Password.setBounds(167, 118, 139, 20);
		contentPane.add(textField_Password);
		textField_Password.setColumns(10);
		
		JButton btnRegistrazione = new JButton("Registrati");
		btnRegistrazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PageRegistrazione().setVisible(true);
				dispose();
			}
		});
		btnRegistrazione.setBounds(100, 163, 89, 23);
		contentPane.add(btnRegistrazione);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Agenzia.Login(textField_Email.getText(), textField_Password.getText())==1) {
					JOptionPane.showMessageDialog(null,"Loggato!");
					new PageUtente().setVisible(true);
					dispose();
				}else {
					JOptionPane.showMessageDialog(null,"Email o password errati");
				}
			}
		});
		btnLogin.setBounds(217, 163, 89, 23);
		contentPane.add(btnLogin);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(73, 81, 73, 14);
		contentPane.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(73, 121, 73, 14);
		contentPane.add(lblPassword);
	}
}
