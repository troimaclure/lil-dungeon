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
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.kikijoli.ville.automation.common.GoTo;
import com.kikijoli.ville.drawable.entite.build.Key;
import com.kikijoli.ville.listeners.GeneralKeyListener;
import com.kikijoli.ville.manager.ProjectileManager;
import com.kikijoli.ville.manager.CameraManager;
import static com.kikijoli.ville.manager.CameraManager.camera;
import com.kikijoli.ville.manager.ColorManager;
import com.kikijoli.ville.manager.DrawManager;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.LockManager;
import com.kikijoli.ville.manager.MessageManager;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.manager.WaterManager;
import com.kikijoli.ville.manager.ShaderManager;
import com.kikijoli.ville.manager.StageManager;
import com.kikijoli.ville.manager.HudManager;
import com.kikijoli.ville.manager.RankManager;
import com.kikijoli.ville.manager.SpellManager;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.SetLevel;
import com.kikijoli.ville.util.TextureUtil;
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
    public static ShapeRenderer hudShapeRenderer;
    public static SpriteBatch spriteBatch;
    public static SpriteBatch hudBatch;
    public static SpriteBatch spriteBatchDefaultColor;
    public static Vector3 worldCoordinates = new Vector3();
    public static Stage stage;
    public static FPSLogger fps;
    public static float LINE_WIDTH = 1;
    public static SetLevel setLevel = null;
    public static boolean settingLevel;
    private static final String ESCAPE_TO_RAGE_QUIT = "PRESS ESCAPE TO RAGE QUIT";
    private static final String ENTER_TO_RELOAD = "PRESS ENTER TO RELOAD";
    private static final String TIME = "TIME ";
    private static final String SCORE = "SCORE : ";
    private static final String LEVEL_SCORE = "LEVEL SCORE : ";
    private Sprite arrowCount = new Sprite(TextureUtil.getTexture("sprite/arrow.png"));

    public static RayHandler getRay() {
        if (ray == null) {
            ray = new RayHandler(getWorld());
            ray.setCulling(true);
            ray.setAmbientLight(0, 0, 0, 0.2f);
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
    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public Tmap() {
        StageManager.loadFromXml("1");
    }

    @Override
    public void show() {
        Gdx.input.setCursorCatched(true);
        fps = new FPSLogger();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        hudShapeRenderer = new ShapeRenderer();
        hudShapeRenderer.setAutoShapeType(true);
        spriteBatch = new SpriteBatch();
        hudBatch = new SpriteBatch();
        spriteBatchDefaultColor = new SpriteBatch();
        Gdx.input.setInputProcessor(new InputMultiplexer(new GeneralKeyListener()));
        CameraManager.initialize(Constantes.TILESIZE * 15, Constantes.TILESIZE * 10);
        EntiteManager.initialize();
        test();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glLineWidth(LINE_WIDTH);
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
        drawShapes();
        drawSprites(delta);
        drawHud();

        getRay().setCombinedMatrix(camera.combined,
                camera.position.x, camera.position.y,
                camera.viewportWidth * camera.zoom,
                camera.viewportHeight * camera.zoom);

        getRay().update();
        if (setLevel != null) {
            settingLevel = true;
        }
        if (settingLevel) {
            setLevel();
        }

//        debug();
    }

    private void drawSprites(float delta) {
        spriteBatch.begin();
        MessageManager.tour();
        LockManager.tour();
        EntiteManager.tour();
        ParticleManager.tour(delta);
        DrawManager.tour();
        ProjectileManager.tour();
        SpellManager.tour();
        StageManager.tour();
        spriteBatch.flush();
        spriteBatch.end();

    }

    private void drawHud() {
        hudShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        HudManager.drawShape();
        if (EntiteManager.playerDead) {
            drawDeadBackground();
        }
        hudShapeRenderer.flush();
        hudShapeRenderer.end();
        hudShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        HudManager.drawLines();

        hudShapeRenderer.flush();
        hudShapeRenderer.end();
        hudBatch.begin();
        drawKeys();
        drawArrowCount();
        HudManager.drawSprite();
        drawTime();
        if (EntiteManager.playerDead) {
            drawDeadMessage();
        }
        hudBatch.flush();
        hudBatch.end();

    }

    private void drawShapes() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        DrawManager.drawShape();
        EntiteManager.player.draw(shapeRenderer);

        shapeRenderer.flush();
        shapeRenderer.end();

    }

    private void debug() {
        debugRenderer.render(world, camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        if (GoTo.path != null) {
            GoTo.path.forEach((t) -> {
                shapeRenderer.rect(t.getX(), t.getY(), t.getWidth(), t.getHeight());
            });
        }
        EntiteManager.entites.forEach((entite) -> {
            shapeRenderer.circle(entite.anchor.x, entite.anchor.y, entite.anchor.radius);
        });
        DrawManager.entites.forEach((entite) -> {
            Rectangle r = entite.getBoundingRectangle();
            shapeRenderer.rect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        });
        SpellManager.spells.forEach((t) -> {
            shapeRenderer.rect(t.getAnchors().getX(), t.getAnchors().getY(), t.getAnchors().getWidth(), t.getAnchors().getHeight());
        });
        LockManager.doors.forEach((t) -> {
            shapeRenderer.circle(t.anchor.x, t.anchor.y, t.anchor.radius);
            shapeRenderer.rect(t.getBoundingRectangle().x, t.getBoundingRectangle().y, t.getBoundingRectangle().width, t.getBoundingRectangle().height);
        });
        shapeRenderer.flush();
        shapeRenderer.end();
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

    private void setLevel() {
        hudShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        hudShapeRenderer.setColor(Color.BLACK);
        hudShapeRenderer.rect(setLevel.rectangle.x, setLevel.rectangle.y, setLevel.rectangle.width, setLevel.rectangle.height);
        hudShapeRenderer.flush();
        hudShapeRenderer.end();
        setLevel.setting();
    }

    private void drawDeadBackground() {
        hudShapeRenderer.setColor(Color.RED);
        hudShapeRenderer.rect(0, Gdx.graphics.getHeight() / 2 - 225, Gdx.graphics.getWidth(), 400);
    }

    private void drawDeadMessage() {
        MessageManager.segoe.getData().setScale(2);
        GlyphLayout layout = new GlyphLayout(MessageManager.segoe, ENTER_TO_RELOAD);
        MessageManager.segoe.setColor(Color.BLACK);
        float fontX = (Gdx.graphics.getWidth() - layout.width) / 2;
        float fontY = (Gdx.graphics.getHeight() + layout.height) / 2;
        MessageManager.segoe.draw(hudBatch, ENTER_TO_RELOAD, fontX, fontY);
        layout = new GlyphLayout(MessageManager.segoe, ESCAPE_TO_RAGE_QUIT);
        fontX = (Gdx.graphics.getWidth() - layout.width) / 2;
        fontY = (float) ((Gdx.graphics.getHeight() + layout.height) / 2 - layout.height * 1.2);
        MessageManager.segoe.draw(hudBatch, ESCAPE_TO_RAGE_QUIT, fontX, fontY);
    }

    private void drawTime() {
        MessageManager.segoe.getData().setScale(1f);
        MessageManager.segoe.setColor(Color.RED);
        float fontX = 50;
        float fontY = (Gdx.graphics.getHeight() - 50);
        MessageManager.SHOWG.draw(hudBatch, TIME + Integer.toString(StageManager.stopwatch), fontX, fontY);
        MessageManager.SHOWG.draw(hudBatch, SCORE + Integer.toString(RankManager.point), fontX + 200, fontY);
        MessageManager.SHOWG.draw(hudBatch, LEVEL_SCORE + Integer.toString(RankManager.currentStagePoint), fontX + 450, fontY);
    }

    private void drawKeys() {
        int i = 1;
        hudBatch.setColor(Color.GOLD);
        for (Key key : EntiteManager.keys) {
            hudBatch.draw(key.getTexture(), Gdx.graphics.getWidth() - (i++ * (Constantes.TILESIZE)), Constantes.TILESIZE, Constantes.TILESIZE / 2, Constantes.TILESIZE / 2);
        }
    }

    private void drawArrowCount() {
        hudBatch.setColor(ColorManager.getTextureColor());
        hudBatch.draw(arrowCount.getTexture(), Gdx.graphics.getWidth() - Constantes.TILESIZE * 2, Gdx.graphics.getHeight() - Constantes.TILESIZE * 1.5f, Constantes.TILESIZE, Constantes.TILESIZE);
        MessageManager.SHOWG.draw(hudBatch, "x " + EntiteManager.arrowCount, Gdx.graphics.getWidth() - Constantes.TILESIZE, Gdx.graphics.getHeight() - Constantes.TILESIZE);
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
