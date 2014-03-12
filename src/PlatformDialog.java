import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class PlatformDialog extends JDialog implements ActionListener, ClosePlatformDialogEvent {

	private final JPanel contentPanel = new JPanel();

	private JComboBox comboBox;
	private Vector<String> platforms;
	private final ReadWriteCSV rw = new ReadWriteCSV();
	private final String filename = "data/platforms.csv";
	private final JDialog addPlatformDialog;

	/**
	 * Create the dialog.
	 */
	public PlatformDialog(String value) {
		int row = GameRater.gameTable.convertRowIndexToModel(GameRater.gameTable.getSelectedRow());
		String gameTitle = GameRater.tblmodel.getValueAt(row, 0).toString();

		setTitle("Platform - " + gameTitle);
		setBounds(100, 100, 448, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(30, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
			comboBox = new JComboBox();
			platforms = rw.loadPlatforms(filename);

			comboBox.setModel(new DefaultComboBoxModel(platforms));
			comboBox.addActionListener(this);
			comboBox.setActionCommand("Platform");
			comboBox.setSelectedItem(value);
			contentPanel.add(comboBox);
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
		addPlatformDialog = new AddPlatformDialog(platforms, this);
	}

	@Override
	public void closePlatformEvent(String p)
	{
		comboBox.setSelectedItem(p);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if ("Save".equals(e.getActionCommand()))
		{
			int inx = GameRater.gameTable.convertRowIndexToModel(GameRater.gameTable.getSelectedRow());
			GameRater.tblmodel.setValueAt(comboBox.getSelectedItem(), inx, GameRater.gameTable.getSelectedColumn());
			GameRater.tblmodel.setValueAt(new Date(), inx, 3);

			PlatformDialog.this.setVisible(false);
		}
		else if ("Cancel".equals(e.getActionCommand()))
		{
			PlatformDialog.this.setVisible(false);
		}
		else if ("Platform".equals(e.getActionCommand()))
		{
			if (comboBox.getSelectedIndex() == comboBox.getModel().getSize() - 1)
			{
				//addPlatformDialog = new AddPlatformDialog(platforms);
				addPlatformDialog.setLocationRelativeTo(this);
				addPlatformDialog.setVisible(true);
			}
		}

	}
}
