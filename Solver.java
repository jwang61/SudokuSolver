import java.util.ArrayList;

public class Solver {
	public Grid grid;
	private SolutionGrid solution;

	public Solver(String fileName){
		solution = new SolutionGrid();
		grid = new Grid(fileName);
	}
	
	public Solver(int[][] grid){
		solution = new SolutionGrid();
		this.grid = new Grid(grid);
	}

	public Solver(Grid g0, SolutionGrid sg0){
		grid = new Grid(g0);
		solution = new SolutionGrid(sg0);
	}

	public boolean solve(){
		this.updateSolutionGrid();
		return this.makeGuess();
	}
	
	private boolean makeGuess(){
		if (this.grid.isSolved()){
			return true;
		}
		Guess guess = getSizeofTwo(); //Assumption that in an unsolved grid, there will always be a square with only two possible numbers.
		if (guess == null){
			return false;
		}
		System.out.println("Guess at " + guess.row + " " + guess.col);
		Solver solver1 = new Solver(this.grid, this.solution);
		Solver solver2 = new Solver(this.grid, this.solution);
		
		solver1.grid.grid[guess.row][guess.col] = guess.posNum.get(0);
		solver2.grid.grid[guess.row][guess.col] = guess.posNum.get(1);

		if (solver1.updateSolutionGrid()){
			System.out.println("SOLVER 1");
			solver1.grid.printGrid();
			if (solver1.makeGuess()){
				System.out.println("SOLVED");
				this.grid = solver1.grid;
				return true;
			}
		}
		else{
			System.out.println("****SOLVER 1 FAILED****");
		}	
		
		if (solver2.updateSolutionGrid()){
			System.out.println("SOLVER 2");
			solver2.grid.printGrid();
			if (solver2.makeGuess()){
				System.out.println("SOLVED");
				this.grid = solver2.grid;
				return true;
			}
		}
		else{
			System.out.println("****SOLVER 2 FAILED****");
		}
		return false;
	}

	public Guess getSizeofTwo(){
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9 ; j++)
				if (grid.grid[i][j] == 0){
					ArrayList<Integer> posNum = solution.getPossibleNums(i,j);
					if (posNum.size() == 2)
						return new Guess(posNum, i, j);
				}
		return null;
	}

	public boolean updateSolutionGrid(){
		boolean change;
		do{
			change = false;
			for (int i = 0; i < 9; i++){
				for (int j = 0; j < 9; j++){
					if (grid.grid[i][j] == 0){
						setPossibleNumbers(i,j);
						ArrayList<Integer> posNum= solution.getPossibleNums(i,j);
						if (posNum.size() == 0){
							return false;
						}
						if (posNum.size() == 1){
							System.out.println(String.format("%1$d at %2$d %3$d", posNum.get(0), i, j));
							grid.grid[i][j] = posNum.get(0);
							change = true;
						}
					}
				}
			}
		}while(change);
		return true;
	}

	public void setPossibleNumbers(int row, int col){
		boolean[] posNum = solution.getPossibleBools(row, col);
		int [] rows = grid.getRow(row);
		for (int i = 0; i < 9; i++){
			if (rows[i]!=0 && posNum[rows[i]-1]){
				posNum[rows[i]-1] = false;
			}
		}
		int [] cols = grid.getCol(col);
		for (int i = 0; i < 9; i++){
			if (cols[i]!=0 && posNum[cols[i]-1]){
				posNum[cols[i]-1] = false;
			}
		}
		int []square = grid.getSquare(row, col);
		for (int i = 0; i < 9; i++){
			if (square[i]!=0 && posNum[square[i]-1]){
				posNum[square[i]-1] = false;
			}
		}		
	}

	private class SolutionGrid{
		private boolean[][][] sg = new boolean[9][9][9];
		public SolutionGrid(){
			for (int i = 0; i < 9; i++){
				for (int j = 0; j < 9; j++){	
					for (int k = 0; k < 9; k++){
						sg[i][j][k] = true;
					}
				}
			}
		}

		public SolutionGrid(SolutionGrid sg0){
			for (int i = 0; i < 9; i++){
				for (int j = 0; j < 9; j++){	
					for (int k = 0; k < 9; k++){
						this.sg[i][j][k] = sg0.sg[i][j][k];
					}
				}
			}
		}

		public boolean[] getPossibleBools(int row, int col){
			return sg[row][col];
		}	

		public ArrayList<Integer> getPossibleNums(int row, int col){
			ArrayList <Integer> posNum = new ArrayList<Integer>();
			for (int i = 0; i < 9; i++){
				if (sg[row][col][i])
					posNum.add(i+1);
			}
			return posNum;
		}
	}
	
	class Guess{
		public ArrayList<Integer> posNum;
		public int row;
		public int col;
		
		public Guess(ArrayList<Integer> posNum, int row, int col){
			this.posNum = posNum;
			this.row = row;
			this.col = col;
		}
	}
}
