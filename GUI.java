package BUSTATION;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		try {
			GUI frame = new GUI();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} //main

	// Create the frame.
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Fatma Emergency Room system");
		getContentPane().setLayout(null);
		setSize(500,500);
		JLabel lblNewLabel = new JLabel("Welcome to Fatma Bus Station");
		lblNewLabel.setBounds(5, 5, 426, 22);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("David", Font.BOLD, 18));
		add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel(" Number of Technical Attendant:");
		lblNewLabel_2.setBounds(0, 20, 259, 59);
		lblNewLabel_2.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_2.setFont(new Font("David", Font.BOLD, 16));
		add(lblNewLabel_2);

		JTextField numTechnicalAttendant = new JTextField("1");
		numTechnicalAttendant.setBounds(350, 55, 50, 30);
		add(numTechnicalAttendant);

		JLabel lblNewLabel_3 = new JLabel(" work time for cleaner :");
		lblNewLabel_3.setFont(new Font("David", Font.BOLD, 16));
		lblNewLabel_3.setBounds(0, 146, 296, 37);
		add(lblNewLabel_3);

		JTextField cleaningTime = new JTextField("2");
		cleaningTime.setBounds(350, 150, 50, 30);
		add(cleaningTime);


		JButton btnNewButton = new JButton("START");
		btnNewButton.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				double CleaningTime = 2;//default
				try{
					CleaningTime=Double.parseDouble(cleaningTime.getText());//returns the cleaning time
				} catch (NumberFormatException ne){}
				int NumTechnicalAttendant=1;
				try{
					NumTechnicalAttendant = Integer.parseInt(numTechnicalAttendant.getText()); //returns how many technicals
				} catch (NumberFormatException ne){}
				BusStation b = new BusStation("buses",NumTechnicalAttendant,CleaningTime); //creates the bus stations and start the program
				}
		});

		btnNewButton.setFont(new Font("David", Font.BOLD, 14));
		btnNewButton.setBounds(104, 199, 89, 23);
		add(btnNewButton);

		JButton btnNewButton_1 = new JButton("EXIT");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		btnNewButton_1.setFont(new Font("David", Font.BOLD, 14));
		btnNewButton_1.setBounds(247, 199, 89, 23);
		add(btnNewButton_1);
	}
}

