import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.inet.editor.BaseEditor;

public class AddGameDialog extends JDialog implements ActionListener, ClosePlatformDialogEvent{
	private JTextField gameTitleTextField;
	private JLabel validationLabel;
	private final JComboBox platformDropDown;
	private JSlider ratingSlider;
	private JDialog addPlatformDialog;
	private BaseEditor editor;

	private Vector<String> platforms;
	private final ReadWriteCSV rw = new ReadWriteCSV();
	private final String filename = "data/platforms.csv";
	private JTextField synopsisTextField;

	/**
	 * Create the dialog.
	 */
	public AddGameDialog() {
		setTitle("Add Game");
		setBounds(100, 100, 1000, 411);
		getContentPane().setLayout(new BorderLayout());
		{
			platforms = rw.loadPlatforms(filename);

			addPlatformDialog = new AddPlatformDialog(platforms, this);
		}
		JPanel leftPanel = new JPanel();
		getContentPane().add(leftPanel, BorderLayout.NORTH);
		GridBagLayout gbl_leftPanel = new GridBagLayout();
		gbl_leftPanel.columnWidths = new int[] {61, 300, 31, 513};
		gbl_leftPanel.rowHeights = new int[] {30, 36, 36, 56, 35, 137};
		gbl_leftPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0};
		gbl_leftPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		leftPanel.setLayout(gbl_leftPanel);
		{
			JLabel reviewLabel = new JLabel("Review:");
			GridBagConstraints gbc_reviewLabel = new GridBagConstraints();
			gbc_reviewLabel.anchor = GridBagConstraints.SOUTHWEST;
			gbc_reviewLabel.insets = new Insets(0, 0, 5, 0);
			gbc_reviewLabel.gridx = 3;
			gbc_reviewLabel.gridy = 0;
			leftPanel.add(reviewLabel, gbc_reviewLabel);
			reviewLabel.setHorizontalAlignment(SwingConstants.LEFT);
			reviewLabel.setVerticalAlignment(SwingConstants.TOP);
			reviewLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		}
		{
			JLabel gameTitleLabel = new JLabel("Title:");
			gameTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			GridBagConstraints gbc_gameTitleLabel = new GridBagConstraints();
			gbc_gameTitleLabel.anchor = GridBagConstraints.EAST;
			gbc_gameTitleLabel.insets = new Insets(0, 0, 5, 5);
			gbc_gameTitleLabel.gridx = 0;
			gbc_gameTitleLabel.gridy = 1;
			leftPanel.add(gameTitleLabel, gbc_gameTitleLabel);
		}
		{
			gameTitleTextField = new JTextField();
			GridBagConstraints gbc_gameTitleTextField = new GridBagConstraints();
			gbc_gameTitleTextField.fill = GridBagConstraints.HORIZONTAL;
			gbc_gameTitleTextField.insets = new Insets(0, 0, 5, 5);
			gbc_gameTitleTextField.gridx = 1;
			gbc_gameTitleTextField.gridy = 1;
			leftPanel.add(gameTitleTextField, gbc_gameTitleTextField);
			gameTitleTextField.setColumns(10);
		}
		{
			validationLabel = new JLabel("");
			validationLabel.setForeground(Color.RED);
			GridBagConstraints gbc_validationLabel = new GridBagConstraints();
			gbc_validationLabel.fill = GridBagConstraints.VERTICAL;
			gbc_validationLabel.anchor = GridBagConstraints.WEST;
			gbc_validationLabel.insets = new Insets(8, 0, 5, 5);
			gbc_validationLabel.gridx = 1;
			gbc_validationLabel.gridy = 0;
			leftPanel.add(validationLabel, gbc_validationLabel);
		}
		{
			JLabel platformLabel = new JLabel("Platform:");
			GridBagConstraints gbc_platformLabel = new GridBagConstraints();
			gbc_platformLabel.anchor = GridBagConstraints.EAST;
			gbc_platformLabel.insets = new Insets(0, 0, 5, 5);
			gbc_platformLabel.gridx = 0;
			gbc_platformLabel.gridy = 2;
			leftPanel.add(platformLabel, gbc_platformLabel);
		}
		platformDropDown = new JComboBox();
		platformDropDown.setModel(new DefaultComboBoxModel(platforms));
		platformDropDown.addActionListener(this);
		platformDropDown.setActionCommand("Platform");

