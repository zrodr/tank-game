package edu.proj.tankgame.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    private final String id;
    private double x;
    private double y;
    private double angle;
    private List<CollisionHandler> collisionHandlers;

    public Entity(String id, double x, double y, double angle) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.angle = angle;

        collisionHandlers = new ArrayList<>();
        collisionHandlers.add(new DefaultHandler());
        collisionHandlers.add(new TankTankHandler());
        collisionHandlers.add(new TankShellHandler());
        collisionHandlers.add(new TankWallHandler());
        collisionHandlers.add(new ShellWallHandler());
        collisionHandlers.add(new ShellShellHandler());
    }

    public String getId() { return id; }

    public double getX() { return x; }
    protected void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }
    protected void setY(double y) {
        this.y = y;
    }

    protected void setAngle(double angle) { this.angle = angle; }
    public double getAngle() {
        return angle;
    }

    protected List<CollisionHandler> getCollisionHandlers(){
        return collisionHandlers;
    }

    public abstract void move(GameState gameState);
    public abstract void checkBounds(GameState gameState);
    public abstract void handleCollision(GameState gameState, Entity entity);
    public abstract double getXBound();
    public abstract double getYBound();

    protected void moveForward(double moveSpeed) {
        x += moveSpeed * Math.cos(angle);
        y += moveSpeed * Math.sin(angle);
    }

    protected void moveBackward(double moveSpeed) {
        x -= moveSpeed * Math.cos(angle);
        y -= moveSpeed * Math.sin(angle);
    }

    protected void turnLeft(double turnSpeed) {
        angle -= turnSpeed;
    }

    protected void turnRight(double turnSpeed) {
        angle += turnSpeed;
    }
}
