package edu.proj.tankgame.model;

/**
 * Model class representing a shell that has been fired by a tank. A shell has a position and an angle, as well as a
 * speed. Shells by default should be unable to turn and only move forward.
 */
public class Shell extends Entity{
    private static final String SHELL_ID_PREFIX = "shell-";
    private static final double MOVEMENT_SPEED = 3.3;
    private final String owner;

    private static long uniqueId = 0L;

    public Shell(double x, double y, double angle, String owner) {
        super(getUniqueId(), x, y, angle);
        this.owner = owner;
    }

    private static String getUniqueId() {
        return SHELL_ID_PREFIX + uniqueId++;
    }

    public String getOwner(){
        return owner;
    }

    @Override
    public void move(GameState gameState) {
        moveForward(MOVEMENT_SPEED);
    }

    @Override
    public void checkBounds(GameState gameState) {
        if(getX() < GameState.SHELL_X_LOWER_BOUND ||
            getX() > GameState.SHELL_X_UPPER_BOUND ||
            getY() < GameState.SHELL_Y_LOWER_BOUND ||
            getY() > GameState.SHELL_Y_UPPER_BOUND){
            gameState.markEntityForRemoval(getId());
        }
    }

    @Override
    public double getXBound(){
        return getX() + 24.0;
    }

    @Override
    public double getYBound(){
        return getY() + 24.0;
    }

    @Override
    public void handleCollision(GameState gameState, Entity entity){
        for(CollisionHandler collisionHandler : getCollisionHandlers()){
            collisionHandler.performCollision(gameState, this, entity);
        }
    }
}