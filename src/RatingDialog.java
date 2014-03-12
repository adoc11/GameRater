import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;


public class RatingDialog extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JSlider slider;

	/**
	 * Create the dialog.
	 */
	public RatingDialog(int value) {
		int row = GameRater.gameTable.convertRowIndexToModel(GameRater.gameTable.getSelectedRow());
		String gameTitle = GameRater.tblmodel.getValueAt(row, 0).toString();

		setTitle("Rating - " + gameTitle);
		setBounds(100, 100, 448, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(30, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
			slider = new JSlider();
			slider.setMajorTickSpacing(1);
			slider.setMaximum(10);
			slider.setValue(value);
			slider.setSnapToTicks(true);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			contentPanel.add(slider);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("Save".equals(e.getActionCommand()))
		{
			int inx = GameRater.gameTable.convertRowIndexToModel(GameRater.gameTable.getSelectedRow());
			GameRater.tblmodel.setValueAt(String.valueOf(slider.getValue()), inx, GameRater.gameTable.getSelectedColumn());
			GameRater.tblmodel.setValueAt(new Date(), inx, 3);

			RatingDialog.this.setVisible(false);
		}
		else if ("Cancel".equals(e.getActionCommand()))
		{
			RatingDialog.this.setVisible(false);
		}

	}

}