		GridBagConstraints gbc_platformDropDown = new GridBagConstraints();
		gbc_platformDropDown.fill = GridBagConstraints.HORIZONTAL;
		gbc_platformDropDown.insets = new Insets(5, 0, 5, 5);
		gbc_platformDropDown.gridx = 1;
		gbc_platformDropDown.gridy = 2;
		leftPanel.add(platformDropDown, gbc_platformDropDown);
		{
			//JTextPane textPane = new JTextPane();
			editor = new BaseEditor( true );
			//JToolBar toolbar = editor.getToolbar();

			GridBagConstraints gbc_textPane = new GridBagConstraints();
			gbc_textPane.gridheight = 5;
			gbc_textPane.insets = new Insets(0, 0, 5, 5);
			gbc_textPane.fill = GridBagConstraints.BOTH;
			gbc_textPane.gridx = 3;
			gbc_textPane.gridy = 1;
			//leftPanel.add(textPane, gbc_textPane);
			leftPanel.add(editor, gbc_textPane);
		}
		{
			JLabel ratingLabel = new JLabel("Rating:");
			GridBagConstraints gbc_ratingLabel = new GridBagConstraints();
			gbc_ratingLabel.anchor = GridBagConstraints.EAST;
			gbc_ratingLabel.insets = new Insets(0, 0, 5, 5);
			gbc_ratingLabel.gridx = 0;
			gbc_ratingLabel.gridy = 3;
			leftPanel.add(ratingLabel, gbc_ratingLabel);
		}
		{
			ratingSlider = new JSlider();
			ratingSlider.setValue(5);
			ratingSlider.setMaximum(10);
			ratingSlider.setMajorTickSpacing(1);
			ratingSlider.setSnapToTicks(true);
			ratingSlider.setPaintTicks(true);
			ratingSlider.setPaintLabels(true);
			ratingSlider.setMinimumSize(ratingSlider.getPreferredSize());
			GridBagConstraints gbc_ratingSlider = new GridBagConstraints();
			gbc_ratingSlider.anchor = GridBagConstraints.WEST;
			gbc_ratingSlider.insets = new Insets(5, 0, 5, 5);
			gbc_ratingSlider.gridx = 1;
			gbc_ratingSlider.gridy = 3;
			leftPanel.add(ratingSlider, gbc_ratingSlider);
		}
		{
			JLabel synopsisLabel = new JLabel("Synopsis:");
			GridBagConstraints gbc_synopsisLabel = new GridBagConstraints();
			gbc_synopsisLabel.anchor = GridBagConstraints.EAST;
			gbc_synopsisLabel.insets = new Insets(0, 0, 5, 5);
			gbc_synopsisLabel.gridx = 0;
			gbc_synopsisLabel.gridy = 4;
			leftPanel.add(synopsisLabel, gbc_synopsisLabel);
		}
		{
			synopsisTextField = new JTextField();
			GridBagConstraints gbc_synopsisTextField = new GridBagConstraints();
			gbc_synopsisTextField.fill = GridBagConstraints.HORIZONTAL;
			gbc_synopsisTextField.insets = new Insets(5, 0, 5, 5);
			gbc_synopsisTextField.gridx = 1;
			gbc_synopsisTextField.gridy = 4;
			leftPanel.add(synopsisTextField, gbc_synopsisTextField);
			synopsisTextField.setColumns(10);
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

		GameRater.frame.setCursor(Cursor.getDefaultCursor());
	}

	@Override
	public void closePlatformEvent(String p)
	{
		platformDropDown.setSelectedItem(p);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Boolean canClose = true;
		if ("Save".equals(e.getActionCommand()))
		{	
			if (gameTitleTextField.getText().length() == 0)
			{
				validationLabel.setText("Please enter a game title");
				canClose = false;
			}

			if (canClose)
			{
				GameData newGame = new GameData();

				newGame.gameData[0] = gameTitleTextField.getText();
				newGame.gameData[1] = ratingSlider.getValue();
				newGame.gameData[2] = platformDropDown.getSelectedItem().toString();
				newGame.gameData[3] = new Date();
				newGame.gameData[4] = synopsisTextField.getText();
				newGame.gameData[5] = editor.getText();

				GameRater.tblmodel.addRow(newGame.gameData);
			}
		}
		else if ("Cancel".equals(e.getActionCommand()))
		{

		}
		else if ("Platform".equals(e.getActionCommand()))
		{
			canClose = false;
			if (platformDropDown.getSelectedIndex() == platformDropDown.getModel().getSize() - 1)
			{
				addPlatformDialog.setLocationRelativeTo(this);
				addPlatformDialog.setVisible(true);
			}
		}

		if (canClose)
		{
			this.setVisible(false);
		}
	}
}
