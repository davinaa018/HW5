package lib;


import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class BoardPanel extends JPanel {
    private static final Color boardColor = new Color(253, 218, 13);
    private Coordinate hoverIntersection;
    private Board board;
    private Coordinate move;
    private Controller controller;

    public BoardPanel(Board board){
        this.board = board;
        this.controller = new Controller("http://omok.atwebpages.com/", this);

        setBackground(boardColor);

        // Hovering effect
        hoveringEffect(true);
        // Place stones
        placeStone();
        initializeBoard();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int size = board.getSize();
        int squareSize = getWidth()/size;
        drawBoard(size, squareSize, g);
        drawHoverEffect(size, squareSize, g);
        drawStones(size, squareSize, g);
    }

    public void initializeBoard(){
        board.initializeBoard();
    }

    public Board getBoard(){
        return board;
    }

    /**
     * Draws the board
     * @param size
     * @param squareSize
     * @param g
     */
    private void drawBoard(int size, int squareSize, Graphics g){
        // Draw horizontal lines
        for (int i = 0; i < size; i++) {
           g.drawLine(0, i * squareSize, getWidth(), i * squareSize );
       }
       // Draw vertical lines
       for (int i = 0; i < size; i++) {
           g.drawLine(i * squareSize, 0, i * squareSize, getHeight());
       }
   }

   /**
     * Draws the hovering effect
     * @param size
     * @param squareSize
     * @param g
     */
    private void drawHoverEffect(int size, int squareSize, Graphics g){
        if (hoverIntersection != null) {
            int x = hoverIntersection.getX() * squareSize + squareSize-7;
            int y = hoverIntersection.getY() * squareSize + squareSize-7;
            int ovalSize = squareSize / 2;
            g.drawOval(x, y, ovalSize, ovalSize);
        }
    }

    /**
     * Draws the stones
     * @param size
     * @param squareSize
     * @param g
     */
    private void drawStones(int size, int squareSize, Graphics g){
        //  Draw Stones
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = i * squareSize + squareSize / 2;
                int y = j * squareSize + squareSize / 2;
                if (board.getPieceAt(i, j) == 'X') {
                    g.setColor(Color.BLACK);
                    g.fillOval(x+8, y+8, squareSize / 2, squareSize / 2);
                    if (board.hasWinner(i, j)) {
                        g.setColor(Color.RED);
                        g.fillOval(x+10, y+10, squareSize / 2 - 4, squareSize / 2 - 4);
                    }
                } else if (board.getPieceAt(i, j) == 'O') {
                    g.setColor(Color.WHITE);
                    g.fillOval(x+8, y+8, squareSize / 2, squareSize / 2);
                    g.setColor(Color.BLACK);
                    if (board.hasWinner(i, j)) {
                        g.setColor(Color.RED);
                        g.fillOval(x+10, y+10, squareSize / 2 - 4, squareSize / 2 - 4);
                    }
                }
            }
        }
    }

     /**
     * Adds a hovering effect to the board
     * @param startGame
     */
    public void hoveringEffect(boolean startGame){
        if(startGame){
            // Hovering effect
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    int size = board.getSize();
                    int squareSize = getWidth() / size;
                    int i = e.getX() / squareSize;
                    int j = e.getY() / squareSize;
                    if (i >= 0 && i < size && j >= 0 && j < size) {
                        hoverIntersection = new Coordinate(i, j);
                    } else {
                        hoverIntersection = null;
                    }
                    repaint();
                }
            });
        }
    }

     /**
     * Places a stone on the board by using MouseListener
     * @param startGame
     */
    public Coordinate placeStone() {
        move = null;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = e.getX() / (getWidth() / board.getSize());
                int j = e.getY() / (getWidth() / board.getSize());
                if (i >= 0 && i < board.getSize() && j >= 0 && j < board.getSize()) {
                    if (board.getPieceAt(i, j) == '-') {
                        // place stone and switch player
                        
                        controller.getInfo();
                        String pid = controller.getNew("random");
                        Coordinate userMove = new Coordinate(i, j);
                        controller.getPlay(pid, userMove);
                        repaint();

                    }
                }
            }
        });
        return move;
    }



    public static void main(String[] args) {
        Board board = new Board(15);
        BoardPanel panel = new BoardPanel(board);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(500, 500);
        frame.setVisible(true);

    }
    
}
