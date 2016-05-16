import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

public class SolverUI {

	private JFrame mainFrame;
	private JPanel sudokuPanel = new JPanel(new GridLayout(3,3));
	private JPanel[][] gridBorders = new JPanel[3][3];
	private JPanel[][] gridPanels = new JPanel[3][3];
	private JTextField[][] sudokuFields = new JTextField[9][9];
	private JPanel gui;
	private Solver solver;

	public SolverUI() {
		prepareGUI();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Sudoku Solver");
		mainFrame.setSize(400, 400);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gui = new JPanel(new BorderLayout(10,10));
        gui.setBorder(new TitledBorder("Sudoku Solver"));
		mainFrame.setContentPane(gui);

        JLabel headerLabel = new JLabel("Welcome to Sudoku Solver", JLabel.CENTER);
        gui.add(headerLabel, BorderLayout.NORTH); 
        
        for (int i = 0; i < 3; i++)
        	for (int j = 0; j < 3; j++){
        		gridBorders[i][j] = new JPanel(new BorderLayout(5,5));
        		gridBorders[i][j].setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
        		gridPanels[i][j] = new JPanel(new GridLayout(3,3));
        		sudokuPanel.add(gridBorders[i][j]);
        		gridBorders[i][j].add(gridPanels[i][j]);
        	}
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				sudokuFields[i][j] = new JTextField(3);
				gridPanels[i/3][j/3].add(sudokuFields[i][j]);
			}
		gui.add(sudokuPanel, BorderLayout.CENTER);
		
		JButton solveButton = new JButton("Solve Sudoku!");
		solveButton.addActionListener(new ButtonClicked());
		gui.add(solveButton, BorderLayout.SOUTH);
		
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	private Boolean tryParseInt(String text) {
		try {
			int value = Integer.parseInt(text);
			return (value > 0 && value < 10);
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	private void updateGrid(){
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				sudokuFields[i][j].setText(String.valueOf(solver.grid.grid[i][j]));
			}
		}
	}
	
	private class ButtonClicked implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			int [][] sudokuGrid = new int[9][9];
			for (int i = 0; i < 9; i++)
				for (int j = 0; j < 9; j++) {
					String text = sudokuFields[i][j].getText();
					if (text == null || text.isEmpty()){
						sudokuGrid[i][j] = 0;
					}
					else
					{
						if (!tryParseInt(text)){
							JOptionPane.showMessageDialog(null, "Please enter number for each textbox.");
							return;
						}
						else{
							sudokuGrid[i][j] = Integer.parseInt(text);
						}
					}
				}
			solver = new Solver(sudokuGrid);
			if (solver.solve()){
				JOptionPane.showMessageDialog(null, "Sudoku Solved!");
				updateGrid();
			}
			else{
				JOptionPane.showMessageDialog(null, "Unable to solve sudoku");
				return;
			}
		}
	}
}
