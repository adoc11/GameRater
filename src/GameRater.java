import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.inet.editor.BaseEditor;

public class GameRater extends JFrame {

	public static GameRater frame;
	public static Vector<GameData> gameDataList = new Vector<GameData>();
	public static JTable gameTable;
	public static DefaultTableModel tblmodel;

	private JPanel contentPane;
	private HintTextField searchField;
	private JButton addButton;
	private JButton viewButton;
	private JButton removeButton;

	private JDialog viewDialogWindow;
	private JDialog changeDialogWindow;
	private JDialog addDialogWindow;
	private TableRowSorter<DefaultTableModel> sorter;
	private BaseEditor editor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					frame = new GameRater();
					frame.setLocationRelativeTo( null );
					frame.setVisible(true);
					frame.requestFocusInWindow();

					WindowListener wl= new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e)
						{
							ReadWriteCSV rw = new ReadWriteCSV();
							String filename = "data/GameData.csv";
							rw.WriteGameData(filename);
							System.exit(0);
						}
					};
					frame.addWindowListener(wl);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public class myTableSelectionListener implements ListSelectionListener 
	{
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			tblmodel = (DefaultTableModel) gameTable.getModel();
			if(e.getValueIsAdjusting() == false)
			{

				if (gameTable.getSelectedRow() >= 0)
				{
					removeButton.setEnabled(true);
					viewButton.setEnabled(true);
				}
				else
				{
					viewButton.setEnabled(false);
					removeButton.setEnabled(false);
				}

				if (gameTable.getSelectedRowCount() > 1)
				{
					viewButton.setEnabled(false);
				}
			}
		}
	}

	public class AddViewRemoveListener implements ActionListener
	{
		//Determine which button has been pressed and what action to take when that happens
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if ("Add".equals(e.getActionCommand()))
			{
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				addDialogWindow = new AddGameDialog();
				addDialogWindow.setLocationRelativeTo(frame);
				addDialogWindow.setVisible(true);

			}
			else if ("View".equals(e.getActionCommand()))
			{
				int inx = gameTable.convertRowIndexToModel(gameTable.getSelectedRow());
				Object game = tblmodel.getDataVector().elementAt(inx);

				frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				viewDialogWindow = new ViewDialog();
				viewDialogWindow.setLocationRelativeTo(frame);
				viewDialogWindow.setVisible(true);
			}
			else if ("Remove".equals(e.getActionCommand()))
			{
				removeGames();
			}
		}
	}

	public class SearchListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if ("Search".equals(e.getActionCommand()))
			{
				String text = searchField.getText();
				//If current expression doesn't parse, don't update.
				if (text.length() == 0) 
					sorter.setRowFilter(null);
				else 
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0));

			}
		}
	}

	private void removeGames()
	{
		int[] selectedRows = gameTable.getSelectedRows();
		int inx;

		Arrays.sort(selectedRows);
		for (int i = selectedRows.length - 1; i >= 0; i--) {
			inx = gameTable.convertRowIndexToModel(selectedRows[i]);

			tblmodel.removeRow(inx);
		}
	}


	/**
	 * Create the frame.
	 */
	public GameRater() {
		setTitle("GameRater");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 603, 515);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel masterPanel = new JPanel();
		masterPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
		contentPane.add(masterPanel, BorderLayout.CENTER);
		masterPanel.setLayout(new BorderLayout(0, 0));

		JPanel searchPanel = new JPanel();
		masterPanel.add(searchPanel, BorderLayout.NORTH);
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));

		searchField = new HintTextField();
		searchField.setHint("Search");
		searchField.setAlignmentX(Component.LEFT_ALIGNMENT);
		searchPanel.add(searchField);
		searchField.setColumns(10);

		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		searchPanel.add(rigidArea);

		JButton searchButton = new JButton("Search");
		searchButton.setActionCommand("Search");
		searchButton.addActionListener(new SearchListener());
		searchPanel.add(searchButton);
		
		searchPanel.getRootPane().setDefaultButton(searchButton);

		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new EmptyBorder(10, 0, 15, 0));
		masterPanel.add(tablePanel, BorderLayout.CENTER);
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS));

		gameTable = new JTable();

		tablePanel.add(new JScrollPane(gameTable));

		ReadWriteCSV rw = new ReadWriteCSV();
		String filename = "data/GameData.csv";
		final Vector<GameData> gameDataList = rw.ReadGameData(filename);

		gameTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		//gameTable.setCellSelectionEnabled(true);
		gameTable.setRowSelectionAllowed(true);
		gameTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		gameTable.setModel(new DefaultTableModel() 
		{
			Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, Date.class, String.class, String.class
			};
			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false
			};
			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

		});

		tblmodel = (DefaultTableModel) gameTable.getModel();	
		for (int j = 0; j < GameData.gameLabels.length; j++)
		{
			tblmodel.addColumn(GameData.gameLabels[j]); 
		}
		for (int i = 0; i < gameDataList.size(); i++)
		{
			tblmodel.addRow(gameDataList.elementAt(i).gameData);
		}

		sorter = new TableRowSorter<DefaultTableModel>(tblmodel);
		gameTable.setRowSorter(sorter);
		gameTable.getColumnModel().getColumn(0).setPreferredWidth(90);
		gameTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		gameTable.getColumnModel().getColumn(4).setPreferredWidth(200);
		gameTable.getColumnModel().removeColumn(gameTable.getColumn("Hidden"));
		
		gameTable.getRowSorter().toggleSortOrder(3); 
		gameTable.getRowSorter().toggleSortOrder(3);

		gameTable.getSelectionModel().addListSelectionListener(new myTableSelectionListener());
		gameTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				JTable table =(JTable) me.getSource();
				int row = table.rowAtPoint(me.getPoint());
				int col = table.columnAtPoint(me.getPoint()); 
				String value = "";
				boolean isReviewDialog = false;

				if (col != 3)
					value = (String)table.getValueAt(row, col);

				if (me.getClickCount() == 2) {
					switch (col)
					{
					case 0:
						changeDialogWindow = new GameTitleDialog(value);
						break;
					case 1:
						changeDialogWindow = new RatingDialog(Integer.parseInt(value));
						break;
					case 2:
						changeDialogWindow = new PlatformDialog(value);
						break;
					case 3:
						changeDialogWindow = null;  // Date is automatically set by the app
						break;
					case 4:
						isReviewDialog = true;
						table.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						int inx = gameTable.convertRowIndexToModel(gameTable.getSelectedRow());
						String review = (String)tblmodel.getValueAt(inx, 5);
						changeDialogWindow = new ReviewDialog(value, review);
						break;
					}

					if (changeDialogWindow != null)
					{
						//if(!isReviewDialog) 
						changeDialogWindow.setLocationRelativeTo(frame);

						changeDialogWindow.setVisible(true);	        		
					}
				}
			}
		});


		JPanel buttonPanel = new JPanel();
		masterPanel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		Component horizontalGlue = Box.createHorizontalGlue();
		buttonPanel.add(horizontalGlue);

		addButton = new JButton("Add");
		addButton.setActionCommand("Add");
		addButton.addActionListener(new AddViewRemoveListener());
		buttonPanel.add(addButton);

		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		buttonPanel.add(rigidArea_1);

		viewButton = new JButton("View");
		viewButton.setActionCommand("View");
		viewButton.addActionListener(new AddViewRemoveListener());
		buttonPanel.add(viewButton);
		viewButton.setEnabled(false);

		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		buttonPanel.add(rigidArea_2);

		removeButton = new JButton("Remove");
		removeButton.setActionCommand("Remove");
		removeButton.addActionListener(new AddViewRemoveListener());
		buttonPanel.add(removeButton);
		removeButton.setEnabled(false);

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		buttonPanel.add(horizontalGlue_1);
	}
}
