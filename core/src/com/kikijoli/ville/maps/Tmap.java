/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.maps;

import box2dLight.Light;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.kikijoli.ville.listeners.GeneralKeyListener;
import com.kikijoli.ville.manager.CameraManager;
import static com.kikijoli.ville.manager.CameraManager.camera;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.LockManager;
import com.kikijoli.ville.manager.MessageManager;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.manager.WaterManager;
import com.kikijoli.ville.manager.ShaderManager;
import com.kikijoli.ville.manager.StageManager;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.util.Constantes;
import java.util.ArrayList;

/**
 *
 * @author troïmaclure
 */
public class Tmap implements Screen {

    public static World world;
    public static RayHandler ray;
    public static ArrayList<Light> lights = new ArrayList<>();
    public static ShapeRenderer shapeRenderer;
    public static SpriteBatch spriteBatch;
    public static SpriteBatch spriteBatchDefaultColor;
    public static Vector3 worldCoordinates = new Vector3();
    public static Stage stage;
    public static FPSLogger fps;
    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public static RayHandler getRay() {
        if (ray == null) {
            ray = new RayHandler(getWorld());
        }
        return ray;
    }

    public static World getWorld() {
        if (world == null) {
            world = new World(new Vector2(0, 0), true);
        }
        return world;
    }

    public static void removeBoxs(Rectangle rectangle) {
        Array<Body> bodies = new Array<>();
        getWorld().getBodies(bodies);
        Body destroy = null;
        for (Body body : bodies) {
            if (Intersector.overlaps((Rectangle) body.getUserData(), rectangle)) {
                destroy = body;
                break;

            }
        }
        getWorld().destroyBody(destroy);
        destroy.setUserData(null);
        destroy = null;
    }

    public static void addBox(int x, int y) {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(x + 32, y + 32));

        Body groundBody = getWorld().createBody(groundBodyDef);

        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(32, 32);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();
        groundBody.setUserData(new Rectangle(x, y, Constantes.TILESIZE, Constantes.TILESIZE));
    }

    public Tmap() {
        StageManager.load(1);
//        GridManager.initialize(100, 100, Constantes.TILESIZE);
    }

    @Override
    public void show() {
        fps = new FPSLogger();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        spriteBatch = new SpriteBatch();
        spriteBatchDefaultColor = new SpriteBatch();
        Gdx.input.setInputProcessor(new InputMultiplexer(new GeneralKeyListener()));
        CameraManager.initialize(Constantes.TILESIZE * 15, Constantes.TILESIZE * 10);
        EntiteManager.initialize();
        test();
    }

    @Override
    public void render(float delta) {
        fps.log();
        ShaderManager.step();
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        CameraManager.tour();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatchDefaultColor.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        background();
        road();
        water();

        spriteBatch.begin();
        MessageManager.drawIndicators();
        LockManager.tour();
        EntiteManager.tour();
        ParticleManager.tour(delta);

        spriteBatch.flush();
        spriteBatch.end();

        getRay().setCombinedMatrix(camera.combined,
                camera.position.x, camera.position.y,
                camera.viewportWidth * camera.zoom,
                camera.viewportHeight * camera.zoom);
        getRay().update();
        debugRenderer.render(world, camera.combined);

    }

    private void road() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        WaterManager.tour();
        shapeRenderer.flush();
        shapeRenderer.end();
    }

    private void water() {

        WaterManager.drawWater();

    }

    private void background() {

        spriteBatch.begin();
        GridManager.tour();
        spriteBatch.flush();
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    private void test() {

    }

}
