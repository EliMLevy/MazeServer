
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GraphicalRepresentation extends JFrame {

    private final int[][] maze;
    private final int rows, cols;
    private final int width, height;
    private final float colWidth, rowHeight;

    public GraphicalRepresentation(int[][] maze, int width, int height) {
        this.rows = maze.length;
        if (rows > 0) {
            this.cols = maze[0].length;
        } else {
            this.cols = 0;
        }

        this.width = width;
        this.height = height;

        this.colWidth = (this.width / this.cols);
        this.rowHeight = (this.height / this.rows);

        setSize(this.width, this.height + (int) this.rowHeight);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.maze = maze;
    }

    @Override
    public void paint(Graphics g) {
        int width = this.getWidth();
        int height = this.getHeight();


        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();


        // g2.setColor(new Color(255, 255, 255));
        // g2.fillRect(0, 0, width, height);
        g2.setBackground(new Color(255,255,255));

        g2.setColor(new Color(0, 0, 0));
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.cols; c++) {

                if (this.maze[r][c] == 1) {
                   g2.setColor(new Color(0,0,0)); 
                } else {
                   g2.setColor(new Color(255,255,255)); 

                }
                g2.fillRect((int) (c * this.colWidth), (int) (r * this.rowHeight + this.rowHeight),
                        (int) this.colWidth,
                        (int) this.rowHeight);

            }
        }

        Graphics2D g2dComponent = (Graphics2D) g;
        g2dComponent.drawImage(bufferedImage, null, 0, 0);

    }

    public static void main(String[] args) {

        int rows = 30;
        int cols = 30;
        final int[][] exampleMaze = MazeGenerator.generate(rows, cols);
        new GraphicalRepresentation(exampleMaze, 600, 600);

    }

}
