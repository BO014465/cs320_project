package Package1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.DropMode;
import javax.swing.UIManager;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import javax.swing.JPasswordField;

public class gui {

	private JFrame frame;
	private JTextField loginUsernameTxtfield;
	private JPasswordField loginPwfield;
	private JTextField registerUsernameTxtfield;
	private JTextField registerPwfield;
	private JTextField registerPwfield2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */


	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(300, 200, 1200, 750);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

        frame.setTitle("Game Library Application");
        frame.setSize(1200,750);
        frame.getContentPane().setLayout(null);

        
		JPanel loginRegisterMainPanel = new JPanel();
		loginRegisterMainPanel.setBounds(0, 0, 1194, 715);
		frame.getContentPane().add(loginRegisterMainPanel);
		loginRegisterMainPanel.setLayout(null);
		
		loginUsernameTxtfield = new JTextField();
		loginUsernameTxtfield.setBounds(479, 173, 175, 22);
		loginRegisterMainPanel.add(loginUsernameTxtfield);
		loginUsernameTxtfield.setColumns(10);
		
		loginPwfield = new JPasswordField();
		loginPwfield.setBounds(479, 238, 175, 22);
		loginRegisterMainPanel.add(loginPwfield);
		
		JLabel loginPwLabel = new JLabel("Password");
		loginPwLabel.setForeground(Color.BLUE);
		loginPwLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		loginPwLabel.setBounds(516, 208, 102, 17);
		loginRegisterMainPanel.add(loginPwLabel);
		
		JLabel loginUsernameLabel = new JLabel("Username");
		loginUsernameLabel.setForeground(Color.BLUE);
		loginUsernameLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		loginUsernameLabel.setBounds(516, 140, 102, 17);
		loginRegisterMainPanel.add(loginUsernameLabel);
        
		JFrame frame2 = new JFrame();
		frame2.setBounds(300, 200, 300, 750);
		frame2.setResizable(false);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setVisible(false);

		

		registerUsernameTxtfield = new JTextField();
		registerUsernameTxtfield.setColumns(10);
		registerUsernameTxtfield.setBounds(479, 429, 175, 22);
		loginRegisterMainPanel.add(registerUsernameTxtfield);
		registerUsernameTxtfield.setVisible(false);
		
		
		registerPwfield = new JPasswordField();
		registerPwfield.setColumns(10);
		registerPwfield.setBounds(479, 464, 175, 22);
		loginRegisterMainPanel.add(registerPwfield);
		registerPwfield.setVisible(false);
		
		registerPwfield2 = new JPasswordField();
		registerPwfield2.setColumns(10);
		registerPwfield2.setBounds(479, 499, 175, 22);
		loginRegisterMainPanel.add(registerPwfield2);
		registerPwfield2.setVisible(false);
		
		JLabel registerUsernameLabel = new JLabel("Username:");
		registerUsernameLabel.setForeground(Color.BLUE);
		registerUsernameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		registerUsernameLabel.setBounds(393, 431, 84, 16);
		loginRegisterMainPanel.add(registerUsernameLabel);
		registerUsernameLabel.setVisible(false);
		
		JLabel registerPwLabel = new JLabel("Password:");
		registerPwLabel.setForeground(Color.BLUE);
		registerPwLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		registerPwLabel.setBounds(393, 467, 84, 16);
		loginRegisterMainPanel.add(registerPwLabel);
		registerPwLabel.setVisible(false);
		
		JLabel registerPwLabel2 = new JLabel("Confirm Password:");
		registerPwLabel2.setForeground(Color.BLUE);
		registerPwLabel2.setFont(new Font("Tahoma", Font.BOLD, 14));
		registerPwLabel2.setBounds(335, 501, 142, 16);
		loginRegisterMainPanel.add(registerPwLabel2);
		registerPwLabel2.setVisible(false);
		
		JButton registerButton2 = new JButton("Register");
		registerButton2.setBounds(521, 534, 97, 25);
		loginRegisterMainPanel.add(registerButton2);
		registerButton2.setVisible(false);
		
		JLabel registerLabel = new JLabel("Register");
		registerLabel.setForeground(Color.BLUE);
		registerLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		registerLabel.setBounds(516, 399, 102, 17);
		loginRegisterMainPanel.add(registerLabel);
		registerLabel.setVisible(false);
		
        
		JButton LoginButton = new JButton("Login");
		LoginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {



			}
		});
		LoginButton.setBackground(Color.LIGHT_GRAY);
		LoginButton.setBounds(479, 273, 175, 50);
		loginRegisterMainPanel.add(LoginButton);
		
		
		
		
		JButton RegisterButton = new JButton("Register");
		RegisterButton.setBackground(Color.LIGHT_GRAY);
		RegisterButton.setBounds(479, 339, 175, 36);
		loginRegisterMainPanel.add(RegisterButton);
		RegisterButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				RegisterButton.setVisible(false);
				registerUsernameTxtfield.setVisible(true);
				registerPwfield.setVisible(true);
				registerPwfield2.setVisible(true);
				registerUsernameLabel.setVisible(true);
				registerPwLabel.setVisible(true);
				registerPwLabel2.setVisible(true);
				registerButton2.setVisible(true);
				registerLabel.setVisible(true);
				loginUsernameTxtfield.setVisible(false);
				loginUsernameLabel.setVisible(false);
				loginPwfield.setVisible(false);
				loginPwLabel.setVisible(false);
				LoginButton.setVisible(false);
				
			}
		});


		
		registerButton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				registerUsernameTxtfield.setVisible(false);
				registerPwfield.setVisible(false);
				registerPwfield2.setVisible(false);
				registerUsernameLabel.setVisible(false);
				registerPwLabel.setVisible(false);
				registerPwLabel2.setVisible(false);
				registerButton2.setVisible(false);
				registerLabel.setVisible(false);
				loginUsernameTxtfield.setVisible(true);
				loginUsernameLabel.setVisible(true);
				loginPwfield.setVisible(true);
				loginPwLabel.setVisible(true);
				LoginButton.setVisible(true);
				RegisterButton.setVisible(true);
				registerUsernameTxtfield.setText("");
				registerPwfield.setText("");
				registerPwfield2.setText("");
			}
		});


		
		JLabel backgroundImage = new JLabel("");
		backgroundImage.setIcon(new ImageIcon("2.jpg"));
		backgroundImage.setBounds(-77, -47, 1298, 774);
		loginRegisterMainPanel.add(backgroundImage);
		

		

		

		

		
	}
}
