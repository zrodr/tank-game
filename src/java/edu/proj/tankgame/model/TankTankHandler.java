package edu.proj.tankgame.model;

import java.util.Arrays;

public class TankTankHandler extends DefaultHandler  {

    public TankTankHandler(){
        super();
    }

    @Override
    public void performCollision(GameState gameState, Entity e1, Entity e2) {
        if(e1 instanceof Tank && e2 instanceof Tank){
            entities[0] = e1;
            entities[1] = e2;

            double[] distances = new double[4];
            distances[0] = entities[0].getXBound() - entities[1].getX();
            distances[1] = entities[1].getXBound() - entities[0].getX();
            distances[2] = entities[0].getYBound() - entities[1].getY();
            distances[3] = entities[1].getYBound() - entities[0].getY();

            double dist = Arrays.stream(distances).min().getAsDouble();

            if(dist == distances[0]){
                entities[0].setX(entities[0].getX() - dist / 2);
                entities[1].setX(entities[1].getX() + dist / 2);
            }
            else if(dist == distances[1]){
                entities[0].setX(entities[0].getX() + dist / 2);
                entities[1].setX(entities[1].getX() - dist / 2);
            }
            else if(dist == distances[2]){
                entities[0].setY(entities[0].getY() - dist / 2);
                entities[1].setY(entities[1].getY() + dist / 2);
            }
            else{
                entities[0].setY(entities[0].getY() + dist / 2);
                entities[1].setY(entities[1].getY() - dist / 2);
            }
        }
    }
}
