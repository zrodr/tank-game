package edu.proj.tankgame.controller;

import edu.proj.tankgame.model.*;
import edu.proj.tankgame.view.MainView;
import edu.proj.tankgame.view.RunGameView;
import edu.proj.tankgame.view.StartMenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

/**
 * GameDriver is the primary controller class for the tank game. The game is launched from GameDriver.main, and
 * GameDriver is responsible for running the game loop while coordinating the views and the data models.
 */
public class GameDriver {
    private final GameState gameState;
    private final MainView mainView;
    private final RunGameView runGameView;
    private int gameOverCountDown = 300;

    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            if(actionCommand.equals(StartMenuView.START_BUTTON_ACTION_COMMAND)){
                mainView.setScreen(MainView.Screen.RUN_GAME_SCREEN);
                runGame();
            } else if (actionCommand.equals(StartMenuView.EXIT_BUTTON_ACTION_COMMAND)){
                mainView.closeGame();
            }
        }
    }

    public GameDriver() {
        gameState = new GameState();
        ButtonListener buttonListener = new ButtonListener();
        GameKeyListener keyListener = new GameKeyListener(gameState);
        mainView = new MainView(buttonListener, keyListener);
        runGameView = mainView.getRunGameView();
    }

    public void start() {
        mainView.setScreen(MainView.Screen.START_MENU_SCREEN);
    }

    private void populateWalls(){
        List<WallImageInfo> wallInfo = WallImageInfo.readWalls();

        for(WallImageInfo info : wallInfo){
            Wall wall = new Wall(info.getX(), info.getY());
            gameState.addEntity(wall);
            runGameView.addDrawableEntity(
                    wall.getId(),
                    info.getImageFile(),
                    wall.getX(),
                    wall.getY(),
                    wall.getAngle());
        }

        wallInfo.clear();
    }

    private void runGame() {
        Tank playerTank = new PlayerTank(
                GameState.PLAYER_TANK_ID,
                RunGameView.PLAYER_TANK_INITIAL_X,
                RunGameView.PLAYER_TANK_INITIAL_Y,
                RunGameView.PLAYER_TANK_INITIAL_ANGLE);
        gameState.addEntity(playerTank);
        runGameView.addDrawableEntity(
                playerTank.getId(),
                RunGameView.PLAYER_TANK_IMAGE_FILE,
                playerTank.getX(),
                playerTank.getY(),
                playerTank.getAngle());

        Tank aiTank = new TurretAITank(
                GameState.AI_TANK_ID,
                RunGameView.AI_TANK_INITIAL_X,
                RunGameView.AI_TANK_INITIAL_Y,
                RunGameView.AI_TANK_INITIAL_ANGLE);
        gameState.addEntity(aiTank);
        runGameView.addDrawableEntity(
                aiTank.getId(),
                RunGameView.AI_TANK_IMAGE_FILE,
                aiTank.getX(),
                aiTank.getY(),
                aiTank.getAngle());

        Tank aiTank2 = new PatrolAITank(
                GameState.AI_TANK2_ID,
                RunGameView.AI_TANK_2_INITIAL_X,
                RunGameView.AI_TANK_2_INITIAL_Y,
                RunGameView.AI_TANK_2_INITIAL_ANGLE);
        gameState.addEntity(aiTank2);
        runGameView.addDrawableEntity(
                aiTank2.getId(),
                RunGameView.AI_TANK_IMAGE_FILE,
                aiTank2.getX(),
                aiTank2.getY(),
                aiTank2.getAngle());

        populateWalls();

        Runnable gameRunner = () -> {
            while (update()) {
                runGameView.repaint();
                try {
                    Thread.sleep(8L);
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
            }

            gameOverCountDown = 300;
            runGameView.reset();
            gameState.clearEntities();
            mainView.setScreen(MainView.Screen.END_MENU_SCREEN);
        };
        new Thread(gameRunner).start();
    }


    private boolean entitiesOverlap (Entity entity1, Entity entity2) {
        return entity1.getX() < entity2.getXBound()
                && entity1.getXBound() > entity2.getX()
                && entity1.getY() < entity2.getYBound()
                && entity1.getYBound() > entity2.getY();
    }


    /*
     * Collision detection avoids double checking pairs that would've already been passed
     * over in previous iterations by beginning the inner loop from 1 position ahead of the outer.
     * This reduces pair-wise operations to only those left unchecked from previous comparisons.
     */
    private void checkCollisions(){
        List<Entity> list = gameState.getEntities();

        for(int y = list.size() - 1; y > 0; y--){
            for(int x = y - 1; x >= 0; x--){
                if(y != x && entitiesOverlap(list.get(y), list.get(x))){
                    list.get(y).handleCollision(gameState, list.get(x));
                }
            }
        }
    }

    /*
     * adds newly fired shells from entity.move() into gameState's map of all entities & runGameView's
     * map of drawable entities.
     */
    private void checkForNewShells(){
        List<Shell> shellsToAdd = gameState.getAddedShells();

        if(!shellsToAdd.isEmpty()) {
            for(Entity entity : shellsToAdd){
                runGameView.addDrawableEntity(
                        entity.getId(),
                        RunGameView.SHELL_IMAGE_FILE,
                        entity.getX(),
                        entity.getY(),
                        entity.getAngle());
                gameState.addEntity(entity);
            }
            shellsToAdd.clear();
            gameState.clearAddedShells();
        }
    }

    /*
     * RunGameView is instructed which animation to play depending on what type of Entity is
     * being removed from the game.
     */
    private void playAnimation(String id){
        if(id.startsWith("shell")){
            runGameView.addAnimation(
                    RunGameView.SHELL_EXPLOSION_ANIMATION,
                    RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                    gameState.getEntity(id).getX(),
                    gameState.getEntity(id).getY());
        }
        else{
            runGameView.addAnimation(
                    RunGameView.BIG_EXPLOSION_ANIMATION,
                    RunGameView.BIG_EXPLOSION_FRAME_DELAY,
                    gameState.getEntity(id).getX(),
                    gameState.getEntity(id).getY());
        }

    }

    private void checkForRemovableEntities(){
        List<String> toRemove = gameState.getEntitiesToRemove();

        if(!toRemove.isEmpty()){
            for(String id : toRemove){
                playAnimation(id);
                gameState.removeEntity(id);
                runGameView.removeDrawableEntity(id);
            }
            toRemove.clear();
            gameState.clearEntitiesToRemove();
        }
    }

    // update should handle one frame of gameplay. All tanks and shells move one step, and all drawn entities
    // should be updated accordingly. It should return true as long as the game continues.
    private boolean update() {
        if(gameState.gameOver()){
            gameOverCountDown--;
            return !gameState.gameOver() || gameOverCountDown > 0;
        }

        if(!gameState.escPressed()){
            //move all drawable entities
            for (Entity entity : gameState.getEntities()) {
                entity.checkBounds(gameState);
                entity.move(gameState);
            }

            checkCollisions();

            checkForNewShells();

            checkForRemovableEntities();

            //tell view where all the drawn images should be
            for(Entity entity : gameState.getEntities()){
                runGameView.setDrawableEntityLocationAndAngle(
                        entity.getId(),
                        entity.getX(),
                        entity.getY(),
                        entity.getAngle());
            }

            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        GameDriver gameDriver = new GameDriver();
        gameDriver.start();
    }
}