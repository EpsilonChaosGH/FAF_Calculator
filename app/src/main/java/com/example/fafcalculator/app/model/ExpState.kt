package com.example.fafcalculator.app.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.fafcalculator.R

enum class ExpState(
    @DrawableRes val iconResId: Int,
    @StringRes val costResId: Int,
    @StringRes val titleResId: Int,
    @StringRes val factionColorResId: Int
) {
    COST_250200(R.drawable.paragon, R.string.paragon_cost, R.string.paragon, R.color.green_aeon),
    COST_202500(R.drawable.salvation, R.string.salvation_cost, R.string.salvation, R.color.green_aeon),
    COST_73200(R.drawable.emissary, R.string.emissary_cost, R.string.emissary, R.color.green_aeon),
    COST_45500(R.drawable.czar, R.string.czar_cost, R.string.czar, R.color.green_aeon),
    COST_27500(R.drawable.colossus, R.string.colossus_cost, R.string.colossus, R.color.green_aeon),
    COST_22000(R.drawable.tempest, R.string.tempest_cost, R.string.tempest, R.color.green_aeon),

    COST_224775(R.drawable.mavor, R.string.mavor_cost, R.string.mavor, R.color.blue_uef),
    COST_72000(R.drawable.duke, R.string.duke_cost, R.string.duke, R.color.blue_uef),
    COST_36000(R.drawable.novax, R.string.novax_cost, R.string.novax, R.color.blue_uef),
    COST_28000(R.drawable.fatboy, R.string.fatboy_cost, R.string.fatboy, R.color.blue_uef),
    COST_12000(R.drawable.atlantis, R.string.atlantis_cost, R.string.atlantis, R.color.blue_uef),

    COST_220000(R.drawable.scathis, R.string.scathis_cost, R.string.scathis, R.color.red_cybran),
    COST_69600(R.drawable.disruptor, R.string.disruptor_cost, R.string.disruptor, R.color.red_cybran),
    COST_37500(R.drawable.megalith, R.string.megalith_cost, R.string.megalith, R.color.red_cybran),
    COST_34000(R.drawable.soulripper, R.string.soulripper_cost, R.string.soulripper, R.color.red_cybran),
    COST_20000(R.drawable.monkeylord, R.string.monkeylord_cost, R.string.monkeylord, R.color.red_cybran),

    COST_187650(R.drawable.yolonaoss, R.string.yolonaoss_cost, R.string.yolonaoss, R.color.yellow_seraphim),
    COST_78000(R.drawable.hovatham, R.string.hovatham_cost, R.string.hovatham, R.color.yellow_seraphim),
    COST_48000(R.drawable.ahwassa, R.string.ahwassa_cost, R.string.ahwassa, R.color.yellow_seraphim),
    COST_26500(R.drawable.ythotha, R.string.ythotha_cost, R.string.ythotha, R.color.yellow_seraphim);

    companion object {
        fun findImageByCoast(cost: Int): Int {
            for (exp in ExpState.values()) {
                if (exp.toString().equals("COST_$cost", true))
                    return exp.iconResId
            }
            return R.drawable.mass_icon
        }

        fun getList(): List<ExpEntity> {
            return enumValues<ExpState>().map {
                ExpEntity(
                    it.iconResId,
                    it.costResId,
                    it.titleResId,
                    it.factionColorResId
                )
            }.toList()
        }
    }
}