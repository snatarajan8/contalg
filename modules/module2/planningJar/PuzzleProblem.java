// PuzzleProblem.java
//
// Author: Rahul Simha
// Jan, 2008.
//
// This class implements the problem spec for a planning problem.
// The puzzle is the standard 8-puzzle: 8 tiles numbered 1,...,8
// in a 9-tile square. The objective is to make legal moves to
// take you from the starting position to the end configuration.
// Random configurations are generated by applying moves randomly.
// If this is done for a large enough number of iterations, the problem
// becomes difficult for an ordinary BFS/DFS algorithm.


import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;


public class PuzzleProblem extends JPanel implements PlanningProblem {

    // Constant describing the number of moves to make when creating 
    // a random instance.
    static int randomMoves = 25;

    // size x size puzzle.
    int size = 3;

    // Default start and desired configurations.
    // currentGrid[1][2] = row 1, col 2 from top left
    int[][] currentGrid = {
	    {1, 2, 3},
	    {4, 5, 6},
	    {7, 8, 0}
	};
    int[][] endGrid = {
	    {1, 0, 2},
	    {4, 5, 3},
	    {7, 8, 6}
	};

    // Plan info.
    LinkedList<State> plan;
    Iterator<State> planIterator;
    PuzzleState currentState = null;

    // GUI variables.
    Font font = new Font ("Serif", Font.BOLD, 30);
    JLabel status;



    public PuzzleProblem (JLabel status)
    {
	this.status = status;
    }


    //////////////////////////////////////////////////////////////////////
    // Screen work


    public void paintComponent (Graphics g)
    {
	super.paintComponent (g);
	Dimension D = this.getSize ();

	// Background.
	g.setColor (Color.white);
	g.fillRect (0,0, D.width, D.height);

	int cellSize = D.height/ (2*size);
	int left = cellSize, right = D.width/2;
	g.setFont (font);
	g.setColor (Color.black);

	// Draw start on left.
	if (currentGrid != null) {
	    for (int i=0; i<size; i++) {
		int y = cellSize + i*cellSize;
		for (int j=0; j<size; j++) {
		    int x = left + j*cellSize;
		    g.drawRect (x,y, cellSize, cellSize);
		    if (currentGrid[i][j] != 0) {
			g.drawString (""+currentGrid[i][j], x+cellSize/3,y+2*cellSize/3);
		    }
		}
	    }
	}

	// i=row, j=col

	// Draw end on right.
	if (endGrid != null) {
	    for (int i=0; i<size; i++) {
		int y = cellSize + i*cellSize;
		for (int j=0; j<size; j++) {
		    int x = right + j*cellSize;
		    g.drawRect (x,y, cellSize, cellSize);
		    if (endGrid[i][j] != 0) {
			g.drawString (""+endGrid[i][j], x+cellSize/3,y+2*cellSize/3);
		    }
		}
	    }
	}
	
    }

    
    //////////////////////////////////////////////////////////////////////
    // GUI construction


    public JPanel getFullPanel ()
    {
	JPanel panel = new JPanel ();
	panel.setLayout (new BorderLayout());
	panel.add (this, BorderLayout.CENTER); 
	panel.add (makeBottomPanel(), BorderLayout.SOUTH);
	return panel;
    }

    JPanel makeBottomPanel ()
    {
	JPanel panel = new JPanel ();

	JButton genB = new JButton ("Generate");
	genB.addActionListener (
		   new ActionListener ()
		   {
		       public void actionPerformed (ActionEvent a)
		       {
			   generateEnd ();
		       }
		   }
        );
	panel.add (genB);

	return panel;
    }



    //////////////////////////////////////////////////////////////////////
    // GUI events


    // The generate button.

    void generateEnd ()
    {
	endGrid = generatePuzzle ();
	this.repaint ();
    }    



    //////////////////////////////////////////////////////////////////////
    // Problem interface implementations.


    public State getStartState ()
    {
	return new PuzzleState (null, size, currentGrid);
    }



    public void setPlan (LinkedList<State> plan)
    {
	this.plan = plan;
	currentState = null;
	if (plan == null) {
	    status.setText ("No solution found by planner");
	    return;
	}
	planIterator = plan.iterator ();
    }


    public ArrayList<State> getNeighbors (State state)
    {
        return getNeighbors (state, true);
    }
    

