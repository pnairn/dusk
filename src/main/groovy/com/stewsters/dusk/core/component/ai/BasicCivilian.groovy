package com.stewsters.dusk.core.component.ai

import com.stewsters.dusk.core.component.Equipment
import com.stewsters.dusk.core.entity.Entity
import com.stewsters.dusk.core.flyweight.Slot
import com.stewsters.util.math.MatUtils
import groovy.transform.CompileStatic

@CompileStatic
class BasicCivilian extends BaseAi implements Ai {

    BasicCivilian() {
        gameTurn = MatUtils.getIntInRange(0, speed)
    }

    @Override
    boolean takeTurn() {

        //nearest opponent
        Entity enemy = findClosestVisibleEnemy()


        if (enemy) {
            //if we have a gun, and they are getting too close, shoot them

            if (entity.distanceTo(enemy) < 2) {
                entity.moveTowardsAndAttack(enemy.x, enemy.y)
            } else {
                if (entity.inventory) {
                    Equipment weapon = entity.inventory.getEquippedInSlot(Slot.PRIMARY_HAND)
                    if (weapon && weapon.entity.item.useFunction != null) {
                        weapon.entity.item.useHeldItem(entity)
                        println "Bang!"
                    } else {
                        entity.moveAway(enemy.x, enemy.y)
                    }
                } else {
                    entity.moveAway(enemy.x, enemy.y)
                }
            }
        } else if (entity.inventory) {

            //if we are standing on an item and we have room, pick it up
            if (entity.inventory.isFull()) {
                entity.randomMovement()
            } else {
                //find nearest visible item
                Entity item = entity.ai.findClosestVisibleItem()

                //if we are standing on it, pickUp
                if (item) {
                    if (item.x == entity.x && item.y == entity.y) {
                        entity.inventory.pickUp(item)
                    } else {
                        entity.moveTowardsAndAttack(item.x, item.y)
                    }
                } else {
                    entity.randomMovement()
                }

            }
        } else if (MatUtils.boolean) {
            entity.randomMovement()
        }

        gameTurn += speed
        return true
    }
}
