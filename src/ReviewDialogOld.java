import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
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

public class ReviewDialogOld extends JDialog implements ActionListener{

	private final JPanel contentPanel = new JPanel();
	

	/**
	 * Create the dialog.
	 */
	public ReviewDialogOld(String value) {
		setTitle("Review");
		setBounds(100, 100, 448, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(30, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
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
				saveButton.setActionCommand("OK");
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
				buttonPane.add(cancelButton);
			}
			{
				Component horizontalGlue = Box.createHorizontalGlue();
				buttonPane.add(horizontalGlue);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("Save".equals(e.getActionCommand()))
		{
			int inx = GameRater.gameTable.convertRowIndexToModel(GameRater.gameTable.getSelectedRow());
			//GameRater.tblmodel.setValueAt(String.valueOf(slider.getValue()), inx, GameRater.gameTable.getSelectedColumn());
			GameRater.tblmodel.setValueAt(new Date(), inx, 3);
			
			//ReviewDialog.this.setVisible(false);
		}
		else if ("Cancel".equals(e.getActionCommand()))
		{
			//ReviewDialog.this.setVisible(false);
		}
		
	}

}
