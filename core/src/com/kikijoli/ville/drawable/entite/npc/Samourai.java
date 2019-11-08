/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.npc;

import com.kikijoli.ville.business.SamouraiBusiness;
import com.kikijoli.ville.business.AbstractBusiness;
import com.kikijoli.ville.component.IComponent;
import com.kikijoli.ville.component.SwordComponent;
import com.kikijoli.ville.interfaces.IBusiness;
import java.util.Arrays;

/**
 *
 * @author troïmaclure
 */
public final class Samourai extends Ennemy implements IBusiness {

    private static final String GUARD = "sprite/samourai.png";

    public Samourai(float srcX, float srcY) {
        super(GUARD, srcX, srcY);
        this.buisiness = this.getDefault();
        this.point = 1000;
        this.components.addAll(Arrays.asList(new IComponent[]{new SwordComponent(this)}));
        this.currentComponent = this.components.get(0);

    }

    @Override
    public AbstractBusiness getDefault() {
        return new SamouraiBusiness(this);
    }

}
