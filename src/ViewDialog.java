import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

import com.inet.editor.BaseEditor;


public class ViewDialog extends JDialog implements ActionListener, ClosePlatformDialogEvent {
	private final  JTextField gameTitleTextField;
	private final  JComboBox platformDropDown;
	private final  JDialog addPlatformDialog;
	private final  JSlider slider;
	private final  JLabel gameTitleLabel;
	private final  JLabel viewPlatformLabel;
	private final  JLabel viewRatingLabel;
	private final  JLabel validationLabel;
	private final  JLabel viewSynopsisLabel;

	private final  JEditorPane reviewTextPane;
	private final  JTextField synopsisTextField;
	private final  JButton editTitleButton;
	private final  JButton editPlatformButton;
	private final  JButton editRatingButton;
	private final  JButton editSynopsisButton;
	private final  JButton editReviewButton;

	private final  BaseEditor editor;

	private final Vector<String> platforms;
	private final ReadWriteCSV rw = new ReadWriteCSV();
	private final String filename = "data/platforms.csv";

	private final JPanel cardLayoutTitlePanel1;
	private final JPanel viewTitlePanel;
	private final JPanel changeTitlePanel;

	private final JPanel cardLayoutTitlePanel2;
	private final JPanel editTitlePanelCard1;
	private final JPanel saveCancelTitlePanelCard2;

	private final JPanel cardLayoutPlatformPanel1;
	private final JPanel cardLayoutPlatformPanel2;

	private final JPanel cardLayoutRatingPanel1;
	private final JPanel cardLayoutRatingPanel2;

	private final JPanel cardLayoutSynopsisPanel1;
	private final JPanel viewSynopsisPanel;
	private final JPanel changeSynopsisPanel;

	private final JPanel cardLayoutSynopsisPanel2;

	private final JPanel cardLayoutReviewPanel1;
	private final JPanel cardLayoutReviewPanel2;

