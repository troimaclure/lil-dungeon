package com.kikijoli.ville.drawable.entite.npc;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.kikijoli.ville.automation.prevent.Prevent;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.interfaces.Ipv;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.RankManager;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.save.EnnemyWrapper;
import com.kikijoli.ville.save.EntiteWrapper;
import com.kikijoli.ville.util.Constantes;
import java.util.ArrayList;

/**
 *
 * @author ajosse
 */
public abstract class Ennemy extends Entite implements Ipv {

    public static void callFriend(Ennemy entite) {
        ArrayList<Entite> seeEntite = EntiteManager.getSeeEntite(entite.sonar);
        seeEntite.stream().filter((e) -> (e instanceof Ennemy) && e != entite).forEachOrdered((e) -> {
            ((Ennemy) e).alarmed();
        });
    }

    public Body body;
    public int pv = 2;
    public ConeLight vision;
    public PointLight sonar;
    public boolean isAlarmed;
    public Vector2 initial;
    public transient Color calm = new Color(0, 0, 0, 0.3f);
    public transient Color alarm = Color.RED;
    public Prevent prevent;

    public Ennemy(String path, float srcX, float srcY, float srcWidth, float srcHeight) {
        super(path, srcX, srcY, srcWidth, srcHeight);
        this.initial = new Vector2(srcX, srcY);
        initVision(srcX, srcY);
        this.speed = getMinSpeed();
    }

    public Ennemy(String path, float srcX, float srcY) {
        this(path, srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
    }

    public void load(EntiteWrapper wrapper) {
        this.setPv(((EnnemyWrapper) wrapper).pv);
        super.load(wrapper);
    }

    public void initVision(float srcX, float srcY) {
        this.vision = new ConeLight(Tmap.getRay(), 150, calm, 1000, srcX + getWidth() / 2, srcY + getHeight() / 2, 0, 75);
        this.vision.setSoftnessLength(Constantes.TILESIZE);
        this.sonar = new PointLight(Tmap.getRay(), 25, Color.CLEAR, 250, srcX, srcY);

    }

    @Override
    protected void calculateAnchors() {
        super.calculateAnchors();
        if (this.vision != null)
            this.vision.setDirection(getRotation() - 90);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        if (this.pv <= 0) {
            if (this.vision.isActive())
                this.vision.setActive(false);
            if (this.sonar.isActive())
                this.sonar.setActive(false);
            return;
        }
        this.vision.setPosition(this.getX() + Constantes.TILESIZE / 2, this.getY() + Constantes.TILESIZE / 2);
        this.sonar.setPosition(this.getX() + Constantes.TILESIZE / 2, this.getY() + Constantes.TILESIZE / 2);
    }

    @Override
    public void dead() {
        super.dead();
        this.vision.remove(true);
        this.sonar.remove(true);
    }

    public boolean see(Entite entite) {
        if (entite == EntiteManager.player && EntiteManager.player.isHidden())
            return false;
        Vector2 center = entite.getCenter();
        return this.vision.contains(center.x, center.y);
    }

    public void calmDown() {
        this.vision.setColor(calm);
        this.isAlarmed = false;
        this.speed = getMinSpeed();
        this.talk("Where is he ? ", Color.ORANGE);
    }

    public void alarmed() {
        this.isAlarmed = true;
        this.vision.setColor(alarm);
        this.speed = getMaxSpeed();
        this.talk("See you !", Color.RED);
        RankManager.caught();
    }

    public int getLookSomewhereElse() {
        int[] rotation = new int[]{0, 90, 180, 270};
        int test = rotation[MathUtils.random(3)];
        if (com.kikijoli.ville.util.MathUtils.getDifference(test, getRotation()) < 90) {
            return getLookSomewhereElse();
        }
        return test;
    }

    public abstract int getMinSpeed();

    public abstract int getMaxSpeed();

    @Override
    public void setPv(int pv) {
        this.pv = pv;
    }

    @Override
    public int getPv() {
        return this.pv;
    }

    @Override
    public EntiteWrapper write(String classDest, String propertyDest) {
        return new EnnemyWrapper(this.getPv(), getX(), getY(), classDest, propertyDest, this.getClass().getCanonicalName());
    }

}
