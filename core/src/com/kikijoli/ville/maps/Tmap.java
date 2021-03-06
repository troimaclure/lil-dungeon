/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.maps;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.object.Key;
import com.kikijoli.ville.listeners.GeneralKeyListener;
import com.kikijoli.ville.manager.ProjectileManager;
import com.kikijoli.ville.manager.CameraManager;
import static com.kikijoli.ville.manager.CameraManager.camera;
import com.kikijoli.ville.manager.CheckpointManager;
import com.kikijoli.ville.manager.CountManager;
import com.kikijoli.ville.manager.DrawManager;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.LockManager;
import com.kikijoli.ville.manager.MessageManager;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.manager.WaterManager;
import com.kikijoli.ville.manager.ShaderManager;
import com.kikijoli.ville.manager.StageManager;
import com.kikijoli.ville.manager.HudManager;
import com.kikijoli.ville.manager.ObjectManager;
import com.kikijoli.ville.manager.RankManager;
import com.kikijoli.ville.manager.StateManager;
import com.kikijoli.ville.manager.ThemeManager;
import com.kikijoli.ville.manager.WeatherManager;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.pathfind.Tile;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.MathUtils;
import com.kikijoli.ville.util.SetLevel;

/**
 *
 * @author troïmaclure
 */
public class Tmap implements Screen {

    public static World world;
    public static RayHandler ray;
    public static ShapeRenderer shapeRenderer;
    public static ShapeRenderer hudShapeRenderer;
    public static SpriteBatch spriteBatch;
    public static SpriteBatch hudBatch;
    public static Vector3 worldCoordinates = new Vector3();
    public static Stage stage;
    public static FPSLogger fps;
    public static float LINE_WIDTH = 2;
    public static SetLevel setLevel = null;
    public static boolean settingLevel;
    private static final String ESCAPE_TO_RAGE_QUIT = "PRESS ESCAPE TO RAGE QUIT";
    private static final String ENTER_TO_RELOAD = "PRESS ENTER TO RELOAD";
    private static final String TIME = "TIME ";
    private static final String SCORE = "SCORE : ";
    private static final String LEVEL_SCORE = "LEVEL SCORE : ";
    public static float delta;
    public static Rectangle questRectangle = new Rectangle(Gdx.graphics.getWidth() - 350, Constantes.TILESIZE, 500, Constantes.TILESIZE);
//    public static ShadowFBO shadowFBO = new ShadowFBO();

    public static RayHandler getRay() {
        if (ray == null) {
            ray = new RayHandler(getWorld(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            ray.setCulling(true);

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
            if (body.getUserData() instanceof Rectangle) {
                if (Intersector.overlaps((Rectangle) body.getUserData(), rectangle)) {
                    destroy = body;
                    getWorld().destroyBody(destroy);
                    destroy.setUserData(null);
                    destroy = null;
                }
            }
        }
    }

    public static void removeAllBoxs() {
        Array<Body> bodies = new Array<>();
        getWorld().getBodies(bodies);
        Body destroy = null;
        for (Body body : bodies) {
            destroy = body;
            getWorld().destroyBody(destroy);
            destroy.setUserData(null);
            destroy = null;
        }

    }

    public static void addBox(float x, float y) {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(x + 32, y + 32));
        Body groundBody = getWorld().createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(32, 32);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();
        groundBody.setUserData(new Rectangle(x, y, Constantes.TILESIZE, Constantes.TILESIZE));
    }

    public static Body addBody(Entite entite) {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(entite.getX() + 32, entite.getY() + 32));
        Body body = getWorld().createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(entite.getWidth() / 2, entite.getHeight() / 2);
        body.createFixture(groundBox, 0.0f);
        groundBox.dispose();
        body.setUserData(entite);
        return body;
    }

