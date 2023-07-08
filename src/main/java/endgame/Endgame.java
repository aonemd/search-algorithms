package endgame;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

import search.SearchProblem;
import search.Operator;

public class Endgame extends SearchProblem {
    private String grid;
    private int gridWidth;
    private int gridHeight;
    private Cell ironManPosition;
    private Cell thanosPosition;
    private List<Cell> stonePositions;
    private List<Cell> warriorPositions;

    public Endgame(String grid) {
        // parse grid into useful information
        String[] gridInfo = grid.split(";");

        String[] gridDimensions = gridInfo[0].split(",");
        this.gridWidth = Integer.parseInt(gridDimensions[0]);
        this.gridHeight = Integer.parseInt(gridDimensions[1]);

        String[] ironManXYPosition = gridInfo[1].split(",");
        this.ironManPosition = new Cell(Integer.parseInt(ironManXYPosition[0]),
                Integer.parseInt(ironManXYPosition[1]));

        String[] thanosXYPosition = gridInfo[2].split(",");
        this.thanosPosition = new Cell(Integer.parseInt(thanosXYPosition[0]),
                Integer.parseInt(thanosXYPosition[1]));

        this.stonePositions = new ArrayList<Cell>();
        if (gridInfo.length > 3) {
            String[] stoneXYPositions = gridInfo[3].split(",");
            for(int i = 0; i < stoneXYPositions.length; i += 2) {
                this.stonePositions.add(new Cell(Integer.parseInt(stoneXYPositions[i]),
                            Integer.parseInt(stoneXYPositions[i+1])));
            }
        }

        this.warriorPositions = new ArrayList<Cell>();
        if (gridInfo.length > 4) {
            String[] warriorXYPositions = gridInfo[4].split(",");
            for(int i = 0; i < warriorXYPositions.length; i += 2) {
                this.warriorPositions.add(new Cell(Integer.parseInt(warriorXYPositions[i]),
                            Integer.parseInt(warriorXYPositions[i+1])));
            }
        }

        HashMap<String, Operator> operators = new HashMap<String, Operator>();
        operators.put("snap", new Operator("snap", 0));
        operators.put("collect", new Operator("collect", 3));
        operators.put("kill", new Operator("kill", 2));
        operators.put("up", new Operator("up", 0));
        operators.put("down", new Operator("down", 0));
        operators.put("right", new Operator("right", 0));
        operators.put("left", new Operator("left", 0));

        this.operators = operators;
        this.initialState = new EndGameState(gridWidth,
                                            gridHeight,
                                            ironManPosition,
                                            thanosPosition,
                                            new HashSet<Cell>(stonePositions),
                                            new HashSet<Cell>(warriorPositions),
                                            operators,
                                            0,
                                            false);
    }

    public static String solve(String grid, String strategy, boolean visualize) {
        Endgame endGameProblem = new Endgame(grid);

        return search(endGameProblem, strategy);
    }

	public static void main(String[] args) {
        String solution = Endgame.solve("2,2;0,0;1,0;1,1;0,1", "BF", false);

        System.out.println(solution);
    }
}