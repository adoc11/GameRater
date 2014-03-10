import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;

import java.awt.Component;

import javax.swing.Box;

import java.awt.Dimension;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Color;
import java.util.Collections;


public class AddPlatformDialog extends JDialog implements ActionListener {

	private ClosePlatformDialogEvent cpde;
	private final JPanel contentPanel = new JPanel();
	private JTextField platformTextField;
	private Vector<String> platforms;
	private JLabel validation;
	
	private ReadWriteCSV rw = new ReadWriteCSV();
	private String filename = "data/platforms.csv";

	/**
	 * Create the dialog.
	 */
	public AddPlatformDialog(Vector<String> p, JDialog pd) {
		setTitle("Add Platform");
		setBounds(100, 100, 448, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(30, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		platforms = p;
		cpde = (ClosePlatformDialogEvent) pd;
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
			platformTextField = new JTextField();
			contentPanel.add(platformTextField);
			platformTextField.setColumns(20);
		}
		{
			validation = new JLabel("");
			contentPanel.add(validation);
			validation.setForeground(Color.RED);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
			{
				Component horizontalGlue = Box.createHorizontalGlue();
				buttonPane.add(horizontalGlue);
			}
			{
				JButton saveButton = new JButton("Save");
				saveButton.setActionCommand("Save");
				saveButton.addActionListener(this);
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				Component rigidArea = Box.createRigidArea(new Dimension(20, 40));
				buttonPane.add(rigidArea);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
			{
				Component horizontalGlue = Box.createHorizontalGlue();
				buttonPane.add(horizontalGlue);
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		Boolean canClose = true;
		if ("Save".equals(e.getActionCommand()))
		{
			for(String p : platforms)
			{
				if (p.toLowerCase().equals(platformTextField.getText().toLowerCase()))
				{
					validation.setText("Already exists in the list");
					canClose = false;
					break;
				}
			}
			
			if (platformTextField.getText().length() == 0)
			{
				validation.setText("Please enter a platform");
				canClose = false;
			}
			
			if (canClose)
			{
				platforms.add(platformTextField.getText());
				Collections.sort(platforms);
				
				rw.WritePlatform(filename, platforms);
				
				cpde.closePlatformEvent (platformTextField.getText());	
			}
		}
		else if ("Cancel".equals(e.getActionCommand()))
		{
			
		}
		
		if (canClose)
		{
			AddPlatformDialog.this.setVisible(false);
		}
	}
	
}
