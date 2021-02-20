package edu.proj.tankgame.model;

/**
 * Model class representing a tank in the game. A tank has a position and an angle. It has a movement speed and a turn
 * speed, both represented below as constants.
 */
public abstract class Tank extends Entity{
    private static final double MOVEMENT_SPEED = 2.0;
    private static final double TURN_SPEED = Math.toRadians(1.5);
    private static final int INITIAL_COOLDOWN = 20;
    private static final int HIT_POINTS = 5;
    private int coolDown;
    private int health;

    public Tank(String id, double x, double y, double angle){
        super(id, x, y, angle);
        this.coolDown = INITIAL_COOLDOWN; //prevents tanks from firing right off the bat
        this.health = HIT_POINTS;
    }

    protected void shoot(GameState gameState){
        if(coolDown == 0){
            gameState.addShell(new Shell(getShellX(), getShellY(), getAngle(), getId()));
            coolDown = 250;
        }
    }

    @Override
    public void move(GameState gameState){
        if(coolDown != 0){
            coolDown--;
        }
    }

    @Override
    public void checkBounds(GameState gameState){
        if(getX() < GameState.TANK_X_LOWER_BOUND){
            setX(GameState.TANK_X_LOWER_BOUND);
        }
        if(getX() > GameState.TANK_X_UPPER_BOUND){
            setX(GameState.TANK_X_UPPER_BOUND);
        }
        if(getY() < GameState.TANK_Y_LOWER_BOUND){
            setY(GameState.TANK_Y_LOWER_BOUND);
        }
        if(getY() > GameState.TANK_Y_UPPER_BOUND){
            setY(GameState.TANK_Y_UPPER_BOUND);
        }
    }

    @Override
    public double getXBound(){
        return getX() + 55.0;
    }

    @Override
    public double getYBound(){
        return getY() + 55.0;
    }

    @Override
    public void handleCollision(GameState gameState, Entity entity){
        for(CollisionHandler collisionHandler : getCollisionHandlers()){
            collisionHandler.performCollision(gameState, this, entity);
        }
    }

    protected void takeDamage(GameState gameState){
        health--;
        if(health == 0){
            gameState.markEntityForRemoval(getId());
        }
    }

    protected double getMovementSpeed(){
        return MOVEMENT_SPEED;
    }

    protected double getTurnSpeed(){
        return TURN_SPEED;
    }

    // The following methods will be useful for determining where a shell should be spawned when it
    // is created by this tank. It needs a slight offset so it appears from the front of the tank,
    // even if the tank is rotated. The shell should have the same angle as the tank.

    private double getShellX() {
        return getX() + 30.0 * (Math.cos(getAngle()) + 0.5);
    }

    private double getShellY() {
        return getY() + 30.0 * (Math.sin(getAngle()) + 0.5);
    }
}
