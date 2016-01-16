package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    private static final String TAG = ApplicationAdapter.class.getSimpleName();
    World world;
    Box2DDebugRenderer debugRenderer;
    Camera camera;
    PolygonBodyFactory factory;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

        Box2D.init();
        Vector2 gravity = new Vector2(0, -1 );
        boolean doSleep = true;
        world = new World(gravity, doSleep);
        factory = new PolygonBodyFactory(world);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();

        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
// Set our body's starting position in the world
        bodyDef.position.set(0.1f, 1.8f);

// Create our body in the world using our body definition
        Body body = world.createBody(bodyDef);

// Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(0.2f);

// Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 1f; // Make it bounce a little bit

// Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

// Remember to dispose of any shapes after you're done with them!
// BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();




        // Create our body definition
        BodyDef groundBodyDef =new BodyDef();
// Set its world position
        groundBodyDef.position.set(new Vector2(0.5f, -0.5f));

// Create a body from the defintion and add it to the world
        Body groundBody = world.createBody(groundBodyDef);

// Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
// Set the polygon shape as a box which is twice the size of our view port and 20 high
// (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(0.5f, 0.1f);
// Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);
// Clean up after ourselves
        groundBox.dispose();




        BodyDef polygonBodyDef = new BodyDef();
        polygonBodyDef.position.set(new Vector2(0, 0));
        Body polygonBody = world.createBody(polygonBodyDef);
        PolygonShape shape = new PolygonShape();
        shape.set(new float[]{0.1f, 0, 0, -0.1f, 0, 0.1f});
        polygonBody.createFixture(shape, 0);
        shape.dispose();

        Body body1 = factory.createPolygon(new Vector2(-0.5f, 0.5f), new float[]{0.2f, 0.5f, 0.1f, 0.6f, 0.1f, 0.4f});

        body1.applyLinearImpulse(0.0f, 0.05f, 0.15f, 0.5f, true);


//        camera.translate(0.1f, 0, 0);
        camera.update();

	}

	@Override
	public void render () {
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world, camera.combined);
        world.step(1/60f, 6, 2);
    }

}
