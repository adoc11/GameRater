import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.Dimension;

import javax.swing.JSplitPane;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import javax.swing.JEditorPane;

import com.inet.editor.BaseEditor;

import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;

public class AddGameDialog extends JDialog implements ActionListener, ClosePlatformDialogEvent{

	private final JPanel contentPanel = new JPanel();
	private JTextField gameTitleTextField;
	private JLabel validationLabel;
	private JComboBox platformDropDown;
	private JSlider ratingSlider;
	private JDialog addPlatformDialog;
	private BaseEditor editor;
	
	private Vector<String> platforms;
	private ReadWriteCSV rw = new ReadWriteCSV();
	private String filename = "data/platforms.csv";
	private JTextField synopsisTextField;

	/**
	 * Create the dialog.
	 */
	public AddGameDialog() {
		setTitle("Add Game");
		setBounds(100, 100, 1000, 411);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel formatPanel = new JPanel();
			contentPanel.add(formatPanel, BorderLayout.CENTER);
			formatPanel.setLayout(new BoxLayout(formatPanel, BoxLayout.X_AXIS));
			{
				JPanel leftPanel = new JPanel();
				formatPanel.add(leftPanel);
				GridBagLayout gbl_leftPanel = new GridBagLayout();
				gbl_leftPanel.columnWidths = new int[] {70, 244};
				gbl_leftPanel.rowHeights = new int[] {20, 20, 45, 20};
				gbl_leftPanel.columnWeights = new double[]{0.0, 1.0};
				gbl_leftPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
				leftPanel.setLayout(gbl_leftPanel);
				{
					JLabel gameTitleLabel = new JLabel("Title:");
					gameTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					GridBagConstraints gbc_gameTitleLabel = new GridBagConstraints();
					gbc_gameTitleLabel.anchor = GridBagConstraints.WEST;
					gbc_gameTitleLabel.fill = GridBagConstraints.VERTICAL;
					gbc_gameTitleLabel.insets = new Insets(0, 0, 5, 5);
					gbc_gameTitleLabel.gridx = 0;
					gbc_gameTitleLabel.gridy = 0;
					leftPanel.add(gameTitleLabel, gbc_gameTitleLabel);
				}
				{
					gameTitleTextField = new JTextField();
					GridBagConstraints gbc_gameTitleTextField = new GridBagConstraints();
					gbc_gameTitleTextField.fill = GridBagConstraints.BOTH;
					gbc_gameTitleTextField.insets = new Insets(0, 0, 5, 5);
					gbc_gameTitleTextField.gridx = 1;
					gbc_gameTitleTextField.gridy = 0;
					leftPanel.add(gameTitleTextField, gbc_gameTitleTextField);
					gameTitleTextField.setColumns(10);
				}
				{
					validationLabel = new JLabel("");
					validationLabel.setForeground(Color.RED);
					GridBagConstraints gbc_validationLabel = new GridBagConstraints();
					gbc_validationLabel.insets = new Insets(0, 0, 5, 0);
					gbc_validationLabel.gridx = 2;
					gbc_validationLabel.gridy = 0;
					leftPanel.add(validationLabel, gbc_validationLabel);
				}
				{
					JLabel platformLabel = new JLabel("Platform:");
					GridBagConstraints gbc_platformLabel = new GridBagConstraints();
					gbc_platformLabel.fill = GridBagConstraints.BOTH;
					gbc_platformLabel.insets = new Insets(0, 0, 5, 5);
					gbc_platformLabel.gridx = 0;
					gbc_platformLabel.gridy = 1;
					leftPanel.add(platformLabel, gbc_platformLabel);
				}
				{
					platformDropDown = new JComboBox();
					platforms = rw.loadPlatforms(filename);
					platformDropDown.setModel(new DefaultComboBoxModel(platforms));
					platformDropDown.addActionListener(this);
					platformDropDown.setActionCommand("Platform");
					
					addPlatformDialog = new AddPlatformDialog(platforms, this);
					
					GridBagConstraints gbc_platformDropDown = new GridBagConstraints();
					gbc_platformDropDown.fill = GridBagConstraints.BOTH;
					gbc_platformDropDown.insets = new Insets(0, 0, 5, 5);
					gbc_platformDropDown.gridx = 1;
					gbc_platformDropDown.gridy = 1;
					leftPanel.add(platformDropDown, gbc_platformDropDown);
				}
				{
					JLabel ratingLabel = new JLabel("Rating:");
					GridBagConstraints gbc_ratingLabel = new GridBagConstraints();
					gbc_ratingLabel.fill = GridBagConstraints.BOTH;
					gbc_ratingLabel.insets = new Insets(0, 0, 5, 5);
					gbc_ratingLabel.gridx = 0;
					gbc_ratingLabel.gridy = 2;
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
					GridBagConstraints gbc_ratingSlider = new GridBagConstraints();
					gbc_ratingSlider.insets = new Insets(0, 0, 5, 5);
					gbc_ratingSlider.fill = GridBagConstraints.BOTH;
					gbc_ratingSlider.gridx = 1;
					gbc_ratingSlider.gridy = 2;
					leftPanel.add(ratingSlider, gbc_ratingSlider);
				}
				{
					JLabel synopsisLabel = new JLabel("Synopsis:");
					GridBagConstraints gbc_synopsisLabel = new GridBagConstraints();
					gbc_synopsisLabel.anchor = GridBagConstraints.EAST;
					gbc_synopsisLabel.fill = GridBagConstraints.VERTICAL;
					gbc_synopsisLabel.insets = new Insets(0, 0, 0, 5);
					gbc_synopsisLabel.gridx = 0;
					gbc_synopsisLabel.gridy = 3;
					leftPanel.add(synopsisLabel, gbc_synopsisLabel);
				}
				{
					synopsisTextField = new JTextField();
					GridBagConstraints gbc_synopsisTextField = new GridBagConstraints();
					gbc_synopsisTextField.insets = new Insets(0, 0, 0, 5);
					gbc_synopsisTextField.fill = GridBagConstraints.BOTH;
					gbc_synopsisTextField.gridx = 1;
					gbc_synopsisTextField.gridy = 3;
					leftPanel.add(synopsisTextField, gbc_synopsisTextField);
					synopsisTextField.setColumns(10);
				}
			}
			{
				JPanel rightPanel = new JPanel();
				formatPanel.add(rightPanel);
				rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
				{
					JLabel reviewLabel = new JLabel("Review:");
					reviewLabel.setHorizontalAlignment(SwingConstants.LEFT);
					reviewLabel.setVerticalAlignment(SwingConstants.TOP);
					reviewLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
					rightPanel.add(reviewLabel);
				}
				{
					editor = new BaseEditor( true );
					rightPanel.add( editor);
					JToolBar toolbar = editor.getToolbar();
				}
			}
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
		
		GameRater.gameTable.setCursor(Cursor.getDefaultCursor());
	}
	
	public void closePlatformEvent(String p)
	{
		platformDropDown.setSelectedItem(p);
	}
	
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
			AddGameDialog.this.setVisible(false);
		}
	}
}
