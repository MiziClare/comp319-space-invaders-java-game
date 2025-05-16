package spaceinvaders;

public class BoundaryCollisionHandler extends CollisionHandler {
    private int screenWidth;
    private int screenHeight;

    public BoundaryCollisionHandler(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    @Override
    protected boolean doHandle(GameObject obj1, GameObject obj2) {
        // Boundary checking doesn't need to determine pairwise collisions, but rather checks if the object position is outside the boundary.
        // But here the chain of responsibility requires that obj1, obj2 collisions be handled.
        // Trick: the BoundaryCollisionHandler only handles objects passed in a ‘collision’ request, i.e. if obj1 or obj2 are out of bounds, they are marked as invalid.
        // Simply check for both obj1 and obj2, and set alive=false if they are out of bounds.

        boolean handled = false;
        handled |= checkBoundary(obj1);
        handled |= checkBoundary(obj2);

        // Returns false if no objects were processed so that subsequent Handlers have a chance to process them
        return handled;
    }

    private boolean checkBoundary(GameObject obj) {
        if (obj.isAlive()) {
            // Check for out-of-bounds logic
            // Bullet exceeds upper and lower boundaries
            if (obj.getY() < 0 || obj.getY() > screenHeight) {
                obj.setAlive(false);
                return true;
            }
            // Enemies to the bottom (which by game logic could mean failure)
            if (obj instanceof Enemy && obj.getY() + obj.getHeight() >= screenHeight) {
                // Go to the bottom and decide whether to end the game or remove the enemies as required
                obj.setAlive(false);
                // This can be followed up by the GameController detecting that there are no players or enemies in the end and executing a game over.
                return true;
            }
        }
        return false;
    }
}
