package edu.proj.tankgame.controller;

import edu.proj.tankgame.model.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {
    private final GameState gameState;

    public GameKeyListener(GameState gameState){
        this.gameState = gameState;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();

        if(keycode == KeyEvent.VK_UP){
            gameState.setForwardMovement(true);
        }
        if(keycode == KeyEvent.VK_DOWN){
            gameState.setBackwardMovement(true);
        }
        if(keycode == KeyEvent.VK_LEFT){
            gameState.setTurnLeft(true);
        }
        if(keycode == KeyEvent.VK_RIGHT){
            gameState.setTurnRight(true);
        }
        if(keycode == KeyEvent.VK_SPACE){
            gameState.setFiring(true);
        }
        if(keycode == KeyEvent.VK_ESCAPE){
            gameState.setEscPressed(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keycode = e.getKeyCode();
        if(keycode == KeyEvent.VK_UP){
            gameState.setForwardMovement(false);
        }
        if(keycode == KeyEvent.VK_DOWN){
            gameState.setBackwardMovement(false);
        }
        if(keycode == KeyEvent.VK_LEFT){
            gameState.setTurnLeft(false);
        }
        if(keycode == KeyEvent.VK_RIGHT){
            gameState.setTurnRight(false);
        }
        if(keycode == KeyEvent.VK_SPACE){
            gameState.setFiring(false);
        }
        if(keycode == KeyEvent.VK_ESCAPE){
            gameState.setEscPressed(false);
        }
    }
}
