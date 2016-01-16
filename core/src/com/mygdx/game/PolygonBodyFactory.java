package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by till on 15.01.16.
 */
public class PolygonBodyFactory {
    World world;

    public PolygonBodyFactory(World world) {
        this.world = world;
    }

    public Body createPolygon(Vector2 position, float[] vertices) {

        BodyDef polygonBodyDef = new BodyDef();
        polygonBodyDef.type = BodyDef.BodyType.DynamicBody;
        polygonBodyDef.position.set(new Vector2(0, 0));
        Body polygonBody = world.createBody(polygonBodyDef);

        PolygonShape polygon = new PolygonShape();
        polygon.set(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygon;
        fixtureDef.density = 4f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 1f; // Make it bounce a little bit

        Fixture fixture = polygonBody.createFixture(fixtureDef);
        polygon.dispose();

        return polygonBody;

    }
}
