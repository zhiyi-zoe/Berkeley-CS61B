# Project 3 Prep

**What distinguishes a hallway from a room? How are they similar?**

Answer: The width of a hallway only can be 1 or 2 tiles, while that of a room can be random. They are both represented as floor visually. 

**Can you think of an analogy between the process of 
drawing a knight world and randomly generating a world 
using rooms and hallways?**

Answer: Determine the area by x and y. Set the room and hallway area to floor tile.

**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually 
get to the full knight world.**

Answer: I will write a method to judge what kind of area the coordinate belongs to by its x and y.

**This question is open-ended. Write some classes 
and methods that might be useful for Project 3. Think 
about what helper methods and classes you used for the lab!**

Answer: In the roomGeneration method, I use 100 to represent a room.
private int roomGeneration(int x, int y) {
    int roomWidth = RANDOM.nextInt(1 + 2 + 2) + 1;
    int roomHeight = RANDOM.nextInt(1 + 2 + 2) + 1;
    for (int i = x; i < x + roomWidth; i++) {
        for (int j = y; j < y + roomHeight; j++) {
            this.tilesNum[i][j] = 100;
        }
    }
}
