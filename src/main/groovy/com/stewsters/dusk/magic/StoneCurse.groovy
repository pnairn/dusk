package com.stewsters.dusk.magic

import com.stewsters.dusk.entity.Entity
import com.stewsters.dusk.flyweight.DamageType
import com.stewsters.dusk.flyweight.Priority
import com.stewsters.dusk.graphic.MessageLog
import groovy.transform.CompileStatic
import squidpony.squidcolor.SColor

@CompileStatic
class StoneCurse implements Spell {

    public static final int STONE_CURSE_RANGE = 10

    @Override
    public boolean cast(Entity caster) {

        Entity enemy = caster.ai.findClosestVisibleEnemy()
        if (!enemy) {
            MessageLog.send('No enemy is close enough to curse.', SColor.RED)
            return false
        } else if (caster.distanceTo(enemy) > STONE_CURSE_RANGE) {
            MessageLog.send("${enemy.name} is too far to curse.", SColor.RED, [caster])
            return false
        } else {
            MessageLog.send("${enemy.name} turns into stone.", SColor.BRIGHT_GREEN, [caster, enemy])

            enemy.levelMap.remove(enemy)

            return new Entity(map: caster.levelMap, x: enemy.x, y: enemy.y,
                    ch: 'S', name: "Statue of ${enemy.name}", color: SColor.WHITE_MOUSE, blocks: true,
                    priority: Priority.ITEM
            )

            return true
        }
    }

    @Override
    public String getDescription() {
        "Turns closest enemy to stone."
    }

}
