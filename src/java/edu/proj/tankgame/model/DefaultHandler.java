package edu.proj.tankgame.model;

public class DefaultHandler implements CollisionHandler {
    protected Entity[] entities;

    public DefaultHandler(){
        entities = new Entity[2];
    }

    @Override
    public void performCollision(GameState gameState, Entity e1, Entity e2) {
    }
}