    public boolean satisfiesGoal (State state)
    {
        // Check if given state has same config as desired end state.
        PuzzleState p = (PuzzleState) state;

	for (int i=0; i<size; i++) {
	    for (int j=0; j<size; j++) {
		if (p.grid[i][j] != endGrid[i][j]) {
		    return false;
		}
	    }
	}
	return true;
    }


    public void drawState (State state)
    {
        // Hard to draw intermediate states => do nothing.
    }


    // The next button - show next state in plan.

    public void next ()
    {
	if ( (planIterator == null) || (!planIterator.hasNext()) ) {
	    status.setText ("No more states left in plan");
	    return;
	}

	PuzzleState state = (PuzzleState) planIterator.next ();
	
	if (! isValid (state) ) {
	    status.setText ("ERROR: not a valid state");
	}

	if (currentState != null) {
	    // Check if neighbors.
	    if (! areNeighbors (currentState, state)) {
		status.setText ("ERROR: not a neighboring state");
	    }
	}
	currentState = state;

	currentGrid = state.grid;
	this.repaint ();
    }


    //////////////////////////////////////////////////////////////////////
    // Utility methods



    boolean isValid (PuzzleState m)
    {
	// Check size, containment of all digits.
	if (m.size != size) {
	    return false;
	}
	if (m.grid.length != size) {
	    return false;
	}

        // See if every digit is contained.
	for (int k=0; k<size*size; k++) {
	    // Check for occurence of k.
	    boolean found = false;
	    for (int i=0; i<size; i++) {
		for (int j=0; j<size; j++) {
		    if (m.grid[i][j] == k) {
			found = true;
		    }
		}
	    }
	    if (! found) {
		return false;
	    }
	}

	return true;
    }


    boolean areNeighbors (PuzzleState p1, PuzzleState p2) 
    {
	// First identify locations of the blank (0) in each.
	int m1 = -1,   n1 = -1;
	for (int i=0; i<size; i++) {
	    for (int j=0; j<size; j++) {
		if (p1.grid[i][j] == 0) {
		    m1 = i;   n1 = j;
		}
	    }
	}

        // The blank tile in the second one.
	int m2 = -1,   n2 = -1;
	for (int i=0; i<size; i++) {
	    for (int j=0; j<size; j++) {
		if (p2.grid[i][j] == 0) {
		    m2 = i;   n2 = j;
		}
	    }
	}

	// First check if they are neighbors.
	if ( ! areNeighbors (m1,n1, m2,n2) ) {
	    return false;
	}

	// Next, check that other elements are the same.
	for (int i=0; i<size; i++) {
	    for (int j=0; j<size; j++) {
		if ( (i != m1) && (i != m2) && (j != n1) && (j != n2) ) {
		    if (p1.grid[i][j] != p2.grid[i][j]) {
			return false;
		    }
		}
	    }
	}

	// Finally, check that a swap occured.
	if (p1.grid[m2][n2] != p2.grid[m1][n1]) {
	    return false;
	}

	// Otherwise, it's ok.
	return true;
    }


    boolean areNeighbors (int m1, int n1, int m2, int n2)
    {
	// North.
	if ( (m1 == m2+1) && (n1 == n2) ) {
	    return true;
	}

	// South.
	if ( (m1 == m2-1) && (n1 == n2) ) {
	    return true;
	}

	// East.
	if ( (m1 == m2) && (n1 == n2+1) ) {
	    return true;
	}

	// West.
	if ( (m1 == m2) && (n1 == n2-1) ) {
	    return true;
	}

	return false;
    }


    // Note: we need two versions (indicated by the boolean "withCost" flag)
    // because the random generator uses this, but without cost.
    
