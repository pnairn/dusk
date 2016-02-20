package com.stewsters.dusk.magic

import com.stewsters.dusk.component.ai.Ai
import com.stewsters.dusk.component.ai.ConfusedOpponent
import com.stewsters.dusk.entity.Entity
import com.stewsters.dusk.graphic.MessageLog
import groovy.transform.CompileStatic
import squidpony.squidcolor.SColor

@CompileStatic
public class Confusion implements Spell {

    public static final int CONFUSE_RANGE = 10
    public static final int CONFUSE_NUM_TURNS = 20


    @Override
    boolean cast(Entity caster) {

        Set<Entity> enemies = caster.ai.findAllVisibleEnemies(CONFUSE_RANGE)

//        Entity enemy = user.ai.findClosestVisibleEnemy()
        if (!enemies) {
            MessageLog.send('No enemy is close enough to confused.', SColor.RED, [caster])
            return false
        } else {

            int turns = (int) Math.ceil(CONFUSE_NUM_TURNS / enemies.size())

            enemies.each { Entity enemy ->
                Ai oldID = enemy.ai
                enemy.levelMap.actors.remove(oldID)
                enemy.ai = new ConfusedOpponent(oldAI: oldID, castor: caster, numTurns: turns)
                enemy.ai.owner = enemy
                enemy.levelMap.actors.add(enemy.ai)

                MessageLog.send("${enemy.name} becomes confused.", SColor.LIGHT_BLUE)
            }

            return true
        }

    }

    @Override
    public String getDescription() {
        "Confuses nearby enemies."
    }

}
