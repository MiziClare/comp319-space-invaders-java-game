package spaceinvaders;

public abstract class CollisionHandler {
    protected CollisionHandler nextHandler;

    public void setNextHandler(CollisionHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    /**
     * Attempt to process the collision of obj1 with obj2.
     * If the current processor cannot handle it, it is passed to the next processor.
     */
    public void handleCollision(GameObject obj1, GameObject obj2) {
        if (!doHandle(obj1, obj2) && nextHandler != null) {
            nextHandler.handleCollision(obj1, obj2);
        }
    }

    /**
     * Subclasses implement specific collision handling logic.
     * @return Returns true if this handler has successfully handled the collision, otherwise returns false
     */
    protected abstract boolean doHandle(GameObject obj1, GameObject obj2);
}
