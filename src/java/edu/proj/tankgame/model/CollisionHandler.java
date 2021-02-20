package edu.proj.tankgame.model;

/*
 * Extensible Collision model using Strategy pattern. The family of collision handling algorithms all
 * implement this interface.
 */
public interface CollisionHandler {
    void performCollision(GameState gameState, Entity e1, Entity e2);
}
