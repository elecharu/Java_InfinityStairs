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

public class Stair extends DynamicGameObject {
	public static final float STAIR_WIDTH = 2;
	public static final float STAIR_HEIGHT = 0.5f;
	public static final int STAIR_TYPE_STATIC = 0;
	public static final int STAIR_TYPE_MOVING = 1;
	public static final int STAIR_STATE_NORMAL = 0;
	public static final int STAIR_STATE_PULVERIZING = 1;
	public static final float STAIR_PULVERIZE_TIME = 0.2f * 4;
	public static final float STAIR_VELOCITY = 2;

	int type;
	int state;
	float stateTime;

	public Stair(float x, float y) {
		super(x, y, STAIR_WIDTH, STAIR_HEIGHT);
		this.state = STAIR_STATE_NORMAL;
		this.stateTime = 0;
	}

	public void update (float deltaTime) {
		if (type == STAIR_TYPE_MOVING) {
			position.add(velocity.x * deltaTime, 0);
			bounds.x = position.x - STAIR_WIDTH / 2;
			bounds.y = position.y - STAIR_HEIGHT / 2;

			if (position.x < STAIR_WIDTH / 2) {
				velocity.x = -velocity.x;
				position.x = STAIR_WIDTH / 2;
			}
			if (position.x > World.WORLD_WIDTH - STAIR_WIDTH / 2) {
				velocity.x = -velocity.x;
				position.x = World.WORLD_WIDTH - STAIR_WIDTH / 2;
			}
		}

		stateTime += deltaTime;
	}

	public void pulverize () {
		state = STAIR_STATE_PULVERIZING;
		stateTime = 0;
		velocity.x = 0;
	}
}
