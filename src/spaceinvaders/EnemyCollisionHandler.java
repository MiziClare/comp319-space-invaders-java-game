package spaceinvaders;

public class EnemyCollisionHandler extends CollisionHandler {
    private SpriteManager spriteManager;

    public EnemyCollisionHandler(SpriteManager spriteManager) {
        this.spriteManager = spriteManager;
    }

    @Override
    protected boolean doHandle(GameObject obj1, GameObject obj2) {
        Bullet bullet = null;
        Enemy enemy = null;

        if (obj1 instanceof Bullet && obj2 instanceof Enemy) {
            bullet = (Bullet) obj1;
            enemy = (Enemy) obj2;
        } else if (obj2 instanceof Bullet && obj1 instanceof Enemy) {
            bullet = (Bullet) obj2;
            enemy = (Enemy) obj1;
        }

        if (bullet != null && enemy != null && bullet.isAlive() && enemy.isAlive() && !bullet.isEnemyBullet()) {
            if (bullet.getBounds().intersects(enemy.getBounds())) {
                // collision occurrence
                bullet.setAlive(false);
                enemy.startExplode(spriteManager.getSprite("explode.png"));
                return true;
            }
        }

        return false;
    }
}
