import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JToolBar;

import java.awt.Dimension;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JEditorPane;

import com.inet.editor.BaseEditor;

import java.awt.Color;


public class ViewDialog extends JDialog implements ActionListener, ClosePlatformDialogEvent {

	private final JPanel contentPanel = new JPanel();
	private JTextField gameTitleTextField;
	private JComboBox platformDropDown;
	private JDialog addPlatformDialog;
	private JSlider slider;
	private JLabel gameTitleLabel;
	private JLabel viewPlatformLabel;
	private JLabel viewRatingLabel;
	private JLabel validationLabel;
	private JLabel viewSynopsisLabel;
	private JLabel viewReviewLabel;
	private JTextField synopsisTextField;
	
	private BaseEditor editor;
	
	private Vector<String> platforms;
	private ReadWriteCSV rw = new ReadWriteCSV();
	private String filename = "data/platforms.csv";
	
	private JPanel cardLayoutTitlePanel1;
	private JPanel viewTitlePanel;
	private JPanel changeTitlePanel;
	
	private JPanel cardLayoutTitlePanel2;
	private JPanel editTitlePanelCard1;
	private JPanel saveCancelTitlePanelCard2;
	
	private JPanel cardLayoutPlatformPanel1;
	private JPanel cardLayoutPlatformPanel2;
	
	private JPanel cardLayoutRatingPanel1;
	private JPanel cardLayoutRatingPanel2;
	
	private JPanel cardLayoutSynopsisPanel1;
	private JPanel viewSynopsisPanel;
	private JPanel changeSynopsisPanel;
	
	private JPanel cardLayoutSynopsisPanel2;
	
	private JPanel cardLayoutReviewPanel1;
	private JPanel cardLayoutReviewPanel2;
	
