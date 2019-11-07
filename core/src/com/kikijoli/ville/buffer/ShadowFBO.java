package com.kikijoli.ville.buffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Array;
import com.kikijoli.ville.manager.CameraManager;
import com.kikijoli.ville.manager.StageManager;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.shader.ShadowMap;
import com.kikijoli.ville.shader.ShadowRender;

/**
 *
 * @author ajosse
 */
public class ShadowFBO {

    public static int lightSize = 1600;
    private float upScale = 0.50f;
    TextureRegion shadowMap1D; //1 dimensional shadow map
    TextureRegion occluders;   //occluder map

    FrameBuffer shadowMapFBO;
    FrameBuffer occludersFBO;

    ShadowMap shader = new ShadowMap();
    ShadowRender render = new ShadowRender();

    public Array<Light> lights = new Array<>();

    public ShadowFBO() {
        lights.add(new Light(0, 0, Color.WHITE));
        occludersFBO = new FrameBuffer(Pixmap.Format.RGBA8888, lightSize, lightSize, false);
        occluders = new TextureRegion(occludersFBO.getColorBufferTexture());
        occluders.flip(false, true);
        //our 1D shadow map, lightSize x 1 pixels, no depth
        shadowMapFBO = new FrameBuffer(Pixmap.Format.RGBA8888, lightSize, 1, false);
        Texture shadowMapTex = shadowMapFBO.getColorBufferTexture();

        //use linear filtering and repeat wrap mode when sampling
        shadowMapTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        shadowMapTex.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        //for debugging only; in order to render the 1D shadow map FBO to screen
        shadowMap1D = new TextureRegion(shadowMapTex);
        shadowMap1D.flip(false, true);

    }

    void renderLight(Light o) {
        //build frame buffers

        System.out.println(lightSize);

        float mx = o.x;
        float my = o.y;
        OrthographicCamera cam = CameraManager.camera;
        //STEP 1. render light region to occluder FBO
        //bind the occluder FBO
        occludersFBO.begin();

        //clear the FBO
        Gdx.gl20.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl20.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
        CameraManager.camera.setToOrtho(false, lightSize, lightSize);

        //set the orthographic camera to the size of our FBO
        //translate camera so that light is in the center 
        cam.translate(mx - lightSize / 2f, my - lightSize / 2f);

        //update camera matrices
        cam.update();
        SpriteBatch batch = Tmap.spriteBatch;
        batch.begin();
        //set up our batch for the occluder pass
        batch.setProjectionMatrix(cam.combined);
        batch.setShader(null); //use default shader
        drawSprites();

        //end the batch before unbinding the FBO
        batch.end();

        //unbind the FBO
        occludersFBO.end();

        //STEP 2. build a 1D shadow map from occlude FBO
        //bind shadow map
        shadowMapFBO.begin();

        //clear it
        Gdx.gl20.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl20.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);

        //set our shadow map shader
        batch.setShader(shader);
        batch.begin();
        shader.setUniformf("resolution", lightSize, lightSize);
        shader.setUniformf("upScale", upScale);

        //reset our projection matrix to the FBO size
        cam.setToOrtho(false, shadowMapFBO.getWidth(), shadowMapFBO.getHeight());
        batch.setProjectionMatrix(cam.combined);

        //draw the occluders texture to our 1D shadow map FBO
        batch.draw(occluders.getTexture(), 0, 0, lightSize, shadowMapFBO.getHeight());

        //flush batch
        batch.end();

        //unbind shadow map FBO
        shadowMapFBO.end();

        //STEP 3. render the blurred shadows
        //reset projection matrix to screen
        cam.setToOrtho(false);
        batch.setProjectionMatrix(cam.combined);

        //set the shader which actually draws the light/shadow 
        batch.setShader(render);
        batch.begin();

        render.setUniformf("resolution", lightSize, lightSize);
        render.setUniformf("softShadows", 1f);
        //set color to light
        batch.setColor(o.color);

        float finalSize = lightSize * upScale;
        //draw centered on light position
        batch.draw(shadowMap1D.getTexture(), mx - finalSize / 2f, my - finalSize / 2f, finalSize, finalSize);

        batch.end();

    }

    public void drawSprites() {
        // ... draw any sprites that will cast shadows here ... //
//        StageManager.tiledMapRenderer.render(new int[]{3});
        Tmap.drawSprites();
//        StageManager.tiledMapRenderer.render(new int[]{4});
    }

    public void render() {

        for (int i = 0; i < lights.size; i++) {
            Light o = lights.get(i);
            if (i == lights.size - 1) {
                o.x = Tmap.worldCoordinates.x;
                o.y = Tmap.worldCoordinates.y;
            }
            renderLight(o);
        }
    }

    public class Light {

        float x, y;
        Color color;

        public Light(float x, float y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }
    }
}
