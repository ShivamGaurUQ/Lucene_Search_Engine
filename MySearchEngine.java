package develop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class MySearchEngine extends JFrame {

	JFrame searchFrame;
	JFrame displayFrame;
	JTextField searchField;
	JButton simpleButton;
	JButton drichletButton;
	JTextArea displayArea;
	JLabel searchLabel;
	JScrollPane pane;
	JTextPane resultPane;
	DefaultStyledDocument document;
	SimpleSearcher searchObj;
	BM25 searchLM;
	jsouptest infoJ;

	ArrayList<String> urls = new ArrayList<String>();

	MySearchEngine() {

		this.searchFrame = new JFrame("Search Engine");
		this.searchFrame.setBounds(200, 200, 600, 500);
		this.searchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.searchFrame.getContentPane().setLayout(null);

		this.searchLabel = new JLabel("Search query");
		this.searchLabel.setBounds(93, 80, 80, 14);

		this.simpleButton = new JButton("Simple Search");
		this.simpleButton.setBounds(93, 121, 139, 23);

		this.drichletButton = new JButton("DrichletLM Search");
		this.drichletButton.setBounds(250, 121, 189, 23);

		this.searchField = new JTextField();
		this.searchField.setBounds(204, 64, 167, 20);
		this.searchField.setColumns(100);
		this.searchFrame.getContentPane().add(this.searchField);
		this.searchFrame.getContentPane().add(this.drichletButton);
		this.searchFrame.getContentPane().add(this.simpleButton);
		this.searchFrame.getContentPane().add(this.searchLabel);
		this.document=new DefaultStyledDocument();
		this.resultPane = new JTextPane(document);

		
		
		resultPane.setPreferredSize(new Dimension(200, 200));

		startSearch();

	}

	public void startSearch() {

		simpleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				document=new DefaultStyledDocument();
				resultPane = new JTextPane(document);

				searchObj = new SimpleSearcher(searchField.getText());

				displayFrame = new JFrame("Simple Search Results");
				
				
				urls = searchObj.listurls();
				Collections.reverse(urls);
				StyleContext context = new StyleContext();
		        // build a style
		        Style style = context.addStyle("searchpane", null);
		        try {
		        	

				for (String s : urls) {
					
					

					infoJ = new jsouptest(s.toString());
					
					
			        
			       
			        StyleConstants.setForeground(style, Color.RED);
			        document.insertString(0,"Keywords : " + infoJ.geKeywords() + "\n\n", style);
			        
			        StyleConstants.setForeground(style, Color.BLACK);
			        document.insertString(0,"Description : " + infoJ.getSummary() + "\n" , style);
			        document.insertString(0,s.toString() + "\n" , style);
			        
			        

				}
				
				StyleConstants.setForeground(style, Color.BLACK);
		        document.insertString(0, "\n\nSimple Search Results : " + "\n\n", style);
		        }catch(Exception e) {}

				
				pane = new JScrollPane(resultPane);

				resultPane.addCaretListener(new CaretListener() {
					public void caretUpdate(CaretEvent ce) {
						int startdot = ce.getDot();
						int endmark = ce.getMark();
						if (startdot != endmark) {

							String url = resultPane.getSelectedText();
							try {
								java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
							} catch (Exception e) {
							}
							;

						}

					}
				});

				displayFrame.setBounds(500, 500, 500, 500);
				displayFrame.add(pane);
				

				displayFrame.pack();

				displayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				displayFrame.setVisible(true);

			}
		});

		drichletButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				document=new DefaultStyledDocument();
				resultPane = new JTextPane(document);

				searchLM = new BM25(searchField.getText());

				displayFrame = new JFrame("Drichlet LM Search Results");
				
				
				urls = searchLM.listurls();
				Collections.reverse(urls);
				StyleContext context = new StyleContext();
		        // build a style
		        Style style = context.addStyle("searchpane", null);
		        try {
		        	

				for (String s : urls) {
					
					

					infoJ = new jsouptest(s.toString());
					
					
			        
			       
			        StyleConstants.setForeground(style, Color.RED);
			        document.insertString(0,"Keywords : " + infoJ.geKeywords() + "\n\n", style);
			        
			        StyleConstants.setForeground(style, Color.BLACK);
			        document.insertString(0,"Description : " + infoJ.getSummary() + "\n" , style);
			        document.insertString(0,s.toString() + "\n" , style);
			        
			        

				}
				
				StyleConstants.setForeground(style, Color.BLACK);
		        document.insertString(0, "\n\nDrichlet LM Search Results : " + "\n\n", style);
		        }catch(Exception e) {}

				
				pane = new JScrollPane(resultPane);

				resultPane.addCaretListener(new CaretListener() {
					public void caretUpdate(CaretEvent ce) {
						int startdot = ce.getDot();
						int endmark = ce.getMark();
						if (startdot != endmark) {

							String url = resultPane.getSelectedText();
							try {
								java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
							} catch (Exception e) {
							}
							;

						}

					}
				});

				displayFrame.setBounds(500, 500, 500, 500);
				displayFrame.add(pane);
				

				displayFrame.pack();

				displayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				displayFrame.setVisible(true);

			}
		});

	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MySearchEngine start = new MySearchEngine();
					start.searchFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