	/**
	 * Create the dialog.
	 */
	public ViewDialog() {
		int row = GameRater.gameTable.convertRowIndexToModel(GameRater.gameTable.getSelectedRow());

		String title = GameRater.tblmodel.getValueAt(row, 0).toString();
		int rating = Integer.parseInt(GameRater.tblmodel.getValueAt(row, 1).toString());
		String platform = GameRater.tblmodel.getValueAt(row, 2).toString();
		String synopsis = GameRater.tblmodel.getValueAt(row, 4).toString();
		String review = GameRater.tblmodel.getValueAt(row, 5).toString();

		setTitle(title);

		setBounds(100, 100, 655, 600);
		getContentPane().setLayout(new BorderLayout());

		JPanel formatPanel = new JPanel();
		getContentPane().add(formatPanel, BorderLayout.CENTER);
		formatPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagLayout gbl_formatPanel = new GridBagLayout();
		gbl_formatPanel.columnWidths = new int[]{68, 417, 150, 0};
		gbl_formatPanel.rowHeights = new int[]{42, 29, 29, 50, 29, 36, 300, 0};
		gbl_formatPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_formatPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		formatPanel.setLayout(gbl_formatPanel);

		validationLabel = new JLabel("");
		GridBagConstraints gbc_validationLabel = new GridBagConstraints();
		gbc_validationLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_validationLabel.insets = new Insets(5, 5, 5, 5);
		gbc_validationLabel.gridx = 1;
		gbc_validationLabel.gridy = 0;
		formatPanel.add(validationLabel, gbc_validationLabel);
		validationLabel.setAlignmentX(0.5f);
		validationLabel.setForeground(Color.RED);

		JLabel titleLabel = new JLabel("Title:");
		titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_titleLabel = new GridBagConstraints();
		gbc_titleLabel.fill = GridBagConstraints.VERTICAL;
		gbc_titleLabel.anchor = GridBagConstraints.EAST;
		gbc_titleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_titleLabel.gridx = 0;
		gbc_titleLabel.gridy = 1;
		formatPanel.add(titleLabel, gbc_titleLabel);

		cardLayoutTitlePanel1 = new JPanel();
		GridBagConstraints gbc_cardLayoutTitlePanel1 = new GridBagConstraints();
		gbc_cardLayoutTitlePanel1.fill = GridBagConstraints.BOTH;
		gbc_cardLayoutTitlePanel1.insets = new Insets(0, 0, 5, 5);
		gbc_cardLayoutTitlePanel1.gridx = 1;
		gbc_cardLayoutTitlePanel1.gridy = 1;
		formatPanel.add(cardLayoutTitlePanel1, gbc_cardLayoutTitlePanel1);
		cardLayoutTitlePanel1.setLayout(new CardLayout(0, 0));

		viewTitlePanel = new JPanel();
		viewTitlePanel.setBorder(new EmptyBorder(0, 5, 0, 0));
		viewTitlePanel.setBackground(new Color(72, 209, 204));
		cardLayoutTitlePanel1.add(viewTitlePanel, "name_245945827916475");
		viewTitlePanel.setLayout(new BoxLayout(viewTitlePanel, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		viewTitlePanel.add(horizontalGlue);

		gameTitleLabel = new JLabel("");
		gameTitleLabel.setBackground(new Color(72, 209, 204));
		gameTitleLabel.setText(title);
		gameTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		viewTitlePanel.add(gameTitleLabel);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		viewTitlePanel.add(horizontalGlue_1);

		changeTitlePanel = new JPanel();
		cardLayoutTitlePanel1.add(changeTitlePanel, "name_245957718807996");
		changeTitlePanel.setLayout(new BoxLayout(changeTitlePanel, BoxLayout.Y_AXIS));

		gameTitleTextField = new JTextField();
		gameTitleTextField.setText(title);
		changeTitlePanel.add(gameTitleTextField);
		gameTitleTextField.setColumns(10);

		cardLayoutTitlePanel2 = new JPanel();
		cardLayoutTitlePanel2.setBorder(new EmptyBorder(0, 5, 0, 0));
		GridBagConstraints gbc_cardLayoutTitlePanel2 = new GridBagConstraints();
		gbc_cardLayoutTitlePanel2.anchor = GridBagConstraints.WEST;
		gbc_cardLayoutTitlePanel2.fill = GridBagConstraints.VERTICAL;
		gbc_cardLayoutTitlePanel2.insets = new Insets(0, 0, 5, 0);
		gbc_cardLayoutTitlePanel2.gridx = 2;
		gbc_cardLayoutTitlePanel2.gridy = 1;
		formatPanel.add(cardLayoutTitlePanel2, gbc_cardLayoutTitlePanel2);
		cardLayoutTitlePanel2.setLayout(new CardLayout(0, 0));

		editTitlePanelCard1 = new JPanel();
		cardLayoutTitlePanel2.add(editTitlePanelCard1, "name_245283698969319");
		editTitlePanelCard1.setLayout(new BoxLayout(editTitlePanelCard1, BoxLayout.X_AXIS));

		editTitleButton = new JButton("Edit");
		editTitleButton.setActionCommand("EditTitle");
		editTitleButton.addActionListener(this);
		editTitlePanelCard1.add(editTitleButton);

		JPanel panel = new JPanel();
		cardLayoutTitlePanel2.add(panel, "name_725546439272519");
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		saveCancelTitlePanelCard2 = new JPanel();
		saveCancelTitlePanelCard2.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(saveCancelTitlePanelCard2);
		saveCancelTitlePanelCard2.setLayout(new BoxLayout(saveCancelTitlePanelCard2, BoxLayout.X_AXIS));

		JButton saveTitleButton = new JButton("Save");
		saveTitleButton.setActionCommand("SaveTitle");
		saveTitleButton.addActionListener(this);
		saveCancelTitlePanelCard2.add(saveTitleButton);

		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		saveCancelTitlePanelCard2.add(rigidArea);

		JButton cancelTitleButton = new JButton("Cancel");
		cancelTitleButton.setActionCommand("CancelTitle");
		cancelTitleButton.addActionListener(this);
		saveCancelTitlePanelCard2.add(cancelTitleButton);

		JLabel platformLabel = new JLabel("Platform:");
		platformLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_platformLabel = new GridBagConstraints();
		gbc_platformLabel.anchor = GridBagConstraints.EAST;
		gbc_platformLabel.insets = new Insets(0, 0, 5, 5);
		gbc_platformLabel.gridx = 0;
		gbc_platformLabel.gridy = 2;
		formatPanel.add(platformLabel, gbc_platformLabel);

		cardLayoutPlatformPanel1 = new JPanel();
		GridBagConstraints gbc_cardLayoutPlatformPanel1 = new GridBagConstraints();
		gbc_cardLayoutPlatformPanel1.fill = GridBagConstraints.BOTH;
		gbc_cardLayoutPlatformPanel1.insets = new Insets(0, 0, 5, 5);
		gbc_cardLayoutPlatformPanel1.gridx = 1;
		gbc_cardLayoutPlatformPanel1.gridy = 2;
		formatPanel.add(cardLayoutPlatformPanel1, gbc_cardLayoutPlatformPanel1);
		cardLayoutPlatformPanel1.setLayout(new CardLayout(0, 0));

		JPanel viewPlatformPanel = new JPanel();
		viewPlatformPanel.setBorder(new EmptyBorder(0, 5, 0, 0));
		viewPlatformPanel.setBackground(new Color(72, 209, 204));
		cardLayoutPlatformPanel1.add(viewPlatformPanel, "name_246549987849649");
		viewPlatformPanel.setLayout(new BoxLayout(viewPlatformPanel, BoxLayout.X_AXIS));
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		viewPlatformPanel.add(horizontalGlue_2);

		viewPlatformLabel = new JLabel("");
		viewPlatformLabel.setText(platform);
		viewPlatformPanel.add(viewPlatformLabel);
		
		Component horizontalGlue_3 = Box.createHorizontalGlue();
		viewPlatformPanel.add(horizontalGlue_3);

		JPanel changePlatformPanel = new JPanel();
		changePlatformPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		cardLayoutPlatformPanel1.add(changePlatformPanel, "name_246754779627284");
		changePlatformPanel.setLayout(new BoxLayout(changePlatformPanel, BoxLayout.Y_AXIS));

		platformDropDown = new JComboBox();
		platforms = rw.loadPlatforms(filename);
		platformDropDown.setModel(new DefaultComboBoxModel(platforms));
		platformDropDown.addActionListener(this);
		platformDropDown.setActionCommand("Platform");
		platformDropDown.setSelectedItem(platform);

		changePlatformPanel.add(platformDropDown);

		cardLayoutPlatformPanel2 = new JPanel();
		cardLayoutPlatformPanel2.setBorder(new EmptyBorder(0, 5, 0, 0));
		GridBagConstraints gbc_cardLayoutPlatformPanel2 = new GridBagConstraints();
		gbc_cardLayoutPlatformPanel2.anchor = GridBagConstraints.WEST;
		gbc_cardLayoutPlatformPanel2.fill = GridBagConstraints.VERTICAL;
		gbc_cardLayoutPlatformPanel2.insets = new Insets(0, 0, 5, 0);
		gbc_cardLayoutPlatformPanel2.gridx = 2;
		gbc_cardLayoutPlatformPanel2.gridy = 2;
		formatPanel.add(cardLayoutPlatformPanel2, gbc_cardLayoutPlatformPanel2);
		cardLayoutPlatformPanel2.setLayout(new CardLayout(0, 0));

		JPanel editPlatformPanel = new JPanel();
		cardLayoutPlatformPanel2.add(editPlatformPanel, "name_248787550264915");
		editPlatformPanel.setLayout(new BoxLayout(editPlatformPanel, BoxLayout.X_AXIS));

		editPlatformButton = new JButton("Edit");
		editPlatformButton.setActionCommand("EditPlatform");
		editPlatformButton.addActionListener(this);
		editPlatformPanel.add(editPlatformButton);

		JPanel saveCancelPlatformPanel = new JPanel();
		cardLayoutPlatformPanel2.add(saveCancelPlatformPanel, "name_248868240303241");
		saveCancelPlatformPanel.setLayout(new BoxLayout(saveCancelPlatformPanel, BoxLayout.X_AXIS));

		JButton savePlatformButton = new JButton("Save");
		savePlatformButton.setActionCommand("SavePlatform");
		savePlatformButton.addActionListener(this);
		saveCancelPlatformPanel.add(savePlatformButton);

		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		saveCancelPlatformPanel.add(rigidArea_1);

		JButton cancelPlatformButton = new JButton("Cancel");
		cancelPlatformButton.setActionCommand("CancelPlatform");
		cancelPlatformButton.addActionListener(this);
		saveCancelPlatformPanel.add(cancelPlatformButton);

		JLabel ratingLabel = new JLabel("Rating:");
		ratingLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_ratingLabel = new GridBagConstraints();
		gbc_ratingLabel.anchor = GridBagConstraints.EAST;
		gbc_ratingLabel.insets = new Insets(0, 0, 5, 5);
		gbc_ratingLabel.gridx = 0;
		gbc_ratingLabel.gridy = 3;
		formatPanel.add(ratingLabel, gbc_ratingLabel);

		cardLayoutRatingPanel1 = new JPanel();
		cardLayoutRatingPanel1.setBorder(null);
		GridBagConstraints gbc_cardLayoutRatingPanel1 = new GridBagConstraints();
		gbc_cardLayoutRatingPanel1.fill = GridBagConstraints.BOTH;
		gbc_cardLayoutRatingPanel1.insets = new Insets(0, 0, 5, 5);
		gbc_cardLayoutRatingPanel1.gridx = 1;
		gbc_cardLayoutRatingPanel1.gridy = 3;
		formatPanel.add(cardLayoutRatingPanel1, gbc_cardLayoutRatingPanel1);
		cardLayoutRatingPanel1.setLayout(new CardLayout(0, 0));

		JPanel viewRatingPanel = new JPanel();
		viewRatingPanel.setBorder(new EmptyBorder(0, 5, 0, 0));
		viewRatingPanel.setBackground(new Color(72, 209, 204));
		cardLayoutRatingPanel1.add(viewRatingPanel, "name_249343463384739");
		viewRatingPanel.setLayout(new BoxLayout(viewRatingPanel, BoxLayout.X_AXIS));
		
		Component horizontalGlue_4 = Box.createHorizontalGlue();
		viewRatingPanel.add(horizontalGlue_4);

		viewRatingLabel = new JLabel("");
		viewRatingLabel.setText(String.valueOf(rating));
		viewRatingPanel.add(viewRatingLabel);
		
		Component horizontalGlue_5 = Box.createHorizontalGlue();
		viewRatingPanel.add(horizontalGlue_5);

		JPanel changeRatingPanel = new JPanel();
		cardLayoutRatingPanel1.add(changeRatingPanel, "name_249399975295239");
		changeRatingPanel.setLayout(new BoxLayout(changeRatingPanel, BoxLayout.Y_AXIS));

		slider = new JSlider();
		slider.setAlignmentX(Component.LEFT_ALIGNMENT);
		slider.setMajorTickSpacing(1);
		slider.setValue(rating);
		slider.setPaintTicks(true);
		slider.setSnapToTicks(true);
		slider.setPaintLabels(true);
		slider.setMaximum(10);
		changeRatingPanel.add(slider);

		cardLayoutRatingPanel2 = new JPanel();
		cardLayoutRatingPanel2.setBorder(new EmptyBorder(0, 5, 0, 0));
		GridBagConstraints gbc_cardLayoutRatingPanel2 = new GridBagConstraints();
		gbc_cardLayoutRatingPanel2.anchor = GridBagConstraints.WEST;
		gbc_cardLayoutRatingPanel2.fill = GridBagConstraints.VERTICAL;
		gbc_cardLayoutRatingPanel2.insets = new Insets(0, 0, 5, 0);
		gbc_cardLayoutRatingPanel2.gridx = 2;
		gbc_cardLayoutRatingPanel2.gridy = 3;
		formatPanel.add(cardLayoutRatingPanel2, gbc_cardLayoutRatingPanel2);
		cardLayoutRatingPanel2.setLayout(new CardLayout(0, 0));

		JPanel editRatingPanel = new JPanel();
		cardLayoutRatingPanel2.add(editRatingPanel, "name_249808573410939");
		editRatingPanel.setLayout(new BoxLayout(editRatingPanel, BoxLayout.X_AXIS));

		editRatingButton = new JButton("Edit");
		editRatingButton.setActionCommand("EditRating");
		editRatingButton.addActionListener(this);
		editRatingPanel.add(editRatingButton);

		JPanel saveCancelRatingPanel = new JPanel();
		cardLayoutRatingPanel2.add(saveCancelRatingPanel, "name_249887295409348");
		saveCancelRatingPanel.setLayout(new BoxLayout(saveCancelRatingPanel, BoxLayout.X_AXIS));

		JButton saveRatingButton = new JButton("Save");
		saveRatingButton.setActionCommand("SaveRating");
		saveRatingButton.addActionListener(this);
		saveCancelRatingPanel.add(saveRatingButton);

		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		saveCancelRatingPanel.add(rigidArea_2);

		JButton cancelRatingButton = new JButton("Cancel");
		cancelRatingButton.setActionCommand("CancelRating");
		cancelRatingButton.addActionListener(this);
		saveCancelRatingPanel.add(cancelRatingButton);

		JLabel synopsisLabel = new JLabel("Synopsis:");
		synopsisLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_synopsisLabel = new GridBagConstraints();
		gbc_synopsisLabel.fill = GridBagConstraints.BOTH;
		gbc_synopsisLabel.insets = new Insets(0, 0, 5, 5);
		gbc_synopsisLabel.gridx = 0;
		gbc_synopsisLabel.gridy = 4;
		formatPanel.add(synopsisLabel, gbc_synopsisLabel);

		cardLayoutSynopsisPanel1 = new JPanel();
		GridBagConstraints gbc_cardLayoutSynopsisPanel1 = new GridBagConstraints();
		gbc_cardLayoutSynopsisPanel1.fill = GridBagConstraints.BOTH;
		gbc_cardLayoutSynopsisPanel1.insets = new Insets(0, 0, 5, 5);
		gbc_cardLayoutSynopsisPanel1.gridx = 1;
		gbc_cardLayoutSynopsisPanel1.gridy = 4;
		formatPanel.add(cardLayoutSynopsisPanel1, gbc_cardLayoutSynopsisPanel1);
		cardLayoutSynopsisPanel1.setLayout(new CardLayout(0, 0));

		viewSynopsisPanel = new JPanel();
		viewSynopsisPanel.setBorder(new EmptyBorder(0, 5, 0, 0));
		viewSynopsisPanel.setBackground(new Color(72, 209, 204));
		cardLayoutSynopsisPanel1.add(viewSynopsisPanel);
		viewSynopsisPanel.setLayout(new BoxLayout(viewSynopsisPanel, BoxLayout.X_AXIS));
		
		Component horizontalGlue_6 = Box.createHorizontalGlue();
		viewSynopsisPanel.add(horizontalGlue_6);

		viewSynopsisLabel = new JLabel();
		viewSynopsisLabel.setText(synopsis);
		viewSynopsisPanel.add(viewSynopsisLabel);
		
		Component horizontalGlue_7 = Box.createHorizontalGlue();
		viewSynopsisPanel.add(horizontalGlue_7);

		changeSynopsisPanel = new JPanel();
		changeSynopsisPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		cardLayoutSynopsisPanel1.add(changeSynopsisPanel);
		changeSynopsisPanel.setLayout(new BoxLayout(changeSynopsisPanel, BoxLayout.X_AXIS));

		synopsisTextField = new JTextField();
		changeSynopsisPanel.add(synopsisTextField);
		synopsisTextField.setColumns(10);
		synopsisTextField.setText(synopsis);

		cardLayoutSynopsisPanel2 = new JPanel();
		cardLayoutSynopsisPanel2.setBorder(new EmptyBorder(0, 5, 0, 0));
		GridBagConstraints gbc_cardLayoutSynopsisPanel2 = new GridBagConstraints();
		gbc_cardLayoutSynopsisPanel2.anchor = GridBagConstraints.WEST;
		gbc_cardLayoutSynopsisPanel2.fill = GridBagConstraints.VERTICAL;
		gbc_cardLayoutSynopsisPanel2.insets = new Insets(0, 0, 5, 0);
		gbc_cardLayoutSynopsisPanel2.gridx = 2;
		gbc_cardLayoutSynopsisPanel2.gridy = 4;
		formatPanel.add(cardLayoutSynopsisPanel2, gbc_cardLayoutSynopsisPanel2);
		cardLayoutSynopsisPanel2.setLayout(new CardLayout(0, 0));

		JPanel editSynopsisPanel = new JPanel();
		cardLayoutSynopsisPanel2.add(editSynopsisPanel);
		editSynopsisPanel.setLayout(new BoxLayout(editSynopsisPanel, BoxLayout.X_AXIS));

		editSynopsisButton = new JButton("Edit");
		editSynopsisButton.setActionCommand("EditSynopsis");
		editSynopsisButton.addActionListener(this);
		editSynopsisPanel.add(editSynopsisButton);

		JPanel saveCancelSynopsisPanel = new JPanel();
		cardLayoutSynopsisPanel2.add(saveCancelSynopsisPanel);
		saveCancelSynopsisPanel.setLayout(new BoxLayout(saveCancelSynopsisPanel, BoxLayout.X_AXIS));

		JButton saveSynopsisButton = new JButton("Save");
		saveSynopsisButton.setActionCommand("SaveSynopsis");
		saveSynopsisButton.addActionListener(this);
		saveCancelSynopsisPanel.add(saveSynopsisButton);

		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		saveCancelSynopsisPanel.add(rigidArea_3);

		JButton cancelSynopsisButton = new JButton("Cancel");
		cancelSynopsisButton.setActionCommand("CancelSynopsis");
		cancelSynopsisButton.addActionListener(this);
		saveCancelSynopsisPanel.add(cancelSynopsisButton);

		JLabel reviewLabel = new JLabel("Review:");
		reviewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_reviewLabel = new GridBagConstraints();
		gbc_reviewLabel.anchor = GridBagConstraints.NORTH;
		gbc_reviewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_reviewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_reviewLabel.gridx = 0;
		gbc_reviewLabel.gridy = 5;
		formatPanel.add(reviewLabel, gbc_reviewLabel);

		cardLayoutReviewPanel1 = new JPanel();
		GridBagConstraints gbc_cardLayoutReviewPanel1 = new GridBagConstraints();
		gbc_cardLayoutReviewPanel1.gridheight = 2;
		gbc_cardLayoutReviewPanel1.fill = GridBagConstraints.BOTH;
		gbc_cardLayoutReviewPanel1.insets = new Insets(0, 0, 5, 5);
		gbc_cardLayoutReviewPanel1.gridx = 1;
		gbc_cardLayoutReviewPanel1.gridy = 5;
		formatPanel.add(cardLayoutReviewPanel1, gbc_cardLayoutReviewPanel1);
		cardLayoutReviewPanel1.setLayout(new CardLayout(0, 0));

		JPanel viewReviewPanel = new JPanel();
		viewReviewPanel.setBackground(new Color(255, 255, 255));
		viewReviewPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		cardLayoutReviewPanel1.add(viewReviewPanel, "name_250177294129247");
		viewReviewPanel.setLayout(new BoxLayout(viewReviewPanel, BoxLayout.X_AXIS));

		reviewTextPane = new JEditorPane();
		reviewTextPane.setContentType("text/html");
		reviewTextPane.setEditable(false);
		reviewTextPane.setText(review);
		reviewTextPane.setCaretPosition(0);

		JScrollPane scroller = new JScrollPane();
		scroller.setAlignmentX(Component.LEFT_ALIGNMENT);
		scroller.setViewportView(reviewTextPane);
		viewReviewPanel.add(scroller);

		JPanel changeReviewPanel = new JPanel();
		changeReviewPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		cardLayoutReviewPanel1.add(changeReviewPanel, "name_254272906869826");
		changeReviewPanel.setLayout(new BoxLayout(changeReviewPanel, BoxLayout.X_AXIS));

		editor = new BaseEditor(true);
		editor.setText(review, true);
		changeReviewPanel.add(editor);

		cardLayoutReviewPanel2 = new JPanel();
		cardLayoutReviewPanel2.setBorder(new EmptyBorder(0, 0, 0, 0));
		GridBagConstraints gbc_cardLayoutReviewPanel2 = new GridBagConstraints();
		gbc_cardLayoutReviewPanel2.anchor = GridBagConstraints.WEST;
		gbc_cardLayoutReviewPanel2.insets = new Insets(0, 0, 5, 0);
		gbc_cardLayoutReviewPanel2.fill = GridBagConstraints.VERTICAL;
		gbc_cardLayoutReviewPanel2.gridx = 2;
		gbc_cardLayoutReviewPanel2.gridy = 5;
		formatPanel.add(cardLayoutReviewPanel2, gbc_cardLayoutReviewPanel2);
		cardLayoutReviewPanel2.setLayout(new CardLayout(0, 0));

		JPanel editReviewPanel = new JPanel();
		editReviewPanel.setBorder(null);
		cardLayoutReviewPanel2.add(editReviewPanel, "name_250344470646622");
		editReviewPanel.setLayout(new BoxLayout(editReviewPanel, BoxLayout.X_AXIS));

		editReviewButton = new JButton("Edit");
		editReviewButton.setActionCommand("EditReview");
		editReviewButton.addActionListener(this);
		editReviewPanel.add(editReviewButton);

		JPanel saveCancelReviewPanel = new JPanel();
		cardLayoutReviewPanel2.add(saveCancelReviewPanel, "name_250344497348912");
		saveCancelReviewPanel.setLayout(new BoxLayout(saveCancelReviewPanel, BoxLayout.X_AXIS));

		JButton saveReviewButton = new JButton("Save");
		saveReviewButton.setActionCommand("SaveReview");
		saveReviewButton.addActionListener(this);
		saveCancelReviewPanel.add(saveReviewButton);

		Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 20));
		saveCancelReviewPanel.add(rigidArea_4);

		JButton cancelReviewButton = new JButton("Cancel");
		cancelReviewButton.setActionCommand("CancelReview");
		cancelReviewButton.addActionListener(this);
		saveCancelReviewPanel.add(cancelReviewButton);

		JPanel backButtonPanel = new JPanel();
		getContentPane().add(backButtonPanel, BorderLayout.SOUTH);
		backButtonPanel.setLayout(new BoxLayout(backButtonPanel, BoxLayout.Y_AXIS));

		Component verticalStrut = Box.createVerticalStrut(15);
		backButtonPanel.add(verticalStrut);

		Component horizontalStrut = Box.createHorizontalStrut(15);
		backButtonPanel.add(horizontalStrut);

		JButton backButton = new JButton("Back");
		cardLayoutReviewPanel2.setBorder(new EmptyBorder(0, 5, 0, 0));
		backButton.setActionCommand("Back");
		backButton.addActionListener(this);
		backButtonPanel.add(backButton);
		backButton.setHorizontalAlignment(SwingConstants.LEFT);

		Component verticalStrut_1 = Box.createVerticalStrut(5);
		backButtonPanel.add(verticalStrut_1);

		addPlatformDialog = new AddPlatformDialog(platforms, this);

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
		Boolean canClose = false;
		if ("EditTitle".equals(e.getActionCommand()))
		{
			CardLayout cl1 = (CardLayout)(cardLayoutTitlePanel1.getLayout());
			CardLayout cl2 = (CardLayout)(cardLayoutTitlePanel2.getLayout());

			cl1.next(cardLayoutTitlePanel1);
			cl2.next(cardLayoutTitlePanel2);
		}
		else if ("EditRating".equals(e.getActionCommand()))
		{
			CardLayout cl1 = (CardLayout)(cardLayoutRatingPanel1.getLayout());
			CardLayout cl2 = (CardLayout)(cardLayoutRatingPanel2.getLayout());

			cl1.next(cardLayoutRatingPanel1);
			cl2.next(cardLayoutRatingPanel2);
		}
		else if ("EditPlatform".equals(e.getActionCommand()))
		{
			CardLayout cl1 = (CardLayout)(cardLayoutPlatformPanel1.getLayout());
			CardLayout cl2 = (CardLayout)(cardLayoutPlatformPanel2.getLayout());

			cl1.next(cardLayoutPlatformPanel1);
			cl2.next(cardLayoutPlatformPanel2);
		}
		else if ("EditSynopsis".equals(e.getActionCommand()))
		{
			CardLayout cl1 = (CardLayout)(cardLayoutSynopsisPanel1.getLayout());
			CardLayout cl2 = (CardLayout)(cardLayoutSynopsisPanel2.getLayout());

			cl1.next(cardLayoutSynopsisPanel1);
			cl2.next(cardLayoutSynopsisPanel2);
		}
		else if ("EditReview".equals(e.getActionCommand()))
		{
			CardLayout cl1 = (CardLayout)(cardLayoutReviewPanel1.getLayout());
			CardLayout cl2 = (CardLayout)(cardLayoutReviewPanel2.getLayout());

			cl1.next(cardLayoutReviewPanel1);
			cl2.next(cardLayoutReviewPanel2);
		}
		else if ("SaveTitle".equals(e.getActionCommand()))
		{
			CardLayout cl1 = (CardLayout)(cardLayoutTitlePanel1.getLayout());
			CardLayout cl2 = (CardLayout)(cardLayoutTitlePanel2.getLayout());

			if (gameTitleTextField.getText().length() == 0)
			{
				validationLabel.setText("Please enter a game title");
			}
			else
			{
				canClose = true;
				gameTitleLabel.setText(gameTitleTextField.getText());
				validationLabel.setText("");
				setTitle(gameTitleTextField.getText());

				int inx = GameRater.gameTable.convertRowIndexToModel(GameRater.gameTable.getSelectedRow());
				GameRater.tblmodel.setValueAt(gameTitleTextField.getText(), inx, 0);
				GameRater.tblmodel.setValueAt(new Date(), inx, 3);

				cl1.next(cardLayoutTitlePanel1);
				cl2.next(cardLayoutTitlePanel2);
			}
		}
		else if ("SaveRating".equals(e.getActionCommand()))
		{
			CardLayout cl1 = (CardLayout)(cardLayoutRatingPanel1.getLayout());
			CardLayout cl2 = (CardLayout)(cardLayoutRatingPanel2.getLayout());

			viewRatingLabel.setText(String.valueOf(slider.getValue()));

			int inx = GameRater.gameTable.convertRowIndexToModel(GameRater.gameTable.getSelectedRow());
			GameRater.tblmodel.setValueAt(String.valueOf(slider.getValue()), inx, 1);
			GameRater.tblmodel.setValueAt(new Date(), inx, 3);

			cl1.next(cardLayoutRatingPanel1);
			cl2.next(cardLayoutRatingPanel2);
		}
		else if ("SavePlatform".equals(e.getActionCommand()))
		{
			CardLayout cl1 = (CardLayout)(cardLayoutPlatformPanel1.getLayout());
			CardLayout cl2 = (CardLayout)(cardLayoutPlatformPanel2.getLayout());

			viewPlatformLabel.setText(platformDropDown.getSelectedItem().toString());

			int inx = GameRater.gameTable.convertRowIndexToModel(GameRater.gameTable.getSelectedRow());
			GameRater.tblmodel.setValueAt(platformDropDown.getSelectedItem(), inx, 2);
			GameRater.tblmodel.setValueAt(new Date(), inx, 3);

			cl1.next(cardLayoutPlatformPanel1);
			cl2.next(cardLayoutPlatformPanel2);
		}
		else if ("SaveSynopsis".equals(e.getActionCommand()))
		{
			CardLayout cl1 = (CardLayout)(cardLayoutSynopsisPanel1.getLayout());
			CardLayout cl2 = (CardLayout)(cardLayoutSynopsisPanel2.getLayout());

			viewSynopsisLabel.setText(synopsisTextField.getText());

			int inx = GameRater.gameTable.convertRowIndexToModel(GameRater.gameTable.getSelectedRow());
			GameRater.tblmodel.setValueAt(synopsisTextField.getText(), inx, 4);
			GameRater.tblmodel.setValueAt(new Date(), inx, 3);

			cl1.next(cardLayoutSynopsisPanel1);
			cl2.next(cardLayoutSynopsisPanel2);
		}
		else if ("SaveReview".equals(e.getActionCommand()))
		{
			CardLayout cl1 = (CardLayout)(cardLayoutReviewPanel1.getLayout());
			CardLayout cl2 = (CardLayout)(cardLayoutReviewPanel2.getLayout());

			reviewTextPane.setText(editor.getText());

			int inx = GameRater.gameTable.convertRowIndexToModel(GameRater.gameTable.getSelectedRow());
			GameRater.tblmodel.setValueAt(editor.getText(), inx, 5);
			GameRater.tblmodel.setValueAt(new Date(), inx, 3);

			cl1.next(cardLayoutReviewPanel1);
			cl2.next(cardLayoutReviewPanel2);
		}
		else if ("CancelTitle".equals(e.getActionCommand()))
		{
			CardLayout cl1 = (CardLayout)(cardLayoutTitlePanel1.getLayout());
			CardLayout cl2 = (CardLayout)(cardLayoutTitlePanel2.getLayout());

			gameTitleTextField.setText(gameTitleLabel.getText());
			validationLabel.setText("");

			cl1.next(cardLayoutTitlePanel1);
			cl2.next(cardLayoutTitlePanel2);
		}
		else if ("CancelRating".equals(e.getActionCommand()))
		{
			CardLayout cl1 = (CardLayout)(cardLayoutRatingPanel1.getLayout());
			CardLayout cl2 = (CardLayout)(cardLayoutRatingPanel2.getLayout());

			slider.setValue(Integer.parseInt(viewRatingLabel.getText()));

			cl1.next(cardLayoutRatingPanel1);
			cl2.next(cardLayoutRatingPanel2);
		}
		else if ("CancelPlatform".equals(e.getActionCommand()))
		{
			CardLayout cl1 = (CardLayout)(cardLayoutPlatformPanel1.getLayout());
			CardLayout cl2 = (CardLayout)(cardLayoutPlatformPanel2.getLayout());

			platformDropDown.setSelectedItem(viewPlatformLabel.getText());

			cl1.next(cardLayoutPlatformPanel1);
			cl2.next(cardLayoutPlatformPanel2);
		}
		else if ("CancelSynopsis".equals(e.getActionCommand()))
		{
			CardLayout cl1 = (CardLayout)(cardLayoutSynopsisPanel1.getLayout());
			CardLayout cl2 = (CardLayout)(cardLayoutSynopsisPanel2.getLayout());

			synopsisTextField.setText(viewSynopsisLabel.getText());

			cl1.next(cardLayoutSynopsisPanel1);
			cl2.next(cardLayoutSynopsisPanel2);
		}
		else if ("CancelReview".equals(e.getActionCommand()))
		{
			CardLayout cl1 = (CardLayout)(cardLayoutReviewPanel1.getLayout());
			CardLayout cl2 = (CardLayout)(cardLayoutReviewPanel2.getLayout());

			cl1.next(cardLayoutReviewPanel1);
			cl2.next(cardLayoutReviewPanel2);
		}
		else if ("Back".equals(e.getActionCommand()))
		{	
			canClose = true;
			if (gameTitleTextField.getText().length() == 0)
			{
				validationLabel.setText("Please enter a game title");
				canClose = false;
			}

			if (canClose)
			{
				ViewDialog.this.setVisible(false);
			}

		}
		else if ("Platform".equals(e.getActionCommand()))
		{
			if (platformDropDown.getSelectedIndex() == platformDropDown.getModel().getSize() - 1)
			{
				addPlatformDialog.setLocationRelativeTo(this);
				addPlatformDialog.setVisible(true);
			}
		}

		if (e.getActionCommand().contains("Edit"))
			EnableEditBtns(e.getActionCommand().replace("Edit",""), false);
		else if (e.getActionCommand().contains("Save") || e.getActionCommand().contains("Cancel"))
			EnableEditBtns(e.getActionCommand().replace("Save","").replace("Cancel",""), true);		

	}

	private void EnableEditBtns(String cmd, boolean action)
	{
		switch (cmd) 
		{
		case "Title":
			editPlatformButton.setEnabled(action);
			editRatingButton.setEnabled(action);
			editSynopsisButton.setEnabled(action);
			editReviewButton.setEnabled(action);
			break;
		case "Platform":
			editTitleButton.setEnabled(action);
			editRatingButton.setEnabled(action);
			editSynopsisButton.setEnabled(action);
			editReviewButton.setEnabled(action);
			break;
		case "Rating":
			editTitleButton.setEnabled(action);
			editPlatformButton.setEnabled(action);
			editSynopsisButton.setEnabled(action);
			editReviewButton.setEnabled(action);
			break;
		case "Synopsis":
			editTitleButton.setEnabled(action);
			editPlatformButton.setEnabled(action);
			editRatingButton.setEnabled(action);
			editReviewButton.setEnabled(action);
			break;
		case "Review":
			editTitleButton.setEnabled(action);
			editPlatformButton.setEnabled(action);
			editRatingButton.setEnabled(action);
			editSynopsisButton.setEnabled(action);
			break;
		}		
	}

}
