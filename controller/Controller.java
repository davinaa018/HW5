package controller;

import lib.Board;
import lib.BoardPanel;
import lib.Coordinate;
import lib.WebService;

public class Controller {
    private String url;
    private WebService webService;
    private BoardPanel boardPanel;

    public Controller(String url, BoardPanel boardPanel){
        // Initialize the board and the board panel
        this.boardPanel = boardPanel;
        this.url = url;
        this.webService = new WebService(url);
    }

    public void getInfo(){
        webService.getInfo();
    }

    public String getNew(String strategy){
        return webService.getNew(strategy);
    }

    public void getPlay(String pid, Coordinate userMove){
        boardPanel.getBoard().makeMove(userMove.getX(), userMove.getY(), 'X');
        Coordinate server = webService.getPlay(pid, userMove.getX(), userMove.getY());
        boardPanel.getBoard().makeMove(server.getX(), server.getY(), 'O');
    }

   

   public static void main(String[] args){
        // Controller controller = new Controller("http://omok.atwebpages.com/");
   }




}
