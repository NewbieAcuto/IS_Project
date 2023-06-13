package boundary;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class MainAll extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainAll frame = new MainAll();
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
	public MainAll() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				new PageLogin().setVisible(true);
				
			}
		});
		btnLogin.setBounds(76, 79, 89, 23);
		contentPane.add(btnLogin);
		
		JButton btnVisiteGuidate = new JButton("Visite Guidate");
		btnVisiteGuidate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				new ListaVisiteGuidate().setVisible(true);
				
			}
		});
		btnVisiteGuidate.setBounds(235, 79, 89, 23);
		contentPane.add(btnVisiteGuidate);
		
		JButton btnContatti = new JButton("Contatti");
		btnContatti.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				new PageContatti().setVisible(true);
				
			}
		});
		btnContatti.setBounds(76, 159, 89, 23);
		contentPane.add(btnContatti);
		
		JButton btnSocieta = new JButton("Societa");
		btnSocieta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				new ListaSocieta().setVisible(true);
				
			}
		});
		btnSocieta.setBounds(235, 159, 89, 23);
		contentPane.add(btnSocieta);
	}
}
