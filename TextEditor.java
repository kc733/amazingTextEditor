package amazingTextEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener{
	
	//----------- components declarations----------------//
	
	JTextArea textArea;
	JScrollPane scrollPane;
	JSpinner fontSizeSpinner;
	JLabel fontLabel;
	JButton colorButton;
	JComboBox fontBox;
	
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	
	//---------------------------//
	
	TextEditor() {
		
		//--------------- frame options ----------------------------//
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Amazing Text Editor");
		this.setSize(500, 700);
		this.setLocationRelativeTo(null); 
		this.setLayout(new FlowLayout());
		//--------------- /frame options ----------------------------//
		
		//------------- text area ------------------//
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("GB18030 Bitmap", Font.PLAIN, 20));
		//------------- /text area ------------------//
		
		//------------- scroll pane ----------------//
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(450,450));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		//-------------/scroll pane ----------------//
		
		//------------ font label------- //
		fontLabel = new JLabel("Font size: ");
		//------------ /font label------- //
			
		//---------- spinner for font size change -------///
		fontSizeSpinner = new JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(40,30));
		fontSizeSpinner.setValue(20);
		
		fontSizeSpinner.addChangeListener(new ChangeListener() {

			@Override // to change text size with the spinner
			public void stateChanged(ChangeEvent e) {
				textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue()));
			}                                           // font and style doesn't change
			
		});
		//---------- /spinner for font size change -------///
		
		//--------- color change button---------//
		colorButton = new JButton("Text color");
		colorButton.addActionListener(this);
		//---------/ color change button---------//
		
		//---------font change button---------//
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		fontBox = new JComboBox(fonts);
		fontBox.addActionListener(this);
		fontBox.setSelectedItem("GB18030 Bitmap");
		//---------/font change button---------//
		
		//----------- menu bar ---------------//
		
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		
		openItem = new JMenuItem("Open");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");
		
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		
		menuBar.add(fileMenu);
		
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		exitItem.addActionListener(this);
		
		//----------- /menu bar ---------------//
		
		
		this.add(fontLabel);
		this.add(fontSizeSpinner);
		this.add(colorButton);
		this.add(fontBox);
		this.add(scrollPane);
		this.setJMenuBar(menuBar);
		this.setVisible(true);
	}
	

	@Override  
	public void actionPerformed(ActionEvent e) {
		
		// change color
		if(e.getSource()== colorButton) {
			
			JColorChooser colorChooser = new JColorChooser();
			Color color = colorChooser.showDialog(null, "Choose a color", Color.black);
			textArea.setForeground(color);
		}
		
		// change font
		if(e.getSource()== fontBox) {
			
			textArea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN,  (int) fontSizeSpinner.getValue()));
		}
		
		// menu
		
		// open a file
		if(e.getSource()== openItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".txt", "txt");
			fileChooser.setFileFilter(fileFilter);
			
			int response = fileChooser.showOpenDialog(null);
			
			if(response == fileChooser.APPROVE_OPTION) {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;
				
				try {
					fileIn = new Scanner(file);
					
					if(file.isFile()) {
						while(fileIn.hasNextLine()) {
							String line = fileIn.nextLine() + "\n";
							textArea.append(line); } 
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace(); }
				finally {
					fileIn.close(); }		
			}
		}
		
		// save a file
		if(e.getSource()== saveItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			int response = fileChooser.showSaveDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION) {
				File file;
				PrintWriter fileOut = null;
				
				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(textArea.getText());
			    } catch (FileNotFoundException e1) {
					e1.printStackTrace(); }
				finally {
					fileOut.close(); }	
			}
		}

		// close program 
		if(e.getSource()== exitItem) {
			System.exit(0); }
	}
	
}
