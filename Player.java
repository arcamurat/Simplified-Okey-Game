public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * DONE: checks this player's hand to determine if this player is winning
     * the player with a complete chain of 14 consecutive numbers wins the game
     * note that the player whose turn is now draws one extra tile to have 15 tiles in hand,
     * and the extra tile does not disturb the longest chain and therefore the winning condition
     * check the assigment text for more details on winning condition
     */
    public boolean checkWinning() {
        return findLongestChain() >= 14;
    }

    /*
     * DONE: used for finding the longest chain in this player hand
     * this method should iterate over playerTiles to find the longest chain
     * of consecutive numbers, used for checking the winning condition
     * and also for determining the winner if tile stack has no tiles
     */
    public int findLongestChain() {
        int currentChainLength = 1;
        int longestChain = 1;
    
        for(int index = 1; index < playerTiles.length; index++){
            if(playerTiles[index] != null && playerTiles[index - 1] != null && 
               playerTiles[index].getValue() == playerTiles[index - 1].getValue() + 1) {
                currentChainLength++;
                longestChain = Math.max(longestChain, currentChainLength);
            } else {
                if(playerTiles[index] != null && playerTiles[index - 1] != null &&
                   !(playerTiles[index].getValue() == playerTiles[index - 1].getValue())) {
                    currentChainLength = 1;
                }
            }
        }
        return longestChain;
    }    

    /*
     * DONE: removes and returns the tile in given index position
     */
    public Tile getAndRemoveTile(int index) {
        Tile removedTile = playerTiles[index];
        playerTiles[index] = null;

        return removedTile;
    }

    /*
     * DONE: adds the given tile to this player's hand keeping the ascending order 
     * this requires you to loop over the existing tiles to find the correct position,
     * then shift the remaining tiles to the right by one
     */
    public void addTile(Tile t) {
        if (t == null) {
            System.out.println("Attempted to add a null Tile.");
            return;
        }
        
        if (playerTiles[0] == null) {
            playerTiles[0] = t; // for the first tile
        }
        else {
            int insertionIndex = 0;
            while (insertionIndex < playerTiles.length && playerTiles[insertionIndex] != null && t.getValue() > playerTiles[insertionIndex].getValue()) {
                insertionIndex++;
            }
    
            if (insertionIndex == playerTiles.length) {
                System.out.println("Player's hand is full. Cannot add tile.");
                return;
            }
    
            // shifting
            for (int i = playerTiles.length - 2; i > insertionIndex; i--) {
                playerTiles[i] = playerTiles[i - 1];
            }
    
            playerTiles[insertionIndex] = t; 
        }
    }
    
    
    /*
     * finds the index for a given tile in this player's hand
     */
    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].matchingTiles(t)) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    /*
    * displays the tiles of this player
    */
    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < playerTiles.length; i++) {
            if (playerTiles[i] != null) {
                System.out.print(playerTiles[i].toString() + " ");
            }
        }
        System.out.println();
    }


    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }

    public void setTiles(Tile[] updatedTiles) {
        playerTiles = updatedTiles;
    }
}
