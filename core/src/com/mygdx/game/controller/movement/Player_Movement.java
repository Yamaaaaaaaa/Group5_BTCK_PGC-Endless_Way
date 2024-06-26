package com.mygdx.game.controller.movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.controller.CheckCollision;
import com.mygdx.game.controller.Direction;
import com.mygdx.game.controller.Movement;
import com.mygdx.game.model.entity.Entity;
import com.mygdx.game.model.entity.Entity_Status;
import com.mygdx.game.view.GameScreen;

public class Player_Movement implements Movement {
    //Singleton
    private static Player_Movement instance;
    private Player_Movement(){}
    public static Player_Movement getInstance(){
        if(instance==null){
            instance = new Player_Movement();
        }
        return instance;
    }

    public boolean upKey, downKey, leftKey, rightKey;
    public boolean attack;
    private void updateDirection(Entity entity){
        upKey = Gdx.input.isKeyPressed(Input.Keys.W);
        rightKey = Gdx.input.isKeyPressed(Input.Keys.D);
        leftKey = Gdx.input.isKeyPressed(Input.Keys.A);
        downKey = Gdx.input.isKeyPressed(Input.Keys.S);


        attack = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);

        if(leftKey || rightKey || upKey || downKey) {
            entity.status = Entity_Status.WALKING;
            if(leftKey && !rightKey && upKey && !downKey) {
                entity.direction = Direction.UPLEFT;
            }
            else if(leftKey && !rightKey && !upKey && downKey) {
                entity.direction = Direction.DOWNLEFT;
            }
            else if(!leftKey && rightKey && upKey && !downKey) {
                entity.direction = Direction.UPRIGHT;
            }
            else if(!leftKey && rightKey && !upKey && downKey) {
                entity.direction = Direction.DOWNRIGHT;
            }
            else if(leftKey) {
                entity.direction = Direction.LEFT;
            }
            else if(rightKey) {
                entity.direction = Direction.RIGHT;
            }
            else if(upKey) {
                entity.direction = Direction.UP;
            }
            else if(downKey){
                entity.direction = Direction.DOWN;
            }
        } else if (attack) {
            entity.status = Entity_Status.ATTACKING;
        } else {
            entity.status = Entity_Status.IDLE;
        }



    }

    private CheckCollision checkCollision;

    @Override
    public void move(Entity entity, GameScreen gameScreen, CheckCollision checkCollision) {
        updateDirection(entity);
        this.checkCollision = checkCollision;

        if(entity.status == Entity_Status.WALKING){
            float oldX, oldY, x, y;
            oldX = x = entity.getX();
            oldY = y = entity.getY();
            // Vector2 oldPosition = new Vector2(x, y);
            if(entity.direction == Direction.UP){
                y += entity.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
            }
            if(entity.direction == Direction.DOWN){
                y -= entity.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
            }
            if(entity.direction == Direction.LEFT){
                x -= entity.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
            }
            if(entity.direction == Direction.RIGHT){
                x += entity.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
            }
            if(entity.direction == Direction.UPLEFT){
                y += entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
                x -= entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
            }
            if(entity.direction == Direction.UPRIGHT){
                y += entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
                x += entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
            }
            if(entity.direction == Direction.DOWNLEFT){
                y -= entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
                x -= entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
            }
            if(entity.direction == Direction.DOWNRIGHT){
                y -= entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
                x += entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
            }

            //  Vector2 newPosition = new Vector2(x, y);
            entity.setPosision(x, y);
            checkCollision.check_KnightWithMap(Gdx.graphics.getDeltaTime(), oldX, oldY);
        }
    }
}
