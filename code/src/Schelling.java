import java.util.ArrayList;

public class Schelling extends CellularAutomaton {
	
	private int threshold;
	private ArrayList<Cell> vacantHousing;


	public Schelling(int n, int m, int states, int threshold) {
		this(n, m, states,threshold, 0);
	}

	public Schelling(int n, int m, int states, int threshold, int defaultState) {
		super(n, m, states, defaultState);

		this.threshold = threshold;
		vacantHousing = new ArrayList<Cell>();
		
		//initCellsAndNextCells();
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
	
	public class VacantHousingException extends Exception{

		public VacantHousingException(){
			System.out.println("Not enough vacant housing");
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
	
	@Override
	public void reset() {
		super.reset();
		initVacantHousing();
	}

	public String toString() {
		String str = new String("Schelling("+n+", "+m+")\n");

		for (Cell c : initialCells) {
			str += c.x+ ", " + c.y+ ", " + c.state + "\n";
		}

		return str;
	}
}
