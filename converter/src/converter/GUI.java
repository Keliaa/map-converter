package converter;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class GUI extends JFrame {

	private JFrame frame;
	private JTextField specularField;
	private JTextField glossField;
	public static JTextField ambientField;
	private JTextField outputField;
	
	private String lastDir = "./";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		
		frame.setTitle("Kinnusen map generator");
		frame.setBounds(100, 100, 387, 379);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton specularButton = new JButton("...");
		specularButton.setBounds(325, 35, 33, 23);
		frame.getContentPane().add(specularButton);
		
		specularField = new JTextField();
		specularField.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 11));
		specularField.setBounds(10, 36, 305, 20);
		frame.getContentPane().add(specularField);
		specularField.setColumns(10);
		
		specularField.setTransferHandler(new FileDropHandler(specularField));
		
		JLabel lblNewLabel = new JLabel("Specular map");
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 10, 136, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblDiffuseMap = new JLabel("Gloss map");
		lblDiffuseMap.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		lblDiffuseMap.setBounds(10, 69, 136, 14);
		frame.getContentPane().add(lblDiffuseMap);
		
		glossField = new JTextField();
		glossField.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 11));
		glossField.setColumns(10);
		glossField.setBounds(10, 95, 305, 20);
		frame.getContentPane().add(glossField);
		
		glossField.setTransferHandler(new FileDropHandler(glossField));
		
		JButton glossButton = new JButton("...");
		glossButton.setBounds(325, 94, 33, 23);
		frame.getContentPane().add(glossButton);
		
		JLabel lblAmbientOcculusionMap = new JLabel("Ambient occulusion map");
		lblAmbientOcculusionMap.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		lblAmbientOcculusionMap.setBounds(10, 126, 170, 14);
		frame.getContentPane().add(lblAmbientOcculusionMap);
		
		ambientField = new JTextField();
		ambientField.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 11));
		ambientField.setColumns(10);
		ambientField.setBounds(10, 152, 305, 20);
		frame.getContentPane().add(ambientField);
		
		ambientField.setTransferHandler(new FileDropHandler(ambientField));
		
		JButton ambientButton = new JButton("...");
		ambientButton.setBounds(325, 151, 33, 23);
		frame.getContentPane().add(ambientButton);
		
		JButton generateButton = new JButton("Generate maps");
		generateButton.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 16));
		generateButton.setBounds(10, 261, 348, 62);
		frame.getContentPane().add(generateButton);
		
		JLabel lblOutputDirectory = new JLabel("Output directory");
		lblOutputDirectory.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		lblOutputDirectory.setBounds(10, 183, 170, 14);
		frame.getContentPane().add(lblOutputDirectory);
		
		outputField = new JTextField();
		String userhome = System.getProperty("user.home");
		outputField.setText(userhome+"\\Desktop\\generated maps\\");
		outputField.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 11));
		outputField.setColumns(10);
		outputField.setBounds(10, 209, 305, 20);
		frame.getContentPane().add(outputField);
		
		outputField.setTransferHandler(new FileDropHandler(outputField));
		
		JButton outputButton = new JButton("...");
		outputButton.setBounds(325, 208, 33, 23);
		frame.getContentPane().add(outputButton);
		
		
		specularButton.addActionListener(new OpenSpecular());
		glossButton.addActionListener(new OpenGloss());
		ambientButton.addActionListener(new OpenAmbient());
		outputButton.addActionListener(new OpenOutput());
		generateButton.addActionListener(new Generate());
		
	}
	
	class OpenSpecular implements ActionListener {		
		public void actionPerformed(ActionEvent e) {
			openFileChooser(specularField);
		}
	}
	
	class OpenGloss implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			openFileChooser(glossField);
		}
	}
	
	class OpenAmbient implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			openFileChooser(ambientField);
		}
	}
	
	class Generate implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Converter converter = new Converter();
			
			if((!ambientField.getText().isBlank() && !outputField.getText().isBlank()) || (!specularField.getText().isBlank() && !glossField.getText().isBlank() && !outputField.getText().isEmpty())) {
			
				if (!ambientField.getText().isEmpty() && !outputField.getText().isEmpty()) {
					converter.makeAmbientOcclusionMap(ambientField.getText(), outputField.getText());
				}
					
				if (!specularField.getText().isBlank() && !glossField.getText().isBlank() && !outputField.getText().isBlank()) {
					converter.makeSMDIMap(specularField.getText(), outputField.getText(), converter.getBlueValues(glossField.getText()));
				}
				
				JOptionPane.showMessageDialog(frame, "Finished creating maps!", "", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(frame, "Pls don't be stupid", "", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	class OpenOutput implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser c = new JFileChooser("./");
			c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			c.setAcceptAllFileFilterUsed(false);
			
			int rVal = c.showOpenDialog(GUI.this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				outputField.setText(c.getSelectedFile().getAbsolutePath() + "\\");
			}
		}
	}
	
	private void openFileChooser(JTextField field) {
		JFileChooser c = new JFileChooser(lastDir);
		
		c.setFileFilter(new FileFilter() {

			public String getDescription() {
				return "PNG Images (*.png) || TGA images (*.tga)";
			}

			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					String filename = f.getName().toLowerCase();
					lastDir = f.getPath();
				    return filename.endsWith(".png") || filename.endsWith(".tga");
				}
			}
		});

		int rVal = c.showOpenDialog(GUI.this);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			field.setText(c.getSelectedFile().getAbsolutePath());
		}
	}
}
