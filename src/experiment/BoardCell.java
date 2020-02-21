package experiment;

public class BoardCell {
	private int row;
	private int column;
	private int num;
	
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
		this.num = num;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	public int getNum() {
		return num;
	}
	
	
}
