import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1;
	Interjack jack = new Interjack('U');
	JButton enter, browse;
	JTextField chara, input, output;
	JRadioButton enc, dec;
	JPanel pane;
	String text;
	String fileVal = "";
	JFileChooser fc;

	public Main() {
		super("Interjack Crypt");
		pane = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		enc = new JRadioButton("encrypt", true);
		dec = new JRadioButton("decrypt");
		enter = new JButton("enter");
		browse = new JButton("browse");
		enter.addActionListener(this);
		browse.addActionListener(this);
		chara = new JTextField("key", 3);
		input = new JTextField("input", 20);
        input.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (input.getText().equals("input")){
                    input.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (input.getText().equals("")) {
                    input.setText("input");
                }
            }
        });    
        chara.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (chara.getText().equals("key")){
                    chara.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (chara.getText().equals("")) {
                    chara.setText("key");
                }
            }
        });           
		output = new JTextField("", 20);
		output.setEditable(false);
		fc = new JFileChooser();
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Interjack Documents", "inj"));
		ButtonGroup group = new ButtonGroup();
		group.add(enc);
		group.add(dec);
		pane.add(chara);
		pane.add(input);
		pane.add(enc);
		pane.add(dec);
		pane.add(enter);
		pane.add(output);
		pane.add(browse);
		add(pane);
        pack();
        setSize(315, 160);
        browse.requestFocusInWindow(); 

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == browse) {
			int returnVal = fc.showOpenDialog(input);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				file = file.getAbsoluteFile();
				if (file.length() != 0) {
					try {
						BufferedReader literate = new BufferedReader(
								new FileReader(file));
						ArrayList<String> sb = new ArrayList<String>();
						String line = literate.readLine();
						while (line != null) {
							sb.add(line);
							//sb.append(System.lineSeparator());
							line = literate.readLine();
						}
						fileVal = fileVal.trim();
						System.out.println(fileVal);
						PrintWriter author;
						if (enc.isSelected()) {
							// encrypt everything in the file to a file with
							// .inj extension
							author = new PrintWriter(file.getParent() + "\\"
									+ file.getName() + ".inj", "UTF-8");
							output.setText("Processing " + file.getName());
							jack.changeKey(chara.getText().charAt(0));
							for (String sting: sb){
								author.println(jack.encrypt(sting));
							}
//							fileVal = jack.encrypt(fileVal);
//							author.println(fileVal);
							output.setText(file.getPath() + ".inj");
							author.close();
						} else if (dec.isSelected()) {
							// decrypt everything to the file with original
							// extension
							String[] finalPath = file.getPath().split(".inj");
							author = new PrintWriter(finalPath[0], "UTF-8");
							output.setText("Processing " + file.getName());
							jack.changeKey(chara.getText().charAt(0));
							for (String sting: sb){
								author.println(jack.decrypt(sting));
							}
//							fileVal = jack.decrypt(fileVal);
//							author.println(fileVal);
							output.setText(finalPath[0]);
							author.close();
						}
						literate.close();
					} catch (Exception E) {
						E.printStackTrace(System.err);
						// JOptionPane.showMessageDialog(null,
						// "404 file not found");
					}
				}
			}
		} else if (e.getSource() == enter) {
			if (enc.isSelected()) {
				// encrypt everything in the line
				jack.changeKey(chara.getText().charAt(0));
				text = jack.encrypt(input.getText());
				output.setText(text);
				StringSelection stringSelection = new StringSelection(text);
				Clipboard clpbrd = Toolkit.getDefaultToolkit()
						.getSystemClipboard();
				clpbrd.setContents(stringSelection, null);
			} else if (dec.isSelected()) {
				// decrypt everything in the line
				jack.changeKey(chara.getText().charAt(0));
				text = jack.decrypt(input.getText());
				output.setText(text);
				StringSelection stringSelection = new StringSelection(text);
				Clipboard clpbrd = Toolkit.getDefaultToolkit()
						.getSystemClipboard();
				clpbrd.setContents(stringSelection, null);
			}
		}
	}

	public static void main(String args[]) {
		Main coo = new Main();
	}
}
