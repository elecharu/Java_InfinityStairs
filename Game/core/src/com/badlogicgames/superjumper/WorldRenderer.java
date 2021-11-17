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

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WorldRenderer {
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;

	private float[] backgroundOffsets = {0, 0, 0, 0, 0};
	World world;
	OrthographicCamera cam;
	SpriteBatch batch;

	public void setBackgroundOffsets(float[] backgroundOffsets) {
		this.backgroundOffsets = backgroundOffsets;
	}



	public WorldRenderer (SpriteBatch batch, World world) {
		this.world = world;
		this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
		this.batch = batch;
	}

	public void render () {
		if (world.player.position.y > cam.position.y) cam.position.y = world.player.position.y;
		cam.position.x = world.player.position.x;
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		renderBackground();
		renderObjects();
	}

	public void renderBackground () {
		batch.begin();
		float offsetCompensation = (cam.position.y - FRUSTUM_HEIGHT/2);
		batch.draw(Assets.backgrounds[0], cam.position.x - FRUSTUM_WIDTH / 2, backgroundOffsets[0] + offsetCompensation, FRUSTUM_WIDTH,
				FRUSTUM_HEIGHT*2);
		batch.draw(Assets.backgrounds[1], cam.position.x - FRUSTUM_WIDTH / 2, backgroundOffsets[1] + offsetCompensation / 1.15f, FRUSTUM_WIDTH,
				FRUSTUM_HEIGHT*2);
		batch.draw(Assets.backgrounds[1], cam.position.x - FRUSTUM_WIDTH / 2, backgroundOffsets[1] + offsetCompensation / 1.15f + FRUSTUM_HEIGHT*2, FRUSTUM_WIDTH,
				FRUSTUM_HEIGHT*2);
		batch.draw(Assets.backgrounds[2], cam.position.x - FRUSTUM_WIDTH / 2, backgroundOffsets[2] + offsetCompensation / 1.3f, FRUSTUM_WIDTH,
				FRUSTUM_HEIGHT*2);
		batch.draw(Assets.backgrounds[2], cam.position.x - FRUSTUM_WIDTH / 2, backgroundOffsets[2] + offsetCompensation / 1.3f + FRUSTUM_HEIGHT*2, FRUSTUM_WIDTH,
				FRUSTUM_HEIGHT*2);
		batch.draw(Assets.backgrounds[3], cam.position.x - FRUSTUM_WIDTH / 2, backgroundOffsets[3] + offsetCompensation / 1.45f, FRUSTUM_WIDTH,
				FRUSTUM_HEIGHT*2);
		batch.draw(Assets.backgrounds[3], cam.position.x - FRUSTUM_WIDTH / 2, backgroundOffsets[3] + offsetCompensation / 1.45f + FRUSTUM_HEIGHT*2, FRUSTUM_WIDTH,
				FRUSTUM_HEIGHT*2);
		batch.draw(Assets.backgrounds[4], cam.position.x - FRUSTUM_WIDTH / 2, backgroundOffsets[3] + offsetCompensation / 1.65f, FRUSTUM_WIDTH,
				FRUSTUM_HEIGHT*2);
		batch.draw(Assets.backgrounds[4], cam.position.x - FRUSTUM_WIDTH / 2, backgroundOffsets[3] + offsetCompensation / 1.65f + FRUSTUM_HEIGHT*2, FRUSTUM_WIDTH,
				FRUSTUM_HEIGHT*2);

		if(backgroundOffsets[1] + offsetCompensation / 1.15f < cam.position.y - 37.5){
			backgroundOffsets[1] += FRUSTUM_HEIGHT*2;
		}
		if(backgroundOffsets[2] + offsetCompensation / 1.3f < cam.position.y - 37.5){
			backgroundOffsets[2] += FRUSTUM_HEIGHT*2;
		}
		if(backgroundOffsets[3] + offsetCompensation / 1.45f < cam.position.y - 37.5){
			backgroundOffsets[3] += FRUSTUM_HEIGHT*2;
		}
		if(backgroundOffsets[4] + offsetCompensation / 1.65f < cam.position.y - 37.5){
			backgroundOffsets[4] += FRUSTUM_HEIGHT*2;
		}

		batch.end();
	}

	public void renderObjects () {
		batch.enableBlending();
		batch.begin();
		renderStairs();
		renderBob();
		renderItems();
		renderSquirrels();
		batch.end();
	}

	private void renderBob () {
		TextureRegion keyFrame;
		TextureRegion keyFramePlayer2;
		//에니메이션
		switch (world.player.state) {
		case Player.PLAYER_STATE_FALL:
			keyFrame = Assets.bobFall.getKeyFrame(world.player.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case Player.PLAYER_STATE_IDLE:
			keyFrame = Assets.bobJump.getKeyFrame(world.player.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case Player.PLAYER_STATE_HIT:
		default:
			keyFrame = Assets.bobHit;
		}

		switch (world.player2.state) {
			case Player.PLAYER_STATE_FALL:
				keyFramePlayer2 = Assets.bobFall.getKeyFrame(world.player2.stateTime, Animation.ANIMATION_LOOPING);
				break;
			case Player.PLAYER_STATE_IDLE:
				keyFramePlayer2 = Assets.bobJump.getKeyFrame(world.player2.stateTime, Animation.ANIMATION_LOOPING);
				break;
			case Player.PLAYER_STATE_HIT:
			default:
				keyFramePlayer2 = Assets.bobHit;
		}


		//좌우반전
		if (world.player.isLookingLeft)
			batch.draw(keyFrame, world.player.position.x + 0.5f, world.player.position.y - 0.5f, -1, 1);
		else
			batch.draw(keyFrame, world.player.position.x - 0.5f, world.player.position.y - 0.5f, 1, 1);

		if (world.player2.isLookingLeft)
			batch.draw(keyFramePlayer2, world.player2.position.x + 0.5f, world.player2.position.y - 0.5f, -1, 1);
		else
			batch.draw(keyFramePlayer2, world.player2.position.x - 0.5f, world.player2.position.y - 0.5f, 1, 1);
	}

	private void renderStairs () {
		int len = world.stairs.size();
		for (int i = 0; i < len; i++) {
			Stair stair = world.stairs.get(i);
			TextureRegion keyFrame = Assets.platform;
			if (stair.state == Stair.STAIR_STATE_PULVERIZING) {
				keyFrame = Assets.brakingPlatform.getKeyFrame(stair.stateTime, Animation.ANIMATION_NONLOOPING);
			}
			batch.draw(keyFrame, stair.position.x - 1, stair.position.y - 0.25f, 2, 0.5f);
		}
	}

	private void renderItems () {
		int len = world.springs.size();
		for (int i = 0; i < len; i++) {
			Spring spring = world.springs.get(i);
			batch.draw(Assets.spring, spring.position.x - 0.5f, spring.position.y - 0.5f, 1, 1);
		}

		len = world.coins.size();
		for (int i = 0; i < len; i++) {
			Coin coin = world.coins.get(i);
			TextureRegion keyFrame = Assets.coinAnim.getKeyFrame(coin.stateTime, Animation.ANIMATION_LOOPING);
			batch.draw(keyFrame, coin.position.x - 0.5f, coin.position.y - 0.5f, 1, 1);
		}
	}

	private void renderSquirrels () {
		int len = world.squirrels.size();
		for (int i = 0; i < len; i++) {
			Squirrel squirrel = world.squirrels.get(i);
			TextureRegion keyFrame = Assets.squirrelFly.getKeyFrame(squirrel.stateTime, Animation.ANIMATION_LOOPING);
			float side = squirrel.velocity.x < 0 ? -1 : 1;
			if (side < 0)
				batch.draw(keyFrame, squirrel.position.x + 0.5f, squirrel.position.y - 0.5f, side * 1, 1);
			else
				batch.draw(keyFrame, squirrel.position.x - 0.5f, squirrel.position.y - 0.5f, side * 1, 1);
		}
	}

}
