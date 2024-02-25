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
            if(playerTiles[index].getValue() == playerTiles[index - 1].getValue() + 1){// would throw a exeption when index=0 => index -1 =-1. 
                currentChainLength++;
                longestChain = Math.max(longestChain, currentChainLength);
            }
            else{
                if(!(playerTiles[index].getValue() == playerTiles[index - 1].getValue())){// if there is two duplicates side to side, the currentChain should not be resetted to 1. 
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
        
        if(playerTiles[0]==null){
            playerTiles[0] = t; // ilk koyulan icin ozel durum.
        }
        else{
            // kouyulacak indexi belirlemen lazim.
                int koyulacakIndex=-1;
                int ilkNullIndexi = 0;
                //ilk null indexini bulalim ilk once
                for(int i =0; i<playerTiles.length;i++){
                    if( playerTiles[i]!=null && playerTiles[i+1]==null ){
                        ilkNullIndexi = i+1;
                        i = playerTiles.length;
                    }
                }
                // koyulacak olan indexi bulalim
                for(int i =0; i<playerTiles.length;i++){
                    if( playerTiles[i]!=null && ( t.value > playerTiles[i].value ) )//i bos degil ve verilen deger i deki degerden buyukse
                    {
                        koyulacakIndex = i+1; // koyulacak olan bu indexe koyulacak. bundan dolayi bu indexteki original degerden baslayarak tum degerler saga kaymali.
                    }
                }
                // ilk null olana mi yoksa koyulacak olana mi koyacagiz onu belirleyelim.
                if( koyulacakIndex==-1 ){
                    koyulacakIndex = ilkNullIndexi;
                }
                // eger direkt bos indexe koyacaksak koyalim
                if ( koyulacakIndex == ilkNullIndexi) {
                    playerTiles[ilkNullIndexi]=t;
                }
                else{ // arada bir yere koyacaksak
                
                        for(int i = ilkNullIndexi; i<koyulacakIndex;i--){
                            playerTiles[i] = playerTiles[i-1]; // koyulacak olan index bosaltilti
                        }
                        playerTiles[koyulacakIndex] = t; // bosaltilan indexe istenilen tile yerlestirildi.
                }
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
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
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
}
