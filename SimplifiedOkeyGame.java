import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;

public class SimplifiedOkeyGame {

    Player[] players;
    Tile[] tiles;
    int tileCount;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public SimplifiedOkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[104];
        int currentTile = 0;

        // four copies of each value, no jokers
        for (int i = 1; i <= 26; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i);
            }
        }

        tileCount = 104;
    }

    /*
     * DONE: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles, this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {
        int givenTile = 0;
        int tilePerPlayer;
        for (int i = 0; i < 4; i++) 
        {
            if ( i == 0) 
            {
                tilePerPlayer = 15;
            }
            else 
            {
                tilePerPlayer = 14;
            }
            for (int k = 0; k < tilePerPlayer; k++) 
            {
                players[i].addTile(tiles[givenTile]);
                givenTile++;
            }
            players[i].numberOfTiles = tilePerPlayer; //playerin Numberoftiles instance variable'i guncellendi.
        }
        tileCount -=givenTile; // tileCount guncellenmeliydi.
    }

    /*
     * DONE: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        players[currentPlayerIndex].addTile(lastDiscardedTile);
        return lastDiscardedTile.toString();
    }

    /*
     * DONE: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * and it will be given to the current player
     * returns the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        int topIndex = tileCount - 1;
        Tile topTile = tiles[topIndex];
        players[currentPlayerIndex].addTile(topTile);
        tiles[topIndex] = null;
        return topTile.toString();
    }

    /*
     * DONE: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {
        Random random = new Random();

        for (int i=0; i <tiles.length ; i++) 
        {
            int randomSelectedTile_index = random.nextInt(tiles.length);
            Tile previous = tiles[i] ;
            tiles[i]= tiles[randomSelectedTile_index];
            tiles[randomSelectedTile_index]=previous;
        }
    }

    /*
     * DONE: check if game still continues, should return true if current player
     * finished the game. use checkWinning method of the player class to determine
     */
    public boolean didGameFinish() {
        if (players[currentPlayerIndex].checkWinning()) 
        {
            return true;
        }
        else 
        {
            return false;
        }
    }

    /* DONE: finds the player who has the highest number for the longest chain
     * if multiple players have the same length may return multiple players
     */
    public Player[] getPlayerWithHighestLongestChain() {
        int longestChain = players[0].findLongestChain();
        for(int i=1; i < players.length;i++){
            if( players[i].findLongestChain() > longestChain ){
                longestChain = players[i].findLongestChain();
            }
        }
        ArrayList<Player> winnerList = new ArrayList<Player>();
        for(int i =0; i<players.length;i++){
            if(players[i].findLongestChain() == longestChain){
                winnerList.add(players[i]);
            }
        }
        Player[] winnersFinal = new Player[winnerList.size()];
        for (int i = 0; i < winnersFinal.length; i++)
        {
            winnersFinal[i] = winnerList.get(i);
        }
        return winnersFinal;
    }
        
    /*
     * checks if there are more tiles on the stack to continue the game
     */
    public boolean hasMoreTileInStack() {
        return tileCount != 0;
    }

    /*
     * TODO: pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * you should check if getting the discarded tile is useful for the computer
     * by checking if it increases the longest chain length, if not get the top tile
     */
    public void pickTileForComputer() {
        
        Tile[] playerTiles = players[currentPlayerIndex].getTiles();
        int playersLongestChain = players[currentPlayerIndex].findLongestChain();
        
        Tile[] controlTiles = new Tile[playerTiles.length];
        System.arraycopy(playerTiles, 0, controlTiles, 0, playerTiles.length);
        Player[] controlPlayer = new Player[1];  //ŞURALARI BİRLEŞTİREMEDİM
        
        Boolean getDiscardedOne = true;
        
        controlPlayer[0].addTile(lastDiscardedTile); //to control
       
        int newControlChain = controlPlayer[0].findLongestChain();
        
            if(newControlChain <= playersLongestChain){
                getDiscardedOne = false;
            }
        
        if(getDiscardedOne){
            getLastDiscardedTile();
        }
        else{
            getTopTile();
        }
    }

    /*
     * TODO: Current computer player will discard the least useful tile.
     * you may choose based on how useful each tile is
     */
    public void discardTileForComputer() {

    }

    /*
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {
        if(tileIndex >= 0 && tileIndex < players[currentPlayerIndex].getTiles().length) {
            players[currentPlayerIndex].getAndRemoveTile(tileIndex); //removing the tile at the specified index from the player's tiles
            lastDiscardedTile = players[currentPlayerIndex].getAndRemoveTile(tileIndex);
        }
    }
    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

}
