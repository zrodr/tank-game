package edu.proj.tankgame.model;

/*
 * Extra feature: AI Logic. Patrol Tank will mind its own business until player comes within 400 units of it. After
 * which, the tank will pursue at a close distance and with increased speed.
 */

public class PatrolAITank extends Tank{
    private static final double PATROL_TANK_ALERTED_MOVE_SPEED = 2.5;
    private int movementTick = 200;
    private boolean playerNoticed = false;

    public PatrolAITank(String id, double x, double y, double angle) {
        super(id, x, y, angle);
    }

    private void patrol(){
        if(movementTick <= 200){
            movementTick--;
            moveForward(getMovementSpeed());
            if(movementTick == 0){
                movementTick = 200;
                setAngle(getAngle() + Math.toRadians(180.0));
            }
        }
    }

    @Override
    public void move(GameState gameState){
        super.move(gameState);

        Entity playerTank = gameState.getEntity(GameState.PLAYER_TANK_ID);

        double dx = playerTank.getX() - getX();
        double dy = playerTank.getY() - getY();

        double distance = Math.sqrt(dx * dx + dy * dy);

        if(distance > 400.0 && !playerNoticed){
            patrol();
            return;
        }
        else {
            playerNoticed = true;
        }

        double angleToPlayer = Math.atan2(dy, dx);
        double angleDifference = getAngle() - angleToPlayer;
        angleDifference -= Math.floor(angleDifference / Math.toRadians(360.0) + 0.5) * Math.toRadians(360.0);

        if(150.0 < distance && playerNoticed){
            moveForward(PATROL_TANK_ALERTED_MOVE_SPEED);
        }else if(distance <= 100.0){
            moveBackward(PATROL_TANK_ALERTED_MOVE_SPEED);
        }

        if(angleDifference < -getTurnSpeed()){
            turnRight(getTurnSpeed());
        }else if(angleDifference > getTurnSpeed()){
            turnLeft(getTurnSpeed());
        }

        shoot(gameState);
    }
}
