package boundary;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Window.Type;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PageRegistrazione extends JFrame {

	private JPanel contentPane;
	private JTextField textCognome;
	private JTextField textNome;
	private JTextField textUsername;
	private JTextField textEmail;
	private JTextField textPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PageRegistrazione frame = new PageRegistrazione();
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
	public PageRegistrazione() {
		setType(Type.POPUP);
		setTitle("Pagina di Registrazione");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(29, 101, 199, 20);
		contentPane.add(lblEmail);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(256, 22, 86, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(256, 104, 86, 14);
		contentPane.add(lblPassword);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblNome.setBounds(29, 22, 86, 14);
		contentPane.add(lblNome);
		
		JLabel lblCognome = new JLabel("Cognome");
		lblCognome.setHorizontalAlignment(SwingConstants.CENTER);
		lblCognome.setBounds(142, 22, 86, 14);
		contentPane.add(lblCognome);
		
		textCognome = new JTextField();
		textCognome.setBounds(142, 47, 86, 20);
		contentPane.add(textCognome);
		textCognome.setColumns(10);
		
		textNome = new JTextField();
		textNome.setColumns(10);
		textNome.setBounds(29, 47, 86, 20);
		contentPane.add(textNome);
		
		textUsername = new JTextField();
		textUsername.setBounds(256, 47, 86, 20);
		contentPane.add(textUsername);
		textUsername.setColumns(10);
		
		textEmail = new JTextField();
		textEmail.setColumns(10);
		textEmail.setBounds(29, 132, 199, 20);
		contentPane.add(textEmail);
		
		textPassword = new JTextField();
		textPassword.setColumns(10);
		textPassword.setBounds(256, 132, 86, 20);
		contentPane.add(textPassword);
		
		JButton btnRegistrazione = new JButton("FATTO!");
		btnRegistrazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			}
		});
		btnRegistrazione.setBounds(335, 214, 89, 23);
		contentPane.add(btnRegistrazione);
	}

}
