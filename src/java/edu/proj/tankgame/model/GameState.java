package edu.proj.tankgame.model;

import edu.proj.tankgame.view.RunGameView;

import java.util.*;

import java.util.stream.Collectors;
/**
 * GameState represents the state of the game "world." The GameState object tracks all of the moving entities like tanks
 * and shells, and provides the controller of the program (i.e. the GameDriver) access to whatever information it needs
 * to run the game. Essentially, GameState is the "data context" needed for the rest of the program.
 */
public class GameState {
    public static final double TANK_X_LOWER_BOUND = 30.0;
    public static final double TANK_X_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.width - 100.0;
    public static final double TANK_Y_LOWER_BOUND = 30.0;
    public static final double TANK_Y_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.height - 120.0;

    public static final double SHELL_X_LOWER_BOUND = -30.0;
    public static final double SHELL_X_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.width;
    public static final double SHELL_Y_LOWER_BOUND = -30.0;
    public static final double SHELL_Y_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.height;

    public static final String PLAYER_TANK_ID = "player-tank";
    public static final String AI_TANK_ID = "ai-tank";
    public static final String AI_TANK2_ID = "ai-tank-2";

    private final Map<String, Entity> entities;
    private final Map<String, Shell> addedShells;
    private final List<String> entitiesToRemove;
    private final boolean[] playerControls = new boolean[5]; //forward, backward, left, right, shoot
    private boolean escPressed = false;

    public GameState(){
        entities = new HashMap<>();
        addedShells = new HashMap<>();
        entitiesToRemove = new ArrayList<>();
    }

    public void addEntity(Entity entity){ entities.put(entity.getId(), entity); }
    public void addShell(Shell shell){ addedShells.put(shell.getId(), shell); }

    public void markEntityForRemoval(String id) {
        if(!entitiesToRemove.contains(id)){
            entitiesToRemove.add(id);
        }
    }

    public Entity getEntity(String id){ return entities.get(id); }
    public void removeEntity(String id){ entities.remove(id); }

    public void clearEntities(){ entities.clear(); }
    public void clearAddedShells(){ addedShells.clear(); }
    public void clearEntitiesToRemove(){ entitiesToRemove.clear(); }

    public List<Entity> getEntities(){ return entities.values().stream().collect(Collectors.toList()); }
    public List<Shell> getAddedShells(){ return addedShells.values().stream().collect(Collectors.toList()); }
    public List<String> getEntitiesToRemove(){ return entitiesToRemove; }

    public boolean gameOver(){
        return !(entities.containsKey(GameState.PLAYER_TANK_ID) &&
                (entities.containsKey(GameState.AI_TANK_ID) || entities.containsKey(GameState.AI_TANK2_ID)));
    }

    public void setForwardMovement(boolean forwardMovement){
        playerControls[0] = forwardMovement;
    }
    public boolean upPressed(){
        return playerControls[0];
    }

    public void setBackwardMovement(boolean backwardMovement){
        playerControls[1] = backwardMovement;
    }
    public boolean downPressed(){
        return playerControls[1];
    }

    public void setTurnLeft(boolean leftTurn){
        playerControls[2] = leftTurn;
    }
    public boolean leftPressed(){
        return playerControls[2];
    }

    public void setTurnRight(boolean rightTurn){
        playerControls[3] = rightTurn;
    }
    public boolean rightPressed(){
        return playerControls[3];
    }

    public void setFiring(boolean firing){
        playerControls[4] = firing;
    }
    public boolean spacePressed(){
        return playerControls[4];
    }

    public void setEscPressed(boolean pressed){
        escPressed = pressed;
    }
    public boolean escPressed(){
        return escPressed;
    }
}
