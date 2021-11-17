/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.badlogicgames.superjumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class Player extends DynamicGameObject {
	public static final int PLAYER_STATE_IDLE = 0;
	public static final int PLAYER_STATE_FALL = 1;
	public static final int PLAYER_STATE_HIT = 2;
	public static final int PLAYER_STATE_CLIMB = 3;
	public static final int PLAYER_STATE_FAIL = 4;
	public static final float PLAYER_WIDTH = 0.8f;
	public static final float PLAYER_HEIGHT = 0.8f;
	public boolean isLookingLeft = false;

	public World world;

	String name;
	int score;
	int state;
	float stateTime;
	float failTime;
	float failHeight;
	int KEY_LEFT;
	int KEY_RIGHT;
	boolean IsFailed = false;

	public Player(String name, float x, float y, World world, int left, int right) {
		super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
		this.name = name;
		this.world = world;
		state = PLAYER_STATE_FALL;
		stateTime = 0;
		score = 1;
		this.KEY_LEFT = left;
		this.KEY_RIGHT = right;
	}

	public void update (float deltaTime) {
		//키 입력 하면 올라가는 state 로 변경
		if(Gdx.input.isKeyJustPressed(KEY_LEFT) && state != PLAYER_STATE_CLIMB
				&& state != PLAYER_STATE_FAIL && isCorrectMoveToLeft()) {
			state = PLAYER_STATE_CLIMB;
		}
		if(Gdx.input.isKeyJustPressed(KEY_RIGHT) && state != PLAYER_STATE_CLIMB
				&& state != PLAYER_STATE_FAIL && isCorrectMoveToRight()) {
			state = PLAYER_STATE_CLIMB;
		}
		//한칸 올라감
		if(state == PLAYER_STATE_CLIMB){
			position.lerp(new Vector2(world.stairs.get(score).position.x,
					world.stairs.get(score).position.y+0.7f), 25*deltaTime);
		}
		//한칸 다 올라갔으면 스코어++ 후 다 올라간 state 로 바꿈
		if(state == PLAYER_STATE_CLIMB && (world.stairs.get(score).position.x-0.1
				< position.x && world.stairs.get(score).position.x+0.1 > position.x)){
			score++;
			state = PLAYER_STATE_IDLE;
		}

		//실패시 왼쪽 또는 오른쪽으로 갔다가
		if(state == PLAYER_STATE_FAIL){
			if(isLookingLeft)
				position.lerp(new Vector2(world.stairs.get(score).position.x-2, position.y), 25*deltaTime);
			else
				position.lerp(new Vector2(world.stairs.get(score).position.x+2, position.y), 25*deltaTime);
		}
		//떨어짐
		if(state == PLAYER_STATE_FAIL && failTime+0.5 < stateTime){
				position.lerp(new Vector2(position.x, failHeight-15f), deltaTime);
		}

		bounds.x = position.x - bounds.width / 2;
		bounds.y = position.y - bounds.height / 2;

		stateTime += deltaTime;
	}

	public boolean isCorrectMoveToLeft(){
		isLookingLeft = true;
		if(world.stairs.get(score).position.x < position.x){
			return true;
		}
		else{
			failRoutine();
			return false;
		}

	}

	public boolean isCorrectMoveToRight(){
		isLookingLeft = false;
		if(world.stairs.get(score).position.x > position.x){
			return true;
		}
		else {
			failRoutine();
			return false;
		}

	}

	public void failRoutine(){
		failTime = stateTime;
		failHeight = position.y;
		state = PLAYER_STATE_FAIL;
		System.out.println(this.name + "의 최종 점수 = " + score + ", 생존 시간 = " + failTime);
	}



	public void hitSquirrel () {
		velocity.set(0, 0);
		state = PLAYER_STATE_HIT;
		stateTime = 0;
	}


}
