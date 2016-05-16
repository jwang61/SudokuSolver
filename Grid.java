import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Grid {
	int [][] grid = new int [9][9];
	
	public Grid(Grid g0){
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				this.grid[i][j] = g0.grid[i][j];
			}
		}
	}
	
	public Grid(int[][] grid){
		this.grid = grid;
	}
	//Creates grid from file
	public Grid(String fileName){
		try 
		{
			FileReader read = new FileReader(fileName);
			BufferedReader fileIn = new BufferedReader(read);
			for (int i = 0; i < 9; i++){
				String line = fileIn.readLine();
				if (line.length() != 9 || line == null){
					fileIn.close();
					throw new IOException("File not correct");
				}
				for (int j = 0; j < 9; j++)
				{
					int n = line.charAt(j) - '0';
					if (n > 9 || n < 0){
						fileIn.close();
						throw new IOException("File not correct");
					}
					grid[i][j] = n;		
				}			
			}
			fileIn.close();		
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("Unable to find file");
		}
		catch (IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
	
	
	public void printGrid(){
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				if (grid[i][j] != 0){
					System.out.print(grid[i][j]);
				}
				else{
					System.out.print('_');
				}
			}
			System.out.println();
		}
		System.out.println("\n\n");
	}
	
	public boolean isSolved(){
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				if (grid[i][j] == 0)
					return false;
			}
		}
		return true;
	}
	
	public int[] getRow(int rowNum){
		return grid[rowNum];
	}
	
	public int[] getCol(int colNum){
		int[] col = new int[9];
		for (int i = 0; i < 9; i++){
			col[i] = grid[i][colNum];
		}
		return col;
	}
	
	public int[] getSquare(int row, int col){
		int[] square = new int[9];
		row /= 3;
		col /= 3;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j ++){
				square[i*3 + j] = grid[row*3 + i][col*3 + j];
			}
		return square;
	}
}
