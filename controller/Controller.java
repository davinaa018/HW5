package controller;

import javax.swing.JTextArea;

import org.json.JSONObject;

import lib.BoardPanel;
import lib.Coordinate;
import lib.WebService;

public class Controller {
    private String url;
    private WebService webService;
    private BoardPanel boardPanel;
    private String strategy;
    private JTextArea logTextArea;
    private String pid;

    public Controller(BoardPanel boardPanel, String strategy, JTextArea logTextArea ){
        // Initialize the board and the board panel
        this.boardPanel = boardPanel;
        this.strategy = strategy;
        this.logTextArea = logTextArea;
    }

    public void setUrl(String url){
        this.url = url;
        webService = new WebService(url);
        getInfo();
        this.pid = getNew();
    }

    public String getUrl(){
        return url;
    }

    public void setStrategy(String strategy){
        this.strategy = strategy;
    }

    public String getStrategy(){
        return strategy;
    }

    public void getInfo(){
        JSONObject obj = webService.getInfo();
        logTextArea.append(obj.toString() + "\n");
    }

    public String getNew(){
        JSONObject obj = webService.getNew(strategy);
        logTextArea.append(obj.toString() + "\n");
        return obj.getString("pid");
    }

    public void getPlay(Coordinate userMove){
        JSONObject obj = webService.getPlay(pid, userMove.getX(), userMove.getY());
        boardPanel.getBoard().makeMove(userMove.getX(), userMove.getY(), 'X');
        logTextArea.append(obj.toString() + "\n");
        if (boardPanel.getBoard().isFull()){
            logTextArea.append("Draw!" + "\n");
            boardPanel.showWinner("Draw");
        }
        if(boardPanel.getBoard().hasWinner(userMove.getX(), userMove.getY())){
            logTextArea.append("You win!" + "\n");
            boardPanel.showWinner("User ");
        }
        JSONObject move = obj.getJSONObject("move");
        int j = (int) move.get("x");
        int k = (int) move.get("y");
        Coordinate server = new Coordinate(j, k);
        boardPanel.getBoard().makeMove(server.getX(), server.getY(), 'O');
        if(boardPanel.getBoard().hasWinner(server.getX(), server.getY())){
            logTextArea.append("Server wins!" + "\n");
            boardPanel.showWinner("Server ");
        }
            

        
    }


}