    public static void addBox(float x, float y, float width, float height) {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(x + width * .5f, y + height * .5f));
        Body groundBody = getWorld().createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(width * .5f, height * .5f);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();
        groundBody.setUserData(new Rectangle(x, y, width, height));
    }

    public static void drawSprites() {

        EntiteManager.draw();
        CheckpointManager.draw();
        ObjectManager.draw();
        LockManager.draw();
        DrawManager.draw();

        ProjectileManager.draw();

    }

    private static void drawLights() {
        getRay().setCombinedMatrix(camera.combined,
            camera.position.x, camera.position.y,
            camera.viewportWidth * camera.zoom,
            camera.viewportHeight * camera.zoom);

        getRay().updateAndRender();
    }

    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public Tmap() {
        StageManager.loadFromXml("2");
    }

    @Override
    public void show() {

//        Gdx.input.setCursorCatched(true);
        fps = new FPSLogger();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        hudShapeRenderer = new ShapeRenderer();
        hudShapeRenderer.setAutoShapeType(true);
        spriteBatch = new SpriteBatch();
        hudBatch = new SpriteBatch();
        Gdx.input.setInputProcessor(new InputMultiplexer(new GeneralKeyListener()));
        CameraManager.initialize(Constantes.TILESIZE * 15, Constantes.TILESIZE * 10);
        EntiteManager.initialize();

        test();
    }

    @Override
    public void render(float delta) {

        Tmap.delta = delta;
        Gdx.gl.glLineWidth(LINE_WIDTH);
        fps.log();
        Gdx.gl.glEnable(0x0BA1);
        Gdx.gl20.glClearColor(ThemeManager.currentTheme.getFontColor().r, ThemeManager.currentTheme.getFontColor().g, ThemeManager.currentTheme.getFontColor().b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        RankManager.tour();
        ShaderManager.step();
        CameraManager.tour();
        EntiteManager.tour();
        StateManager.tour();
        CountManager.tour();
        CheckpointManager.tour();
        ObjectManager.tour();
        ProjectileManager.tour();
        StageManager.tour();

        spriteBatch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        draw();

        if (setLevel != null) {
            settingLevel = true;
        }
        if (settingLevel) {
            setLevel();
        }

        MessageManager.draw();
        drawHud();
        WeatherManager.tour();
//        debug();
    }

    private void draw() {

        StageManager.tiledMapRenderer.setView(camera);
        StageManager.tiledMapRenderer.render(new int[]{0, 1});
        WaterManager.drawWater();
        StageManager.tiledMapRenderer.render(new int[]{2, 4});

        drawShapes();
        spriteBatch.begin();

        drawSprites();
        ParticleManager.draw(Tmap.delta);
        spriteBatch.flush();
        spriteBatch.end();
        StageManager.tiledMapRenderer.render(new int[]{5});
        drawLights();
        spriteBatch.begin();
        EntiteManager.player.postDraw(Tmap.spriteBatch);
        WeatherManager.draw();
        spriteBatch.flush();
        spriteBatch.end();

    }

    private void drawHud() {

        hudShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        hudShapeRenderer.setColor(Color.WHITE);
        HudManager.drawShape();
        if (EntiteManager.playerDead) {
            drawDeadBackground();
        }
        hudShapeRenderer.setColor(Color.BLACK);
        hudShapeRenderer.rect(questRectangle.x, questRectangle.y, questRectangle.width, questRectangle.height);
        hudShapeRenderer.flush();
        hudShapeRenderer.end();

        hudBatch.begin();

        HudManager.drawSprite();

        drawKeys();
        drawHudMessage();

        if (EntiteManager.playerDead) {
            drawDeadMessage();
        }
        ParticleManager.drawFixed(delta);
        hudBatch.flush();
        hudBatch.end();
        hudShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        hudShapeRenderer.setColor(Color.WHITE);
        hudShapeRenderer.rect(questRectangle.x, questRectangle.y, questRectangle.width, questRectangle.height);

        HudManager.drawLines();

        hudShapeRenderer.flush();
        hudShapeRenderer.end();
    }

    private void drawShapes() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        DrawManager.drawShapeFilled();
        EntiteManager.player.draw(shapeRenderer);

        shapeRenderer.flush();
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Point);
        DrawManager.drawShapeDrawed();
        shapeRenderer.flush();
        shapeRenderer.end();

    }

    private void debug() {
        debugRenderer.render(world, camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        EntiteManager.entites.forEach((entite) -> {
            shapeRenderer.circle(entite.anchor.x, entite.anchor.y, entite.anchor.radius);
        });
        DrawManager.entites.forEach((entite) -> {
            Rectangle r = entite.getBoundingRectangle();
            shapeRenderer.rect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        });

        LockManager.doors.forEach((t) -> {
            shapeRenderer.circle(t.anchor.x, t.anchor.y, t.anchor.radius);
            shapeRenderer.rect(t.getBoundingRectangle().x, t.getBoundingRectangle().y, t.getBoundingRectangle().width, t.getBoundingRectangle().height);
        });
        int row = 0;

        for (Tile[] t : GridManager.grid) {
            int col = 0;
            for (Tile tile : t) {
                shapeRenderer.rect(col++ * Constantes.TILESIZE, row * Constantes.TILESIZE, Constantes.TILESIZE, Constantes.TILESIZE);
            }
            row++;
        }
        shapeRenderer.setColor(Color.BLUE);
        if (GoTo.pathTest != null) {
            GoTo.pathTest.forEach((t) -> {
                shapeRenderer.rect(t.getX(), t.getY(), t.getWidth(), t.getHeight());
            });
        }
        shapeRenderer.setColor(Color.YELLOW);

        if (GoTo.goalTest != null) {
            shapeRenderer.circle(GoTo.goalTest.x, GoTo.goalTest.y, 20);
        }
        shapeRenderer.flush();
        shapeRenderer.end();
        spriteBatch.begin();
        row = 0;
        for (Tile[] t : GridManager.grid) {
            int col = 0;
            for (Tile tile : t) {
                MessageManager.segoe.draw(spriteBatch, tile.state, col++ * Constantes.TILESIZE + 32, row * Constantes.TILESIZE + 32);
            }
            row++;
        }
        spriteBatch.flush();
        spriteBatch.end();
    }

    private void setLevel() {
        hudShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        hudShapeRenderer.setColor(Color.BLACK);
        hudShapeRenderer.rect(setLevel.rectangle.x, setLevel.rectangle.y, setLevel.rectangle.width, setLevel.rectangle.height);
        hudShapeRenderer.flush();
        hudShapeRenderer.end();
        hudBatch.begin();
        String title = "Level " + setLevel.level;
        Vector2 centerString = MathUtils.centerString(title, MessageManager.LEVELFONT, new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        int number = setLevel.end ? 0 * setLevel.count / 2 : (setLevel.count + 1 - setLevel.delay) * setLevel.count / 2;

        MessageManager.LEVELFONT.setColor(ThemeManager.currentTheme.getFontColor());
        MessageManager.LEVELFONT.draw(hudBatch, title, centerString.x + Gdx.graphics.getWidth() / 2 + number, centerString.y + number);
        MessageManager.LEVELFONT.setColor(Color.WHITE);
        MessageManager.LEVELFONT.draw(hudBatch, title, centerString.x + 5 + Gdx.graphics.getWidth() / 2 - number, centerString.y + 5 - number);
        hudBatch.flush();
        hudBatch.end();
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

    private void drawHudMessage() {
        MessageManager.SHOWG.setColor(Color.SALMON);
        float fontX = Constantes.TILESIZE;
        float fontY = (Gdx.graphics.getHeight() - Constantes.TILESIZE);
        MessageManager.SHOWG.draw(hudBatch, TIME + Integer.toString(MathUtils.transformIpsToSec(StageManager.stopwatch)), fontX, fontY);
        MessageManager.SHOWG.draw(hudBatch, SCORE + Integer.toString(RankManager.point), fontX + 200, fontY);
        MessageManager.SHOWG.draw(hudBatch, LEVEL_SCORE + Integer.toString(RankManager.currentStagePoint), fontX + 450, fontY);
        MessageManager.SHOWG.setColor(Color.WHITE);
        Vector2 center = MathUtils.centerString(ThemeManager.currentTheme.getThemeObjectiveMessage(), MessageManager.SHOWG, questRectangle);
        MessageManager.SHOWG.draw(hudBatch, ThemeManager.currentTheme.getThemeObjectiveMessage(), questRectangle.x + 25, center.y);
        MessageManager.SHOWG.setColor(ThemeManager.currentTheme.getFontColor());
        MessageManager.SHOWG.draw(hudBatch, ThemeManager.currentTheme.getThemeObjectiveMessage(), questRectangle.x + 26, center.y + 1);
    }

    private void drawKeys() {
        int i = 1;
        hudBatch.setColor(Color.GOLD);
        for (Key key : EntiteManager.keys) {
            hudBatch.draw(key.getTexture(), Gdx.graphics.getWidth() - (i++ * (Constantes.TILESIZE)), Constantes.TILESIZE, Constantes.TILESIZE / 2, Constantes.TILESIZE / 2);
        }
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
