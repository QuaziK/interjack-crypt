import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Main extends JFrame implements ActionListener {
	Interjack jack = new Interjack('Ü');
	JButton enter;
	JTextField chara, input, output;
	JRadioButton enc, dec;
	JPanel pane;
	String text;

	public Main() {
		super("Interjack Crypt");
		setSize(315, 150);
		pane = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
		enc = new JRadioButton("encrypt", true);
		dec = new JRadioButton("decrypt");
		enter = new JButton("enter");
		enter.addActionListener(this);
		chara = new JTextField("key", 3);
		input = new JTextField("input", 20);
		output = new JTextField("out", 20);
		ButtonGroup group = new ButtonGroup();
		group.add(enc);
		group.add(dec);
		pane.add(chara);
		pane.add(input);
		pane.add(enc);
		pane.add(dec);
		pane.add(enter);
		pane.add(output);
		add(pane);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (enc.isSelected()) {
			jack.changeKey(chara.getText().charAt(0));
			text = jack.encrypt(input.getText());
			output.setText(text);
			StringSelection stringSelection = new StringSelection(text);
			Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
			clpbrd.setContents(stringSelection, null);
			input.setText("");
		} else if (dec.isSelected()) {
			jack.changeKey(chara.getText().charAt(0));
			text = jack.decrypt(input.getText());
			output.setText(text);
			StringSelection stringSelection = new StringSelection(text);
			Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
			clpbrd.setContents(stringSelection, null);
			input.setText("");
		}
	}

	public static void main(String args[]) {
		Main coo = new Main();
	}

}