    public ArrayList<State> getNeighbors (State state, boolean withCost)
    {
	PuzzleState p = (PuzzleState) state;
	ArrayList<State> neighbors = new ArrayList<State> ();
	
	// Find 0 (the space).
	int m=-1, n=-1;
	for (int i=0; i<size; i++) {
	    for (int j=0; j<size; j++) {
		if (p.grid[i][j] == 0) {
		    m = i;  n = j;
		}
	    }
	}

	// Try all locations. North.
	if (isValid (m+1, n)) {
	    int[][] nextGrid = makeGrid (p, m,n, m+1,n);
	    PuzzleState q = new PuzzleState (p, p.size, nextGrid);
            if (withCost) {
                q.costFromStart = p.costFromStart + 1;
                q.estimatedCostToGoal = goalCost (q.grid);
            }
	    neighbors.add (q);
	}

	// South.
	if (isValid (m-1, n)) {
	    int[][] nextGrid = makeGrid (p, m,n, m-1,n);
	    PuzzleState q = new PuzzleState (p, p.size, nextGrid);
            if (withCost) {
                q.costFromStart = p.costFromStart + 1;
                q.estimatedCostToGoal = goalCost (q.grid);
            }
	    neighbors.add (q);
	}

	// East
	if (isValid (m, n+1)) {
	    int[][] nextGrid = makeGrid (p, m,n, m,n+1);
	    PuzzleState q = new PuzzleState (p, p.size, nextGrid);
            if (withCost) {
                q.costFromStart = p.costFromStart + 1;
                q.estimatedCostToGoal = goalCost (q.grid);
            }
	    neighbors.add (q);
	}

	// West
	if (isValid (m, n-1)) {
	    int[][] nextGrid = makeGrid (p, m,n, m,n-1);
	    PuzzleState q = new PuzzleState (p, p.size, nextGrid);
            if (withCost) {
                q.costFromStart = p.costFromStart + 1;
                q.estimatedCostToGoal = goalCost (q.grid);
            }
	    neighbors.add (q);
	}

	return neighbors;
    }


    boolean isValid (int i, int j) 
    {
	if ( (i < 0) || (j < 0) || (i >= size) || (j >= size) ) {
	    return false;
	}
	return true;
    }



    // Make a neighboring grid.

    int[][] makeGrid (PuzzleState p, int m1, int n1, int m2, int n2)
    {
	int[][] nextGrid = new int [size][size];

	// First, copy.
	for (int i=0; i<size; i++) {
	    for (int j=0; j<size; j++) {
		nextGrid[i][j] = p.grid[i][j];
	    }
	}

	// Now swap.
	int temp = nextGrid[m1][n1];
	nextGrid[m1][n1] = nextGrid[m2][n2];
	nextGrid[m2][n2] = temp;

	return nextGrid;
    }


    double goalCost (int[][] grid)
    {
        double d = distance (grid, endGrid);
	return d;
    }


    double distance (int[][] grid1, int[][] grid2)
    {
        // Here, distance is just the number of places where
        // the two configurations differ. 
	double sum = 0;
	for (int i=0; i<size; i++) {
	    for (int j=0; j<size; j++) {
		if (grid1[i][j] != grid2[i][j]) {
		    sum += 1;
		}
	    }
	}
	return sum;
    }


    int[][] generatePuzzle ()
    {
	int[][] grid = new int [size][size];
	// Copy and find the zero.
	int m=-1, n=-1;
	for (int i=0; i<size; i++) {
	    for (int j=0; j<size; j++) {
		grid[i][j] = currentGrid[i][j];
		if (grid[i][j] == 0) {
		    m = i;   n = j;
		}
	    }
	}

	// Now do a large number of random moves.
	PuzzleState state = new PuzzleState (null, size, grid);
	for (int k=0; k<randomMoves; k++) {
	    ArrayList<State> neighbors = getNeighbors (state, false);
	    // Pick a random one.
	    int w = UniformRandom.uniform (0, neighbors.size()-1);
	    PuzzleState nextState = (PuzzleState) neighbors.get(w);
	    state = nextState;
	}

	return state.grid;
    }



    int[][] generateRandomPuzzle ()
    {
	// First make a permutation.
	int[] numbers = new int [size*size];
	for (int i=0; i<size*size; i++) {
	    numbers[i] = i;
	}
	for (int i=0; i<size*size-1; i++) {
	    int j = UniformRandom.uniform ( (int) i, (int) (size*size-1) );
	    int temp = numbers[i];
	    numbers[i] = numbers[j];
	    numbers[j] = temp;
	}	
	int[][] grid = new int [size][size];
	int k = 0;
	for (int i=0; i<size; i++) {
	    for (int j=0; j<size; j++) {
		grid[i][j] = numbers[k];
		k ++;
	    }
	}
	return grid;
    }



}




