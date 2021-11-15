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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class World {
	public interface WorldListener {
		public void jump ();

		public void highJump ();

		public void hit ();

		public void coin ();
	}

	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 15*1000;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final Vector2 gravity = new Vector2(0, -12);

	public final Player player;
	public final List<Stair> stairs;
	public final List<Spring> springs;
	public final List<Squirrel> squirrels;
	public final List<Coin> coins;
	public Castle castle;
	public final WorldListener listener;
	public final Random rand;

	public float heightSoFar;
	public int score;
	public int state;

	public World (WorldListener listener) {
		this.player = new Player(5, 0.87f, this);
		this.stairs = new ArrayList<Stair>();
		this.springs = new ArrayList<Spring>();
		this.squirrels = new ArrayList<Squirrel>();
		this.coins = new ArrayList<Coin>();
		this.listener = listener;
		rand = new Random();
		generateLevel();

		this.heightSoFar = 0;
		this.score = player.score;
		this.state = WORLD_STATE_RUNNING;
	}

	private void generateLevel () {
		float y = Stair.STAIR_HEIGHT / 2;
		float x = 5;
		stairs.add(new Stair(x, y));
		while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
			y += 0.75;
			if(rand.nextBoolean())
				x = stairs.get(stairs.size()-1).position.x + 1;
			else
				x = stairs.get(stairs.size()-1).position.x - 1;
			stairs.add(new Stair(x, y));
		}
	}

	public void update (float deltaTime, float accelX) {
		updateBob(deltaTime, accelX);
		updatePlatforms(deltaTime);
		updateSquirrels(deltaTime);
		updateCoins(deltaTime);
		if (player.state != Player.PLAYER_STATE_HIT) checkCollisions();
	}

	private void updateBob (float deltaTime, float accelX) {

		player.update(deltaTime);
		score = player.score;
		heightSoFar = Math.max(player.position.y, heightSoFar);
	}

	private void updatePlatforms (float deltaTime) {
		int len = stairs.size();
		for (int i = 0; i < len; i++) {
			Stair stair = stairs.get(i);
			stair.update(deltaTime);
			if (stair.state == Stair.STAIR_STATE_PULVERIZING && stair.stateTime > Stair.STAIR_PULVERIZE_TIME) {
				stairs.remove(stair);
				len = stairs.size();
			}
		}
	}

	private void updateSquirrels (float deltaTime) {
		int len = squirrels.size();
		for (int i = 0; i < len; i++) {
			Squirrel squirrel = squirrels.get(i);
			squirrel.update(deltaTime);
		}
	}

	private void updateCoins (float deltaTime) {
		int len = coins.size();
		for (int i = 0; i < len; i++) {
			Coin coin = coins.get(i);
			coin.update(deltaTime);
		}
	}

	private void checkCollisions () {
		checkPlatformCollisions();
		checkSquirrelCollisions();
		checkItemCollisions();
	}

	private void checkPlatformCollisions () {

	}



	private void checkSquirrelCollisions () {
		int len = squirrels.size();
		for (int i = 0; i < len; i++) {
			Squirrel squirrel = squirrels.get(i);
			if (squirrel.bounds.overlaps(player.bounds)) {
				player.hitSquirrel();
				listener.hit();
			}
		}
	}

	private void checkItemCollisions () {
		int len = coins.size();
		for (int i = 0; i < len; i++) {
			Coin coin = coins.get(i);
			if (player.bounds.overlaps(coin.bounds)) {
				coins.remove(coin);
				len = coins.size();
				listener.coin();
				score += Coin.COIN_SCORE;
			}

		}

		if (player.velocity.y > 0) return;

		len = springs.size();
		for (int i = 0; i < len; i++) {
			Spring spring = springs.get(i);
			if (player.position.y > spring.position.y) {
				if (player.bounds.overlaps(spring.bounds)) {
					listener.highJump();
				}
			}
		}
	}

}
