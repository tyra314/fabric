/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.api.event.crafting;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

/**
 * Callback when item stack is crafted using a recipe.
 * Only triggered on the logical server.
 * <p>
 * Upon return:
 * - SUCCESS, FAIL cancels further processing
 * - PASS falls back to further processing
 */
public interface RecipeCraftedCallback {

    public static final Event<RecipeCraftedCallback> EVENT = EventFactory.createArrayBacked(RecipeCraftedCallback.class,
            (listeners) -> (player, world, recipe, result_stack, ingredients) -> {
                for (RecipeCraftedCallback event : listeners) {
                    ActionResult result = event.onCrafted(player, world, recipe, result_stack, ingredients);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    ActionResult onCrafted(PlayerEntity player, World world, Recipe recipe, ItemStack result, Inventory ingredients);
}
