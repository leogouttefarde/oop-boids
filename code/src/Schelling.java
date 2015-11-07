import java.util.ArrayList;
import java.util.Vector;

public class Schelling extends Conway {

	
	private int defaultState;
	private int states;
	private int threshold;
	private int cells[][];
	private int nextCells[][];
	private Vector<Cell> initialCells;
	private ArrayList<Cell> vacantHousing;


	public Schelling(int n, int m, int states, int threshold) {
		this(n, m, states,threshold, 0);
	}

	public Schelling(int n, int m, int states, int threshold, int defaultState) {
		super(n, m);

		this.states = states;
		this.defaultState = defaultState;
		this.threshold = threshold;
		cells = new int[n][m];
		nextCells = new int[n][m];
		initialCells = new Vector<Cell>();
		vacantHousing = new ArrayList<Cell>();
		
		initCellsAndNextCells();
	}

	private void initCellsAndNextCells(){
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				cells[x][y] = defaultState;
				nextCells[x][y] = defaultState;
			}
		}
	}
	public int[][] getCells() {
		return cells;
	}

	public void addCell(int x, int y, int state) {
		initialCells.add(new Cell(x, y, state));
		cells[x][y] = state;
	}
	
	public void initVacantHousing(){
		vacantHousing.clear();
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				if(cells[x][y] == defaultState)
					vacantHousing.add(new Cell(x, y, defaultState));
			}
		}
	}

	public void nextGeneration() {
		int nbNeighbors[] = new int[this.states];

		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				nextCells[x][y] = cells[x][y];
			}
		}
		
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				final int cellState = cells[x][y];
				
				// k = 0 => vacant housing
				for(int k = 1; k < this.states; k++){
					nbNeighbors[k] = 0;
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {

							if (i != 1 || j != 1) {
								int nx = getNeighbor(x, i, n);
								int ny = getNeighbor(y, j, m);

								//System.out.println(x+", "+y + ", "+k + ", "+i + ", "+j + " :  "+ nx+", "+ny+", "+cells[nx][ny]);
								if (cells[nx][ny] == k)
									nbNeighbors[k]++;
							}
						}
					}
				}
				// k = 0 => vacant housing
				int k = 1;
				while(k != this.states && nbNeighbors[k] < this.threshold)
					k++;
				
				if(k < this.states && k != cellState && cellState != defaultState && nbNeighbors[k] >= this.threshold){
					System.out.println(x + " " + y);
					nextCells[x][y] = defaultState;
					vacantHousing.add(new Cell(x, y, defaultState));
					if(!vacantHousing.isEmpty()){
						System.out.println(vacantHousing.get(0));
						Cell c = vacantHousing.remove(0);
						System.out.println(vacantHousing.get(0));
						//System.out.println(c.x + " " + c.y);
						nextCells[c.x][c.y] = cellState;
					} 
					else {
						try {
							throw new VacantHousingException();
						} catch (VacantHousingException e) {}		
					}
				}
			}
		}
	}

	public void finishGeneration() {
		int tmp[][] = cells;
		cells = nextCells;
		nextCells = tmp;
	}

	public void reset() {
		initCellsAndNextCells();
		
		for (Cell c : initialCells) {
			// System.out.println(c.x+", "+c.y);
			cells[c.x][c.y] = c.state;
		}
		initVacantHousing();
	}

	public String toString() {
		String str = new String("Schelling("+n+", "+m+")\n");

		for (Cell c : initialCells) {
			str += c.x+ ", " + c.y+ ", " + c.state + "\n";
		}

		return str;
	}


	public class Cell {
		public int x;
		public int y;
		public int state;

		public Cell(int x, int y, int state) {
			this.x = x;
			this.y = y;
			this.state = state;
		}

	}
	
	public class VacantHousingException extends Exception{

		public VacantHousingException(){
			System.out.println("Not enough vacant housing");
		}
	}
}
