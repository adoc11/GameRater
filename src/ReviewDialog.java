import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;

import java.awt.Component;

import javax.swing.Box;

import com.inet.editor.BaseEditor;

import java.awt.Dimension;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.SwingConstants;

public class ReviewDialog extends JDialog implements ActionListener{

	private final JPanel contentPanel = new JPanel();
	private BaseEditor editor;
	private JTextField synopsisTextField;
	private JLabel synopsisLabel;
	private String review;
	private String synopsis;

	/**
	 * Create the dialog.
	 */
	public ReviewDialog(String syn, String rev) {
		setTitle("Review");
		
		Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		int width = screenSize.width / 2;
		//int height = screenSize.height;
		editor = new BaseEditor( true );
        // use BorderLayout to maximize the editor 
		setBounds(100, 100, 600, 600);
		
		this.setLocation(width, 100);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(30, 10, 5, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout());
		
		review = rev;
		synopsis = syn;
		
		JPanel synopsisPanel = new JPanel();
		contentPanel.add(synopsisPanel, BorderLayout.NORTH);
		synopsisPanel.setBorder(new EmptyBorder(0, 0, 12, 0));
		synopsisPanel.setLayout(new BoxLayout(synopsisPanel, BoxLayout.X_AXIS));
		
		synopsisLabel = new JLabel();
		synopsisLabel.setHorizontalAlignment(SwingConstants.LEFT);
		synopsisLabel.setText("Synopsis: ");
		synopsisPanel.add(synopsisLabel);
		
		synopsisTextField = new JTextField();
		synopsisTextField.setText(synopsis);
		synopsisTextField.setBackground(Color.white);
		synopsisPanel.add(synopsisTextField);
		
		JPanel reviewPanel = new JPanel();
		contentPanel.add(reviewPanel, BorderLayout.CENTER);
		reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));
		
		editor.setText(review, true);
		reviewPanel.add( editor);
		JToolBar toolbar = editor.getToolbar();
		
		JPanel buttonPane = new JPanel();
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));

		Component horizontalGlue = Box.createHorizontalGlue();
		buttonPane.add(horizontalGlue);
		
		JButton saveButton = new JButton("Save");
		saveButton.setActionCommand("Save");
		saveButton.addActionListener(this);
		buttonPane.add(saveButton);
		getRootPane().setDefaultButton(saveButton);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 40));
		buttonPane.add(rigidArea);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);

		Component horizontalGlue2 = Box.createHorizontalGlue();
		buttonPane.add(horizontalGlue2);
        
        GameRater.gameTable.setCursor(Cursor.getDefaultCursor());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("Save".equals(e.getActionCommand()))
		{
			int inx = GameRater.gameTable.convertRowIndexToModel(GameRater.gameTable.getSelectedRow());
			GameRater.tblmodel.setValueAt(synopsisTextField.getText(), inx, GameRater.gameTable.getSelectedColumn());
			GameRater.tblmodel.setValueAt(editor.getText(), inx, 5);
			GameRater.tblmodel.setValueAt(new Date(), inx, 3);
			
			ReviewDialog.this.setVisible(false);
		}
		else if ("Cancel".equals(e.getActionCommand()))
		{
			editor.setText(review, true);
			synopsisTextField.setText(synopsis);
			ReviewDialog.this.setVisible(false);
		}
		
	}

}
