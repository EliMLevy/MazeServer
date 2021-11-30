
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MazeGenerator {

    public static void main(String[] args) {

    }

    public static int[][] generate(int rows, int cols) {
        int[][] grid = new int[rows][cols];

        for (int i = 0; i < cols; i++) {
            grid[0][i] = 1;
            grid[grid.length - 1][i] = 1;
        }

        for (int i = 0; i < rows; i++) {
            grid[i][0] = 1;
            grid[i][grid[i].length - 1] = 1;
        }

        // Select a random point on the top wall

        // while current != null
        // choose a possible move from current
        // place a wall in one of the possible moves
        // add position to stack
        // move current position
        // if there are no more posible moves pop a position off the stack

        List<Tree> trees = new ArrayList<>();

        trees.add(new Tree(new Vec(2, 0)));
        trees.add(new Tree(new Vec(cols - 3, rows - 1)));

        for(int i = 0; i < 4; i++) {
            Tree top = new Tree(new Vec((int)(Math.random() * (cols - 4) + 2), 0));
            Tree bottom = new Tree(new Vec((int)(Math.random() * (cols - 4) + 2), rows - 1));
            trees.add(top);
            trees.add(bottom);
        }

        for(int i = 0; i < 4; i++) {
            Tree left = new Tree(new Vec(0,(int) (Math.random() * (rows - 4) + 2)));
            Tree right = new Tree(new Vec(cols - 1,(int) (Math.random() * (rows - 4) + 2)));
            trees.add(left);
            trees.add(right);
        }


        while(true) {
            for(int i = 0; i < trees.size(); i++){
                Tree t = trees.get(i);
                for(int j = 0; j < 10 && t.pos != null; j++) {
                    t.move(grid);
                }
                if(t.pos == null) {
                    trees.remove(t);
                }
            }
            if(trees.size() == 0) {
                break;
            }
        }


        grid[0][1] = 0;
        grid[grid.length - 1][grid[0].length - 2] = 0;

        return grid;

    }

    public static Vec chooseNextMove(int[][] grid, Vec current) {
        // returns a spot chosen randomly from a list of spots that meet the following
        // criteria:
        // Must not be a wall
        // must not be next to more than one wall
        List<Vec> possibilities = new ArrayList<>();
        // check left
        if (current.x - 1 >= 0 && grid[current.y][current.x - 1] != 1
                && sumOfNeighbors(grid, current.y, current.x - 1, "LEFT") == 1) {
            possibilities.add(new Vec(current.x - 1, current.y));
        }
        // right
        if (current.x + 1 < grid[current.y].length && grid[current.y][current.x + 1] != 1
                && sumOfNeighbors(grid, current.y, current.x + 1, "RIGHT") == 1) {
            possibilities.add(new Vec(current.x + 1, current.y));
        }
        // up
        if (current.y - 1 >= 0 && grid[current.y - 1][current.x] != 1
                && sumOfNeighbors(grid, current.y - 1, current.x, "UP") == 1) {
            possibilities.add(new Vec(current.x, current.y - 1));
        }
        // down
        if (current.y + 1 < grid.length && grid[current.y + 1][current.x] != 1
                && sumOfNeighbors(grid, current.y + 1, current.x, "DOWN") == 1) {
            possibilities.add(new Vec(current.x, current.y + 1));
        }

        if (possibilities.size() > 0) {
            int index = (int) (Math.random() * possibilities.size());
            return possibilities.get(index);
        } else {
            return null;
        }
    }

    private static int sumOfNeighbors(int[][] grid, int row, int col, String dir) {
        int sum = 0;


        switch (dir) {
            case "LEFT":
                sum += grid[row - 1][col - 1];
                sum += grid[row + 1][col - 1];
                break;
            case "RIGHT":
                sum += grid[row - 1][col + 1];
                sum += grid[row + 1][col + 1];
                break;
            case "UP":
                sum += grid[row - 1][col + 1];
                sum += grid[row - 1][col - 1];
                break;
            case "DOWN":
                sum += grid[row + 1][col + 1];
                sum += grid[row + 1][col - 1];

                break;

            default:
                break;
        }

        sum += grid[row - 1][col];
        sum += grid[row + 1][col];
        sum += grid[row][col + 1];
        sum += grid[row][col - 1];
        // left and right
        return sum;
    }

    private static class Vec {
        public int x, y;

        public Vec(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + this.x + "," + this.y + ")";
        }
    }

    private static class Tree {
        public Vec pos;
        public List<Vec> stack;

        public Tree(Vec pos) {
            this.pos = pos;
            this.stack = new LinkedList<>();
        }

        public void move(int[][] grid) {
            if(this.pos != null) {
                Vec nextMove = chooseNextMove(grid, this.pos);
                if (nextMove != null) {
                    this.stack.add(0, this.pos);
                    grid[nextMove.y][nextMove.x] = 1;
                    this.pos = nextMove;
                } else {
                    if (this.stack.isEmpty()) {
                        this.pos = null;
                    } else {
                        this.pos = stack.remove(0);
                    }
                }

            }

        }
    }

}