	/**
	 * Create the dialog.
	 */
	public ViewDialog() {
		setBounds(100, 100, 655, 444);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		int row = GameRater.gameTable.convertRowIndexToModel(GameRater.gameTable.getSelectedRow());

		String title = GameRater.tblmodel.getValueAt(row, 0).toString();
		int rating = Integer.parseInt(GameRater.tblmodel.getValueAt(row, 1).toString());
		String platform = GameRater.tblmodel.getValueAt(row, 2).toString();
		String synopsis = GameRater.tblmodel.getValueAt(row, 4).toString();
		String review = GameRater.tblmodel.getValueAt(row, 5).toString();
		
		setTitle(title);
		
		JPanel masterPanel = new JPanel();
		masterPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		contentPanel.add(masterPanel);
		masterPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel formatPanel = new JPanel();
		formatPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		masterPanel.add(formatPanel, BorderLayout.CENTER);
		formatPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JLabel titleLabel = new JLabel("Title:");
		titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		formatPanel.add(titleLabel);
		
		cardLayoutTitlePanel1 = new JPanel();
		formatPanel.add(cardLayoutTitlePanel1);
		cardLayoutTitlePanel1.setLayout(new CardLayout(0, 0));
		
		viewTitlePanel = new JPanel();
		viewTitlePanel.setBorder(new EmptyBorder(0, 5, 0, 0));
		cardLayoutTitlePanel1.add(viewTitlePanel, "name_245945827916475");
		viewTitlePanel.setLayout(new BoxLayout(viewTitlePanel, BoxLayout.X_AXIS));
		
		gameTitleLabel = new JLabel("");
		gameTitleLabel.setText(title);
		gameTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		viewTitlePanel.add(gameTitleLabel);
		
		changeTitlePanel = new JPanel();
		cardLayoutTitlePanel1.add(changeTitlePanel, "name_245957718807996");
		changeTitlePanel.setLayout(new BoxLayout(changeTitlePanel, BoxLayout.Y_AXIS));
		
		Component verticalStrut_1 = Box.createVerticalStrut(25);
		changeTitlePanel.add(verticalStrut_1);
		
		gameTitleTextField = new JTextField();
		changeTitlePanel.add(gameTitleTextField);
		gameTitleTextField.setColumns(10);
		gameTitleTextField.setText(title);
		
		Component verticalStrut_2 = Box.createVerticalStrut(25);
		changeTitlePanel.add(verticalStrut_2);
		
		cardLayoutTitlePanel2 = new JPanel();
		cardLayoutTitlePanel2.setBorder(new EmptyBorder(0, 5, 0, 0));
		formatPanel.add(cardLayoutTitlePanel2);
		cardLayoutTitlePanel2.setLayout(new CardLayout(0, 0));
		
		editTitlePanelCard1 = new JPanel();
		cardLayoutTitlePanel2.add(editTitlePanelCard1, "name_245283698969319");
		editTitlePanelCard1.setLayout(new BoxLayout(editTitlePanelCard1, BoxLayout.X_AXIS));
		
		JButton editTitleButton = new JButton("Edit");
		editTitleButton.setActionCommand("EditTitle");
		editTitleButton.addActionListener(this);
		editTitlePanelCard1.add(editTitleButton);
		
		JPanel panel = new JPanel();
		cardLayoutTitlePanel2.add(panel, "name_725546439272519");
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Component verticalStrut_3 = Box.createVerticalStrut(25);
		panel.add(verticalStrut_3);
		
		saveCancelTitlePanelCard2 = new JPanel();
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
		
		Component verticalStrut_4 = Box.createVerticalStrut(5);
		panel.add(verticalStrut_4);
		
		validationLabel = new JLabel("");
		validationLabel.setAlignmentX(0.5f);
		validationLabel.setForeground(Color.RED);
		panel.add(validationLabel);
		
		JLabel platformLabel = new JLabel("Platform:");
		platformLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		formatPanel.add(platformLabel);
		
		cardLayoutPlatformPanel1 = new JPanel();
		formatPanel.add(cardLayoutPlatformPanel1);
		cardLayoutPlatformPanel1.setLayout(new CardLayout(0, 0));
		
		JPanel viewPlatformPanel = new JPanel();
		viewPlatformPanel.setBorder(new EmptyBorder(0, 5, 0, 0));
		cardLayoutPlatformPanel1.add(viewPlatformPanel, "name_246549987849649");
		viewPlatformPanel.setLayout(new BoxLayout(viewPlatformPanel, BoxLayout.X_AXIS));
		
		viewPlatformLabel = new JLabel("");
		viewPlatformLabel.setText(platform);
		viewPlatformPanel.add(viewPlatformLabel);
		
		JPanel changePlatformPanel = new JPanel();
		changePlatformPanel.setBorder(new EmptyBorder(0, 5, 0, 0));
		cardLayoutPlatformPanel1.add(changePlatformPanel, "name_246754779627284");
		changePlatformPanel.setLayout(new BoxLayout(changePlatformPanel, BoxLayout.Y_AXIS));
		
		platformDropDown = new JComboBox();
		platforms = rw.loadPlatforms(filename);
		platformDropDown.setModel(new DefaultComboBoxModel(platforms));
		platformDropDown.addActionListener(this);
		platformDropDown.setActionCommand("Platform");
		platformDropDown.setSelectedItem(platform);
		
		addPlatformDialog = new AddPlatformDialog(platforms, this);
		
		Component verticalStrut_5 = Box.createVerticalStrut(25);
		changePlatformPanel.add(verticalStrut_5);
		
		changePlatformPanel.add(platformDropDown);
		
		Component verticalStrut_6 = Box.createVerticalStrut(25);
		changePlatformPanel.add(verticalStrut_6);
		
		cardLayoutPlatformPanel2 = new JPanel();
		cardLayoutPlatformPanel2.setBorder(new EmptyBorder(0, 5, 0, 0));
		formatPanel.add(cardLayoutPlatformPanel2);
		cardLayoutPlatformPanel2.setLayout(new CardLayout(0, 0));
		
		JPanel editPlatformPanel = new JPanel();
		cardLayoutPlatformPanel2.add(editPlatformPanel, "name_248787550264915");
		editPlatformPanel.setLayout(new BoxLayout(editPlatformPanel, BoxLayout.X_AXIS));
		
		JButton editPlatformButton = new JButton("Edit");
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
		formatPanel.add(ratingLabel);
		
		cardLayoutRatingPanel1 = new JPanel();
		cardLayoutRatingPanel1.setBorder(null);
		formatPanel.add(cardLayoutRatingPanel1);
		cardLayoutRatingPanel1.setLayout(new CardLayout(0, 0));
		
		JPanel viewRatingPanel = new JPanel();
		viewRatingPanel.setBorder(new EmptyBorder(0, 5, 0, 0));
		cardLayoutRatingPanel1.add(viewRatingPanel, "name_249343463384739");
		viewRatingPanel.setLayout(new BoxLayout(viewRatingPanel, BoxLayout.X_AXIS));
		
		viewRatingLabel = new JLabel("");
		viewRatingLabel.setText(String.valueOf(rating));
		viewRatingPanel.add(viewRatingLabel);
		
		JPanel changeRatingPanel = new JPanel();
		cardLayoutRatingPanel1.add(changeRatingPanel, "name_249399975295239");
		changeRatingPanel.setLayout(new BoxLayout(changeRatingPanel, BoxLayout.Y_AXIS));
		
		Component verticalStrut_7 = Box.createVerticalStrut(25);
		changeRatingPanel.add(verticalStrut_7);
		
		slider = new JSlider();
		slider.setMajorTickSpacing(1);
		slider.setValue(rating);
		slider.setPaintTicks(true);
		slider.setSnapToTicks(true);
		slider.setPaintLabels(true);
		slider.setMinimum(1);
		slider.setMaximum(10);
		changeRatingPanel.add(slider);
		
		cardLayoutRatingPanel2 = new JPanel();
		cardLayoutRatingPanel2.setBorder(new EmptyBorder(0, 5, 0, 0));
		formatPanel.add(cardLayoutRatingPanel2);
		cardLayoutRatingPanel2.setLayout(new CardLayout(0, 0));
		
		JPanel editRatingPanel = new JPanel();
		cardLayoutRatingPanel2.add(editRatingPanel, "name_249808573410939");
		editRatingPanel.setLayout(new BoxLayout(editRatingPanel, BoxLayout.X_AXIS));
		
		JButton editRatingButton = new JButton("Edit");
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
		
		JLabel synopsisLabel = new JLabel("Synopsis");
		synopsisLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		formatPanel.add(synopsisLabel);
		
		cardLayoutSynopsisPanel1 = new JPanel();
		formatPanel.add(cardLayoutSynopsisPanel1);
		cardLayoutSynopsisPanel1.setLayout(new CardLayout(0, 0));
		
		viewSynopsisPanel = new JPanel();
		viewSynopsisPanel.setBorder(new EmptyBorder(0, 5, 0, 0));
		cardLayoutSynopsisPanel1.add(viewSynopsisPanel);
		viewSynopsisPanel.setLayout(new BoxLayout(viewSynopsisPanel, BoxLayout.X_AXIS));
		
		viewSynopsisLabel = new JLabel();
		viewSynopsisLabel.setText(synopsis);
		viewSynopsisPanel.add(viewSynopsisLabel);
		
		changeSynopsisPanel = new JPanel();
		changeSynopsisPanel.setBorder(new EmptyBorder(0, 5, 0, 0));
		cardLayoutSynopsisPanel1.add(changeSynopsisPanel);
		changeSynopsisPanel.setLayout(new BoxLayout(changeSynopsisPanel, BoxLayout.X_AXIS));
		
		synopsisTextField = new JTextField();
		changeSynopsisPanel.add(synopsisTextField);
		synopsisTextField.setColumns(10);
		synopsisTextField.setText(title);
		
		cardLayoutSynopsisPanel2 = new JPanel();
		cardLayoutSynopsisPanel2.setBorder(new EmptyBorder(0, 5, 0, 0));
		formatPanel.add(cardLayoutSynopsisPanel2);
		cardLayoutSynopsisPanel2.setLayout(new CardLayout(0, 0));
		
		JPanel editSynopsisPanel = new JPanel();
		cardLayoutSynopsisPanel2.add(editSynopsisPanel);
		editSynopsisPanel.setLayout(new BoxLayout(editSynopsisPanel, BoxLayout.X_AXIS));
		
		JButton editSynopsisButton = new JButton("Edit");
		editSynopsisButton.setActionCommand("EditSnyopsis");
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
		
		JLabel reviewLabel = new JLabel("Review");
		reviewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		formatPanel.add(reviewLabel);
		
		cardLayoutReviewPanel1 = new JPanel();
		formatPanel.add(cardLayoutReviewPanel1);
		cardLayoutReviewPanel1.setLayout(new CardLayout(0, 0));
		
		JPanel viewReviewPanel = new JPanel();
		viewReviewPanel.setBorder(new EmptyBorder(0, 5, 0, 0));
		cardLayoutReviewPanel1.add(viewReviewPanel, "name_250177294129247");
		viewReviewPanel.setLayout(new BoxLayout(viewReviewPanel, BoxLayout.X_AXIS));
		
		viewReviewLabel = new JLabel();
		viewReviewLabel.setText(review);
		viewReviewPanel.add(viewReviewLabel);
		
		JPanel changeReviewPanel = new JPanel();
		changeReviewPanel.setBorder(new EmptyBorder(0, 5, 0, 0));
		cardLayoutReviewPanel1.add(changeReviewPanel, "name_254272906869826");
		changeReviewPanel.setLayout(new BoxLayout(changeReviewPanel, BoxLayout.X_AXIS));
		
		editor = new BaseEditor( true );
		editor.setText(review, true);
		changeReviewPanel.add( editor);
		JToolBar toolbar = editor.getToolbar();
		
		cardLayoutReviewPanel2 = new JPanel();
		cardLayoutReviewPanel2.setBorder(new EmptyBorder(0, 5, 0, 0));
		formatPanel.add(cardLayoutReviewPanel2);
		cardLayoutReviewPanel2.setLayout(new CardLayout(0, 0));
		
		JPanel editReviewPanel = new JPanel();
		editReviewPanel.setBorder(null);
		cardLayoutReviewPanel2.add(editReviewPanel, "name_250344470646622");
		editReviewPanel.setLayout(new BoxLayout(editReviewPanel, BoxLayout.X_AXIS));
		
		JButton editReviewButton = new JButton("Edit");
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
		masterPanel.add(backButtonPanel, BorderLayout.SOUTH);
		backButtonPanel.setLayout(new BoxLayout(backButtonPanel, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		backButtonPanel.add(verticalStrut);
		
		Component horizontalStrut = Box.createHorizontalStrut(15);
		backButtonPanel.add(horizontalStrut);
		
		JButton backButton = new JButton("Back");
		backButton.setActionCommand("Back");
		backButton.addActionListener(this);
		backButtonPanel.add(backButton);
		backButton.setHorizontalAlignment(SwingConstants.LEFT);
		
		GameRater.gameTable.setCursor(Cursor.getDefaultCursor());
	}
	
	public void closePlatformEvent(String p)
	{
		platformDropDown.setSelectedItem(p);
	}
	
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
		else if ("SaveReview".equals(e.getActionCommand()))
		{
			CardLayout cl1 = (CardLayout)(cardLayoutReviewPanel1.getLayout());
			CardLayout cl2 = (CardLayout)(cardLayoutReviewPanel2.getLayout());
			
			cl1.next(cardLayoutReviewPanel1);
			cl2.next(cardLayoutReviewPanel1);
		}
		else if ("CancelTitle".equals(e.getActionCommand()))
		{
			CardLayout cl1 = (CardLayout)(cardLayoutTitlePanel1.getLayout());
			CardLayout cl2 = (CardLayout)(cardLayoutTitlePanel2.getLayout());
			
			gameTitleTextField.setText(gameTitleLabel.getText());
			
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
				//addPlatformDialog = new AddPlatformDialog(platforms);
				addPlatformDialog.setLocationRelativeTo(this);
				addPlatformDialog.setVisible(true);
			}
		}
		
	}

}
