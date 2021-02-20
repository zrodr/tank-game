package edu.proj.tankgame.model;

public class Wall extends Entity{
    private static final String WALL_ID_PREFIX = "wall-";
    private static long uniqueID = 0L;
    private static final int HIT_POINTS = 3;
    private int health;

    public Wall(double x, double y){
        super(getUniqueID(), x, y, 0.0);
        this.health = HIT_POINTS;
    }

    private static String getUniqueID(){ return WALL_ID_PREFIX + uniqueID++; }

    protected void takeDamage(GameState gameState){
        health--;
        if(health == 0){
            gameState.markEntityForRemoval(getId());
        }
    }

    @Override
    public void move(GameState gameState) {
    }

    @Override
    public void checkBounds(GameState gameState) {
    }

    @Override
    public double getXBound(){
        return getX() + 32.0;
    }

    @Override
    public double getYBound(){
        return getY() + 32.0;
    }

    @Override
    public void handleCollision(GameState gameState, Entity entity){
        for(CollisionHandler collisionHandler : getCollisionHandlers()){
            collisionHandler.performCollision(gameState, this, entity);
        }
    }
}
