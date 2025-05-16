package spaceinvaders;

public class PlayerCollisionHandler extends CollisionHandler {
    private SpriteManager spriteManager;
    public PlayerCollisionHandler(SpriteManager spriteManager) {
        this.spriteManager = spriteManager;
    }
    @Override
    protected boolean doHandle(GameObject obj1, GameObject obj2) {
        // Determining player-enemy bullet collisions
        // Assuming the player class is Player
        // Enemy Bullet: b.isEnemyBullet() == true

        if (obj1 instanceof Bullet && obj2 instanceof Player) {
            Bullet b = (Bullet) obj1;
            Player p = (Player) obj2;

            // Determining if it's an enemy bullet
            if (b.isAlive() && p.isAlive() && b.getBounds().intersects(p.getBounds()) && bIsEnemy(b)) {
                b.setAlive(false);
                p.setAlive(false);
                return true;
            }
        } else if (obj2 instanceof Bullet && obj1 instanceof Player) {
            Bullet b = (Bullet) obj2;
            Player p = (Player) obj1;

            if (b.isAlive() && p.isAlive() && b.getBounds().intersects(p.getBounds()) && bIsEnemy(b)) {
                b.setAlive(false);
                p.setAlive(false);
                return true;
            }
        }

        return false;
    }

    private boolean bIsEnemy(Bullet b) {
        // There was an enemyBullet field in Bullet.java at the beginning, use it to determine
        // If there was an enemyBullet field in Bullet before then simply return b.isEnemyBullet()
        // Assuming we have an isEnemyBullet() method in the Bullet.
        // Add an isEnemyBullet() accessor here based on the previous Bullet code example (or add it if you don't have one):
        // return b.isEnemyBullet(); // If there is no isEnemyBullet() method here, add an accessor.

        // If there was no isEnemyBullet() before, add a getter for it based on the boolean enemyBullet passed in during the Bullet construction: // public boolean isEnemyBullet().
        // public boolean isEnemyBullet() { return enemyBullet; }
        return b.isEnemyBullet();
    }

}
