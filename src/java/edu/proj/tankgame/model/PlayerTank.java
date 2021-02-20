package edu.proj.tankgame.model;

public class PlayerTank extends Tank{
    public PlayerTank(String id, double x, double y, double angle){
        super(id, x, y, angle);
    }
    @Override
    public void move(GameState gameState){
        super.move(gameState);

        if(gameState.upPressed()){
            moveForward(getMovementSpeed());
        }
        if(gameState.downPressed()){
            moveBackward(getMovementSpeed());
        }
        if(gameState.leftPressed()){
            turnLeft(getTurnSpeed());
        }
        if(gameState.rightPressed()){
            turnRight(getTurnSpeed());
        }
        if(gameState.spacePressed()){
            shoot(gameState);
        }
    }
}
